package strmutation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algutils.AlgorithmRandomUtilities;
import mutations.MutationStrategy;

public class InstanceTimeSeriesInsertBothStrategy implements MutationStrategy {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(TimeSeriesInsertBothStrategy.class);

	public boolean alter(AlgorithmIndividual individual) {

		AlgorithmConfiguration config = AlgorithmConfiguration.getInstance();
		AlgorithmRandomUtilities randSupport = AlgorithmRandomUtilities.getInstance();
		
		boolean res = false;
		
		int maxSize = config.getMaxG()-individual.getGeneSize();
		
		if (maxSize>0){
			
			int availableLeft = individual.getGene(0);
			
			int rightGene = individual.getGene(individual.getGeneSize()-1);
			
			int availableRight = config.getData().getGenSize() - rightGene -1;
			
			int avaialableGenes = Math.min(availableLeft, availableRight);			

			
			if (avaialableGenes>0) {
				
				int len = randSupport.getFromInterval(2, Math.min(maxSize,avaialableGenes*2));
				
				if (len%2!=0)
					len--;
					
				for (int i=0;i<(len/2);i++) {
					individual.putGene(availableLeft-(i+1));
					individual.putGene(rightGene+(i+1));
				}
							
				res = true;
					
			}
			
			
		}
	
		return res;

	}

}