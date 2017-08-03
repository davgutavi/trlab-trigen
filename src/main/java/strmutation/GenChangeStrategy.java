package strmutation;

import mutations.MutationStrategy;
import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.AlgorithmDataset;
import algutils.AlgorithmRandomUtilities;

public class GenChangeStrategy implements MutationStrategy {

	
	public boolean alter (AlgorithmIndividual individual) {
	
		
		AlgorithmConfiguration PARAM      = AlgorithmConfiguration.getInstance();
		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();
		AlgorithmDataset      DATOS      = PARAM.getData();
		
		boolean res = true;
		
		if(individual.getGeneSize()>PARAM.getMinG()){
			
			ALEATORIOS.newBag();
			
			ALEATORIOS.putMarbles(individual.getGenes());
			
			int bola = ALEATORIOS.extractAmarble();
					
			individual.deleteGene(bola);
			
			
			if (individual.getGeneSize()<PARAM.getMaxG()){
				
				ALEATORIOS.newBag();
			
				ALEATORIOS.putMarbles(DATOS.getGenesBag(),individual.getGenes());
			
				bola = ALEATORIOS.extractAmarble();
		
				individual.putGene(bola);
			
			}
			
			else{
				res = false;
			}
		}
		
		else{
		
			res = false;
		}
		
		
		return res;
		
		
	}



}
