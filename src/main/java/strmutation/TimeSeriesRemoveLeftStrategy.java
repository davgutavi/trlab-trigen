package strmutation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algutils.AlgorithmRandomUtilities;
import mutations.MutationStrategy;

public class TimeSeriesRemoveLeftStrategy implements MutationStrategy {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(TimeSeriesRemoveLeftStrategy.class);

	public boolean alter(AlgorithmIndividual individual) {

		AlgorithmConfiguration config = AlgorithmConfiguration.getInstance();
		AlgorithmRandomUtilities randSupport = AlgorithmRandomUtilities.getInstance();
		
		boolean res = false;
			
		int minSize = individual.getTimeSize() - config.getMinT();
		
//		LOG.debug("minSize = "+minSize);
		
    	if (minSize>0){
			
			int border = randSupport.getFromInterval(1, minSize);
			
			int leftTime = individual.getTime(0);
			
//			LOG.debug("border = "+border);
			
			for (int i=0;i<border;i++)					
				individual.deleteTime(leftTime+i);
							
			res = true;					
		}
	
		return res;

		}

}
