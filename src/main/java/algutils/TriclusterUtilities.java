package algutils;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import algcore.AlgorithmDataset;
import algcore.AlgorithmIndividual;

public class TriclusterUtilities {

	public static List<double[][][]> original(AlgorithmIndividual tricluster, AlgorithmDataset dataset) {

		List<double[][][]> res = new LinkedList<double[][][]>();

		Collection<Integer> genes = tricluster.getGenes();
		Collection<Integer> condiciones = tricluster.getSamples();
		Collection<Integer> tiempos = tricluster.getTimes();

		double[][][] cgt = buildCube(condiciones, genes, tiempos, dataset, 1);

		res.add(cgt);

		double[][][] tgc = buildCube(tiempos, genes, condiciones, dataset, 2);

		res.add(tgc);

		double[][][] gtc = buildCube(genes, tiempos, condiciones, dataset, 3);

		res.add(gtc);

		return res;

	}

	public static double[][][] buildGTCView(AlgorithmIndividual tricluster, AlgorithmDataset dataset) {

		return buildCube(tricluster.getGenes(), tricluster.getTimes(), tricluster.getSamples(), dataset, 3);

	}

	private static double[][][] buildCube(Collection<Integer> series, Collection<Integer> xAxis,
			Collection<Integer> graphics, AlgorithmDataset dataset, int type) {

		double[][][] res = new double[series.size()][xAxis.size()][graphics.size()];

		Iterator<Integer> igr = graphics.iterator();

		int i = 0;

		while (igr.hasNext()) {

			int j = 0;

			int grafico = (igr.next()).intValue();

			Iterator<Integer> ieje = xAxis.iterator();

			while (ieje.hasNext()) {

				int k = 0;

				int ejex = (ieje.next()).intValue();

				Iterator<Integer> iser = series.iterator();

				while (iser.hasNext()) {

					int serie = (iser.next()).intValue();

					double valor = 0;

					if (type == 1) {
						valor = dataset.getValue(ejex, serie, grafico);
					} else if (type == 2) {
						valor = dataset.getValue(ejex, grafico, serie);
					} else {
						valor = dataset.getValue(serie, grafico, ejex);
					}

					res[k][j][i] = valor;

					k++;

				}

				j++;

			}

			i++;

		}

		return res;

	}

	/**
	 * 
	 * @param yCoor Y coordinate of the center.
	 * @param xCoor X coordinate of the center.
	 * @param tCoor T coordinate of the center.
	 * @param yLen  Y length of the tensor.
	 * @param xLen  X length of the tensor.
	 * @param tLen  T length of the tensor.
	 * @param nR    Number of rows of the input dataset.
	 * @param nC    Number of columns of the input dataset.
	 * @param nT    Number of times of the input dataset.
	 * @return The upper [0] (Y), lower [1] (Y), left [2] (X), right [3] (X), front
	 *         [4] (T) and, depth [5] (T) indexes of the tensor.
	 */

	public static int[] getTensorLimits(int yCoor, int xCoor, int tCoor, int yLen, int xLen, int tLen, int nR, int nC, int nT) {

		int[] res = new int[6];
		int[] ylim = getCoordinateInterval(yCoor, yLen, nR);
		int[] xlim = getCoordinateInterval(xCoor, xLen, nC);
		int[] tlim = getCoordinateInterval(tCoor, tLen, nT);

		res[0] = ylim[0];
		res[1] = ylim[1];
		res[2] = xlim[0];
		res[3] = xlim[1];
		res[4] = tlim[0];
		res[5] = tlim[1];

		return res;
	}

	/**
	 * 
	 * @param centralValue  The central value of the interval.
	 * @param intervalLenth The length of the interval.
	 * @param maxValue      The maximum allowed value.
	 * @return The left [0] and the right [1] values of the interval.
	 */

	public static int[] getCoordinateInterval(int centralValue, int intervalLenth, int maxValue) {

		int[] res = new int[2];

		int numeroIzquierda = centralValue + 1;

		int numeroDerecha = maxValue - (centralValue + 1);

		int reparto1 = intervalLenth / 2;

		int reparto2 = intervalLenth - reparto1;

		boolean dispIzquierda = reparto1 <= numeroIzquierda;

		boolean dispDerecha = reparto2 <= numeroDerecha;

		if (dispIzquierda == false && dispDerecha == true) {

			res[0] = 0;
			res[1] = centralValue + reparto2 + (reparto1 - numeroIzquierda);

		}

		else if (dispIzquierda == true && dispDerecha == false) {

			res[0] = centralValue - (reparto1 - 1) - (reparto2 - numeroDerecha);
			res[1] = maxValue - 1;

		}

		else if (dispIzquierda == true && dispDerecha == true) {

			res[0] = centralValue - (reparto1 - 1);
			res[1] = centralValue + reparto2;

		}

		return res;

	}
	
	
	public static Collection<Integer> getIntervalComponent(int centralValue, int intervalLenth, int maxValue){
		
		int[] componentlim = getCoordinateInterval(centralValue, intervalLenth,maxValue);
		
		Collection<Integer> res = new LinkedList<Integer>();
	
		for (int i = componentlim[0]; i <= componentlim[1]; i++)
			res.add(new Integer(i));
		
		return res;
		
		
	}
	
	public static Collection<Integer> getDispersedRandomComponent (int numberOfCoordinates, Set<Integer> bagOfCoordinates) {
				
		Collection<Integer> res = new LinkedList<Integer>();
		
		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();
		
		ALEATORIOS.newBag();
		
		ALEATORIOS.putMarbles(bagOfCoordinates);
		
		int [] randomCoordinates = ALEATORIOS.extractNmarbles(numberOfCoordinates);
		
		for (int i = 0;i<randomCoordinates.length;i++){
			res.add(new Integer(randomCoordinates[i]));
		}
				
		return res;
				
	}
	

}
