package es.uned.jchacon.model_elements.process_control;

import java.awt.event.ActionEvent;
import java.util.regex.Pattern;

import javax.swing.JTextField;

import es.uned.jchacon.model_elements.process_control.continuous.Source;
import es.uned.jchacon.model_elements.process_control.continuous.StateSpaceModel;
import es.uned.jchacon.model_elements.process_control.continuous.Sum;

public class Test {
	public static void main(String[] args) {
		Test test = new Test();
		
//		test.testSource();
//		test.testSum();
//		test.testStateSpace();
		test.testRegex();
	}
	
	public void testSource() {
		Source src = new Source();
		double[] x = new double[1];
		
		src.linkStates(x);
		
		x[0] = 4;

		System.out.println("---------------------");
		System.out.println("Testing Source object");
		System.out.println("---------------------");
		System.out.println("Number of inputs: "+src.getNumberOfInputs());
		System.out.println("Number of outputs: "+src.getNumberOfOutputs());
		System.out.println("Number of states: "+src.getNumberOfStates());
		
		System.out.println("Input 0: "+src.getInput(0));
		System.out.println("Output 0: "+src.getOutput(0));
		System.out.println("Output 1: "+src.getOutput(1));		
	}

	public void testSum() {
		Source src_a = new Source(), src_b = new Source();
		double[] x = new double[1], y = new double[1];		
		
		src_a.linkStates(x);
		src_b.linkStates(y);

		Sum sum_ab = new Sum();
		
		sum_ab.setNumberOfInputs(2);
		sum_ab.setInput(0, src_a, 0);
		sum_ab.setInput(1, src_b, 0);		

		x[0] = 4; y[0] = 2;
	
		System.out.println("------------------");
		System.out.println("Testing Sum object");
		System.out.println("------------------");
		System.out.println("Number of inputs: "+sum_ab.getNumberOfInputs());
		System.out.println("Number of outputs: "+sum_ab.getNumberOfOutputs());
		System.out.println("Number of states: "+sum_ab.getNumberOfStates());
		
		System.out.println("Input 0: "+sum_ab.getInput(0));
		System.out.println("Input 1: "+sum_ab.getInput(1));
		System.out.println("Input 2: "+sum_ab.getInput(2));
		System.out.println("Output 0: "+sum_ab.getOutput(0));
		System.out.println("Output 1: "+sum_ab.getOutput(1));		
	}

	public void testStateSpace() {
		Source src = new Source();
		double[] x = new double[1], u = new double[1];		
		
		StateSpaceModel ss = new StateSpaceModel(new double[]{0}, new double[]{1}, new double[]{1}, new double[]{0});
		
		src.linkStates(u);
		ss.linkStates(x);
		ss.setInput(0, src, 0);
		
		u[0] = 1;

		System.out.println("------------------");
		System.out.println("Testing StateSpaceModel object");
		System.out.println("------------------");
		System.out.println("Number of inputs: "+ss.getNumberOfInputs());
		System.out.println("Number of outputs: "+ss.getNumberOfOutputs());
		System.out.println("Number of states: "+ss.getNumberOfStates());
		
		System.out.println("Input 0: "+ss.getInput(0));
		System.out.println("Input 1: "+ss.getInput(1));
		System.out.println("Input 2: "+ss.getInput(2));
		System.out.println("Output 0: "+ss.getOutput(0));
		System.out.println("Output 1: "+ss.getOutput(1));		
	}


	public void testRegex() {
		System.out.println("-------------");
		System.out.println("Testing Regex");
		System.out.println("-------------");
		
		String[] numbers = new String[]{"+1", "1.1", "-.1", ".", ".1.1", "+1.", "1e-1", "-1e2", ".e-4"};
		
		for (String s : numbers) System.out.println("is " + s + " a number?: "+testNumber(s));
	}
	
	public boolean testNumber(String input) {
		String number = "(\\+|-)?((\\d+\\.)|(\\.?\\d))\\d*";
		String exponent = "((e|E)(\\+|-)?\\d+)?";
		Pattern pattern = Pattern.compile(number+exponent);
		
  		return pattern.matcher(input).matches();	
	}
}
