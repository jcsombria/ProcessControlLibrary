package es.uned.jchacon.model_elements.process_control.continuous;

/**
 * 
 * @author Jesús Chacón <jcsombria@gmail.com>
 * 
 * Interface Block
 *
 * An interface for an object representing a simulation block. 
 */
public interface Block {
	public int getNumberOfStates();
	public int getNumberOfInputs();
	public int getNumberOfOutputs();

//	public void setNumberOfInputs();
//	public void setNumberOfOutputs();
	
	public double[] getInputs();
	public double getInput(int i);
	public double[] getOutputs();
	public double getOutput(int i);	
//	public void setInput(Block src);
//	public void setInput(int inputIndex, Block src);
//	public void setInput(Block src, int outputIndex);
	public void setInput(int inputIndex, Block src, int outputIndex);
}
