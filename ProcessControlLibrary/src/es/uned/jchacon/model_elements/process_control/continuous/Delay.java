package es.uned.jchacon.model_elements.process_control.continuous;

public class Delay extends AbstractBlock implements Continuous {
	private double[] dx;

	public Delay() {
		setNumberOfInputs(0);
		setNumberOfOutputs(1);
		setNumberOfStates(1);
		dx = new double[getNumberOfStates()];
	}
	
	public void linkStates(double[] x) { 
		if(x != null) {
			this.x = x; 
			setNumberOfOutputs(x.length);
		}
	}

	@Override
	public double getOutput(int i) { 
		return (i<getNumberOfOutputs()) ? x[i] : 0;
	}

	public void updateOutput() { }	

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

	public double[] getRates() {
		return dx;
	}

}
