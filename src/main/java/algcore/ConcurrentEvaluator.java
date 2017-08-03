package algcore;

import java.util.concurrent.Callable;

/**
 * Concurrent interface for evaluator operator. This interface is been used by {@link TriGen} in the evaluator operator 
 * in for evaluate all individuals of a population in a concurrent manner.
 * 
 * @author David Gutiérrez-Avilés
 */
public class ConcurrentEvaluator implements Callable<Boolean> {

	/**
	 * fitness function method field.
	 */
	private FitnessFunction fitnessFunction;
	
	/**
	 * individual to evaluate field.
	 */
	private AlgorithmIndividual individual;
	
	/**
	 * individual population index field.
	 */
	@SuppressWarnings("unused")
	private int individualIndex;

	/**
	 * Builds a concurrent evaluator for a {@link FitnessFunction} method and an {@link AlgorithmIndividual} to evaluate.
	 * 
	 * @param fitnessFunction the fitness function method
	 * @param individual the individual to evaluate
	 * @param individualIndex the individual population index
	 */
	public ConcurrentEvaluator(FitnessFunction fitnessFunction, AlgorithmIndividual individual, int individualIndex) {
		this.fitnessFunction = fitnessFunction;
		this.individual = individual;
		this.individualIndex = individualIndex;
	}
	
	public Boolean call() throws Exception {

		fitnessFunction.evaluate(individual);

		return new Boolean(true);
	}

}
