package es.uned.jchacon.model_elements.process_control.continuous;

/**
 * 
 * @author Jesús Chacón <jcsombria@gmail.com>
 * 
 * Interface Continuous
 *
 * An interface for an object representing a continuous simulation block. 
 */
public interface Continuous extends Block {
	public void linkStates(double[] x);	
	public double[] getRates();
//	public double[] getRates(double[] x, double[] u);
//	public double[] getOutput(double[] x, double[] u);
}
