package strmutation;

import mutations.MutationStrategy;
import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algutils.AlgorithmRandomUtilities;

public class ConditionRemovalStrategy implements MutationStrategy {

	public boolean alter(AlgorithmIndividual individual) {

		AlgorithmConfiguration config      = AlgorithmConfiguration.getInstance();
		AlgorithmRandomUtilities randomSupport = AlgorithmRandomUtilities.getInstance();
		
		boolean res = true;
		
		if(individual.getSampleSize()>config.getMinC()){
			
			randomSupport.newBag();
			randomSupport.putMarbles(individual.getSamples());
			int bola = randomSupport.extractAmarble();
					
			individual.deleteSample(bola);
	
		}
		else{
			res = false;
		}
		
		return res;

	}// procedimiento_mutar

}// clase
