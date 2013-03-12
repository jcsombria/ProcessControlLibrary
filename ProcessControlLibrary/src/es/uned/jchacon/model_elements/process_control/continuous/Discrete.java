package es.uned.jchacon.model_elements.process_control.continuous;


/**
 * 
 * @author Jesús Chacón <jcsombria@gmail.com>
 * 
 * Interface Discrete
 *
 * An interface for an object representing a discrete simulation block. 
 */
public interface Discrete extends Block {
	public void update(double x);
	public void update();	
}
