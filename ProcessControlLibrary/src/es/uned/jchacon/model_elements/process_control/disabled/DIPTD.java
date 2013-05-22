package es.uned.jchacon.model_elements.process_control.disabled;

import es.uned.jchacon.model_elements.process_control.continuous.StateSpaceModel;

public class DIPTD extends StateSpaceModel {
	private double gain;
	
	public DIPTD() {
		super(new double[]{0,1,0,0}, new double[]{0,1}, new double[]{1,0}, new double[]{0});
	}
	
	public DIPTD(double gain) {
		super(new double[]{0,1,0,0}, new double[]{0,gain}, new double[]{1,0}, new double[]{0});
	}
	
	public void setGain(double gain) {
		this.gain = gain;
		setModel(A, new double[]{0,this.gain}, C, D);
	}
}
