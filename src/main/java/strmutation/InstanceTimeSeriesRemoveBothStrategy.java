package strmutation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algutils.AlgorithmRandomUtilities;
import mutations.MutationStrategy;

public class InstanceTimeSeriesRemoveBothStrategy implements MutationStrategy {
	
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(TimeSeriesInsertBothStrategy.class);


	
	public boolean alter(AlgorithmIndividual individual) {

		AlgorithmConfiguration config = AlgorithmConfiguration.getInstance();
		AlgorithmRandomUtilities randSupport = AlgorithmRandomUtilities.getInstance();

		boolean res = false;

		int minSize = individual.getGeneSize() - config.getMinG();
		

		if (minSize > 0) {

			int len = randSupport.getFromInterval(2, minSize);

			if (len % 2 != 0)
				len--;

			int leftGene = individual.getGene(0);
			int rightGene = individual.getGene(individual.getGeneSize() - 1);

			for (int i = 0; i < (len / 2); i++) {
				individual.deleteGene(leftGene + i);
				individual.deleteGene(rightGene - i);
			}

			res = true;

		}

		return res;

	}

}