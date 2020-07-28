package fitnessfunctions;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.FitnessFunction;
import algcore.TriGen;
import algutils.Point;
import algutils.TriclusterUtilities;
import strfitness.StrMsr3d;
import strfitness.StrTotalEvents;

public class Msr3dXYbias implements FitnessFunction {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(Msr3dXYbias.class);

	private static final double BIAS_VALUE = 25.0;

	private FitnessStrategy msr3d;
	private FitnessStrategy bias;

	public Msr3dXYbias() {
	
		msr3d = new StrMsr3d();
		bias = new StrTotalEvents();

	}

	public void evaluate(AlgorithmIndividual individual) {
				
		double total_events = bias.calculate(individual);
				
		double sizeValue = 0.0;
		double overlappingvalue = 0.0;
		double measure = Double.MAX_VALUE;
		double ffvalue = Double.MAX_VALUE;
		
		double wf = AlgorithmConfiguration.getInstance().getWf();
		double ws = AlgorithmConfiguration.getInstance().getWg();
		double wo = AlgorithmConfiguration.getInstance().getWog();
		
		
		double sizeXY = TriclusterUtilities.getInstance().toPoints(individual).size();
		double size = sizeXY/(AlgorithmConfiguration.getInstance().getMaxG()*AlgorithmConfiguration.getInstance().getMaxC());
		sizeValue = ws * size;
		
		individual.setSizeValue(sizeValue);

		
		Set<Point> currentPoints =  TriclusterUtilities.getInstance().toPoints(TriGen.getInstance().getSolutions());
		int commonXY = TriclusterUtilities.getInstance().commonXYcells(individual,currentPoints);
			
		double overlapping = 0;
		if(currentPoints.size()!=0)
			overlapping = commonXY/currentPoints.size();
		
		overlappingvalue = wo * overlapping;
		
		individual.setOverlappingValue(overlappingvalue);
		
		if (total_events>BIAS_VALUE) {
			
		   measure = msr3d.calculate(individual);
			
		   ffvalue = ((wf * measure) + (overlappingvalue) + (sizeValue))
					/ (wf + wo + ws);
			
			
		}
		

		individual.setFitnessValue(measure);		
		individual.setFitnessFunctionValue(ffvalue);

	}

}