package mutations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmIndividual;
import algcore.Mutation;
import algutils.AlgorithmRandomUtilities;
import strmutation.CondicionInsertionStrategy;
import strmutation.ConditionChangeStrategy;
import strmutation.ConditionRemovalStrategy;
import strmutation.GenChangeStrategy;
import strmutation.GenInsertionStrategy;
import strmutation.GenRemovalStrategy;
import strmutation.TimeSeriesInsertBothStrategy;
import strmutation.TimeSeriesInsertLeftStrategy;
import strmutation.TimeSeriesInsertRightStrategy;
import strmutation.TimeSeriesRemoveBothStrategy;
import strmutation.TimeSeriesRemoveLeftStrategy;
import strmutation.TimeSeriesRemoveRightStrategy;

public class TimeSeriesMutation implements Mutation {
	
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(TimeSeriesMutation.class);

	private MutationStrategy insertG;
	private MutationStrategy removeG;
	private MutationStrategy changeG;
	
	private MutationStrategy insertC;
	private MutationStrategy removeC;
	private MutationStrategy changeC;
		
	private MutationStrategy insertL;
	private MutationStrategy insertR;
	private MutationStrategy insertB;
	
	private MutationStrategy removeL;
	private MutationStrategy removeR;
	private MutationStrategy removeB;
		
	public TimeSeriesMutation() {
		 
		insertG = new GenInsertionStrategy();
		removeG = new GenRemovalStrategy();
		changeG = new GenChangeStrategy();
		
		insertC = new CondicionInsertionStrategy();
		removeC = new ConditionRemovalStrategy();
		changeC = new ConditionChangeStrategy();

		insertL = new TimeSeriesInsertLeftStrategy();
		insertR = new TimeSeriesInsertRightStrategy();
		insertB = new TimeSeriesInsertBothStrategy();
		
		removeL = new TimeSeriesRemoveLeftStrategy();
		removeR = new TimeSeriesRemoveRightStrategy();
		removeB = new TimeSeriesRemoveBothStrategy();

	}

	public void mutate(AlgorithmIndividual individual) {
		
		AlgorithmRandomUtilities randomSupport = AlgorithmRandomUtilities.getInstance();
		
		int op = randomSupport.getFromInterval(1,3);
		
		if (op==1) 
			insertG.alter(individual);
		else if (op==2)
			removeG.alter(individual);
		else
			changeG.alter(individual);
		
		op = randomSupport.getFromInterval(1,3);
		
		if (op==1) 
			insertC.alter(individual);
		else if (op==2)
			removeC.alter(individual);
		else
			changeC.alter(individual);
		
		op = randomSupport.getFromInterval(1,6);
		
		if (op==1) 
			insertL.alter(individual);
		else if (op==2)
			insertR.alter(individual);
		else if (op==3)
			insertB.alter(individual);
		else if (op==4)
			removeL.alter(individual);
		else if (op==5)
			removeR.alter(individual);
		else
			removeB.alter(individual);
	}

}