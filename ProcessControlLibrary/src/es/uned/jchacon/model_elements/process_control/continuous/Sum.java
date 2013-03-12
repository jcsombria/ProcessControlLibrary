package es.uned.jchacon.model_elements.process_control.continuous;

import java.util.regex.Pattern;

import es.uned.jchacon.model_elements.process_control.continuous.AbstractBlock.Link;

public class Sum extends AbstractBlock implements Siso {
	public enum Sign { POSITIVE, NEGATIVE };
	
	private double[] signs = new double[] { +1, +1 };
	
	public Sum() {
		setNumberOfInputs(2);
		setNumberOfOutputs(1);
		setNumberOfStates(0);
	}

	public Sum(String signsString) {
		this();
		
		boolean isValid = Pattern.matches("(\\+|-)+", signsString);
		if(isValid) {
			int size = signsString.length();
			Sign[] sign = new Sign[size];
			for(int i=0; i<size; i++) sign[i] = (signsString.charAt(i) == '-') ? Sign.NEGATIVE : Sign.POSITIVE;
			setSigns(sign);
		}		
	}
	
	public Sum(Sign[] signs) {
		this();
		
		setSigns(signs);
	}
	
	public void setSigns(Sign[] s) {
		int n = s.length;
		if(n != ninputs) return;
		
		for(int i=0; i<ninputs; i++) signs[i] = (s[i] == Sign.POSITIVE) ? +1.0 : -1.0;	
	}
	
	public void setSigns(String signsString) {
		boolean isValid = Pattern.matches("(\\+|-)+", signsString);
		if(isValid) {
			int size = signsString.length();
			Sign[] sign = new Sign[size];
			for(int i=0; i<size; i++) sign[i] = (signsString.charAt(i) == '-') ? Sign.NEGATIVE : Sign.POSITIVE;
			setSigns(sign);
		}		
	}

	@Override
	public void setNumberOfInputs(int size) {
		super.setNumberOfInputs(size);
	
		int end = (size > ninputs) ? ninputs : size;
		double[] oldSigns = signs;
		signs = new double[size];		
		
		System.arraycopy(oldSigns, 0, signs, 0, end);			
		for(int i = end; i < size; i++) signs[i] = +1;
		
		ninputs = size;
	}	
	
	public double getOutput(int i) {
		if(i != 0) return 0;
			
		return getOutputs()[i];
	}

	@Override
	public void updateOutput() {
		double[] u = getInputs();

		y[0] = 0;
		for(int i=0; i<ninputs; i++) y[0] += signs[i]*u[i];		
	}
	
/*
 * Interface Siso
 */
	public void setInput(Block source, int sourceIndex) { setInput(0, source, sourceIndex); }

	public void setInput(Siso source) { setInput(0, source, 0); }

	public double getInput() { return getInput(0); }

	public double getOutput() { return getOutput(0); }

}
