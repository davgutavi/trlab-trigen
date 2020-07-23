package strcrossover;

import algcore.AlgorithmIndividual;
import algcore.TriGen;
import algutils.TriclusterUtilities;
import crossovers.CrossoverStrategy;

public class SwitchXYStrategy implements CrossoverStrategy {

	public AlgorithmIndividual[] cross(AlgorithmIndividual father, AlgorithmIndividual mother,
			String individualClassName) {

		AlgorithmIndividual[] r = new AlgorithmIndividual[2];

		r[0] = TriclusterUtilities.getInstance().buildIndividual(mother.getGenes(), father.getSamples(),
				father.getTimes(), individualClassName,
				"from crossover [" + ((TriGen.getInstance()).getOngoingGenerationIndex() + 1) + "]");

		r[1] = TriclusterUtilities.getInstance().buildIndividual(father.getGenes(), mother.getSamples(),
				mother.getTimes(), individualClassName,
				"from crossover [" + ((TriGen.getInstance()).getOngoingGenerationIndex() + 1) + "]");

		return r;

	}

}