package algutils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import algcore.AlgorithmDataset;
import algcore.AlgorithmIndividual;

public class TriclusterUtilities {

//	@SuppressWarnings("unused")
//	private static final Logger LOG = LoggerFactory.getLogger(TriclusterUtilities.class);

	static private TriclusterUtilities singleton = new TriclusterUtilities();

	private TriclusterUtilities() {
	}

	public static TriclusterUtilities getInstance() {
		return singleton;
	}
	
	
	
	

// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>	
// >>>>>>>>>>>>>>>>>>>>>>>>> Fitness functions transformations >>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 
// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	public List<double[][][]> original(AlgorithmIndividual tricluster, AlgorithmDataset dataset) {

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
	
	public double[][][] buildCGTView(AlgorithmIndividual tricluster, AlgorithmDataset dataset) {

		return buildCube(tricluster.getSamples(), tricluster.getGenes(), tricluster.getTimes(), dataset, 1);

	}
	
	public double[][][] buildTGCView(AlgorithmIndividual tricluster, AlgorithmDataset dataset) {

		return buildCube(tricluster.getTimes(), tricluster.getGenes(), tricluster.getSamples(), dataset, 2);

	}

	public double[][][] buildGTCView(AlgorithmIndividual tricluster, AlgorithmDataset dataset) {

		return buildCube(tricluster.getGenes(), tricluster.getTimes(), tricluster.getSamples(), dataset, 3);

	}
	


