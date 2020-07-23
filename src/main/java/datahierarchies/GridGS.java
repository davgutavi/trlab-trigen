package datahierarchies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.DataHierarchy;
import algutils.AlgorithmRandomUtilities;
import algutils.Point;
import algutils.TriclusterUtilities;

public class GridGS implements DataHierarchy {

//	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(GridGS.class);

	// X = Samples (columns), Y = Genes (rows)
	private Set<Point> grid;
	// K = time coordinate, V = visits
	private Map<Integer, Integer> times;

	private int x_Size;
	private int y_Size;
	private int t_Size;

	private int visited_points;

	public GridGS() {

		grid = new HashSet<Point>();
		times = new HashMap<Integer, Integer>();

	}

	public void initialize(int geneSize, int sampleSize, int timeSize) {

		x_Size = sampleSize;
		y_Size = geneSize;
		t_Size = timeSize;
		visited_points = 0;

		for (int x = 0; x < x_Size; x++)
			for (int y = 0; y < y_Size; y++)
				grid.add(new Point(x, y));

		for (int i = 0; i < t_Size; i++)
			times.put(new Integer(i), new Integer(0));

	}

	public void update(AlgorithmIndividual solution) {

		for (Integer x : solution.getSamples())
			for (Integer y : solution.getGenes()) {
				if(grid.remove(new Point(x, y)))
					visited_points++;
			}

		if (grid.isEmpty())
			for (int x = 0; x < x_Size; x++)
				for (int y = 0; y < y_Size; y++)
					grid.add(new Point(x, y));

		for (Integer t : solution.getTimes())
			times.put(t, new Integer(times.get(t).intValue() + 1));

	}

	public boolean isAvailable() {
	
		return visited_points < (x_Size * y_Size);
	}

	public ArrayList<ArrayList<Collection<Integer>>> build_gst_coorinates(int n) {
		
		LOG.debug("\n>>>>>GridGS.build_gst_coorinates\n");

		ArrayList<ArrayList<Collection<Integer>>> res = new ArrayList<ArrayList<Collection<Integer>>>(n);

		int nmarbles = n; 
		if(grid.size()<n)
			nmarbles = grid.size();
		
		
		AlgorithmRandomUtilities.getInstance().newPointsBag(grid);
		
		
		
		Point[] centers = AlgorithmRandomUtilities.getInstance().extractNpointMarbles(nmarbles);

		for (int i = 0; i < centers.length; i++) {

			// build genes and samples
			ArrayList<Collection<Integer>> components = new ArrayList<Collection<Integer>>(3);

			List<Integer> x = new LinkedList<Integer>();
			List<Integer> y = new LinkedList<Integer>();

			LOG.debug("\nCenter: " + centers[i]+"\n");

			int x_center = centers[i].getX();
			int y_center = centers[i].getY();

			int tamX = AlgorithmRandomUtilities.getInstance().getFromInterval(
					AlgorithmConfiguration.getInstance().getMinC(), AlgorithmConfiguration.getInstance().getMaxC());

			int tamY = AlgorithmRandomUtilities.getInstance().getFromInterval(
					AlgorithmConfiguration.getInstance().getMinG(), AlgorithmConfiguration.getInstance().getMaxG());

			int[] radios = TriclusterUtilities.getInstance().getTensor2DLimits(y_center, x_center, tamY, tamX,
					AlgorithmConfiguration.getInstance().getData().getGenSize(),
					AlgorithmConfiguration.getInstance().getData().getSampleSize());

//			LOG.debug("Radios: "+Arrays.toString(radios)+"\n");
			
			for (int iy = radios[0]; iy <= radios[1]; iy++)
				y.add(new Integer(iy));

			for (int ix = radios[2]; ix <= radios[3]; ix++)
				x.add(new Integer(ix));

			components.add(y);
			components.add(x);

			// build times
			components.add(buildHierarchicalList(AlgorithmRandomUtilities.getInstance().getFromInterval(
					AlgorithmConfiguration.getInstance().getMinT(), AlgorithmConfiguration.getInstance().getMaxT())));

			LOG.debug("x: "+x+" y:"+y+" t:"+components.get(2)+"\n");


			res.add(components);

		}
		
		LOG.debug("\n<<<<<GridGS.build_gst_coorinates\n\n");

		return res;
	}

	@Override
	public ArrayList<ArrayList<Collection<Integer>>> build_gs_coorinates(int n) {

		ArrayList<ArrayList<Collection<Integer>>> res = new ArrayList<ArrayList<Collection<Integer>>>(n);

		int nmarbles = n; 
		if(grid.size()<n)
			nmarbles = grid.size();
		
		AlgorithmRandomUtilities.getInstance().newPointsBag(grid);
		Point[] centers = AlgorithmRandomUtilities.getInstance().extractNpointMarbles(nmarbles);

		for (int i = 0; i < centers.length; i++) {

			// build genes and samples
			ArrayList<Collection<Integer>> components = new ArrayList<Collection<Integer>>(2);

			List<Integer> x = new LinkedList<Integer>();
			List<Integer> y = new LinkedList<Integer>();

			LOG.debug("Center: " + centers[i]);

			int x_center = centers[i].getX();
			int y_center = centers[i].getY();

			int tamX = AlgorithmRandomUtilities.getInstance().getFromInterval(
					AlgorithmConfiguration.getInstance().getMinC(), AlgorithmConfiguration.getInstance().getMaxC());

			int tamY = AlgorithmRandomUtilities.getInstance().getFromInterval(
					AlgorithmConfiguration.getInstance().getMinG(), AlgorithmConfiguration.getInstance().getMaxG());

			int[] radios = TriclusterUtilities.getInstance().getTensor2DLimits(y_center, x_center, tamY, tamX,
					AlgorithmConfiguration.getInstance().getData().getGenSize(),
					AlgorithmConfiguration.getInstance().getData().getSampleSize());

			for (int iy = radios[0]; iy <= radios[1]; iy++)
				y.add(new Integer(iy));

			for (int ix = radios[2]; ix <= radios[3]; ix++)
				x.add(new Integer(ix));

			components.add(y);
			components.add(x);

//				Collections.sort(x);
//				Collections.sort(y);
//				LOG.debug("X {"+x.size()+"}: "+x);
//				LOG.debug("Y {"+y.size()+"}: "+y);

			res.add(components);

		}

		return res;
	}

