package fitnessfunctions;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.FitnessFunction;
import strfitness.StrMatching;
import strfitness.StrMslInstancets;
import strfitness.StrSizes;

public class MslInstanceTs implements FitnessFunction {

	// private static final Logger LOG = LoggerFactory.getLogger(Msl.class);

	private static final double HIGHEST_LSL_MULTI = 2.0 * Math.PI;

	private FitnessStrategy tamanyos;
	private FitnessStrategy solapamiento;
	private FitnessStrategy mslts;

	public MslInstanceTs() {

		tamanyos = new StrSizes();
		solapamiento = new StrMatching();
		mslts = new StrMslInstancets();

	}

	public void evaluate(AlgorithmIndividual individual) {

		AlgorithmConfiguration config = AlgorithmConfiguration.getInstance();

		double[] aux1 = ((StrSizes) tamanyos).getAmount(individual);

		double[] aux2 = ((StrMatching) solapamiento).getAmount(individual);

		double eval_base = mslts.calculate(individual);

		// double maximoValor = (3.0*Math.PI)/2.0;

		double maximoValor = HIGHEST_LSL_MULTI;

		double neval = eval_base / maximoValor;

		double wog = config.getWog();
		double woc = config.getWoc();
		double wot = config.getWot();

		double wg = config.getWg();
		double wc = config.getWc();
		double wt = config.getWt();

		double prg = aux1[0];
		double prc = aux1[1];
		double prt = aux1[2];

		double sg = aux2[0];
		double sc = aux2[1];
		double st = aux2[2];

		double wf = config.getWf();

		double cprg = 1.0 - prg;
		double cprc = 1.0 - prc;
		double cprt = 1.0 - prt;

		double fitness = ((wf * neval) + (wog * sg) + (woc * sc) + (wot * st)
				+ (cprg * wg) + (cprc * wc) + (cprt * wt))
				/ (wf + wog + woc + wot + wg + wc + wt);

		individual.setFitnessValue(neval);

		individual.setOverlappingValue((wog * sg) + (woc * sc) + (wot * st));

		individual.setSizeValue((cprg * wg) + (cprc * wc) + (cprt * wt));

		individual.setFitnessFunctionValue(fitness);

	}

}