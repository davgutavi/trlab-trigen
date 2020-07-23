package solutioncriteria;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmIndividual;
import algcore.SolutionCriterion;
import algcore.TriGen;
import algutils.TriclusterUtilities;

public class MinimumFitness implements SolutionCriterion {
	
//	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(MinimumFitness.class);

	public AlgorithmIndividual chooseTheBest(List<AlgorithmIndividual> population) {

		LOG.debug("\n\nMinimumFitness >>>>>\n\n");
				
		List<AlgorithmIndividual> currentSolutions = TriGen.getInstance().getSolutions();
		
		
		
		
		
		AlgorithmIndividual best = Collections.min(population);	
		
		LOG.debug("Best: "+TriclusterUtilities.getInstance().individualToString(best)+"\n");
		
		if(currentSolutions.contains(best)) {
			
			LOG.debug("Repeated solution\n");
			
			LOG.debug("Current solutions:\n"+TriclusterUtilities.getInstance().populationToString(currentSolutions)+"\n");
			
			Collections.sort(population);
			
			LOG.debug("\nLast population:\n"+TriclusterUtilities.getInstance().populationToString(population)+"\n");
			
			Iterator<AlgorithmIndividual> it = population.iterator();
			
			it.next();
			
			boolean enc = false;
			
			while(it.hasNext()&&!enc) {
				
				AlgorithmIndividual next = it.next();
								
				if(!currentSolutions.contains(next)) {
					enc = true;
					best = next;
				}
								
			}
			
			LOG.debug("New Best: "+TriclusterUtilities.getInstance().individualToString(best)+"\n");
			
		}
			
		LOG.debug("\n\n<<<<< MinimumFitness\n\n");
		
		return best;

	}

}