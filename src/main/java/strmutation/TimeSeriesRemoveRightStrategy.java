package strmutation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algutils.AlgorithmRandomUtilities;
import mutations.MutationStrategy;

public class TimeSeriesRemoveRightStrategy implements MutationStrategy {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(TimeSeriesRemoveRightStrategy.class);

	public boolean alter(AlgorithmIndividual individual) {

		AlgorithmConfiguration config = AlgorithmConfiguration.getInstance();
		AlgorithmRandomUtilities randSupport = AlgorithmRandomUtilities.getInstance();
		
		boolean res = false;
		
		int minSize = individual.getTimeSize() - config.getMinT();
		
//		LOG.debug("minSize = "+minSize);
		
		if (minSize>0){
			
			int border = randSupport.getFromInterval(1, minSize);
			
			int rightTime = individual.getTime(individual.getTimeSize()-1);
			
//			LOG.debug("border = "+border);
			
			for (int i=0;i<border;i++)					
				individual.deleteTime(rightTime-i);
							
			res = true;					
		}
	
		return res;

		}

}