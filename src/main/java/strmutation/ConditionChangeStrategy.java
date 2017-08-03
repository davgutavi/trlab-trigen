package strmutation;

import mutations.MutationStrategy;
import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.AlgorithmDataset;
import algutils.AlgorithmRandomUtilities;

public class ConditionChangeStrategy  implements MutationStrategy {

	public boolean alter(AlgorithmIndividual individual) {

		AlgorithmConfiguration PARAM      = AlgorithmConfiguration.getInstance();
		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();
		AlgorithmDataset      DATOS      = PARAM.getData();
		
		boolean res = true;
		
		if(individual.getSampleSize()>PARAM.getMinC()){
			
			ALEATORIOS.newBag();
			ALEATORIOS.putMarbles(individual.getSamples());
			int bola = ALEATORIOS.extractAmarble();
					
			individual.deleteSample(bola);
			
			if (individual.getSampleSize()<PARAM.getMaxC()){
				
				ALEATORIOS.newBag();
			
				ALEATORIOS.putMarbles(DATOS.getSamplesBag(),individual.getSamples());
			
				bola = ALEATORIOS.extractAmarble();
		
				individual.putSample(bola);
			
				
			}
			else{
				res = false;
			}
	
		}
		else{
			res = false;
		}
		
		return res;

	}// procedimiento_mutar
	
}
