package datahierarchies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.DataHierarchy;
import algutils.AlgorithmRandomUtilities;

public class LevelsGST implements DataHierarchy {

	// CLAVE = la coordenada (tiempo,gen o condicion) VALOR = el nivel de la
	// jerarquia
	private Map<Integer, Integer> jerarquia_tiempos;
	private Map<Integer, Integer> jerarquia_genes;
	private Map<Integer, Integer> jerarquia_condiciones;
	

	public LevelsGST() {
		jerarquia_tiempos = new HashMap<Integer, Integer>();
		jerarquia_genes = new HashMap<Integer, Integer>();
		jerarquia_condiciones = new HashMap<Integer, Integer>();
	}

	@Override
	public ArrayList<ArrayList<Collection<Integer>>> build_gst_coorinates(int n) {
		
		ArrayList<ArrayList<Collection<Integer>>> res = new ArrayList<ArrayList<Collection<Integer>>>(n);

		for (int i = 0; i < n; i++) {
			int gs = AlgorithmRandomUtilities.getInstance().
					getFromInterval(AlgorithmConfiguration.getInstance().getMinG(), 
							AlgorithmConfiguration.getInstance().getMaxG());
			int cs = AlgorithmRandomUtilities.getInstance().
					getFromInterval(AlgorithmConfiguration.getInstance().getMinC(), 
							AlgorithmConfiguration.getInstance().getMaxC());
			
			int ts = AlgorithmRandomUtilities.getInstance().
					getFromInterval(AlgorithmConfiguration.getInstance().getMinT(), 
							AlgorithmConfiguration.getInstance().getMaxT());

			List<Integer> gl = getHierarchicalListOfGenes(gs);
			List<Integer> cl = getHierarchicalListOfSamples(cs);
			List<Integer> tl = getHierarchicalListOfTimes(ts);
			
			ArrayList<Collection<Integer>> aux1 = new ArrayList<Collection<Integer>> (3);
			aux1.add(gl);
			aux1.add(cl);
			aux1.add(tl);
			
			res.add(aux1);
		}

		return res;
	}



	@Override
	public ArrayList<ArrayList<Collection<Integer>>> build_gs_coorinates(int n) {
		
		ArrayList<ArrayList<Collection<Integer>>> res = new ArrayList<ArrayList<Collection<Integer>>>(n);

		for (int i = 0; i < n; i++) {
			
			int gs = AlgorithmRandomUtilities.getInstance().
					getFromInterval(AlgorithmConfiguration.getInstance().getMinG(), 
							AlgorithmConfiguration.getInstance().getMaxG());
			int cs = AlgorithmRandomUtilities.getInstance().
					getFromInterval(AlgorithmConfiguration.getInstance().getMinC(), 
							AlgorithmConfiguration.getInstance().getMaxC());
			
			ArrayList<Collection<Integer>> aux1 = new ArrayList<Collection<Integer>> (2);
			
			List<Integer> gl = getHierarchicalListOfGenes(gs);
			List<Integer> cl = getHierarchicalListOfSamples(cs);
							
			aux1.add(gl);
			aux1.add(cl);
			res.add(aux1);

		}

		return res;
	}
	
	@Override
	public ArrayList<ArrayList<Collection<Integer>>> build_st_coorinates(int n) {
		
		ArrayList<ArrayList<Collection<Integer>>> res = new ArrayList<ArrayList<Collection<Integer>>>(n);

		for (int i = 0; i < n; i++) {
			
			int conditions_size = AlgorithmRandomUtilities.getInstance().
					getFromInterval(AlgorithmConfiguration.getInstance().getMinC(), 
							AlgorithmConfiguration.getInstance().getMaxC());
			
			int times_size = AlgorithmRandomUtilities.getInstance().
					getFromInterval(AlgorithmConfiguration.getInstance().getMinT(), 
							AlgorithmConfiguration.getInstance().getMaxT());
			
			
			ArrayList<Collection<Integer>> aux1 = new ArrayList<Collection<Integer>> (2);
			
			List<Integer> cl = getHierarchicalListOfSamples(conditions_size);
			List<Integer> tl = getHierarchicalListOfTimes(times_size);
			
			aux1.add(cl);				
			aux1.add(tl);
			res.add(aux1);

		}

		return res;
	}



