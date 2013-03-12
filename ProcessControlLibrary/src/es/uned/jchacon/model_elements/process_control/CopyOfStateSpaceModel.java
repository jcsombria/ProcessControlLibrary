/**
 * @author Jesús Chacón <jchacon@bec.uned.es>
 */

package es.uned.jchacon.model_elements.process_control;

import es.uned.jchacon.model_elements.process_control.continuous.AbstractBlock;
import es.uned.jchacon.model_elements.process_control.continuous.Continuous;

public class CopyOfStateSpaceModel extends AbstractBlock implements Continuous {
	protected double[] A, B, C, D;
	private boolean updateOutputOnGetRates = false;	

	public CopyOfStateSpaceModel() {
		setModel(new double[]{0,1,0,0}, new double[]{0,1},new double[]{0,1},new double[]{0});		
	}
	
	public CopyOfStateSpaceModel(double[] A, double[] B, double[] C, double[] D) {
		setModel(A, B, C, D);	
	}
	
	/* Modifies the system matrices to a new value */
	public void setModel(double[] A, double[] B, double[] C, double[] D) {
		setNumberOfStates((int)Math.sqrt(A.length));
		setNumberOfInputs((int)(B.length/nstates));
		setNumberOfOutputs((int)(C.length/nstates));

		this.A = A;
		this.B = B;		
		this.C = C;
		this.D = D;		
	}
	
/* 
 * Interface Continuous 	
 */
	
	/* Returns the derivatives of the states of system as dx(t)=A*x(t)+B*u(t), for the given values of x and u. */
	public double[] getRates(double[] x, double[] u) {
		double[] dx = new double[nstates];
		
		for(int i=0; i<nstates; i++) {
			dx[i] = 0;
			for(int j=0, row=nstates*i; j<nstates; j++) dx[i] += A[row+j]*x[j];
			for(int j=0, row=ninputs*i; j<ninputs; j++) dx[i] += B[row+j]*u[j];
		}
			
		return dx;
	}

	/* Returns the output of the system as y(t)=C*x(t)+D*u(t), for the given values of x and u. */
	public double[] getOutput(double[] x, double[] u) {
		double[] y = new double[noutputs];

		for(int i=0; i<noutputs; i++) {
			y[i] = 0;
			for(int j=0, row = nstates*i; j<nstates; j++) y[i] += C[row+j]*x[j];
			for(int j=0, row = ninputs*i; j<ninputs; j++) y[i] += D[row+j]*u[j];
		}

		return y;
	}








	/**
	 * @return An array of <i>double</i> with the derivatives of the states
	 */
	public double[] getRates() {
//		double[] u = new double[ninputs]; for(int i=0; i<ninputs; i++) u[i] = getInput(i);
		double[] u = getInputs();
		double[] dx = new double[nstates];
		
		if(u == null || x == null) return null; 
		
		for(int i=0; i<nstates; i++) {
			dx[i] = 0;
			for(int j=0, row=nstates*i; j<nstates; j++) dx[i] += A[row+j]*x[j];
			for(int j=0, row=ninputs*i; j<ninputs; j++) dx[i] += B[row+j]*u[j];
		}

		if(updateOutputOnGetRates) updateOutput();
		
		return dx;
	}

/*	public double[] getOutputs() {
		for(int i=0; i<noutputs; i++) {
			y[i] = 0;
			for(int j=0, row = nstates*i; j<nstates; j++) y[i] += C[row+j]*x[j];
			for(int j=0, row = ninputs*i; j<ninputs; j++) y[i] += D[row+j]*u[j];
		}

		return y;
	}*/
	
	/**
	 * Update the output of the system as y(t)=C*x(t)+D*u(t)
	 * 
	 * @return An array of <i>double</i> with the outputs
	 */
	public void updateOutput() {		
		double[] u = getInputs();

		for(int i=0; i<noutputs; i++) {
			y[i] = 0;
			for(int j=0, row = nstates*i; j<nstates; j++) y[i] += C[row+j]*x[j];
			for(int j=0, row = ninputs*i; j<ninputs; j++) y[i] += D[row+j]*u[j];
		}
	}

	/**
	 * Links the states of the object to an external array. 
	 * 
	 * @param The external states
	 */
	public void linkStates(double[] x) { this.x = x; }

//	/**
//	 * Links the inputs of the object to an external array. 
//	 * 
//	 * @param The external inputs
//	 */
//	public void linkInputs(double[] u) { this.u = u; }
//
//	/**
//	 * Links the outputs of the object to an external array. 
//	 * 
//	 * @param The external outputs
//	 */
//	public void linkOutputs(double[] y) { this.y = y;	}

//	public double getInput(int i) {
//		return links[i].getValue();		
//	}

}
