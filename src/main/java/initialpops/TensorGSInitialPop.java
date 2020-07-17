package initialpops;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.InitialPop;
import algcore.TriGen;
import strinitialpop.Tensor2DStrategy;

public class TensorGSInitialPop  implements InitialPop {
	
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(TensorGSInitialPop.class);

	private InitialPopStrategy tensor2D;

	public TensorGSInitialPop() {

		tensor2D = new Tensor2DStrategy ();

	}

	public List<AlgorithmIndividual> produceInitialPop() {

		List<AlgorithmIndividual> l = tensor2D.generateIndividuals(AlgorithmConfiguration.getInstance().getI(),
				TriGen.getInstance().getIndividualClassName());
		
//		LOG.debug("--> Initial Pop: "+l);
		
		return l;

	}

}