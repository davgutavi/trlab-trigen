package algcore;

/**
 * Fitness operator function interface.  
 * 
 * @author David Gutiérrez-Avilés
 *
 */
public interface FitnessFunction {

	/**
	 * Evaluates an individual with this fitness function.
	 * 
	 * @param individual the individual to evaluate
	 */
	public void evaluate(AlgorithmIndividual individual);

}
