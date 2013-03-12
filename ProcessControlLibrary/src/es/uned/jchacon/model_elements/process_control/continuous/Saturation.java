package es.uned.jchacon.model_elements.process_control.continuous;

public class Saturation extends AbstractBlock implements Siso {
	private double uMax;
	private double uMin;
	
	public Saturation() {
		setNumberOfInputs(1);
		setNumberOfOutputs(1);
		setNumberOfStates(0);
		uMin = Double.NEGATIVE_INFINITY; 
		uMax = Double.POSITIVE_INFINITY;
	}

	public Saturation(double uMin, double uMax) {
		this();
		this.uMin = uMin;
		this.uMax = uMax;
	}

	@Override
	public double getOutput(int i) {
		if(i != 0) return 0;

		y[0] = getInput(0);
		if(y[0] < uMin) y[0] = uMin;
		if(y[0] > uMax) y[0] = uMax;

		return y[0];
	}
	
	public void setRange(double uMin, double uMax) {
		this.uMin = uMin;
		this.uMax = uMax;
	}
	
/* 
 * Interface Siso
 */
	public void setInput(Block source, int sourceIndex) {
		setInput(0, source, sourceIndex); 
	}
	
	public void setInput(Siso source) {
		setInput(0, source, 0); 
	}
	
	public double getInput() { 
		return getInput(0); 
	}

	public double getOutput() { 
		return getOutput(0); 
	}
		
/* 
 * Interface Discrete 	
 */	
	
	/* Update the discrete state */
	public void update(double value) {
		y[0] = value;
		if(y[0] < uMin) y[0] = uMin;
		if(y[0] > uMax) y[0] = uMax;
	}

	@Override
	public void updateOutput() { update(getInput(0)); }
}