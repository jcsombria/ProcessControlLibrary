/**
 * @author Jesús Chacón <jchacon@bec.uned.es>
 */

package es.uned.jchacon.model_elements.process_control.continuous;

public class SisoPlant extends StateSpaceModel implements Siso {

	public void setInput(Block source, int sourceIndex) { setInput(source, sourceIndex); }

	public void setInput(Siso source) { setInput(0, source, 0); }

	public double getInput() { return getInput(0); }

	public double getOutput() { return getOutput(0); }
}
