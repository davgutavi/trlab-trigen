package strmutation;

import mutations.MutationStrategy;
import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algutils.AlgorithmRandomUtilities;

public class TimeRemovalStrategy implements MutationStrategy {

	
	public boolean alter (AlgorithmIndividual individual) {
		
		AlgorithmConfiguration PARAM      = AlgorithmConfiguration.getInstance();
		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();
		
		boolean res = true;
		
		if(individual.getTimeSize()>PARAM.getMinT()){
			
			ALEATORIOS.newBag();
			ALEATORIOS.putMarbles(individual.getTimes());
			int bola = ALEATORIOS.extractAmarble();
					
			individual.deleteTime(bola);
	
		}
		else{
			res = false;
		}
		
		return res;
		
	
	}//procedimiento_mutar

}//clase
