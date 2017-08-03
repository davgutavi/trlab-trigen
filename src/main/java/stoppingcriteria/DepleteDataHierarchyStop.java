package stoppingcriteria;

import algcore.AlgorithmConfiguration;
import algcore.DataHierarchy;
import algcore.StoppingCriterion;

public class DepleteDataHierarchyStop implements StoppingCriterion {

	public boolean checkCriterion() {
		
		
		AlgorithmConfiguration PARAM = AlgorithmConfiguration.getInstance();
		
		DataHierarchy JER = PARAM.getDataHierarchy();
		
		boolean res  = JER.isAvailable();
		
		//System.out.println("********************************************************************"+res);
		
		return res;
	
	}

}