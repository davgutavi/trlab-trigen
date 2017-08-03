package strmutation;

import mutations.MutationStrategy;
import algcore.AlgorithmConfiguration;
import algcore.AlgorithmDataset;
import algcore.AlgorithmIndividual;
import algutils.AlgorithmRandomUtilities;

public class TimeInsertionStrategy implements MutationStrategy {

	public boolean alter(AlgorithmIndividual individual) {

		AlgorithmConfiguration PARAM      = AlgorithmConfiguration.getInstance();
		AlgorithmDataset      DATOS      = PARAM.getData();
		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();
		
		Boolean res = true;
		
		if (individual.getTimeSize()<PARAM.getMaxT()){
		
			ALEATORIOS.newBag();
			
			ALEATORIOS.putMarbles(DATOS.getTimesBag(),individual.getTimes());
		
			int bola = ALEATORIOS.extractAmarble();
	
			individual.putTime(bola);
		
		}
		else{
			res = false;
		}
	
		return res;

	}// procedimiento_mutar

}// clase
