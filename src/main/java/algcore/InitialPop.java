package algcore;

/**
 * 
 * Initial population operator interface. It defines method in order to produce the initial population.
 * This method can be composed by several {@link InitialPopStrategy}.  
 * @author David Gutiérrez-Avilés
 */

import java.util.List;


public interface InitialPop {

	/**
	 * Initial population operator.
	 * @return Initial population as a {@link List} of {@link AlgorithmIndividual}.
	 */
	public List<AlgorithmIndividual> produceInitialPop ();

}
