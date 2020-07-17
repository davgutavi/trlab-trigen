package algutils;

import input.algorithm.Implementation;
import algcore.Crossover;
import algcore.DataHierarchy;
import algcore.FitnessFunction;
import algcore.InitialPop;
import algcore.Mutation;
import algcore.Selection;
import algcore.SolutionCriterion;
import algcore.StoppingCriterion;
import algcore.TriGen;

public class TriGenBuilder {

	static private TriGenBuilder singleton = new TriGenBuilder();
	private TriGenBuilder() {}
	public static TriGenBuilder getInstance() {return singleton;}
	
	
	public void buildTriGen(Implementation implementation)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {

		InitialPop initialPop;
		FitnessFunction fitnessFunction;
		Selection selection;
		Crossover crossover;
		Mutation mutation;
		SolutionCriterion solCriterion;
		StoppingCriterion stopCriterion;

		initialPop = (InitialPop) (Class
				.forName(implementation.getInitialPop())).newInstance();
		fitnessFunction = (FitnessFunction) (Class.forName(implementation
				.getFitness())).newInstance();
		selection = (Selection) (Class.forName(implementation.getSelection()))
				.newInstance();
		crossover = (Crossover) (Class.forName(implementation.getCrossover()))
				.newInstance();
		mutation = (Mutation) (Class.forName(implementation.getMutation()))
				.newInstance();
		solCriterion = (SolutionCriterion) (Class.forName(implementation
				.getSolutionCriterion())).newInstance();
		stopCriterion = (StoppingCriterion) (Class.forName(implementation
				.getStoppingCriterion())).newInstance();

		TriGen alg = TriGen.getInstance();

		alg.set(implementation.getIndividual(), initialPop,
				fitnessFunction, selection, crossover, mutation, solCriterion,
				stopCriterion);

	}

	public DataHierarchy buildDataHierarchyV2(String type, int geneSize,
			int sampleSize, int timeSize) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {

		DataHierarchy j = (DataHierarchy) (Class.forName(type)).newInstance();

		j.initialize(geneSize, sampleSize, timeSize);

		return j;

	}

}
