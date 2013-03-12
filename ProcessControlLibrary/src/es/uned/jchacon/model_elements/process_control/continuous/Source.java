package es.uned.jchacon.model_elements.process_control.continuous;

public class Source extends AbstractBlock {
//	private double[] x = new double[]{0};

//	public int getNumberOfOutputs() { return x.length; }
	
//	public double[] getOutputs() { return x; }	

//	public double getOutput(int i) { 
//		if(i < 0 || i >= x.length) return 0;
//		return x[i];
//	}

	public Source() {
		setNumberOfInputs(0);
		setNumberOfOutputs(1);
		setNumberOfStates(1);
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
	public void setInput(Block source, int sourceIndex) { setInput(0, source, sourceIndex); }
	
	public void setInput(Siso source) { setInput(0, source, 0); }
	
	public double getInput() { return getInput(0); }
	
	public double getOutput() { return getOutput(0); }

}
