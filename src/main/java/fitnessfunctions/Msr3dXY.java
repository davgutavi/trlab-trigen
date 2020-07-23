package fitnessfunctions;

import java.util.Set;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.FitnessFunction;
import algcore.TriGen;
import algutils.Point;
import algutils.TriclusterUtilities;
import strfitness.StrMsr3d;

public class Msr3dXY implements FitnessFunction {

	// private static final Logger LOG = LoggerFactory.getLogger(Msl.class);

	private static final double HIGHEST_LSL_MULTI = 2.0 * Math.PI;

	private FitnessStrategy mslts;

	public Msr3dXY() {
	
		mslts = new StrMsr3d();

	}

	public void evaluate(AlgorithmIndividual individual) {

		Set<Point> currentPoints =  TriclusterUtilities.getInstance().toPoints(TriGen.getInstance().getSolutions());
		int commonXY = TriclusterUtilities.getInstance().commonXYcells(individual,currentPoints);
		double sizeXY = TriclusterUtilities.getInstance().toPoints(individual).size();	
		
		double measure = mslts.calculate(individual)/HIGHEST_LSL_MULTI;		
		double overlapping = commonXY/currentPoints.size();		
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