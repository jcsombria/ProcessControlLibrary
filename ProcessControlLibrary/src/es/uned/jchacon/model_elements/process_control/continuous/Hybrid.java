package es.uned.jchacon.model_elements.process_control.continuous;


/**
 * 
 * @author Jesús Chacón <jcsombria@gmail.com>
 * 
 * Interface Discrete
 *
 * An interface for an object representing a discrete simulation block. 
 */
public interface Hybrid extends Block {
//	public double evaluate(double x);	
//	public void update(double x);
	public double[] getRates();
	public double evaluate();	
	public void update();
}
