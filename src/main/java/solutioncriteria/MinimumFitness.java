package solutioncriteria;

import java.util.Collections;
import java.util.List;

import algcore.AlgorithmIndividual;
import algcore.SolutionCriterion;

public class MinimumFitness implements SolutionCriterion {

	public AlgorithmIndividual chooseTheBest(List<AlgorithmIndividual> population) {

		AlgorithmIndividual mejor = Collections.min(population);
		
		return mejor;

	}

}