package algcore;

import java.util.List;

/**
 * Selection operator interface. 
 * 
 * @author  David Gutiérrez-Avilés
 *
 */
public interface Selection {

	/**
	 * Selects the individuals that promotes to the next generation.
	 * 
	 * @param numberOfSelections the number of individuals to be selected
	 * @param population the population from the individuals have to be selected
	 * @return the selected individuals
	 */
	public List<AlgorithmIndividual> select (int numberOfSelections, List<AlgorithmIndividual> population);

}
