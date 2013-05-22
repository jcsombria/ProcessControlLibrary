package es.uned.jchacon.model_elements.process_control.continuous;

/**
 * 
 * @author jcsombria
 *
 *	DiscretePidController
 *
 *	Class to implement a discrete PID controller.
 *               ________
 *	setpoint ---|        |
 *	output   ---| PID(z) |---- control action
 *	tracking ---|________|
 */

public class DiscretePidController extends DiscreteStateSpaceModel implements Discrete {
//public class DiscretePidController extends AbstractBlock implements Discrete {
	private double kp;
	private double ki;
	private double kd;
	private double ks;
	private double kt;
	private double ts;
	private double uMax;
	private double uMin;
	private double u;
	private double[] antiwindupInput = new double[]{0};
	private boolean tracking;
	private boolean antiwindup;
	
	/**
	 * Create a new DiscreteFilteredPidController 
	 */	
	public DiscretePidController() {
		antiwindup = false;
		setParameters(1, 1, 1, 10, 10, 1);
		this.uMin = 0;
		this.uMax = 1;
		linkInputAntiwindup(antiwindupInput, 0);
	}

	/**
	 * Create a new DiscreteFilteredPidController 
	 */	
	public DiscretePidController(double Kp, double Ki, double Kd) {
		setParameters(kp, ki, kd, 10, 10, 1);
		antiwindup = false;
		this.uMin = 0;		
		this.uMax = 1;
		linkInputAntiwindup(antiwindupInput, 0);
	}

	/**
	 * Create a new DiscreteFilteredPidController 
	 */	
	public DiscretePidController(double kp, double ki, double kd, double ks,
			double kt, double ts, boolean antiwindup, double uMin, double uMax) {
		setParameters(kp, ki, kd, ks, kt, ts);
		this.antiwindup = antiwindup;
		this.uMin = uMin;		
		this.uMax = uMax;
		linkInputAntiwindup(antiwindupInput, 0);
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
	public void setParameters(double kp, double ki, double kd, double ks, double kt, double ts) {
		this.ts = (ts > 0) ? ts : 1.0;
		this.kp = (kp > 0) ? kp : 0;
		this.ki = (ki > 0) ? ki : 0;
		this.kd = (kd > 0) ? kd : 0;
		this.kt = (ks > 0) ? ks : 0;
		this.ks = (ks > 0) ? ks : 0;
		double alpha = (tracking ? 1 + ki*kt*ts : 1), 
			k1 = (kp+ki*ts+kd/ts)/alpha, 
			k2 = -(kp+2*kd/ts)/alpha,
			k3 =  (kd/ts)/alpha, 
			k4 =  (1/ts)/alpha, 
			k5 = (tracking ? ki*kt*ts/alpha : 0),
			k6 = ((!tracking && antiwindup) ? ks*ts/alpha : 0);
		setModel(new double[][]{{k4-k6,k2,k3},{0,0,0},{0,1,0}},
				 new double[][]{{k1,-k1,k5,k6},{1,-1,0,0},{0,0,0,0}},
				 new double[][]{{1, 0, 0}},
				 new double[][]{{0,0,0,0}}, ts);
	}
	
	/**
	 * Set the controller parameters 
	 * 
	 * @param kp The proportional gain
	 */	
	public void setKp(double kp) {
		setParameters(kp, ki, kd, ks, kt, ts);
	}

	/**
	 * Set the integral gain (ki=kp/ti)
	 * 
	 * @param td The integral gain
	 */	
	public void setKi(double ki) {
		setParameters(kp, ki, kd, ks, kt, ts);
	}

	/**
	 * Set the integral time 
	 * 
	 * @param ti The integral time
	 */	
	public void setTi(double ti) {
		setParameters(ts, kp, kp/ti, kd, kt, ks);
	}

	/**
	 * Set the derivative gain (kd=kp*td) 
	 * 
	 * @param td The derivative time
	 */	
	public void setKd(double kd) {
		setParameters(kp, ki, kd, ks, kt, ts);
	}
	
	/**
	 * Set the derivative time 
	 * 
	 * @param td The derivative time
	 */	
	public void setTd(double td) {
		setParameters(kp, ki, kp*td, ks, kt, ts);
	}
	
	/**
	 * Set the sampling period 
	 * 
	 * @param ts The sampling period
	 */	
	public void setTs(double ts) {
		setParameters(kp, ki, kd, ks, kt, ts);
	}
	
	/**
	 * Set the sampling period 
	 * 
	 * @param ts The sampling period
	 */	
	public void setKs(double ks) {
		setParameters(kp, ki, kd, ks, kt, ts);
	}
	
	/**
	 * Set the sampling period 
	 * 
	 * @param ts The sampling period
	 */	
	public void setKt(double kt) {
		setParameters(kp, ki, kd, ks, kt, ts);
	}

	/**
	 * Enable or disable the antiwindup mechanism 
	 * 
	 * @param enabled <i>true</i> or <i>false</i>
	 */	
	public void setAntiwindup(boolean enabled) {
		this.antiwindup = enabled;
		setParameters(kp, ki, kd, ks, kt, ts);
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
	 * Enable the input tracking mode 
	 * 
	 * @param enabled <i>true</i> or <i>false</i>
	 */	
	public void setTracking(boolean enabled) {
		this.tracking = enabled;
		setParameters(kp, ki, kd, ks, kt, ts);
	}
	
	/**
	 * Update the state of the controller 
	 */	
	public void update() {
		super.update();
		u = getOutput();
//		if(antiwindup) {
			antiwindupInput[0] = (u < uMin) ? uMin : (u > uMax) ? uMax : u;
//		}
/*		double setpoint = getInput(0), output = getInput(1), manual = getInput(2);
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
		y[0] = u;*/
	}

	/**
	 * Update the state of the controller 
	 */	
	public void update(double x) {
		
	}

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

	/**
	 * Link the tracking input to the output <i>index</i> of the <i>Block</i> source 
	 * 
	 * @param source The <i>Block</i> which contains the output to be linked
	 * @param index The index of the output to be linked
	 */	
	public void linkInputTracking(Block source, int index) {
		setInput(2, source, index);
	}
	
	/**
	 * Link the tracking input to the output <i>index</i> of the <i>double[]</i> source  
	 * 
	 * @param source The vector which contains the variable to be linked
	 * @param index The index of the variable to be linked
	 */	
	public void linkInputTracking(double[] source, int index) {
		setInput(2, source, index);
	}

	/**
	 * Link the antiwindup input to the output <i>index</i> of the <i>Block</i> source 
	 * 
	 * @param source The <i>Block</i> which contains the output to be linked
	 * @param index The index of the output to be linked
	 */	
	public void linkInputAntiwindup(Block source, int index) {
		if(source == null) {			
			setInput(3, antiwindupInput, index);
			return;
		}
		setInput(3, source, index);
	}

	/**
	 * Link the antiwindup input to the output <i>index</i> of the <i>double[]</i> source  
	 * 
	 * @param source The vector which contains the variable to be linked
	 * @param index The index of the variable to be linked
	 */	
	public void linkInputAntiwindup(double[] source, int index) {
		if(source == null) {			
			setInput(3, antiwindupInput, index);
			return;
		}
		setInput(3, source, index);
	}
}