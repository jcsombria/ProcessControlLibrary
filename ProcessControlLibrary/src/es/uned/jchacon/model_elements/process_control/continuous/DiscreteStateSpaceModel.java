package es.uned.jchacon.model_elements.process_control.continuous;

public class DiscreteStateSpaceModel extends AbstractBlock implements Discrete { //, Connectable {
	private double samplingPeriod;
	protected double[][] A, B, C, D;
	
	// Creates a new DiscreteSSModel with the default matrices A, B, C, and D
	public DiscreteStateSpaceModel() {
		setModel(new double[][]{{1},{0}}, new double[][]{{1}}, 
				 new double[][]{{1}}, new double[][]{{0}}, 1);
	}
	
	// Creates a new DiscreteSSModel with the specified matrices A, B, C, and D
	public DiscreteStateSpaceModel(double[][] A, double[][] B, double[][] C, double[][] D, double ts) {//, double Ts) {
		setModel(A, B, C, D, ts);
	}
	
	/* Modify the system matrices */
	public void setModel(double[][] A, double[][] B, double[][] C, double[][] D, double ts) {
		if(!isSquare(A)	|| !hasRowSize(B, A.length) || !hasColumnSize(C, A.length)) return;
	
		setNumberOfStates(A.length);
		setNumberOfInputs(B[0].length);
		setNumberOfOutputs(C.length);

		this.A = A;
		this.B = B;
		this.C = C;
		this.D = D;
		this.samplingPeriod = (ts > 0) ? ts : 1;
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
	
	// Updates the vector of states	
	public void setState(double[] x) { for(int i=0; i<x.length; i++) this.x[i] = x[i]; }

	// Returns the vector of states	
	public double[] getState() { return x; }
		
// Interface SimulationElemement
//	@Override
//	public void preliminary() {}
//	@Override
//	public int getNumberOfStates() { return _x.length; }
	/*
//	@Override
	// Returns the rates of change of the states
	public double[] getRates(double t, double[] x, double[] u) {
//		_x = x.clone();
		int N = x.length;
		double[] dx = new double[N];
		
		for(int i=0; i<nstates; i++)
			for(int j=0, row = nstates*i; j<nstates; j++)
				dx[i] += mA[row+j]*x[j];

		for(int i=0, row = ninputs*i; i<nstates; i++) 
			for(int j=0; j<ninputs; j++)
				dx[i] += mB[row+j]*u[j];
			
		return dx;
	}
*/
/*	// Returns the output of the model
	public double[] getOutput(double t, double[] x, double[] u) {
		int N = x.length;
		double[] dx = new double[N];
		
		for(int i=0; i<nstates; i++)
			for(int j=0, row = nstates*i; j<nstates; j++)
				dx[i] += mC[row+j]*x[j];

		for(int i=0, row = ninputs*i; i<nstates; i++) 
			for(int j=0; j<ninputs; j++)
				dx[i] += mD[row+j]*u[j];
			
		return dx;		
	}

	public double[] getOutput(double[] x, double[] u) {
		// TODO Auto-generated method stub
		return null;
	}*/

	/* Returns the derivatives of the states of system as dx(t)=A*x(t)+B*u(t), for the given values of x and u. */
	public void update() {
		double[] u = getInputs();
		double[] dx = new double[nstates];
		
		if(u == null || x == null) return; 
		try {		
			for(int i=0; i<nstates; i++) {
				dx[i] = 0;
				for(int j=0; j<nstates; j++) {
					dx[i] += A[i][j]*x[j];
				}
				for(int j=0; j<ninputs; j++) {
					dx[i] += B[i][j]*u[j];
				}
			}
			for(int i=0; i<nstates; i++){
				x[i] = dx[i];
			}
			for(int i=0; i<noutputs; i++) {
				y[i] = 0;
				for(int j=0; j<nstates; j++) {
					y[i] += C[i][j]*x[j];
				}
				for(int j=0; j<ninputs; j++) {
					y[i] += D[i][j]*u[j];
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {}
	}
	
	/* Returns the output of the system as y(t)=C*x(t)+D*u(t), for the given values of x and u. */
	public double[] getOutput() {
		return y;
	}

	public void update(double x) {
		update();
	}

	public double[] getStates() {
		return x;
	}
}
