package strfitness;

import java.util.Collection;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import fitnessfunctions.FitnessStrategy;

public class StrTotalEvents implements FitnessStrategy  {

	public double calculate(AlgorithmIndividual individual) {
		
		double r = 0.0;
		
		Collection<Integer> x_components = individual.getSamples();
		Collection<Integer> y_components = individual.getGenes();
		
			
		for (Integer x:x_components)
			for (Integer y:y_components)
				r+= AlgorithmConfiguration.getInstance().getData()
					.getValue(y,x,
							AlgorithmConfiguration.getInstance().getData().getTimeSize()-1);
		
		return r;
		
	}

}