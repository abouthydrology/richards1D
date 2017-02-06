/**
 *  @author Niccol�
 */

package richards_classes;

/**
 * In the case where the matrix A is tridiagonal, i.e. with the special structure there is a very fast and efficient special case of the Gau�-algorithm, called the Thomas-algorithm.
 * Since it is a variation of the Gau�-algorithm, the Thomas algorithm is a direct method to solve general
 * linear tridiagonal systems. As the original Gau�-algorithm, it proceeds in two stages, one forward
 * elimination and one back-substitution.
 * (Numerical Methods for Free Surface Hydrodynamics Solution of linear algebraic equation systems, Dumbser, M., Universit� degli studi di Trento)
 */

public class Thomas {
	
	double[] mainDiagonal;
	double[] upperDiagonal;
	double[] lowerDiagonal;
	double[] rHS;
	double[] solution;
	int DIM;
	

	public Thomas(){}
	
	/**
	 * 
	 * @param upperDiagonal upper diagonal of the coefficient matrix A of the linear system, it is a vector of length N
	 * @param mainDiagonal main diagonal of the coefficient matrix A of the linear system, it is a vector of length N
	 * @param lowerDiagonal lower diagonal of the coefficient matrix A of the linear system, it is a vector of length N
	 * @param rHS right hand side term of the linear system, it is a vector of length N
	 */
	public void set(double[] upperDiagonal, double[] mainDiagonal, double[] lowerDiagonal, double[] rHS){
			this.upperDiagonal = upperDiagonal;
			this.mainDiagonal = mainDiagonal;
			this.lowerDiagonal = lowerDiagonal;
			this.rHS = rHS;
			
			this.DIM = this.mainDiagonal.length;
			this.solution = new double[this.DIM];
	}
	
	
	/**
	 * The method solver computes the solution of the linear system with the thomas algorithm
	 */
	public double[] solver(){
				
		double gamma = 0.0;
		if(this.mainDiagonal.length!= this.upperDiagonal.length | this.mainDiagonal.length!= this.lowerDiagonal.length | this.mainDiagonal.length!= this.rHS.length){
			throw new IllegalArgumentException( "System size error! |n"
					+ "Check the length of diagonal vectors and the right hand side term of the system ");
		}
		

		this.upperDiagonal[0] = this.upperDiagonal[0]/this.mainDiagonal[0];
		this.rHS[0] = this.rHS[0]/this.mainDiagonal[0];
		

		for(int i = 1; i < this.DIM; i++) {
			gamma = 1 / (this.mainDiagonal[i] - this.upperDiagonal[i-1]*this.lowerDiagonal[i]);
			this.upperDiagonal[i] = this.upperDiagonal[i]*gamma;
			this.rHS[i] = (this.rHS[i] - this.lowerDiagonal[i]*this.rHS[i-1])*gamma;
		}

		this.solution[this.DIM-1] = this.rHS[this.DIM-1];

		for(int i=this.DIM-2; i > -1; i--) {
			this.solution[i] = this.rHS[i] - this.upperDiagonal[i]*this.solution[i+1];
		}
		
		return this.solution;

	}

	
	/**
	 * This method prints the solution on the screen
	 */
	public void printSolution(){
		System.out.println("The solution computed with Thomas algorithm is: \n");
		for(int i=0;i<this.solution.length;i++){
			System.out.println(this.solution[i]);
		}
	}
}