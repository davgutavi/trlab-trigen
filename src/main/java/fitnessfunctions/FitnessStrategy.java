package fitnessfunctions;

import algcore.AlgorithmIndividual;

public interface FitnessStrategy {

	public double calculate (AlgorithmIndividual individual);

}