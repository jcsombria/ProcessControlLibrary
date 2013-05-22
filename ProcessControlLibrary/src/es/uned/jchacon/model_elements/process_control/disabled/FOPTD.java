package es.uned.jchacon.model_elements.process_control.disabled;

import es.uned.jchacon.model_elements.process_control.continuous.StateSpaceModel;

public class FOPTD extends StateSpaceModel {
	private double gain;
	private double tc;
	
	public FOPTD() {
		super(new double[]{-1}, new double[]{1}, new double[]{1}, new double[]{0});
	}

	public FOPTD(double gain, double tc) {
		super(new double[]{-1/tc}, new double[]{gain/tc}, new double[]{1}, new double[]{0});
		this.gain = gain;
		this.tc = tc;
	}
	
	public void setGain(double gain) {
		this.gain = gain;
		setModel(A, new double[]{this.gain/tc}, C, D);
	}
	
	public void setTimeConstant(double tc) {
		this.tc = tc;
		setModel(new double[]{-1/tc}, B, C, D);
	}

}
