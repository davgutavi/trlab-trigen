package strmutation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algutils.AlgorithmRandomUtilities;
import mutations.MutationStrategy;

public class InstanceTimeSeriesRemoveLeftStrategy implements MutationStrategy {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(TimeSeriesRemoveLeftStrategy.class);

	public boolean alter(AlgorithmIndividual individual) {

		AlgorithmConfiguration config = AlgorithmConfiguration.getInstance();
		AlgorithmRandomUtilities randSupport = AlgorithmRandomUtilities.getInstance();
		
		boolean res = false;
			
		int minSize = individual.getGeneSize() - config.getMinG();
		
		
    	if (minSize>0){
			
			int border = randSupport.getFromInterval(1, minSize);
			
			int leftGene = individual.getGene(0);			
			
			for (int i=0;i<border;i++)					
				individual.deleteGene(leftGene+i);
							
			res = true;					
		}
	
		return res;

		}

}
