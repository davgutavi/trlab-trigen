package crossovers;

import algcore.AlgorithmIndividual;



public interface CrossoverStrategy {

	public AlgorithmIndividual[] cross (AlgorithmIndividual father, AlgorithmIndividual mother, String individualClassName);

}
