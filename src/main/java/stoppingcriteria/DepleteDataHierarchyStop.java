package stoppingcriteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmConfiguration;
import algcore.StoppingCriterion;

public class DepleteDataHierarchyStop implements StoppingCriterion {
	
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(DepleteDataHierarchyStop.class);

	public boolean checkCriterion() {
			
		return AlgorithmConfiguration.getInstance().getDataHierarchy().isAvailable();
	
	}

}