package datahierarchies;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmDataset;
import algcore.AlgorithmIndividual;
import algcore.DataHierarchy;
import algcore.TriGen;
import algutils.AlgorithmRandomUtilities;
import strinitialpop.TensorStrategy;
import utils.TextUtilities;

public class LevelDataHierarchy implements DataHierarchy {

	// CLAVE = la coordenada (tiempo,gen o condicion) VALOR = el nivel de la
	// jerarquia
	private Map<Integer, Integer> jerarquia_tiempos;
	private Map<Integer, Integer> jerarquia_genes;
	private Map<Integer, Integer> jerarquia_condiciones;
	private String cadena_jerarquia;

	public LevelDataHierarchy() {

	}

	public void initializeDataHierarchy(int geneSize, int sampleSize, int timeSize) {

		// CLAVE = la coordenada (tiempo,gen o condicion) VALOR = el nivel de la
		// jerarquia
		jerarquia_tiempos = new HashMap<Integer, Integer>();
		jerarquia_genes = new HashMap<Integer, Integer>();
		jerarquia_condiciones = new HashMap<Integer, Integer>();

		for (int i = 0; i < timeSize; i++) {
			jerarquia_tiempos.put(new Integer(i), new Integer(0));
		}
		for (int i = 0; i < geneSize; i++) {
			jerarquia_genes.put(new Integer(i), new Integer(0));
		}
		for (int i = 0; i < sampleSize; i++) {
			jerarquia_condiciones.put(new Integer(i), new Integer(0));
		}

		// cadena_jerarquia = "\nJerarquía Inicial:\n";
		// cadena_jerarquia += Pantalla.imprimeJerarquia("Tiempos",
		// jerarquia_tiempos);
		// cadena_jerarquia += Pantalla.imprimeJerarquia("Condiciones",
		// jerarquia_condiciones);
		// cadena_jerarquia += Pantalla.imprimeJerarquia("Genes",
		// jerarquia_genes);

	}

	public List<Integer> getHierarchicalListOfTimes(int timeSize) {
		
		//return buildHierarchicalList(timeSize, jerarquia_tiempos);
		/*ANTIGUO LAURAList<Integer> l_times = new LinkedList<Integer>();
		// Se crea List<Integer> con los valores del tiempo desde "el ultimo valor del tiempo - el valor que queremos que tenga la ventana" hasta "el ultimo valor del tiempo"
		for (int i=jerarquia_tiempos.size()-timeSize;i<jerarquia_tiempos.size();i++){
			l_times.add(i);
		}		
		System.out.println("LISTA RESULTADO TIMES JERARQUICA: "+l_times);
		return l_times;*/
		//PRUEBA16/05/2019 LAURA
		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();
		
		AlgorithmConfiguration PARAM      = AlgorithmConfiguration.getInstance();
		AlgorithmDataset      DATOS      = PARAM.getData();
		int tamT = ALEATORIOS.getFromInterval(PARAM.getMinT(),PARAM.getMaxT());
		int [] tiempo = TensorStrategy.fronteras (timeSize,tamT,DATOS.getTimeSize());
		
		List<Integer> list_t = new LinkedList<Integer>();
		for (int g = tiempo[0];g<=tiempo[1];g++) 
			list_t.add(new Integer (g));

		return list_t;
	}

	public List<Integer> getHierarchicalListOfGenes(int geneSize) {
		return buildHierarchicalList(geneSize, jerarquia_genes);
	}

	public List<Integer> getHierarchicalListOfSamples(int sampleSize) {
		return buildHierarchicalList(sampleSize, jerarquia_condiciones);
	}

