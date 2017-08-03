package algcore;

import java.util.List;

/**
 * Interface for crossover method. This interface is used by {@link TriGen} for perform the crossover operator.
 * 
 * @author David Gutiérrez-Avilés
 */
public interface Crossover {

	/**
	 * Build a {@link List} of {@link <AlgorithmIndividual>} created by crossing from selected population.
	 * 
	 * @param numberOfChildren the number of children to build
	 * @param selectedPopulation the selected population
	 * @return individuals created by crossing
	 */
	public List<AlgorithmIndividual> cross(int numberOfChildren,List<AlgorithmIndividual> selectedPopulation);

}