	@Override
	public ArrayList<Collection<Integer>> build_i_coorinates(int n, char type) {
		
		ArrayList<Collection<Integer>> res = new ArrayList<Collection<Integer>>(n);
		
		for (int i = 0; i < n; i++) {
			
			
			List<Integer> l = null;
			
			if (type=='g') {
				int gs = AlgorithmRandomUtilities.getInstance().
						getFromInterval(AlgorithmConfiguration.getInstance().getMinG(), 
								AlgorithmConfiguration.getInstance().getMaxG());
				l = getHierarchicalListOfGenes(gs);
			}
			else if (type=='s') {
				int cs = AlgorithmRandomUtilities.getInstance().
						getFromInterval(AlgorithmConfiguration.getInstance().getMinC(), 
								AlgorithmConfiguration.getInstance().getMaxC());
				l = getHierarchicalListOfSamples(cs);
				
			}
			else if (type =='t') {
				int ts = AlgorithmRandomUtilities.getInstance().
						getFromInterval(AlgorithmConfiguration.getInstance().getMinT(), 
								AlgorithmConfiguration.getInstance().getMaxT());
				l = getHierarchicalListOfTimes(ts);
			}
							
			res.add(l);

		}

		return res;
	}

	
	public void initialize(int geneSize, int sampleSize, int timeSize) {

		// CLAVE = la coordenada (tiempo,gen o condicion) VALOR = el nivel de la
		// jerarquia
	

		for (int i = 0; i < timeSize; i++) {
			jerarquia_tiempos.put(new Integer(i), new Integer(0));
		}
		for (int i = 0; i < geneSize; i++) {
			jerarquia_genes.put(new Integer(i), new Integer(0));
		}
		for (int i = 0; i < sampleSize; i++) {
			jerarquia_condiciones.put(new Integer(i), new Integer(0));
		}

	}
	
	public boolean isAvailable() {
		boolean enc = false;

		Set<Entry<Integer, Integer>> c = jerarquia_condiciones.entrySet();

		for (Entry<Integer, Integer> ec : c) {

			if ((ec.getValue()).intValue() == 0) {
				enc = true;
			}

		}

		if (!enc) {

			Set<Entry<Integer, Integer>> t = jerarquia_tiempos.entrySet();

			for (Entry<Integer, Integer> et : t) {

				if ((et.getValue()).intValue() == 0) {
					enc = true;
				}

			}

		}

		if (!enc) {

			Set<Entry<Integer, Integer>> g = jerarquia_genes.entrySet();

			for (Entry<Integer, Integer> eg : g) {

				if ((eg.getValue()).intValue() == 0) {
					enc = true;
				}

			}
		}

		return enc;
	}


	public void update(AlgorithmIndividual solution) {

		
		for (Integer t: solution.getTimes()) 			
			jerarquia_tiempos.put(t, new Integer(jerarquia_tiempos.get(t).intValue()+1));
		
		for (Integer g: solution.getGenes()) 			
			jerarquia_genes.put(g, new Integer(jerarquia_genes.get(g).intValue()+1));
	
		for (Integer c: solution.getSamples()) 			
			jerarquia_condiciones.put(c, new Integer(jerarquia_condiciones.get(c).intValue()+1));


	}// actualiza_jerarquia
	

	public double getPercentage() {
		AlgorithmConfiguration param = AlgorithmConfiguration.getInstance();

		int ng = (param.getData()).getGenSize();
		int nc = (param.getData()).getSampleSize();
		int nt = (param.getData()).getTimeSize();

		Set<Entry<Integer, Integer>> g = jerarquia_genes.entrySet();

		double gcount = 0.0;

		for (Entry<Integer, Integer> eg : g) {

			if ((eg.getValue()).intValue() == 0) {
				gcount++;
			}

		}

		Set<Entry<Integer, Integer>> c = jerarquia_condiciones.entrySet();

		double ccount = 0.0;

		for (Entry<Integer, Integer> ec : c) {

			if ((ec.getValue()).intValue() == 0) {
				ccount++;
			}

		}

		Set<Entry<Integer, Integer>> t = jerarquia_tiempos.entrySet();

		double tcount = 0.0;

		for (Entry<Integer, Integer> et : t) {

			if ((et.getValue()).intValue() == 0) {
				tcount++;
			}

		}

		// LOG.debug("Count = "+gcount+","+ccount+","+tcount);
		// LOG.debug("Sizes = "+ng+","+nc+","+nt);
		double v = ((gcount + ccount + tcount) / (double) (ng + nc + nt)) * 100.0;

		return v;
	}

	

