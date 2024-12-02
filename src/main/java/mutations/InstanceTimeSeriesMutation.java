package mutations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmIndividual;
import algcore.Mutation;
import algutils.AlgorithmRandomUtilities;
import strmutation.CondicionInsertionStrategy;
import strmutation.ConditionChangeStrategy;
import strmutation.ConditionRemovalStrategy;
import strmutation.InstanceTimeSeriesInsertBothStrategy;
import strmutation.InstanceTimeSeriesInsertLeftStrategy;
import strmutation.InstanceTimeSeriesInsertRightStrategy;
import strmutation.InstanceTimeSeriesRemoveBothStrategy;
import strmutation.InstanceTimeSeriesRemoveLeftStrategy;
import strmutation.InstanceTimeSeriesRemoveRightStrategy;
import strmutation.TimeChangeStrategy;
import strmutation.TimeInsertionStrategy;
import strmutation.TimeRemovalStrategy;

public class InstanceTimeSeriesMutation implements Mutation {
	
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(TimeSeriesMutation.class);

	
	private MutationStrategy insert_instance_L;
	private MutationStrategy insert_instance_R;
	private MutationStrategy insert_instance_B;
	
	private MutationStrategy remove_instance_L;
	private MutationStrategy remove_instance_R;
	private MutationStrategy remove_instance_B;
	
	private MutationStrategy insertC;
	private MutationStrategy removeC;
	private MutationStrategy changeC;
	
	private MutationStrategy insertT;
	private MutationStrategy removeT;
	private MutationStrategy changeT;
		
	public InstanceTimeSeriesMutation() {
		
		insert_instance_L = new InstanceTimeSeriesInsertLeftStrategy();
		insert_instance_R = new InstanceTimeSeriesInsertRightStrategy();
		insert_instance_B = new InstanceTimeSeriesInsertBothStrategy();
		
		remove_instance_L = new InstanceTimeSeriesRemoveLeftStrategy();
		remove_instance_R = new InstanceTimeSeriesRemoveRightStrategy();
		remove_instance_B = new InstanceTimeSeriesRemoveBothStrategy();
		
		insertC = new CondicionInsertionStrategy();
		removeC = new ConditionRemovalStrategy();
		changeC = new ConditionChangeStrategy();
		 
		insertT = new TimeInsertionStrategy();
		removeT = new TimeRemovalStrategy();
		changeT = new TimeChangeStrategy();
		
	}

	public void mutate(AlgorithmIndividual individual) {
		
		AlgorithmRandomUtilities randomSupport = AlgorithmRandomUtilities.getInstance();
		
		int op = randomSupport.getFromInterval(1,6);
		
		if (op==1) 
			insert_instance_L.alter(individual);
		else if (op==2)
			insert_instance_R.alter(individual);
		else if (op==3)
			insert_instance_B.alter(individual);
		else if (op==4)
			remove_instance_L.alter(individual);
		else if (op==5)
			remove_instance_R.alter(individual);
		else
			remove_instance_B.alter(individual);
		
		op = randomSupport.getFromInterval(1,3);
		
		if (op==1) 
			insertC.alter(individual);
		else if (op==2)
			removeC.alter(individual);
		else
			changeC.alter(individual);
		
		
		op = randomSupport.getFromInterval(1,3);
		
		if (op==1) 
			insertT.alter(individual);
		else if (op==2)
			removeT.alter(individual);
		else
			changeT.alter(individual);
		
		
		
	}

}
