package es.uned.jchacon.model_elements.process_control.continuous;

/**
 * @author jcsombria
 *
 *	DiscreteFilteredPidController
 *
 *	Class to implement a discrete PID controller with a first-order filter.
 *  Mode 1:
 *               ________
 *	            |        |
 *	error    ---| PID(z) |---- control action
 *	            |________|
 *  
 *  Mode 2:
 *               ________
 *	setpoint ---|        |
 *	            | PID(z) |---- control action
 *	output   ---|________|
 */

public class DiscreteFilteredPidController extends DiscreteStateSpaceModel {
	private double kp;
	private double ti;
	private double td;
	private double ts;
	private double uMax;
	private double uMin;
	private double u;
	private boolean tracking;
	private boolean antiwindup;
//	private double ei;
//	private double e_prev;
//	private double ed;
	private double tf;
	
	/**
	 * Create a new DiscreteFilteredPidController 
	 */	
	public DiscreteFilteredPidController() {
		antiwindup = false;
		setParameters(1, 1, 1, 1, 1);
		this.uMin = 0;
		this.uMax = 1;
	}

	/**
	 * Create a new DiscreteFilteredPidController 
	 * 
	 * @param kp The proportional gain
	 * @param ti The integral time
	 * @param td The derivative time
	 * @param tf The time constant of the filter
	 * @param ts The sampling period
	 */	
	public DiscreteFilteredPidController(double kp, double ti, double td, double tf) {
		setParameters(kp, ti, td, tf, ts);
		antiwindup = false;
		this.uMin = 0;		
		this.uMax = 1;
	}

	/**
	 * Create a new DiscreteFilteredPidController 
	 * 
	 * @param kp The proportional gain
	 * @param ti The integral time
	 * @param td The derivative time
	 * @param tf The time constant of the filter
	 * @param ts The sampling period
	 */	
	public DiscreteFilteredPidController(double kp, double ti, double td, double tf, double ts) {
		setParameters(kp, ti, td, tf, ts);
		this.antiwindup = false;
		this.uMin = 0;
		this.uMax = 1;
	}

	/**
	 * Create a new DiscreteFilteredPidController 
	 * 
	 * @param kp The proportional gain
	 * @param ti The integral time
	 * @param td The derivative time
	 * @param tf The time constant of the filter
	 * @param ts The sampling period
	 */	
	public DiscreteFilteredPidController(double kp, double ti, double td, double tf, boolean antiwindup, double uMin, double uMax) {
		setParameters(kp, ti, td, tf, ts);
		this.antiwindup = antiwindup;
		this.uMin = uMin;		
		this.uMax = uMax;
	}

	/**
	 * Set the controller parameters 
	 * 
	 * @param kp The proportional gain
	 * @param ti The integral time
	 * @param td The derivative time
	 * @param tf The time constant of the filter
	 * @param ts The sampling period
	 */	
	public void setParameters(double kp, double ti, double td, double tf, double ts) {
		this.ts = (ts > 0) ? ts : 1.0;
		this.kp = (kp > 0) ? kp : 0;
		this.ti = (ti > 0) ? ti : 0;
		this.td = (td > 0) ? td : 0;
		this.tf = (tf > 0) ? tf : 0;		
		double alpha = (1+tf/ts), 
			   k1 =  (kp/alpha)*(1+ts/ti+td/ts),
			   k2 = -(kp/alpha)*(1+2*td/ts),
			   k3 =  (kp/alpha)*(td/ts),
			   k4 =   (1/alpha)*tf/ts;
		setModel(new double[][]{{k4,k2,k3},{0,0,0},{0,1,0}},
				 new double[][]{{k1,-k1},{1,-1},{0,0}},
				 new double[][]{{1, 0, 0}},
				 new double[][]{{0,0}}, ts);
	}
	
	/**
	 * Set the controller parameters 
	 * 
	 * @param kp The proportional gain
	 */	
	public void setKp(double kp) {
		setParameters(ts, kp, ti, td, tf);
	}

	/**
	 * Set the integral gain (ki=kp/ti)
	 * 
	 * @param td The integral gain
	 */	
	public void setKi(double ki) {
		if(ki <= 0) return;
		setParameters(ts, kp, kp/ki, td, tf);
	}

	/**
	 * Set the integral time 
	 * 
	 * @param ti The integral time
	 */	
	public void setTi(double ti) {		
		setParameters(ts, kp, ti, td, tf);
	}