	public Map<Integer, Integer> getTimeHierarchy() {
		return jerarquia_tiempos;
	}

	public Map<Integer, Integer> getGenHierarchy() {
		return jerarquia_genes;
	}

	public Map<Integer, Integer> getSampleHierarchy() {
		return jerarquia_condiciones;
	}

	// ******************************************************************************************
	// AUXILIARES
	// ******************************************************************************************

	private List<Integer> getHierarchicalListOfTimes(int timeSize) {
		return buildHierarchicalList(timeSize, jerarquia_tiempos);
	}

	private List<Integer> getHierarchicalListOfGenes(int geneSize) {
		return buildHierarchicalList(geneSize, jerarquia_genes);
	}

	private List<Integer> getHierarchicalListOfSamples(int sampleSize) {
		return buildHierarchicalList(sampleSize, jerarquia_condiciones);
	}
	
	

	
	private List<Integer> buildHierarchicalList(int number, Map<Integer, Integer> hierarchy) {

		List<Integer> l = new LinkedList<Integer>();
		int faltan = number;
		int nivel = 0;

		while (faltan > 0) {
			// System.out.println(" Faltan = "+faltan+" Nivel = "+nivel);

			// System.out.println(" --P_TRES--");

			faltan = fillListWithNcoordinatesFromLevel(l, faltan, nivel, hierarchy);

			// System.out.println(" --F_TRES--");
			nivel++;
		}
		// System.out.println("Lista: "+l);
		return l;
	}

	// completa_lista_con_n_coordenadas_del_nivel

	private int fillListWithNcoordinatesFromLevel(List<Integer> l, int n, int level, Map<Integer, Integer> hierarchy) {

		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();

		int faltan = 0;

		List<Integer> lista_coordenadas_en_el_nivel = fillListWithCoordinatesFromLevel(level, hierarchy);

		// System.out.println(" Lista coordenadas nivel "+nivel+" :
		// "+lista_coordenadas_en_el_nivel);

		int tam = lista_coordenadas_en_el_nivel.size();

		if (tam < n) {
			// System.out.println(" Rama 1: tam = "+tam+",n = "+n);
			faltan = n - tam;
			l.addAll(lista_coordenadas_en_el_nivel);
		} else if (tam == n) {
			// System.out.println(" Rama 2: tam = "+tam+",n = "+n);
			faltan = 0;
			l.addAll(lista_coordenadas_en_el_nivel);
		} else {// tam>n
			// System.out.println(" Rama 3: tam = "+tam+",n = "+n);

			for (int i = 0; i < n; i++) {

				int indice = ALEATORIOS.getFromInterval(0, tam - 1);
				Integer coordenada = lista_coordenadas_en_el_nivel.get(indice);
				while (l.contains(coordenada)) {
					indice = ALEATORIOS.getFromInterval(0, tam - 1);
					coordenada = lista_coordenadas_en_el_nivel.get(indice);
				}
				l.add(coordenada);
			}
		}
		// System.out.println(" Lista : "+l);
		return faltan;
	}

	private List<Integer> fillListWithCoordinatesFromLevel(int level, Map<Integer, Integer> hierarchy) {

		List<Integer> l = new LinkedList<Integer>();

		Set<Entry<Integer, Integer>> conjunto = hierarchy.entrySet();
		Iterator<Entry<Integer, Integer>> it = conjunto.iterator();

		while (it.hasNext()) {

			Entry<Integer, Integer> entry = it.next();
			int valor = (entry.getValue()).intValue();
			if (valor == level) {

				// OJO: CONSTRUIMOS OBJETO NUEVO
				int key = (entry.getKey()).intValue();
				Integer coordenada = new Integer(key);
				l.add(coordenada);
				// OJO: CONSTRUIMOS OBJETO NUEVO

			}
		}

		return l;
	}













	

}// clase