	public void updateDataHierarchy(AlgorithmIndividual solution) {

		TriGen estado = TriGen.getInstance();
		String aux = "##########data hierarchy " + (estado.getOngoingSolutionIndex() + 1) + ":\n";

		Iterator<Integer> it_tiempos = (solution.getTimes()).iterator();

		while (it_tiempos.hasNext()) {

			Integer k = it_tiempos.next();
			Integer v = jerarquia_tiempos.get(k);
			Integer nv = new Integer(v.intValue() + 1);
			jerarquia_tiempos.put(k, nv);

		}

		Iterator<Integer> it_genes = (solution.getGenes()).iterator();

		while (it_genes.hasNext()) {

			Integer k = it_genes.next();
			Integer v = jerarquia_genes.get(k);
			Integer nv = new Integer(v.intValue() + 1);
			jerarquia_genes.put(k, nv);

		}

		Iterator<Integer> it_condiciones = (solution.getSamples()).iterator();

		while (it_condiciones.hasNext()) {

			Integer k = it_condiciones.next();
			Integer v = jerarquia_condiciones.get(k);
			Integer nv = new Integer(v.intValue() + 1);
			jerarquia_condiciones.put(k, nv);

		}

		// aux += Pantalla.imprimeJerarquia("Tiempos", jerarquia_tiempos);
		// aux += "\n";
		// aux += Pantalla.imprimeJerarquia("Condiciones",
		// jerarquia_condiciones);
		// aux += "\n";
		// aux += Pantalla.imprimeJerarquia("Genes", jerarquia_genes);

		appendHierarchicalString(aux);

	}// actualiza_jerarquia

	public void appendHierarchicalString(String hierarchicalString) {

		this.cadena_jerarquia += "*************\n\n\n\n" + hierarchicalString;

	}

	public String getHierarchicalString() {
		return cadena_jerarquia;
	}

	// ******************************************************************************************
	// AUXILIARES
	// ******************************************************************************************

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

	public List<Integer>[] buildGenesAndSamples(int geneSize, int sampleSize) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Integer>[] buildGenesAndSamples(int n) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
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

	public int getAvailableGenes() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getAvailableSamples() {
		// TODO Auto-generated method stub
		return 0;
	}

	public AlgorithmIndividual getRest() {
		// TODO Auto-generated method stub
		return null;
	}

	public AlgorithmIndividual toIndividual() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPercentage() {
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

		DecimalFormat f = TextUtilities.getDecimalFormat('.', "#.##");

		return "J=" + f.format(v) + "%";
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

	public String getHierarchicalReport() {

		String res = "";

		List<int[]> tamG = numberOfElementsForEachLevel(jerarquia_genes);
		List<int[]> tamC = numberOfElementsForEachLevel(jerarquia_condiciones);
		List<int[]> tamT = numberOfElementsForEachLevel(jerarquia_tiempos);

		res = "genes hierarchy:\t";

		Iterator<int[]> ig = tamG.iterator();
		while (ig.hasNext()) {
			int[] next = ig.next();
			res += "level " + next[0] + "->" + next[1] + "\t";
		}

		res += "\n";

		res += "conditions hierarchy:\t";

		Iterator<int[]> ic = tamC.iterator();
		while (ic.hasNext()) {
			int[] next = ic.next();
			res += "level " + next[0] + "->" + next[1] + "\t";
		}

		res += "\n";

		res += "times hierarchy:\t";

		Iterator<int[]> it = tamT.iterator();
		while (it.hasNext()) {
			int[] next = it.next();
			res += "level " + next[0] + "->" + next[1] + "\t";
		}

		return res;
	}

	private List<int[]> numberOfElementsForEachLevel(Map<Integer, Integer> hierarchy) {

		Set<Entry<Integer, Integer>> conjunto = hierarchy.entrySet();

		Set<Integer> niveles = new HashSet<Integer>(hierarchy.values());

		List<int[]> res = new LinkedList<int[]>();

		Iterator<Integer> itNiveles = niveles.iterator();

		while (itNiveles.hasNext()) {

			Integer nivel = itNiveles.next();

			int cont = 0;

			Iterator<Entry<Integer, Integer>> itConjunto = conjunto.iterator();
			while (itConjunto.hasNext()) {
				Entry<Integer, Integer> next = itConjunto.next();
				if (next.getValue().equals(nivel))
					cont++;
			}

			// res[nivel.intValue()] = cont;

			res.add(new int[] { nivel.intValue(), cont });

		}

		return res;
	}

}// clase
