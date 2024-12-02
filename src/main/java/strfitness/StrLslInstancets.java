package strfitness;

import java.util.List;

import org.apache.commons.math3.stat.StatUtils;

import fitnessfunctions.FitnessStrategy;
import labutils.Conversions;
import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.AlgorithmDataset;
import algutils.FitnessUtilities;
import algutils.TriclusterUtilities;


/**
 * @author David Gutiérrez-Avilés
 */

public class StrLslInstancets implements FitnessStrategy  {
	
	public double calculate(AlgorithmIndividual individual) {

		double res = 0.0;
		
		AlgorithmDataset dataset = (AlgorithmConfiguration.getInstance()).getData();		
					
		double[][][] cgt_data = TriclusterUtilities.getInstance().buildCGTView(individual, dataset);	
		double [][] cgt_angulos = FitnessUtilities.getInstance().buildTableOfAngles(cgt_data);
		List<Double> cgt_delta = FitnessUtilities.getInstance().buildTableOfDifferencies(cgt_angulos);
						
		double[][][] tgc_data = TriclusterUtilities.getInstance().buildTGCView(individual, dataset);		
		double [][] tgc_angulos = FitnessUtilities.getInstance().buildTableOfAngles(tgc_data);				
		List<Double> tgc_delta = FitnessUtilities.getInstance().buildTableOfDifferencies(tgc_angulos);

		double [] views = new double[2];
		views [0] = StatUtils.mean(Conversions.fromListOfDoubleToArray(cgt_delta));
		views [1] = StatUtils.mean(Conversions.fromListOfDoubleToArray(tgc_delta));
		res = StatUtils.mean(views);
				
		return res;

	}

}