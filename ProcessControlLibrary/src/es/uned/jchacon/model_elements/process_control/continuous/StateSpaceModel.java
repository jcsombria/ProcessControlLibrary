/**
 * @author Jesús Chacón <jchacon@bec.uned.es>
 */

package es.uned.jchacon.model_elements.process_control.continuous;

public class StateSpaceModel extends AbstractBlock implements Continuous {
	protected double[][] A, B, C, D;
	private boolean updateOutputOnGetRates = false;	

	public StateSpaceModel() {
		setModel(new double[][]{{0,1},{0,0}}, new double[][]{{0},{1}},new double[][]{{0,1}},new double[][]{{0}});		
	}
	
	public StateSpaceModel(double[][] A, double[][] B, double[][] C, double[][] D) {
		setModel(A, B, C, D);
	}
	
	/* Modify the system matrices */
	public void setModel(double[][] A, double[][] B, double[][] C, double[][] D) {
		if(!isSquare(A)	|| !hasRowSize(B, A.length) || !hasColumnSize(C, A.length)) return;
	
		setNumberOfStates(A.length);
		setNumberOfInputs(B[0].length);
		setNumberOfOutputs(C.length);

		this.A = A;
		this.B = B;
		this.C = C;
		this.D = D;
	}

	/**
	 * Checks if the size of the matrix A is p x q
	 * @param A the matrix
	 * @return true if the size of A is p x q, false otherwise
	 */
	public boolean hasDimension(double[][] A, int p, int q) {
		if(A == null) return false;
		
		boolean isValid = true;
		
		// have all the rows the same dimension?
		int m = A.length, n = A[0].length;
		for(int i=1; i<m; i++) 	if(A[m].length != m) { isValid = false; break; }
		
		// is the matrix square?
		if(m != n) isValid = false; 
		
		return isValid;
	}

	/**
	 * Checks if the matrix A has p rows 
	 * @param A the matrix
	 * @return true if A has p rows, false otherwise
	 */
	private boolean hasRowSize(double[][] A, int p) {
		if(A == null) return false;
		
		boolean isValid = true;
		
		// have all the rows the same dimension?
		int m = A.length, n = A[0].length;
		for(int i=1; i<m; i++) 	if(A[i] == null || A[i].length != n) { isValid = false; break; }
		
		// is the matrix square?
		if(m != p) isValid = false; 
		
		return isValid;
	}

	/**
	 * Checks if the matrix A has q columns 
	 * @param A the matrix
	 * @return true if A has q columns, false otherwise
	 */
	private boolean hasColumnSize(double[][] A, int q) {
		if(A == null) return false;
		
		boolean isValid = true;
		
		int m = A.length, n = A[0].length;
		// have all the rows the same dimension?
		for(int i=1; i<m; i++) 	if(A[i] == null || A[i].length != n) { isValid = false; break; }
		
		// is the matrix square?
		if(n != q) isValid = false; 
		
		return isValid;
	}

	/**
	 * Checks if the matrix A is square
	 * @param A the matrix
	 * @return true if A is square, false otherwise
	 */
	private boolean isSquare(double[][] A) {
		if(A == null) return false;
		
		boolean isValid = true;	
		int m = A.length, n = A[0].length;
		// have all the rows the same dimension?
		for(int i=1; i<m; i++) 	if(A[i] == null || A[i].length != n) { isValid = false; break; }

		// is the matrix square?
		if(m != n) isValid = false; 		
		
		return isValid;
	}
	
/* 
 * Interface Continuous 	
 */
	
	/* Returns the derivatives of the states of system as dx(t)=A*x(t)+B*u(t), for the given values of x and u. */
	public double[] getRates(double[] x, double[] u) {
		double[] dx = new double[nstates];
try {		
		for(int i=0; i<nstates; i++) {
			dx[i] = 0;
			for(int j=0; j<nstates; j++) dx[i] += A[i][j]*x[j];
			for(int j=0; j<ninputs; j++) dx[i] += B[i][j]*u[j];
		}
			
	} catch (ArrayIndexOutOfBoundsException e) {}
		return dx;
	}

