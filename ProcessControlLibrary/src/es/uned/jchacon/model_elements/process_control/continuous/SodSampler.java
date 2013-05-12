package es.uned.jchacon.model_elements.process_control.continuous;


public class SodSampler extends AbstractBlock implements Discrete, Siso {
	private double delta;	
	private double alpha;
	
	public SodSampler() {
		setNumberOfInputs(1);
		setNumberOfOutputs(1);
		setNumberOfStates(0);
		delta = 0.1;
		alpha = 0.0;
	}

	public SodSampler(double delta, double alpha) {
		this();
		this.delta = delta;
		this.alpha = alpha;
	}

//	public double getOutput() {	return y[0]; }
	
	public double getTolerance() { return 1e-5; }
	
	public void init(double value) { y[0] = (Math.floor(value/delta)+alpha)*delta; }
	
	public void setDelta(double delta) { this.delta = delta; }
	
	public void setAlpha(double alpha) { this.alpha = alpha; }
	
/* 
 * Interface Discrete 	
 */	
	
	/* Returns the number of states */
//	public int getNumberOfStates() { return 0; }

	/* Returns the number of inputs */
//	public int getNumberOfInputs() { return 1; }

	/* Returns the number of outputs */
//	public int getNumberOfOutputs() { return 0; }
	
	/* Detect an event */
	public double evaluate(double value) { return delta - Math.abs(value - y[0]) + 1e-5; }
	
	/* Detect an event */
	public double evaluate() { return evaluate(getInput(0)); }
	
	/* Update the discrete state */
	public void update(double value) {
		int levels = (int)Math.floor(Math.abs(value - y[0])/delta);
		y[0] += Math.signum(value - y[0])*levels*delta;
	}

//	@Override
	public void updateOutput() { update(getInput(0)); }

	@Override
	public void setInput(int inputIndex, Block source, int sourceIndex) {
		super.setInput(inputIndex, source, sourceIndex);
		init(source.getInput(sourceIndex));
	}
	
	public void init() { init(getInput(0));	}
	
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

	public void update() {
		update(getInput(0));		
	}	
}