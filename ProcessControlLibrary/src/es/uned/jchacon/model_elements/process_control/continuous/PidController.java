package es.uned.jchacon.model_elements.process_control.continuous;

public class PidController extends StateSpaceModel implements Siso {
	private double setpoint;
	private double kp = 1.0;
	private double ki = 1.0;
	private double kd = 1.0;
	private double n = 20;
	
	private double ts;
	private double lastSample;
	private double uMin;
	private double uMax;
	private boolean antiwindup;
	private double t_last;
	private double ks;
	
	public String hello() {
		return "Hello World! Here I am, a new PID!";
	}

	public PidController() {
//		super(new double[][]{{0, 0}, {0, -1/20}}, new double[][]{{1}, {21}}, new double[][]{{1.0, 1.0}}, new double[][]{{21.0}});
		ts = 1;
		kp = 1;
		ki = 1;
		kd = 1;
		ks = 1;
		lastSample = 0;
		antiwindup = false;
		t_last = 0;
		setParameters(1, 1, 1, 2, 1);
		this.uMin = 0;
		this.uMax = 1;
	}

	public PidController(double Kp, double Ki, double Kd) {
//		super(new double[][]{{0, 0}, {0, -20/Kd}}, new double[][]{{1}, {Kd*20}}, new double[][]{{Ki, Kd}}, new double[][]{{Kp+Kd*20}});
//		this.kp = Kp;
//		this.ki = Ki;
//		this.kd = Kd;
		setParameters(kp, ki, kd, 2, 1);
		antiwindup = false;
		this.uMin = 0;		
		this.uMax = 1;
		t_last = 0;
	}

	public PidController(double kp, double ki, double kd, double n, boolean antiwindup, double ks, double uMin, double uMax) {
		setParameters(kp, ki, kd, n, ks);
//		super(new double[][]{{0, 0}, {0, -n/kd}}, new double[][]{{1}, {kd*n}}, new double[][]{{ki, kd}}, new double[][]{{kp+kd*n}});
//		this.kp = kp;
//		this.ki = ki;
//		this.kd = kd;
		this.antiwindup = antiwindup;
		this.uMin = uMin;		
		this.uMax = uMax;
//		setKs(ks);
		t_last = 0;
	}

	private void setParameters(double kp, double ki, double kd, double n, double ks) {
		this.kp = (kp > 0) ? kp : 0;
		this.ki = (ki > 0) ? ki : 0;
		this.kd = (kd > 0) ? kd : 0;
		this.ks = (ks > 0) ? ks : 0;
		this.n = (n > 0) ? n : 0;		
		if(kd > 0) {
			setModel(new double[][]{{0, 0}, {0, -n/kd}}, new double[][]{{1}, {kd*n}}, new double[][]{{ki, kd}}, new double[][]{{kp+kd*n}});
		} else {
			setModel(new double[][]{{0, 0}, {0, 0}}, new double[][]{{1}, {0}}, new double[][]{{ki, 0}}, new double[][]{{kp}});
		}
	}
	
	public void setKp(double kp) {
		setParameters(kp, ki, kd, n, ks);
//		if(kp >= 0)
//		this.kp = Kp;
//		setModel(A, B, C, new double[][]{{kp+kd*n}});
	}

	public void setKi(double ki) {
		setParameters(kp, ki, kd, n, ks);
//		this.ki = Ki;
//		setModel(A, B, new double[][]{{Ki, kd}}, D);
	}

	public void setTi(double ti) {		
		setParameters(kp, 1/ti, kd, n, ks);
//		this.ki = this.kp / Ti;
//		setModel(A, B, new double[][]{{ki, kd}}, D);
	}

	public void setKd(double kd) {
		setParameters(kp, ki, kd, n, ks);
//		if(kd > 0) {
//			this.kd = kd;
//		} else {
//			this.kd = n;
//			this.n = 0;
//		}
//		setModel(new double[][]{{0, 0}, {0, -n/kd}}, B, new double[][]{{ki, kd}}, new double[][]{{kp+kd*n}});		
	}

	public void setKs(double ks) {
		setParameters(kp, ki, kd, n, ks);
//		this.ks = Ks;
//		setModel(new double[][]{{0, 0}, {0, -n/kd}}, B, new double[][]{{ki, kd}}, new double[][]{{kp+kd*n}});
	}
	
	public void setN(double n) {
		setParameters(kp, ki, kd, n, ks);
//		this.n = n;
//		setModel(new double[][]{{0, 0}, {0, -n/kd}}, B, C, new double[][]{{kp+kd*n}});
	}
	
	public void setAntiwindup(boolean enabled) {
		antiwindup = enabled;
	}
	
	public void setUMax(double uMax) {
		this.uMax = uMax;
	}

	public void setUMin(double uMin) {
		this.uMin = uMin;
	}
	
	// Updates the setpoint of the controller
	public void setpoint(double sp) {
		this.setpoint = sp;
	}
	
	/* Sets a range for the control signal */
	public void setRange(double uMin, double uMax) {
		this.uMin = uMin;
		this.uMax = uMax;
	}

	@Override
	public double[] getRates() {
		double[] u = getInputs();

		if(u == null || x == null) return null; 
		
		double[] dx = super.getRates();
		
		if(antiwindup) {
			double v = (y[0] < uMin) ? uMin : (y[0] > uMax) ? uMax : y[0];
			dx[0] += ks*(v - y[0]);
//			if((y[0] > u_max && dx[0] > 0) || (y[0] < u_min && dx[0] < 0)) dx[0] = 0;
		}
		
		return dx;
	}
	
/* 
 * Interface Siso
 */
	public double getInput() { return getInput(0); }

	public double getOutput() { return getOutput(0); }

	public void setInput(Block source, int sourceIndex) { setInput(0, source, sourceIndex); }

	public void setInput(Siso source) { setInput(0, source, 0); }

}