	private double[][][] buildCube(Collection<Integer> series, Collection<Integer> xAxis, Collection<Integer> graphics,
			AlgorithmDataset dataset, int type) {

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

// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>	
// >>>>>>>>>>>>>>>>>>>>>>>>> Initial population utilities >>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 
// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

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

	public int[] getTensorLimits(int yCoor, int xCoor, int tCoor, int yLen, int xLen, int tLen, int nR, int nC,
			int nT) {

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
	
	public int[] getTensor2DLimits(int yCoor, int xCoor, int yLen, int xLen, int nR, int nC) {

		int[] res = new int[4];
		int[] ylim = getCoordinateInterval(yCoor, yLen, nR);
		int[] xlim = getCoordinateInterval(xCoor, xLen, nC);

		res[0] = ylim[0];
		res[1] = ylim[1];
		res[2] = xlim[0];
		res[3] = xlim[1];

		return res;
	}

	/**
	 * 
	 * @param centralValue  The central value of the interval.
	 * @param intervalLenth The length of the interval.
	 * @param maxValue      The maximum allowed value.
	 * @return The left [0] and the right [1] values of the interval.
	 */

	public int[] getCoordinateInterval(int centralValue, int intervalLenth, int maxValue) {

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

	public Collection<Integer> getIntervalComponent(int centralValue, int intervalLenth, int maxValue) {

		int[] componentlim = getCoordinateInterval(centralValue, intervalLenth, maxValue);

		Collection<Integer> res = new LinkedList<Integer>();

		for (int i = componentlim[0]; i <= componentlim[1]; i++)
			res.add(new Integer(i));

		return res;

	}

	public Collection<Integer> getDispersedRandomComponent(int numberOfCoordinates, Set<Integer> bagOfCoordinates) {

		Collection<Integer> res = new LinkedList<Integer>();

		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();

		ALEATORIOS.newBag();

		ALEATORIOS.putMarbles(bagOfCoordinates);

		int[] randomCoordinates = ALEATORIOS.extractNmarbles(numberOfCoordinates);

		for (int i = 0; i < randomCoordinates.length; i++) {
			res.add(new Integer(randomCoordinates[i]));
		}

		return res;

	}

// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<	
// <<<<<<<<<<<<<<<<<<<<<<<<< Initial population utilities <<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 
// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>	
// >>>>>>>>>>>>>>>>>>>>>>>>> Crossover utilities >>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 
// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	public AlgorithmIndividual[] getReproductivePair(List<AlgorithmIndividual> population) {

		AlgorithmRandomUtilities randomSupport = AlgorithmRandomUtilities.getInstance();

//		LOG.debug("Pop size ="+population.size());

		randomSupport.newBag(population.size());

		AlgorithmIndividual[] r = new AlgorithmIndividual[2];

		int fatherIndex = randomSupport.extractAmarble();

		int motherIndex = randomSupport.extractAmarble();

		r[0] = population.get(fatherIndex);

		r[1] = population.get(motherIndex);

		return r;

	}

	/**
	 * 
	 * @param father Chromosome from dad.
	 * @param mother Chromosome from mom.
	 * @param minL   minimum lenght of the new chromosome
	 * @param maxL   maximum length of the chromosome
	 * @return a new totaly random chromosome
	 */
	public int[] getRandomChromosome(Collection<Integer> father, Collection<Integer> mother, int minL, int maxL) {

		AlgorithmRandomUtilities ALEAT = AlgorithmRandomUtilities.getInstance();

		ALEAT.newBag();
		ALEAT.putMarbles(father);
		ALEAT.putMarbles(mother);

		int minimo = 0;
		int maximo = ALEAT.bagSize();

		minimo = minL;

		if (maximo > maxL)
			maximo = maxL;

		int tam = ALEAT.getFromInterval(minimo, maximo);

		return ALEAT.extractNmarbles(tam);
	}

	public Collection<Integer>[] buildOnePointComponents(Collection<Integer> fatherComponent,
			Collection<Integer> motherComponent, int minL, int maxL, int datasetComponentSize) {

		@SuppressWarnings("unchecked")
		Collection<Integer>[] childrensComponents = new Collection[2];

		int[] cutIndexes = getIndexesFromComponent(fatherComponent.size(), motherComponent.size(), minL);

		List<Integer> sonComponent = componentByIndexes(fatherComponent.toArray(new Integer[0]),
				motherComponent.toArray(new Integer[0]), 0, cutIndexes[0], cutIndexes[1], motherComponent.size());
		chekSizes(sonComponent, minL, maxL, datasetComponentSize);
		Collections.sort(sonComponent);

		List<Integer> daughterComponent = componentByIndexes(fatherComponent.toArray(new Integer[0]),
				motherComponent.toArray(new Integer[0]), cutIndexes[0], fatherComponent.size(), 0, cutIndexes[1]);
		chekSizes(daughterComponent, minL, maxL, datasetComponentSize);
		Collections.sort(daughterComponent);

		childrensComponents[0] = sonComponent;
		childrensComponents[1] = daughterComponent;

//		LOG.debug("\n");
//		LOG.debug("Componente Padre = "+fatherComponent.toString());
//		LOG.debug("Componente Madre = "+motherComponent.toString());
//		LOG.debug("Puntos de corte = "+TextUtilities.vectorOfIntToString(cutIndexes));
//		LOG.debug("Componente Hijo = "+sonComponent.toString());
//		LOG.debug("Componente Hija = "+daughterComponent.toString());
//		LOG.debug("\n");

		return childrensComponents;

	}

	private List<Integer> componentByIndexes(Integer[] fatherComponent, Integer[] motherComponent, int lowerFather,
			int upperFather, int lowerMother, int upperMother) {

		List<Integer> r = new LinkedList<Integer>();

		for (int j = lowerFather; j < upperFather; j++) {
			Integer n = new Integer(fatherComponent[j]);
			if (!r.contains(n))
				r.add(n);
		}

		for (int j = lowerMother; j < upperMother; j++) {

			Integer n = new Integer(motherComponent[j]);
			if (!r.contains(n))
				r.add(n);

		}

		return r;

	}

	private void chekSizes(List<Integer> component, int minL, int maxL, int datasetComponentSize) {

		AlgorithmRandomUtilities rndUtils = AlgorithmRandomUtilities.getInstance();

		int difMin = minL - component.size();
		int difMax = component.size() - maxL;

		if (difMin > 0) {

			rndUtils.newBag(datasetComponentSize);

			for (int i = 0; i < difMin; i++) {

				int bola = rndUtils.extractAmarble();
				Integer coordenada = new Integer(bola);

				while (component.contains(coordenada)) {

					bola = rndUtils.extractAmarble();
					coordenada = new Integer(bola);
				}

				component.add(coordenada);
			}
		}

		if (difMax > 0) {

			rndUtils.newBag(component.size());

			for (int i = 0; i < difMax; i++) {

				int bola = rndUtils.extractAmarble();
				component.remove(bola);
			}
		}

	}

	private int[] getIndexesFromComponent(int fatherSize, int motherSize, int minL) {

		int[] res = new int[2]; // 0: indice padre, 1: indice madre
		int indicePadre = 0;
		int indiceMadre = 0;

		if (minL != 1) {
			if (fatherSize == motherSize) {

				double porcentaje = AlgorithmRandomUtilities.getInstance().getPercentage();
				indicePadre = (int) (porcentaje * fatherSize);
				indiceMadre = (int) (porcentaje * motherSize);
				res[0] = indicePadre;
				res[1] = indiceMadre;

			} else {

				double porcentaje = AlgorithmRandomUtilities.getInstance().getPercentage();
				boolean correcto = true;

				if (porcentaje != 0.0) {

					indicePadre = (int) (porcentaje * fatherSize);
					indiceMadre = (int) (porcentaje * motherSize);
					int tamHijo = indicePadre + (motherSize - indiceMadre);
					int tamHija = (fatherSize - indicePadre) + indiceMadre;
					if (tamHijo < minL || tamHija < minL)
						correcto = false;
				}

				while (porcentaje == 0.0 || !correcto) {

					porcentaje = AlgorithmRandomUtilities.getInstance().getPercentage();
					correcto = true;

					if (porcentaje != 0.0) {
						indicePadre = (int) (porcentaje * fatherSize);
						indiceMadre = (int) (porcentaje * motherSize);
						int tamHijo = indicePadre + (motherSize - indiceMadre);
						int tamHija = (fatherSize - indicePadre) + indiceMadre;
						if (tamHijo < minL || tamHija < minL)
							correcto = false;
					}

				}
				res[0] = indicePadre;
				res[1] = indiceMadre;
			}

		} else {

			double porcentaje = AlgorithmRandomUtilities.getInstance().getPercentage();
			indicePadre = (int) (porcentaje * fatherSize);
			indiceMadre = (int) (porcentaje * motherSize);
			res[0] = indicePadre;
			res[1] = indiceMadre;

		}

		return res;

	}

	public Collection<Integer>[] buildTimeSeriesComponents(Collection<Integer> fatherComponent,
			Collection<Integer> motherComponent, int minL, int maxL) {

		@SuppressWarnings("unchecked")
		Collection<Integer>[] childrensComponents = new Collection[2];

		Set<Integer> f = new HashSet<Integer>(fatherComponent);
		Set<Integer> m = new HashSet<Integer>(motherComponent);

		Set<Integer> intersection = new HashSet<Integer>(f);
		intersection.retainAll(m);

		if (!intersection.isEmpty()) {

//			LOG.debug("-------------- INTERSECCIÓN");

			List<Integer> aux = new LinkedList<Integer>(intersection);
			Collections.sort(aux);

//			LOG.debug("Componente resultado = " + aux);

			checkTimeSeriesLimits(aux, minL, maxL);

			childrensComponents[0] = aux;
			childrensComponents[1] = aux;

		} else {

			List<Integer> lf = new ArrayList<Integer>(fatherComponent);
			List<Integer> lm = new ArrayList<Integer>(motherComponent);

			int from = Math.min(lf.get(lf.size() - 1).intValue(), lm.get(lm.size() - 1).intValue()) + 1;
			int to = Math.max(lf.get(0).intValue(), lm.get(0).intValue()) - 1;

//			LOG.debug("-------------- HUECOS");
//			LOG.debug("from = " + from + ",to = " + to);

			// Series temporales consecutivas
			if ((to - from) < 0) {

//				LOG.debug("-------------- HUECO CONSECUTIVO ");
//				LOG.debug("Componente padre = " + fatherComponent);
//				LOG.debug("Componente madre = " + motherComponent);

				List<Integer> aux = new LinkedList<Integer>(lf);

				aux.addAll(lm);
				Collections.sort(aux);

//				LOG.debug("Componente resultado = " + aux);

				checkTimeSeriesLimits(aux, minL, maxL);

				childrensComponents[0] = aux;
				childrensComponents[1] = aux;

			} else {

//				LOG.debug("-------------- HUECO NORMAL ");
//				LOG.debug("Componente padre = " + fatherComponent);
//				LOG.debug("Componente madre = " + motherComponent);

				List<Integer> aux = new LinkedList<Integer>();

				for (int i = from; i <= to; i++)
					aux.add(new Integer(i));

//				LOG.debug("Componente resultado = " + aux);

				checkTimeSeriesLimits(aux, minL, maxL);

				childrensComponents[0] = aux;
				childrensComponents[1] = aux;

			}

		}

		return childrensComponents;

	}

	private void checkTimeSeriesLimits(List<Integer> ts, int minL, int maxL) {

		if (ts.size() < minL) {

//			LOG.debug("************ Añadir");
			int from = ts.get(ts.size() - 1) + 1;
			int to = from + (minL - ts.size() - 1);
//			LOG.debug("from = " + from);
//			LOG.debug("to = " + to);
			for (int i = from; i <= to; i++)
				ts.add(new Integer(i));
//			LOG.debug("Componente corregida= " + ts);

		}

		else if (ts.size() > maxL) {

			int n = ts.size() - maxL;

			for (int i = 0; i < n; i++)
				((LinkedList<Integer>) ts).removeLast();

//			LOG.debug("************ Quitar " + n + " elementos");
//			LOG.debug("Componente corregida= " + ts);
		}

	}

// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<	
// <<<<<<<<<<<<<<<<<<<<<<<<< Crossover utilities <<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 
// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	// >>>>>>>>>>>>>>>>>>>>>>>>> Building individuals >>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public AlgorithmIndividual buildIndividual(Collection<Integer> g, Collection<Integer> c, Collection<Integer> t,
			String individualClassName, String entryLog) {
		AlgorithmIndividual individuo = null;
		try {

			individuo = (AlgorithmIndividual) (Class.forName(individualClassName)).newInstance();

			individuo.initialize(g, c, t);
			individuo.addEntry(entryLog);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return individuo;
	}
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	// <<<<<<<<<<<<<<<<<<<<<<<<< Building individuals <<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	// >>>>>>>>>>>>>>>>>>>>>>>>> Mutation utilities >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		// >>>>>>>>>>>>>>>>>>>>>>>>> Printing populations >>>>>>>>>>>>>>>>>>>>>>>>>>>>
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	
	public String populationToString (List<AlgorithmIndividual> population) {
		
		String r = "";
		
		int i = 1;
		
		for(AlgorithmIndividual ind:population) {
			
			r+="["+i+"] "+individualToString(ind);
			
			if (i<population.size())
				r+="\n";
			
			i++;
		}
				
		return r;
	}
	
	public String individualToString (AlgorithmIndividual individual) {
		
		String r = "";
		
//		r += "G(Y){"+individual.getGeneSize()+"}"+individual.getGenes()+
//				" ----- S(X){"+individual.getSampleSize()+"}"+individual.getSamples()+
//				" ----- T{"+individual.getTimeSize()+"}"+individual.getTimes();
		
		r += " X{"+individual.getSampleSize()+"} "+individual.getSamples()+
				"  Y {"+individual.getGeneSize()+"} "+individual.getGenes()+" F = "+individual.getFitnessFunctionValue();
		
		return r;
	}
	
	
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		// <<<<<<<<<<<<<<<<<<<<<<<<< Printing populations <<<<<<<<<<<<<<<<<<<<<<<<<<<<
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>	
	// >>>>>>>>>>>>>>>>>>>>>>>>> Solution Criterion >>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	public int commonXYcells (AlgorithmIndividual potentialIndividual, Set<Point> currentPoints) {
		
		Set<Point> intersection = new HashSet<Point>(currentPoints);
		intersection.retainAll(toPoints(potentialIndividual));
		
		return intersection.size();
			
	}

	
	public int commonXYcells (AlgorithmIndividual potentialIndividual, List<AlgorithmIndividual> currentPopulation) {
		
		Set<Point> intersection = new HashSet<Point>(toPoints(currentPopulation));
		intersection.retainAll(toPoints(potentialIndividual));
		
		return intersection.size();
			
	}
	
	
	public int commonXYcells (AlgorithmIndividual a, AlgorithmIndividual b) {
			
		Set<Point> intersection= new HashSet<Point>(toPoints(a));
		intersection.retainAll(toPoints(b));
		
		return intersection.size();
			
	}
	
	public Set<Point> toPoints (List<AlgorithmIndividual> currentPopulation){
		
		Set<Point> r = new HashSet<Point>();
		
		for (AlgorithmIndividual individual:currentPopulation) 
			r.addAll(toPoints(individual));
				
		return r;
	}
	
	

	public Set<Point> toPoints (AlgorithmIndividual individual){
		
		Set<Point> r = new HashSet<Point>();
		
		for (Integer x:individual.getSamples())
			for (Integer y:individual.getGenes())
				r.add(new Point(x.intValue(),y.intValue()));
				
		return r;
	}
	
	
	
	
	
	
	
	
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		// <<<<<<<<<<<<<<<<<<<<<<<<< Solution Criterion <<<<<<<<<<<<<<<<<<<<<<<<<<<<
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	
}