	/* Returns the output of the system as y(t)=C*x(t)+D*u(t), for the given values of x and u. */
	public double[] getOutput(double[] x, double[] u) {
		double[] y = new double[noutputs];
try{
		for(int i=0; i<noutputs; i++) {
			y[i] = 0;
			for(int j=0; j<nstates; j++) y[i] += C[i][j]*x[j];
			for(int j=0; j<ninputs; j++) y[i] += D[i][j]*u[j];
		}
	} catch (ArrayIndexOutOfBoundsException e) {}
		return y;
	}








	/**
	 * @return An array of <i>double</i> with the derivatives of the states
	 */
	public double[] getRates() {
		double[] u = getInputs();
		double[] dx = new double[nstates];
		
		if(u == null || x == null) return null; 
try{		
		for(int i=0; i<nstates; i++) {
			dx[i] = 0;
			for(int j=0; j<nstates; j++) dx[i] += A[i][j]*x[j];
			for(int j=0; j<ninputs; j++) dx[i] += B[i][j]*u[j];
		}
}catch(ArrayIndexOutOfBoundsException e ){}
		if(updateOutputOnGetRates) updateOutput();
		
		return dx;
	}

	/**
	 * @return An array of <i>double</i> with the outputs
	 */
	/* Returns the output of the system as y(t)=C*x(t)+D*u(t), for the given values of x and u. */
	@Override
	public double[] getOutputs() {
		double[] u = getInputs();
		double[] y = new double[noutputs];
		try{
			for(int i=0; i<noutputs; i++) {
				y[i] = 0;
				for(int j=0; j<nstates; j++) y[i] += C[i][j]*x[j];
				for(int j=0; j<ninputs; j++) y[i] += D[i][j]*u[j];
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("nstates: "+nstates+", ninputs: "+ninputs+", noutputs: "+noutputs);
			System.out.println("C.length: "+C.length+", D.lenght: "+D.length);
			System.out.println("y, x, u: "+ y.length + ", " + x.length + ", " + u.length);
		}
		
		return y;
	}

	/**
	 * Returns the output of the system as y(t)=C*x(t)+D*u(t), 
	 * for the given values of x and u. 
	 * 
	 * @return A <i>double</i> with the value of the output <i>i</i>
	 **/
	@Override
	public double getOutput(int i) {
		double[] u = getInputs();
		double y = 0;
		try{
			for(int j=0; j<nstates; j++) y += C[i][j]*x[j];
			for(int j=0; j<ninputs; j++) y += D[i][j]*u[j];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("nstates: "+nstates+", ninputs: "+ninputs+", noutputs: "+noutputs);
			System.out.println("C.length: "+C.length+", D.lenght: "+D.length);
			System.out.println("x, u: "+ ", " + x.length + ", " + u.length);
		}
		
		return y;
	}

	/**
	 * Update the output of the system as y(t)=C*x(t)+D*u(t)
	 * 
	 * @return An array of <i>double</i> with the outputs
	 */
	@Override
	public void updateOutput() {		
		double[] u = getInputs();

		try {
			for(int i=0; i<noutputs; i++) {
				y[i] = 0;
				for(int j=0; j<nstates; j++) y[i] += C[i][j]*x[j];
				for(int j=0; j<ninputs; j++) y[i] += D[i][j]*u[j];
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("nstates: "+nstates+", ninputs: "+ninputs+", noutputs: "+noutputs);
			System.out.println("C.length: "+C.length+", D.lenght: "+D.length);
			System.out.println("y, x, u: "+ y.length + ", " + x.length + ", " + u.length);
		}
	}


	/**
	 * Links the states of the object to an external array. 
	 * 
	 * @param The external states
	 */
	public void linkStates(double[] x) { 
		this.x = x;
	}

}