	/**
	 * Set the derivative gain (kd=kp*td) 
	 * 
	 * @param td The derivative time
	 */	
	public void setKd(double kd) {
		if(kp == 0) return;
		setParameters(ts, kp, ti, kd/kp, tf);
	}

	/**
	 * Set the derivative time 
	 * 
	 * @param td The derivative time
	 */	
	public void setTd(double td) {
		setParameters(ts, kp, ti, td, tf);
	}

	/**
	 * Set the sampling period 
	 * 
	 * @param ts The sampling period
	 */	
	public void setTs(double ts) {
		setParameters(ts, kp, ti, td, tf);
	}
	
	/**
	 * Set the time constant of the filter 
	 * 
	 * @param tf The time constant of the filter
	 */	
	public void setTf(double tf) {
		setParameters(ts, kp, ti, td, tf);
	}
	
	/**
	 * Set a valid range for the control signal 
	 * 
	 * @param uMin The minimum value of the control action
	 * @param uMax The maximum value of the control action
	 */	
	public void setRange(double uMin, double uMax) {
		this.uMin = uMin;
		this.uMax = uMax;
	}
	
	/**
	 * Enable or disable the antiwindup mechanism 
	 * 
	 * @param enabled <i>true</i> or <i>false</i>
	 */	
	public void setAntiwindup(boolean enabled) {
		this.antiwindup = enabled;
	}
	
	/**
	 * Enable the input tracking mode 
	 * 
	 * @param enabled <i>true</i> or <i>false</i>
	 */	
	public void setTracking(boolean enabled) {
		this.tracking = enabled;
	}
	
	/**
	 * Update the state of the controller 
	 */	
	public void update() {
		super.update();
		u = u + super.getOutput(0);
	}

	/* Updates the state of the controller. */
/*	public void update() {
		double setpoint = getInput(0), output = getInput(1), manual = getInput(2);
		double e = setpoint - output;
		double es = tracking ? manual - u : 0; 
		
		ei += 0.5*(e_prev + e)*ts + tf*es*ts;
		
		u = kp*e + ti*ei; // + kd*ed;
		if(antiwindup) {
			double v = (u < uMin) ? uMin : (u > uMax) ? uMax : u;
			if(u != v) {
				ei = (v - kp*e)/ti; // - kd*ed)/ki;
				u = v;
			}
		}

		e_prev = e;
		y[0] = u;
	}*/


	/**
	 * Link the setpoint to the output <i>index</i> of the <i>Block</i> source 
	 * 
	 * @param source The <i>Block</i> which contains the output to be linked
	 * @param index The index of the output to be linked
	 */	
	public void linkInputSetpoint(Block source, int index) {
		setInput(0, source, index);
	}
	
	/**
	 * Link the setpoint input to the output <i>index</i> of the <i>double[]</i> source  
	 * 
	 * @param source The vector which contains the variable to be linked
	 * @param index The index of the variable to be linked
	 */	
	public void linkInputSetpoint(double[] source, int index) {
		setInput(0, source, index);
	}
	
	/**
	 * Link the process variable input to the output <i>index</i> of the <i>Block</i> source 
	 * 
	 * @param source The <i>Block</i> which contains the output to be linked
	 * @param index The index of the output to be linked
	 */	
	public void linkInputProcess(Block source, int index) {
		setInput(1, source, index);
	}
	
	/**
	 * Link the process variable input to the output <i>index</i> of the <i>double[]</i> source  
	 * 
	 * @param source The vector which contains the variable to be linked
	 * @param index The index of the variable to be linked
	 */	
	public void linkInputProcess(double[] source, int index) {
		setInput(1, source, index);
	}

//	/**
//	 * Link the tracking input to the output <i>index</i> of the <i>Block</i> source 
//	 * 
//	 * @param source The <i>Block</i> which contains the output to be linked
//	 * @param index The index of the output to be linked
//	 */	
//	public void linkInputTracking(Block source, int index) {
//		setInput(2, source, index);
//	}
//	
//	/**
//	 * Link the tracking input to the output <i>index</i> of the <i>double[]</i> source  
//	 * 
//	 * @param source The vector which contains the variable to be linked
//	 * @param index The index of the variable to be linked
//	 */	
//	public void linkInputTracking(double[] source, int index) {
//		setInput(2, source, index);
//	}

	public void update(double x) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Get the output of the controller 
	 * 
	 * @return double the output of the controller
	 */	
	public double getOutput() {
		return u;
	}
}