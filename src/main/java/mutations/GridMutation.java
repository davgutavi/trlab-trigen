package mutations;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmIndividual;
import algcore.Mutation;
import algutils.AlgorithmRandomUtilities;
import strmutation.GridInsertBorderStrategy;
import strmutation.GridInsertPerimeterStrategy;
import strmutation.GridRemoveBorderStrategy;
import strmutation.GridRemovePerimeterStrategy;
import strmutation.TimeChangeStrategy;
import strmutation.TimeInsertionStrategy;
import strmutation.TimeRemovalStrategy;

public class GridMutation implements Mutation {
	
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(GridMutation.class);
	
	private MutationStrategy insertPerimeter;
	private MutationStrategy insertBorder;
	private MutationStrategy removePerimeter;
	private MutationStrategy removeBorder;

	private MutationStrategy insertTime;
	private MutationStrategy removeTime;
	private MutationStrategy changeTime;
	
	public GridMutation() {

		insertPerimeter = new GridInsertPerimeterStrategy();
		insertBorder = new GridInsertBorderStrategy();
		removePerimeter = new GridRemovePerimeterStrategy();
		removeBorder = new GridRemoveBorderStrategy();
		insertTime = new TimeInsertionStrategy();
		removeTime = new TimeRemovalStrategy();
		changeTime = new TimeChangeStrategy();

	}

	@Override
	public void mutate(AlgorithmIndividual individual) {
		
//		LOG.debug("");
//		LOG.debug("--> [B] X{"+individual.getSampleSize()+"} = "+individual.getSamples()+
//				" , Y{"+individual.getGeneSize()+"} = "+individual.getGenes());
		
		int op = AlgorithmRandomUtilities.getInstance().getFromInterval(1,4);
		
		if (op==1) 
			insertPerimeter.alter(individual);
		else if (op==2)
			insertBorder.alter(individual);
		else if (op==3)
			removePerimeter.alter(individual);
		else
			removeBorder.alter(individual);
		
		
		op = AlgorithmRandomUtilities.getInstance().getFromInterval(1,3);
		
		if (op==1) 
			insertTime.alter(individual);
		else if (op==2)
			removeTime.alter(individual);
		else
			changeTime.alter(individual);
		
		
//		LOG.debug("--> [A] X{"+individual.getSampleSize()+"} = "+individual.getSamples()+
//		" , Y{"+individual.getGeneSize()+"} = "+individual.getGenes());
//		LOG.debug("");
				
	}
	
	
}
