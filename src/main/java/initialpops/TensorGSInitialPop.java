package initialpops;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.InitialPop;
import algcore.TriGen;
import strinitialpop.HierarchycalStrategy;
import strinitialpop.Tensor2DStrategy;

public class TensorGSInitialPop  implements InitialPop {
	
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(TensorGSInitialPop.class);

	private InitialPopStrategy tensor2D;
	private InitialPopStrategy hierarchy;

	public TensorGSInitialPop() {

		tensor2D = new Tensor2DStrategy ();
		hierarchy = new HierarchycalStrategy();

	}

	public List<AlgorithmIndividual> produceInitialPop() {
		
		int random_tensors = (int) (AlgorithmConfiguration.getInstance().getAle() * 
				AlgorithmConfiguration.getInstance().getI());
		
		int guided_tensors = AlgorithmConfiguration.getInstance().getI() - random_tensors;
	
		List<AlgorithmIndividual> initialPop = tensor2D.generateIndividuals(random_tensors,
				TriGen.getInstance().getIndividualClassName());
		
		List<AlgorithmIndividual> hierarchyPop =  hierarchy.generateIndividuals(guided_tensors,
				TriGen.getInstance().getIndividualClassName());
		
		initialPop.addAll(hierarchyPop);
		
		int rest = AlgorithmConfiguration.getInstance().getI() - initialPop.size();
		
		if (rest>0)
			initialPop.addAll(tensor2D.generateIndividuals(rest,
					TriGen.getInstance().getIndividualClassName()));
		
			
		return initialPop;

	}

}