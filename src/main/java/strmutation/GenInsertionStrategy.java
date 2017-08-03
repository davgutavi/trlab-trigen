package strmutation;

import mutations.MutationStrategy;
import algcore.AlgorithmConfiguration;
import algcore.AlgorithmDataset;
import algcore.AlgorithmIndividual;
import algutils.AlgorithmRandomUtilities;

public class GenInsertionStrategy implements MutationStrategy {

	public boolean alter(AlgorithmIndividual individual) {

		AlgorithmConfiguration PARAM      = AlgorithmConfiguration.getInstance();
		AlgorithmDataset      DATOS      = PARAM.getData();
		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();
		
		Boolean res = true;
		
		if (individual.getGeneSize()<PARAM.getMaxG()){
		
			ALEATORIOS.newBag();
		
			ALEATORIOS.putMarbles(DATOS.getGenesBag(),individual.getGenes());
		
			int bola = ALEATORIOS.extractAmarble();
	
			individual.putGene(bola);
		
		}
		
		else{
			res = false;
		}
		
		return res;

	}// procedimiento_mutar

}// clase
