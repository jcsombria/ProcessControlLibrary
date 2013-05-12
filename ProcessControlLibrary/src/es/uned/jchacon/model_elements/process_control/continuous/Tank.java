package es.uned.jchacon.model_elements.process_control.continuous;

public class Tank extends StateSpaceModel implements Hybrid {
	private boolean isEmpty = true;
	private double alpha = 0;
	private double tankSection = 1;
	private final double G = 9.81;
	private final double K = Math.sqrt(2*G);
	
	public Tank() {
		setModel(new double[][]{{0}}, new double[][]{{1,-1}},
				new double[][]{{1}}, new double[][]{{0,0}});
	}

	public Tank(double a) {
		this();
		if(a <= 0) return;
		tankSection = a;
		setModel(new double[][]{{0}}, new double[][]{{1/a,-1/a}},
				new double[][]{{1},{0}}, new double[][]{{0,0},{0,0}});
	}

	@Override
	public double[] getRates() {
		double[] dx = super.getRates();
		
		if(x[0] >= 0) {
			dx[0] -= alpha*(K/tankSection)*Math.sqrt(x[0]);
		} else if(dx[0] < 0) {
			dx[0] = 0;			
		}		
	
		return dx;
	}
	
	@Override
	public double getOutput(int i) {
		if(i == 1) {
			return alpha*(K/tankSection)*Math.sqrt(x[0]);
		} else {
			return super.getOutput(i);
		}
	}

	@Override
	public double[] getOutputs() {
		double[] outputs = super.getOutputs();
		outputs[1] = alpha*(K/tankSection)*Math.sqrt(x[0]);

		return outputs;
	}

	public double evaluate() {
		return x[0];
	}

	public void update() {
		isEmpty = (x[0] <= 0);
	}

	public void setValvePosition(double alpha) {
		if(alpha < 0 || alpha > 1) {
			return;
		} else {
			this.alpha = alpha;
		}
	}
	
	public double getSteadyStateInput(double hsp) {
		if(hsp < 0) {
			return 0.0;
		}		
		return K * Math.sqrt(hsp);
	}
}
