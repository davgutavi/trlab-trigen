package fitnessfunctions;

import strfitness.StrMatching;
import strfitness.StrMsr3d;
import strfitness.StrSizes;
import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.FitnessFunction;

public class Msr3d implements FitnessFunction {

	private FitnessStrategy tamanyos;
	private FitnessStrategy solapamiento;
	private FitnessStrategy msr3d;

	public Msr3d() {

		tamanyos = new StrSizes();
		solapamiento = new StrMatching();
		msr3d = new StrMsr3d();

	}

	public void evaluate(AlgorithmIndividual individual) {

		AlgorithmConfiguration config = AlgorithmConfiguration.getInstance();

		double[] aux1 = ((StrSizes) tamanyos).getAmount(individual);

		double[] aux2 = ((StrMatching) solapamiento).getAmount(individual);

		double neval = msr3d.calculate(individual);

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