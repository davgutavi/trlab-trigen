package strmutation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algutils.AlgorithmRandomUtilities;
import mutations.MutationStrategy;

public class InstanceTimeSeriesInsertRightStrategy implements MutationStrategy {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(TimeSeriesInsertRightStrategy.class);
	
	public boolean alter(AlgorithmIndividual individual) {

		AlgorithmConfiguration config = AlgorithmConfiguration.getInstance();
		AlgorithmRandomUtilities randSupport = AlgorithmRandomUtilities.getInstance();
		
		boolean res = false;
		
		int maxSize = config.getMaxG()-individual.getGeneSize();
		
		int rigthGene = individual.getGene(individual.getGeneSize()-1);

		
		if (maxSize>0){
			
			int availableGenes = config.getData().getGenSize() - rigthGene - 1;
			
			if (availableGenes>0) {
				
				int border = randSupport.getFromInterval(1, Math.min(maxSize, availableGenes));
								
				for (int i=0;i<border;i++)					
					individual.putGene(rigthGene+(i+1));
							
				res = true;
				
			}
					
		}
	
		return res;

	}

}