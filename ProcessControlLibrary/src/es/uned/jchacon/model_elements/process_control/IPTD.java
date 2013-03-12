package es.uned.jchacon.model_elements.process_control;

import es.uned.jchacon.model_elements.process_control.continuous.StateSpaceModel;

public class IPTD extends StateSpaceModel {
	private double gain;
	
	public IPTD() {
		super(new double[]{0}, new double[]{1}, new double[]{1}, new double[]{0});
		gain = 1;
	}
	
	public IPTD(double gain) {
		super(new double[]{0}, new double[]{gain}, new double[]{1}, new double[]{0});
	}
	
	public void setGain(double gain) {
		this.gain = gain;
		setModel(A, new double[]{this.gain}, C, D);
	}
}
