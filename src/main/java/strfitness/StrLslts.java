package strfitness;

import java.util.List;

import org.apache.commons.math3.stat.StatUtils;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmDataset;
import algcore.AlgorithmIndividual;
import algutils.FitnessUtilities;
import algutils.TriclusterUtilities;
import fitnessfunctions.FitnessStrategy;
import labutils.Conversions;

/**
 * Variation of {@link LslMsl} strategy calculating only the angles of tgc graphic view.
 * 
 * @author david
 *
 */
public class StrLslts implements FitnessStrategy  {

	public double calculate(AlgorithmIndividual individual) {

		AlgorithmDataset dataset = (AlgorithmConfiguration.getInstance()).getData();		
		
		double[][][] individualData = TriclusterUtilities.getInstance().buildGTCView(individual, dataset);
		
		double [][] slopesAngles = FitnessUtilities.getInstance().buildTableOfAngles(individualData);
				
		List<Double> delta = FitnessUtilities.getInstance().buildTableOfDifferencies(slopesAngles);

		return StatUtils.mean(Conversions.fromListOfDoubleToArray(delta));
	}

}