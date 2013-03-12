package es.uned.jchacon.model_elements.process_control.continuous;

/**
 * 
 * @author Jesús Chacón <jcsombria@gmail.com>
 * 
 * Interface Siso
 *
 * An interface for an object representing a Single-Input-Single-Output simulation block. 
 */
public interface Siso extends Block {
	public void setInput(Block source, int sourceIndex);
	public void setInput(Siso source);
	public double getInput();	
	public double getOutput();
}
