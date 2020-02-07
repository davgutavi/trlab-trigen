package strmutation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algutils.AlgorithmRandomUtilities;
import mutations.MutationStrategy;

public class TimeSeriesInsertRightStrategy implements MutationStrategy {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(TimeSeriesInsertRightStrategy.class);
	
	public boolean alter(AlgorithmIndividual individual) {

		AlgorithmConfiguration config = AlgorithmConfiguration.getInstance();
		AlgorithmRandomUtilities randSupport = AlgorithmRandomUtilities.getInstance();
		
		boolean res = false;
		
		int maxSize = config.getMaxT()-individual.getTimeSize();
		
		int rightTime = individual.getTime(individual.getTimeSize()-1);
		
//		LOG.debug("maxSize = "+maxSize);
		
//		LOG.debug("rightTime = "+rightTime);
		
		if (maxSize>0){
			
			int abialableTimes = config.getData().getTimeSize() - rightTime - 1;
			
//			LOG.debug("abialableTimes = "+abialableTimes);
			
			if (abialableTimes>0) {
				
				int border = randSupport.getFromInterval(1, Math.min(maxSize, abialableTimes));
				
//				LOG.debug("border = "+border);
								
				for (int i=0;i<border;i++)					
					individual.putTime(rightTime+(i+1));
							
				res = true;
				
			}
					
		}
	
		return res;

	}

}