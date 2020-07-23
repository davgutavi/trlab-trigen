package fitnessfunctions;

import java.util.Set;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.FitnessFunction;
import algcore.TriGen;
import algutils.Point;
import algutils.TriclusterUtilities;
import strfitness.StrMslts;

public class MslXY implements FitnessFunction {

	// private static final Logger LOG = LoggerFactory.getLogger(Msl.class);

	private static final double HIGHEST_LSL_MULTI = 2.0 * Math.PI;

	private FitnessStrategy mslts;

	public MslXY() {
	
		mslts = new StrMslts();

	}

	public void evaluate(AlgorithmIndividual individual) {

		Set<Point> currentPoints =  TriclusterUtilities.getInstance().toPoints(TriGen.getInstance().getSolutions());
		int commonXY = TriclusterUtilities.getInstance().commonXYcells(individual,currentPoints);
		double sizeXY = TriclusterUtilities.getInstance().toPoints(individual).size();	
		
		double measure = mslts.calculate(individual)/HIGHEST_LSL_MULTI;		
		
		
		
		double overlapping = 0;
		if(currentPoints.size()!=0)
			overlapping = commonXY/currentPoints.size();		
		
		
		double size = sizeXY/(AlgorithmConfiguration.getInstance().getMaxG()*AlgorithmConfiguration.getInstance().getMaxC());

		double wf = AlgorithmConfiguration.getInstance().getWf();
		double wo = AlgorithmConfiguration.getInstance().getWog();
		double ws = AlgorithmConfiguration.getInstance().getWg();
				
		double fitness = ((wf * measure) + (wo * overlapping) + (ws * size))
				/ (wf + wo + ws);

		individual.setFitnessValue(measure);

		individual.setOverlappingValue(wo*overlapping);

		individual.setSizeValue(ws*size);

		individual.setFitnessFunctionValue(fitness);

	}

}