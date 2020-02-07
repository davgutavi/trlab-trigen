package strmutation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algutils.AlgorithmRandomUtilities;
import mutations.MutationStrategy;

public class TimeSeriesInsertLeftStrategy implements MutationStrategy {
	
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(TimeSeriesInsertLeftStrategy.class);

	public boolean alter(AlgorithmIndividual individual) {

		AlgorithmConfiguration config = AlgorithmConfiguration.getInstance();
		AlgorithmRandomUtilities randSupport = AlgorithmRandomUtilities.getInstance();
		
		boolean res = false;
		
		int maxSize = config.getMaxT()-individual.getTimeSize();
		
//		LOG.debug("maxSize = "+maxSize);
		
		if (maxSize>0){
			
			int abialableTimes = individual.getTime(0);
			
//			LOG.debug("abialableTimes = "+abialableTimes);
			
			if (abialableTimes>0) {
				
				int border = randSupport.getFromInterval(1, Math.min(maxSize, abialableTimes));
										
//				LOG.debug("border = "+border);
//				LOG.debug("leftTime = "+leftTime);
				
				for (int i=0;i<border;i++)					
					individual.putTime(abialableTimes-(i+1));
							
				res = true;
				
			}
					
		}
	
		return res;

	}	

}