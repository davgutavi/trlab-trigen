package algcore;

import java.util.List;

/**
 * Solution criterion interface. The {@link TriGen} algorithm uses this criterion in order to choose the best solution 
 * for an evolutionary process.
 * 
 * @author David Gutiérrez-Avilés
 */
public interface SolutionCriterion {

	/**
	 * Chooses the best individual from the population.
	 * 
	 * @param population the population
	 * @return the best individual
	 */
	public AlgorithmIndividual chooseTheBest(List<AlgorithmIndividual> population);

}
