package solutioncriteria;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmIndividual;
import algcore.SolutionCriterion;
import algcore.TriGen;
import algutils.Point;
import algutils.TriclusterUtilities;

public class FitXYoverlapping implements SolutionCriterion {
	
//	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(FitXYoverlapping.class);
	
	private static final double SOLUTION_PERCETAGE = 0.4;

	public AlgorithmIndividual chooseTheBest(List<AlgorithmIndividual> population) {

		LOG.debug("\nFitXYoverlapping >>>>>\n\n");
				
		List<AlgorithmIndividual> currentSolutions = TriGen.getInstance().getSolutions();
		Set<Point> currentPoints =  TriclusterUtilities.getInstance().toPoints(currentSolutions);
		
		Collections.sort(population);
		List<AlgorithmIndividual> potentialSolutions = population.subList(0, (int) (population.size()*SOLUTION_PERCETAGE)+1);
		
		
		AlgorithmIndividual best = population.get(0);
		int best_xy_overlapping = TriclusterUtilities.getInstance().commonXYcells(best,currentPoints);
		
		
		for(AlgorithmIndividual ind:potentialSolutions) {
			
			int ind_xy_overlapping = TriclusterUtilities.getInstance().commonXYcells(ind,currentPoints);
			
			if(ind_xy_overlapping<best_xy_overlapping) {
				
				best_xy_overlapping = ind_xy_overlapping;
				best = ind;
			}
			
			
		}
		
		LOG.debug("Best: "+TriclusterUtilities.getInstance().individualToString(best)+"\n");
					
		LOG.debug("\n\n<<<<< nFitXYoverlapping\n\n");
		
		return best;

	}

}