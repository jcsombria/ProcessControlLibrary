package es.uned.jchacon.model_elements.process_control;

import es.uned.jchacon.model_elements.process_control.continuous.StateSpaceModel;

public class SOPTD extends StateSpaceModel {
	private double gain;
	private double tc1;
	private double tc2;
	
	public SOPTD() {
		super(new double[]{0,1,-1,-2}, new double[]{0,1}, new double[]{1,0}, new double[]{0});
		gain = 1;
		tc1 = 1;
		tc2 = 1;
	}

	public SOPTD(double gain, double tc1, double tc2) {
		super(new double[]{0,1,-1/(tc1*tc2),-(tc1+tc2)/(tc1*tc2)}, new double[]{0,gain/(tc1*tc2)}, new double[]{1,0}, new double[]{0});
		this.gain = gain;
		this.tc1 = tc1;
		this.tc2 = tc2;
	}
	
	public void setGain(double gain) {
		this.gain = gain;
		setModel(A, new double[]{gain/(tc1*tc2)}, C, D);
	}
	
	public void setTimeConstants(double tc1, double tc2) {
		this.tc1 = tc1;
		this.tc2 = tc2;
		setModel(new double[]{0,1,-1/(tc1*tc2),-(tc1+tc2)/(tc1*tc2)}, new double[]{gain/(tc1*tc2)}, C, D);
	}

}
