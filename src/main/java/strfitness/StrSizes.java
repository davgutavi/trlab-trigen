package strfitness;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import fitnessfunctions.FitnessStrategy;

public class StrSizes  implements FitnessStrategy {

	
	public double calculate(AlgorithmIndividual individual) {
		return 0;
	}
	
	
	public double[] getAmount (AlgorithmIndividual individual){
		
		AlgorithmConfiguration PARAM  = AlgorithmConfiguration.getInstance();
		
		double[] r = new double[3];
				
		double ng = individual.getGeneSize();
		double nc = individual.getSampleSize();
		double nt = individual.getTimeSize();
		
		double tG = PARAM.getMaxG();
		double tC = PARAM.getMaxC();
		double tT = PARAM.getMaxT();
		
		r[0] = ng/tG;
		r[1] = nc/tC;
		r[2] = nt/tT;
			
		return r;
		
	}
	
}
