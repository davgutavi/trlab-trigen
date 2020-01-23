package strinitialpop;

/**
 * Estrategia que crea Individuos de forma totalmete aleatoria. 
 * @author DAVID GUTIERREZ AVILES
 */

import initialpops.InitialPopStrategy;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.AlgorithmDataset;
import algutils.AlgorithmRandomUtilities;
import algutils.TriclusterUtilities;

public class TensorStrategy implements InitialPopStrategy {

	public List<AlgorithmIndividual> generateIndividuals(int numberOfIndividuals, String individualClassName) {

		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();
		AlgorithmConfiguration PARAM = AlgorithmConfiguration.getInstance();
		AlgorithmDataset DATOS = PARAM.getData();

		List<AlgorithmIndividual> l = new LinkedList<AlgorithmIndividual>();

		for (int i = 0; i < numberOfIndividuals; i++) {

			int coordenadaGen = ALEATORIOS.getFromInterval(0, DATOS.getGenSize() - 1);
			int coordenadaCondicion = ALEATORIOS.getFromInterval(0, DATOS.getSampleSize() - 1);
			int coordenadaTiempo = ALEATORIOS.getFromInterval(0, DATOS.getTimeSize() - 1);

			int tamG = ALEATORIOS.getFromInterval(PARAM.getMinG(), PARAM.getMaxG());
			int tamC = ALEATORIOS.getFromInterval(PARAM.getMinC(), PARAM.getMaxC());
			int tamT = ALEATORIOS.getFromInterval(PARAM.getMinT(), PARAM.getMaxT());

			int[] radios = TriclusterUtilities.getTensorLimits(coordenadaGen, coordenadaCondicion, coordenadaTiempo,
					tamG, tamC, tamT, DATOS.getGenSize(), DATOS.getSampleSize(), DATOS.getTimeSize());

			Collection<Integer> genes = new LinkedList<Integer>();
			for (int g = radios[0]; g <= radios[1]; g++)
				genes.add(new Integer(g));

			Collection<Integer> condiciones = new LinkedList<Integer>();
			for (int c = radios[2]; c <= radios[3]; c++)
				condiciones.add(new Integer(c));

			Collection<Integer> tiempos = new LinkedList<Integer>();
			for (int t = radios[4]; t <= radios[5]; t++)
				tiempos.add(new Integer(t));

			AlgorithmIndividual nuevo = null;
			try {
				nuevo = (AlgorithmIndividual) (Class.forName(individualClassName)).newInstance();
				nuevo.initialize(genes, condiciones, tiempos);

				nuevo.addEntry("from initial population: tensor");

				l.add(nuevo);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}

		return l;

	}// procedimiento

	@SuppressWarnings("unused")
	private void comprueba(AlgorithmIndividual nuevo) {
		AlgorithmConfiguration PARAM = AlgorithmConfiguration.getInstance();
		String cadena = "";
		if (nuevo.getGeneSize() < PARAM.getMinG() || nuevo.getGeneSize() > PARAM.getMaxG())
			cadena += " G = " + nuevo.getGeneSize();
		if (nuevo.getSampleSize() < PARAM.getMinC() || nuevo.getSampleSize() > PARAM.getMaxC())
			cadena += " C = " + nuevo.getSampleSize();
		if (nuevo.getTimeSize() < PARAM.getMinT() || nuevo.getTimeSize() > PARAM.getMaxT())
			cadena += " T = " + nuevo.getTimeSize();

		if (!cadena.equalsIgnoreCase(""))
			System.out.println(cadena);

	}

}
