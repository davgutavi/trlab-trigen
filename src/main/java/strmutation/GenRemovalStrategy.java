package strmutation;

import mutations.MutationStrategy;
import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algutils.AlgorithmRandomUtilities;

public class GenRemovalStrategy implements MutationStrategy {

	
public boolean alter (AlgorithmIndividual individual) {
	
		
		AlgorithmConfiguration PARAM      = AlgorithmConfiguration.getInstance();
		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();
		
		boolean res = true;
		
		if(individual.getGeneSize()>PARAM.getMinG()){
			
			ALEATORIOS.newBag();
			ALEATORIOS.putMarbles(individual.getGenes());
			int bola = ALEATORIOS.extractAmarble();
					
			individual.deleteGene(bola);
	
		}
		else{
			res = false;
		}
		
		return res;
		
		
	}

}//clase
