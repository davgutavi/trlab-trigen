package mutations;

import strmutation.CondicionInsertionStrategy;
import strmutation.ConditionChangeStrategy;
import strmutation.ConditionRemovalStrategy;
import strmutation.GenChangeStrategy;
import strmutation.GenInsertionStrategy;
import strmutation.GenRemovalStrategy;
//import strmutation.TimeChangeStrategy;
//import strmutation.TimeInsertionStrategy;
//import strmutation.TimeRemovalStrategy;
import algcore.AlgorithmIndividual;
import algcore.Mutation;
import algutils.AlgorithmRandomUtilities;

public class PutRemChanMutation implements Mutation {

	//private MutationStrategy quitar_tiempo;
	private MutationStrategy quitar_gen;
	private MutationStrategy quitar_condicion;
	//private MutationStrategy poner_tiempo;
	private MutationStrategy poner_gen;
	private MutationStrategy poner_condicion;
	private MutationStrategy cambiar_gen;
	private MutationStrategy cambiar_condicion;
	//private MutationStrategy cambiar_tiempo;
		
	public PutRemChanMutation() {

		//quitar_tiempo    = new TimeRemovalStrategy();
		quitar_gen       = new GenRemovalStrategy();
		quitar_condicion = new ConditionRemovalStrategy();
		//poner_tiempo     = new TimeInsertionStrategy();
		poner_gen        = new GenInsertionStrategy();
		poner_condicion  = new CondicionInsertionStrategy();
		cambiar_gen = new GenChangeStrategy();
		cambiar_condicion = new ConditionChangeStrategy();
		//cambiar_tiempo = new TimeChangeStrategy();

	}

	public void mutate(AlgorithmIndividual individual) {

		AlgorithmRandomUtilities randomSupport = AlgorithmRandomUtilities.getInstance();
		
		applyAlteration (individual,randomSupport.getFromInterval(1,6));

	}

	private void applyAlteration(AlgorithmIndividual individual, int type) {
		
		if      (type==1) quitar_gen.alter(individual);
		else if (type==2) quitar_condicion.alter(individual);
		else if (type==3) poner_gen.alter(individual);
		else if (type==4) poner_condicion.alter(individual);
		else if (type==5) cambiar_gen.alter(individual);
		else if (type==6) cambiar_condicion.alter(individual);
				
	}

}