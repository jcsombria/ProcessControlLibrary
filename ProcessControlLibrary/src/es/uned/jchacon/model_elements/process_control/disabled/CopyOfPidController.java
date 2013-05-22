package es.uned.jchacon.model_elements.process_control;

import es.uned.jchacon.model_elements.process_control.continuous.Block;
import es.uned.jchacon.model_elements.process_control.continuous.Siso;

public class CopyOfPidController extends CopyOfStateSpaceModel implements Siso {
	private double setpoint;
	private double Kp = 1.0;
	private double Ki = 1.0;
	private double Kd = 1.0;
	private double N = 20;
	
	private double Ts;
	private double lastSample;
	private double u_min;
	private double u_max;
	private boolean antiwindup;
	private double t_last;
	private double Ks;
	
	public String hello() {
		return "Hello World! Here I am, a new PID!";
	}

	public CopyOfPidController() {
		super(new double[]{0, 0, 0, -1/20}, new double[]{1, 21}, new double[]{1.0, 1.0}, new double[]{21.0});
		Ts = 1;
		Kp = 1;
		Ki = 1;
		Kd = 1;
		Ks = 1;
		lastSample = 0;
		antiwindup = false;
		t_last = 0;
	}

	public CopyOfPidController(double Kp, double Ki, double Kd) {
		super(new double[]{0, 0, 0, -20/Kd}, new double[]{1, Kd*20}, new double[]{Ki, Kd}, new double[]{Kp+Kd*20});
		this.Kp = Kp;
		this.Ki = Ki;
		this.Kd = Kd;
		antiwindup = false;
		t_last = 0;
	}

	public void setKp(double Kp) {
		this.Kp = Kp;
		setModel(A, B, C, new double[]{Kp+Kd*N});
	}

	public void setKi(double Ki) {
		this.Ki = Ki;
		setModel(A, B, new double[]{Ki, Kd}, D);
	}

	public void setTi(double Ti) {		
		this.Ki = this.Kp / Ti;
		setModel(A, B, new double[]{Ki, Kd}, D);
	}

	public void setKd(double Kd) {
		this.Kd = Kd;
		setModel(new double[]{0, 0, 0, -N/Kd}, B, new double[]{Ki, Kd}, new double[]{Kp+Kd*N});
	}

	public void setKs(double Ks) {
		this.Ks = Ks;
		setModel(new double[]{0, 0, 0, -N/Kd}, B, new double[]{Ki, Kd}, new double[]{Kp+Kd*N});
	}
	
	public void setN(double N) {
		this.N = N;
		setModel(new double[]{0, 0, 0, -N/Kd}, B, C, new double[]{Kp+Kd*N});
	}
	
	public void setAntiwindup(boolean enabled) { antiwindup = enabled; }
	
	// Updates the setpoint of the controller
	public void setpoint(double sp) {
		this.setpoint = sp;
	}
	/* Sets a range for the control signal */
	public void setRange(double u_min, double u_max) {
		this.u_min = u_min;
		this.u_max = u_max;
	}

	@Override
	public double[] getRates() {
		double[] u = getInputs();

		if(u == null || x == null) return null; 
		
		double[] dx = super.getRates();
		
		if(antiwindup) {
			double v = (y[0] < u_min) ? u_min : (y[0] > u_max) ? u_max : y[0];
			dx[0] += Ks*(v - y[0]);
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
	
//	/* Updates the state of the controller. */
//	@Override
//	public void updateOutput() {
//		super.updateOutput();
//		if(y[0] ) 
//	}
	
//	/* Updates the state of the controller.  */
//	public void update(double y) {
//		double[] x = getStates(), e = new double[]{setpoint - y};
//		lastSample = y;
//
//		setStates(getRates(x, e));
//	}
//	
//	/* Updates the state of the controller. */
//	public void updateOutput() {
//		double[] x = getStates(), e = new double[]{setpoint - y};
//		lastSample = y;
//
///*	    if (onlyP)  u = P;
//	    else if (onlyI) { 
//	       I = aux_I;  u = I;
//	    } else {
//	         if (antiwindup) {
//	              aux_u = P + aux_I ;
////	              if ((aux_u > umax) || (aux_u < umin)) {  xc = 0.0;  aux_I = 0; }
//	         }
//	         I = aux_I;
//
//	         u = P +  I;
//	        // I = I + K*dt/Ti *(setPoint  - y);
//	    }
//	    if (u > umax ) u = umax;
//	    if (u < umin ) u = umin;
//*/
//		
//		double u = getOutput(x, e)[0];
//		double[] x_new = getRates(x, e);
//		if(antiwindup) if(u > u_max || u < u_min)
//			x_new[0] = (u - Kp*e[0]) / Ki;  
//		setStates(x_new);
//	}
////
//	public double output() {
//		return getOutput(getStates(), new double[]{setpoint - lastSample})[0];
//	}

}