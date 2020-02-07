package strmutation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algutils.AlgorithmRandomUtilities;
import mutations.MutationStrategy;

public class TimeSeriesRemoveBothStrategy implements MutationStrategy {
	
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(TimeSeriesInsertBothStrategy.class);


	
	public boolean alter(AlgorithmIndividual individual) {

		AlgorithmConfiguration config = AlgorithmConfiguration.getInstance();
		AlgorithmRandomUtilities randSupport = AlgorithmRandomUtilities.getInstance();

		boolean res = false;

		int minSize = individual.getTimeSize() - config.getMinT();
		
//		LOG.debug("minSize = "+minSize);

		if (minSize > 0) {

			int len = randSupport.getFromInterval(2, minSize);

			if (len % 2 != 0)
				len--;

//			LOG.debug("len = "+len);

			int leftTime = individual.getTime(0);
			int rightTime = individual.getTime(individual.getTimeSize() - 1);

			for (int i = 0; i < (len / 2); i++) {
				individual.deleteTime(leftTime + i);
				individual.deleteTime(rightTime - i);
			}

			res = true;

		}

		return res;

	}

}