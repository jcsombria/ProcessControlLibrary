package es.uned.jchacon.model_elements.process_control.continuous;


public class DiscretePidController extends AbstractBlock implements Discrete {
	private double kp;
	private double ki;
	private double kd;
	private double ts;
	private double uMax;
	private double uMin;
	private double u;
	private boolean tracking;
	private boolean antiwindup;
	private double ei;
	private double e_prev;
	private double ed;
	private double ks;
	private double n;
	
	public String sayHello() {
		return "Hello World! Here I am, a new PID!";
	}

	public DiscretePidController() {
		setNumberOfInputs(3);
		setNumberOfOutputs(1);
		setNumberOfStates(0);
		antiwindup = false;
		setParameters(1, 1, 1, 1, 1, 1);
		this.uMin = 0;
		this.uMax = 1;
	}

	public DiscretePidController(double Kp, double Ki, double Kd) {
		setNumberOfInputs(3);
		setNumberOfOutputs(1);
		setNumberOfStates(0);		
		setParameters(ts, kp, ki, kd, 2, 1);
		antiwindup = false;
		this.uMin = 0;		
		this.uMax = 1;
	}

	public DiscretePidController(double kp, double ki, double kd, double n, boolean antiwindup, double ks, double uMin, double uMax) {
		setNumberOfInputs(1);
		setNumberOfOutputs(1);
		setNumberOfStates(0);
		setParameters(ts, kp, ki, kd, n, ks);
		this.antiwindup = antiwindup;
		this.uMin = uMin;		
		this.uMax = uMax;
	}

	private void setParameters(double ts, double kp, double ki, double kd, double n, double ks) {
		this.ts = (ts > 0) ? ts : 1.0;
		this.kp = (kp > 0) ? kp : 0;
		this.ki = (ki > 0) ? ki : 0;
		this.kd = (kd > 0) ? kd : 0;
		this.ks = (ks > 0) ? ks : 0;
		this.n = (n > 0) ? n : 0;		
//		if(kd > 0) {
//			setModel(new double[][]{{0, 0}, {0, -n/kd}}, new double[][]{{1}, {kd*n}}, new double[][]{{ki, kd}}, new double[][]{{kp+kd*n}});
//		} else {
//			setModel(new double[][]{{0, 0}, {0, 0}}, new double[][]{{1}, {0}}, new double[][]{{ki, 0}}, new double[][]{{kp}});
//		}
	}
	
	public void setKp(double kp) {
		setParameters(ts, kp, ki, kd, n, ks);
	}

	public void setKi(double ki) {
		setParameters(ts, kp, ki, kd, n, ks);
	}

	public void setTi(double ti) {		
		setParameters(ts, kp, 1/ti, kd, n, ks);
	}

	public void setKd(double kd) {
		setParameters(ts, kp, ki, kd, n, ks);
	}
	
	public void setTs(double ts) {
		setParameters(ts, kp, ki, kd, n, ks);
	}
	
	public void setKs(double ks) {
		setParameters(ts, kp, ki, kd, n, ks);
	}
	
//	// Updates the setpoint of the controller
//	public void setpoint(double sp) {
//		_setpoint = sp;
//	}
	
	public void setAntiwindup(boolean enabled) {
		this.antiwindup = enabled;
	}
	
	/* Sets a range for the control signal */
	public void setRange(double uMin, double uMax) {
		this.uMin = uMin;
		this.uMax = uMax;
	}
	
	public void setTracking(boolean enabled) {
		this.tracking = enabled;
	}
	
//	
//	/* Updates the state of the controller.  */
//	public void update(double y) {
//		double[] x = getStates(), e = new double[]{_setpoint - y};
//		_lastSample = y;
//
//		setStates(getRates(x, e));
//	}
	
	/* Updates the state of the controller. */
	public void update() {
		double output = getInput(0), setpoint = getInput(1), manual = getInput(2);
		double e = setpoint - output;
		double es = tracking ? manual - u : 0; 
		
		ei += 0.5*(e_prev + e)*ts + ks*es*ts;
		
		u = kp*e + ki*ei; // + kd*ed;
		if(antiwindup) {
			double v = (u < uMin) ? uMin : (u > uMax) ? uMax : u;
			if(u != v) {
				ei = (v - kp*e)/ki; // - kd*ed)/ki;
				u = v;
			}
		}

		e_prev = e;
		y[0] = u;
	}


	public void update(double x) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateOutput() {
		// TODO Auto-generated method stub
		
	}
}