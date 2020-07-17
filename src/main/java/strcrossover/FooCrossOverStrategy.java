package strcrossover;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmIndividual;
import algutils.TriclusterUtilities;
import crossovers.CrossoverStrategy;

public class FooCrossOverStrategy implements CrossoverStrategy {
	
    @SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(FooCrossOverStrategy.class);

	public AlgorithmIndividual[] cross(AlgorithmIndividual father, AlgorithmIndividual mother, String individualClassName) {
				
		AlgorithmIndividual[] r = new AlgorithmIndividual[2];
		
		
		AlgorithmIndividual son1 = TriclusterUtilities.getInstance().
				buildIndividual(father.getGenes(),father.getSamples(),father.getTimes(),
						individualClassName,"from crossover: foo");
		
		
		
		r[0] = son1;
		
		return r;

	}

	

}