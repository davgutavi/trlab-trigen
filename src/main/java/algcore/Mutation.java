package algcore;

/**
 * Mutation operator interface. 
 * 
 * @author David Gutiérrez-Avilés
 *
 */
public interface Mutation {

	/**
	 * Mutates an individual.
	 * 
	 * @param individual the individual to mutate
	 */
	public void mutate(AlgorithmIndividual individual);

}