	@Override
	public ArrayList<Collection<Integer>> build_i_coorinates(int n, char type) {

		ArrayList<Collection<Integer>> res = new ArrayList<Collection<Integer>>(n);

		if (type == 'g' || type == 's' || type == 'c') {
			
			int nmarbles = n; 
			if(grid.size()<n)
				nmarbles = grid.size();

			AlgorithmRandomUtilities.getInstance().newPointsBag(grid);
			Point[] centers = AlgorithmRandomUtilities.getInstance().extractNpointMarbles(nmarbles);

			for (int i = 0; i < centers.length; i++) {

				int x_center = centers[i].getX();
				int y_center = centers[i].getY();

				int tamX = AlgorithmRandomUtilities.getInstance().getFromInterval(
						AlgorithmConfiguration.getInstance().getMinC(), AlgorithmConfiguration.getInstance().getMaxC());

				int tamY = AlgorithmRandomUtilities.getInstance().getFromInterval(
						AlgorithmConfiguration.getInstance().getMinG(), AlgorithmConfiguration.getInstance().getMaxG());

				int[] radios = TriclusterUtilities.getInstance().getTensor2DLimits(y_center, x_center, tamY, tamX,
						AlgorithmConfiguration.getInstance().getData().getGenSize(),
						AlgorithmConfiguration.getInstance().getData().getSampleSize());

				if (type == 'g') {
					List<Integer> y = new LinkedList<Integer>();
					for (int iy = radios[0]; iy <= radios[1]; iy++)
						y.add(new Integer(iy));
					res.add(y);
				} else if (type == 's' || type == 'c') {
					List<Integer> x = new LinkedList<Integer>();
					for (int ix = radios[2]; ix <= radios[3]; ix++)
						x.add(new Integer(ix));
					res.add(x);
				}

			}

		}

		else if (type == 't') {
			res.add(buildHierarchicalList(AlgorithmRandomUtilities.getInstance().getFromInterval(
					AlgorithmConfiguration.getInstance().getMinT(), AlgorithmConfiguration.getInstance().getMaxT())));

		}

		return res;
	}

	public double getPercentage() {

		double s = grid.size();
		double total = x_Size * y_Size;

		return 100.0d * s / total;

	}

	public Map<Integer, Integer> getTimeHierarchy() {

		Map<Integer, Integer> res = new HashMap<Integer, Integer>();
		res.put(new Integer(0), new Integer(0));
		return res;
	}

	public Map<Integer, Integer> getGenHierarchy() {
		Map<Integer, Integer> res = new HashMap<Integer, Integer>();
		res.put(new Integer(0), new Integer(0));
		return res;
	}

	public Map<Integer, Integer> getSampleHierarchy() {
		Map<Integer, Integer> res = new HashMap<Integer, Integer>();
		res.put(new Integer(0), new Integer(0));
		return res;
	}

	public String toString() {

		String r = "";

		for (int i = 0; i < y_Size; i++)
			for (int j = 0; j < x_Size; j++) {

				Point p = new Point(j, i);

				if (grid.contains(p))
					r += "0";
				else
					r += "1";

				if (j == (x_Size - 1))
					r += "\n";
				else
					r += " , ";

			}

		return r;

	}

	// ************************************** PRIVATE METHODS
	// **************************************

	private List<Integer> buildHierarchicalList(int size) {

		List<Integer> res = new LinkedList<Integer>();
		int faltan = size;
		int nivel = 0;

		while (faltan > 0) {

			List<Integer> lista_coordenadas_en_el_nivel = new LinkedList<Integer>();

			for (Entry<Integer, Integer> entry : times.entrySet())
				if (entry.getValue().intValue() == nivel)
					lista_coordenadas_en_el_nivel.add(new Integer(entry.getKey().intValue()));

			int tam = lista_coordenadas_en_el_nivel.size();

			if (tam < faltan) {
				faltan = faltan - tam;
				res.addAll(lista_coordenadas_en_el_nivel);
			} else if (tam == faltan) {
				faltan = 0;
				res.addAll(lista_coordenadas_en_el_nivel);
			} else {// tam>faltan

				AlgorithmRandomUtilities.getInstance().newBag();
				AlgorithmRandomUtilities.getInstance().putMarbles(lista_coordenadas_en_el_nivel);
				int[] timeMarbles = AlgorithmRandomUtilities.getInstance().extractNmarbles(faltan);

				for (int i = 0; i < faltan; i++)
					res.add(new Integer(timeMarbles[i]));
				
				faltan = 0;

			}

			nivel++;

		}

		return res;
	}

}
