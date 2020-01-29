package strcrossover;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.TriGen;
import algutils.TriclusterUtilities;
import crossovers.CrossoverStrategy;



public class TimeSeriesStrategy implements CrossoverStrategy {
	
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(TimeSeriesStrategy.class);

	public AlgorithmIndividual[] cross(AlgorithmIndividual father, AlgorithmIndividual mother, String individualClassName) {
		
		AlgorithmConfiguration config  = AlgorithmConfiguration.getInstance();
		
		AlgorithmIndividual[] r = new AlgorithmIndividual[2];
		father.addEntry("father ["+((TriGen.getInstance()).getOngoingGenerationIndex()+1)+"]");		
		mother.addEntry("mother ["+((TriGen.getInstance()).getOngoingGenerationIndex()+1)+"]");
		
//		LOG.debug(">>>> Cruce");
//		LOG.debug("Padre:"+father);
//		LOG.debug("Madre:"+mother);
		
//		LOG.debug(father.toString());
//		LOG.debug(mother.toString());
		
		
		
		Collection<Integer> [] childGenes = TriclusterUtilities.getInstance().buildOnePointComponents(father.getGenes(),mother.getGenes(),
				config.getMinG(), config.getMaxG(), config.getData().getGenSize());
		
		Collection<Integer> [] childConditions = TriclusterUtilities.getInstance().buildOnePointComponents(father.getSamples(),mother.getSamples(),
				config.getMinC(), config.getMaxC(), config.getData().getSampleSize());
		
		Collection<Integer> [] childTimes = TriclusterUtilities.getInstance().buildTimeSeriesComponents(father.getTimes(),mother.getTimes(),
				config.getMinT(), config.getMaxT());
		
		
		r[0] = TriclusterUtilities.getInstance().buildIndividual(childGenes[0],childConditions[0],childTimes[0],individualClassName,
				"from crossover ["+((TriGen.getInstance()).getOngoingGenerationIndex()+1)+"]");
		
		
		r[1] = TriclusterUtilities.getInstance().buildIndividual(childGenes[1],childConditions[1],childTimes[1],individualClassName,
				"from crossover ["+((TriGen.getInstance()).getOngoingGenerationIndex()+1)+"]");
		
	
//		LOG.debug("Hijo:"+r[0]);
//		LOG.debug("Hija:"+r[1]);
				
		return r;

	}
	

		
}