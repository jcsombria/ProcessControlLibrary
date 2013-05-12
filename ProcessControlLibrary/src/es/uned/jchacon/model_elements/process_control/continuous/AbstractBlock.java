package es.uned.jchacon.model_elements.process_control.continuous;

public abstract class AbstractBlock implements Block {	
	protected double[] x;
	protected double[] u;
	protected double[] y;
	
	protected int ninputs = 0;
	protected int noutputs = 0;
	protected int nstates = 0;
	
	protected class Link {
		Block source;
		int index;		
		
		public Link() { source = null; index = -1;}
		
		public Link(Block source, int index){
			this.source = source;
			this.index = index;			
		}		
		
		public double getValue() { 
			if(source == null) return 0;
			else return source.getOutput(index);
		}
	};
	
	protected Link[] links = null;
	
	/**
	 * @param The number of inputs
	 */	
	protected void setNumberOfInputs(int size) {
		if(size <= 0) return;

		int end = (size > ninputs) ? ninputs : size;
		Link[] oldlinks = links;
		links = new Link[size];		
		
		if(oldlinks != null) System.arraycopy(oldlinks, 0, links, 0, end);			
		for(int i = end; i < size; i++) links[i] = new Link();
		
		ninputs = size;
	}

	/**
	 * @param The number of outputs
	 */	
	protected void setNumberOfOutputs(int size) { 
		if(size <= 0) return;
		
		int end = (size > noutputs) ? noutputs : size;
		double[] yPrev = y;
		y = new double[size];		
		
		if(yPrev != null) System.arraycopy(yPrev, 0, y, 0, end);
		for(int i = noutputs; i < size; i++) y[i] = 0;
		
		noutputs = size;
	}

	/**
	 * @param The number of states
	 */	
	protected void setNumberOfStates(int size) { 
		if(size > 0) {
			if(x == null || x.length != size){
				x = new double[size]; 
			}
			nstates = size; 
		} 
	}
	
	/**
	 * @return The number of states
	 */	
	public int getNumberOfStates() { return nstates; }

	/**
	 * @return The number of inputs
	 */	
	public int getNumberOfInputs() { return ninputs; }

	/**
	 * @return The number of outputs
	 */	
	public int getNumberOfOutputs() { return noutputs; }
	
	/**
	 * Links the inputs to an external vector 
	 * 
	 * @param sourceVector The vector which contains the variable to be linked
	 * @param sourceIndex The index of the variable to be linked
	 */	
	public void setInputs(double[] u) { this.u = u; }	
	
	/**
	 * Links the inputs to an external vector 
	 * 
	 * @param sourceVector The vector which contains the variable to be linked
	 * @param sourceIndex The index of the variable to be linked
	 */	
	public void setOutputs(double[] y) { this.y = y; }	

	/**
	 * Links sourceVector[sourceIndex] to the <i>inputIndex</i> input 
	 * 
	 * @param sourceVector The vector which contains the variable to be linked
	 * @param sourceIndex The index of the variable to be linked
	 */	
	public void setInput(int inputIndex, Block source, int sourceIndex) {	
		if(source == null 
		|| sourceIndex >= source.getNumberOfOutputs() 
		|| inputIndex >= getNumberOfInputs()) return;

		links[inputIndex] = new Link(source, sourceIndex);
	}

	/**
	 * @return The values of the inputs
	 */	
	public double[] getInputs() {
		double[] inputs = new double[ninputs];
		
		for(int i=0; i<ninputs; i++) inputs[i] = links[i].getValue();

		return inputs;
	}

	/**
	 * @param i The index of the input 
	 * @return The input <i>i</i>
	 */	
	public double getInput(int i) {
		if(i < 0 || i >= ninputs) return 0;
		
		return links[i].getValue();
	}

	/**
	 * @return The values of the outputs
	 */	
	public double[] getOutputs() { return y; }
	
	/**
	 * @return The values of the <i>i</i> output
	 */	
	public double getOutput(int i) {
		if(i < 0 || i >= noutputs) return 0;
		
		return y[i];
	}
	
	/**
	 * Update the values of the outputs
	 */	
//	public abstract void updateOutput();

}
