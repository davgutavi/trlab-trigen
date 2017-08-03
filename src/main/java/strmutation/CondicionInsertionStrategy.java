package strmutation;

import mutations.MutationStrategy;
import algcore.AlgorithmConfiguration;
import algcore.AlgorithmDataset;
import algcore.AlgorithmIndividual;
import algutils.AlgorithmRandomUtilities;

public class CondicionInsertionStrategy implements MutationStrategy {

	public boolean alter(AlgorithmIndividual individual) {

		AlgorithmConfiguration config      = AlgorithmConfiguration.getInstance();
		AlgorithmDataset      dataset      = config.getData();
		AlgorithmRandomUtilities randomSupport = AlgorithmRandomUtilities.getInstance();
		
		Boolean res = true;
		
		if (individual.getSampleSize()<config.getMaxC()){
		
			randomSupport.newBag();
		
			randomSupport.putMarbles(dataset.getSamplesBag(),individual.getSamples());
		
			int bola = randomSupport.extractAmarble();
	
			individual.putSample(bola);
		
		}
		else{
			res = false;
		}
	
		return res;

	}

}