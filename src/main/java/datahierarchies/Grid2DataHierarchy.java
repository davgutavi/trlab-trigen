package datahierarchies;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmDataset;
import algcore.AlgorithmIndividual;
import algcore.DataHierarchy;
import algcore.TriGen;
import algutils.AlgorithmRandomUtilities;
import general.RandomSupport;



public class Grid2DataHierarchy implements DataHierarchy {
	
	private static final Logger LOG = LoggerFactory.getLogger(Grid2DataHierarchy.class);

	private SortedSet<Par> jerarquia;
	private List<Integer> genes;
	private List<Integer> condiciones;
//	private String cadena_jerarquia;
	private boolean disponible;
	private AlgorithmIndividual resto;


	
	public Grid2DataHierarchy() {
				
	}
	
	public void  initializeDataHierarchy (int geneSize, int sampleSize, int timeSize) {
		
		initializeDataHierarchy(geneSize,sampleSize);
	}
	
	public void  initializeDataHierarchy (int geneSize, int sampleSize) {
		
		jerarquia = new TreeSet<Par> ();
		genes = new LinkedList<Integer>();
		condiciones = new LinkedList<Integer> ();
		disponible = true;
		
		
		for(int i=0;i<geneSize;i++){
			
			Integer auxi = new Integer(i);
			
			genes.add(auxi);
			
			for (int j=0;j<sampleSize;j++){
				
				Integer auxj = new Integer(j);
				Par auxp = new Par (i,j);
				if (!condiciones.contains(auxj)) condiciones.add(auxj);
				jerarquia.add(auxp);
			
			}
						
		}
		
//		cadena_jerarquia = "initial data hierarchy:\n\n"+toString()+"\n";
		
		
	}
	
	
	
	
	public void updateDataHierarchy(AlgorithmIndividual solution) {
		
		updateSet(solution);
		updateLists();
		updateState();
//		updateString();
		
		
	}
	
	private void updateSet (AlgorithmIndividual solution){
		
		Iterator<Integer> it1 =  (solution.getGenes()).iterator();
		
		while (it1.hasNext()){
			
			int i = (it1.next()).intValue();
			
			Iterator<Integer> it2 = (solution.getSamples()).iterator();
			
			while (it2.hasNext()){
				
				int j = (it2.next()).intValue();
				
				Par aux = new Par(i,j);
								
				jerarquia.remove(aux);
				
			}
			
		}
	}
	
	private void updateLists (){
		
		Iterator<Par> it = jerarquia.iterator();
		
		genes.clear();
		condiciones.clear();
		
		while (it.hasNext()){
			
			Par par = it.next();
			Integer i = new Integer (par.i);
			Integer j = new Integer (par.j);
			
			if (!genes.contains(i)) genes.add(i);
			if (!condiciones.contains(j)) condiciones.add(j);
		
		}
				
	}
	
	private void updateState(){
		
		AlgorithmConfiguration PARAM = AlgorithmConfiguration.getInstance();
		
		int minG = PARAM.getMinG();
		int minC = PARAM.getMinC();
		
		int tamG = genes.size();
		int tamC = condiciones.size();
		
		if (tamG<minG || tamC<minC) disponible = false;
		
		if (disponible==false) 	{
			System.out.println("**********************data hierarchy depleted!");
			buildRest();
		}
				
	}
	
	private void buildRest(){
		
		AlgorithmConfiguration PARAM = AlgorithmConfiguration.getInstance();
		AlgorithmDataset DATA = PARAM.getData();
		TriGen TRI = TriGen.getInstance();
		
		List<Integer> lt = new LinkedList<Integer>();
		for (int i=0;i<DATA.getTimeSize();i++) 	lt.add(new Integer(i));
		
		Collections.sort(lt);
		Collections.sort(genes);
		Collections.sort(condiciones);
		
		try {
			resto = (AlgorithmIndividual) (Class.forName(TRI.getIndividualClassName())).newInstance();
			resto.initialize(lt,genes,condiciones);
			resto.addEntry("data hierarchy remainder G = "+TRI.getOngoingSolutionIndex()+"\n");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
//	private void updateString (){
//		
//		TriGen TRI = TriGen.getInstance();
////		cadena_jerarquia = "data hierarchy for solution ( "+TRI.getOngoingSolutionIndex()+" ):\n\n"+toString()+"\n";
//		
//	}

	

	public List<Integer> [] buildGenesAndSamples (int geneSize, int sampleSize){
		
		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();
		
		List<Integer> [] res = null;
		
		int demandadas = geneSize*sampleSize;
		
		Set<Par> elegidos = new HashSet<Par>();
		
		int faltan = demandadas;		
		
		while (faltan>0){
							
			//from genes.size() to genes.size()-1
			int ig = ALEATORIOS.getFromInterval(0, genes.size()-1);
			//from condiciones.size() to condiciones.size()-1
			int ic = ALEATORIOS.getFromInterval(0, condiciones.size()-1);
			int g = genes.get(ig);
			int c = condiciones.get(ic);
			
			Par par = new Par(g,c);
				
			//System.out.println("jerarquia.contains(par) = "+jerarquia.contains(par));
			
			//System.out.println("Aleatorio = "+par);
			
			if (jerarquia.contains(par)){
				
				boolean insertado = elegidos.add(par);
				
				if (insertado)	faltan--;
				
			}//contains
								
		}//while
		
		
				
		res = fromCellToList(elegidos);
		
		List<Integer> gL = res[0];

		int rG = geneSize - gL.size();

		LOG.debug("rG = "+rG);
		
		if (rG > 0) {

			RandomSupport rnd = new RandomSupport();
			
			rnd.emptyMainBag();

			rnd.putMarblesIntoMainBag(genes);

			for (int i = 0; i < rG; i++) {

				boolean enc = false;

				while (!enc) {

					Integer iG = new Integer(rnd.extractAmarbleFromMainBag());

					if (!gL.contains(iG)) {
						gL.add(iG);
						enc = true;
					}

				}

			}
			
			Collections.sort(gL);

		}
		
		List<Integer> cL = res[1];

		int rC = sampleSize - cL.size();
		
		LOG.debug("rC = "+rC);
		
		if (rC > 0) {

			RandomSupport rnd = new RandomSupport();
			
			rnd.emptyMainBag();

			rnd.putMarblesIntoMainBag(condiciones);

			for (int i = 0; i < rC; i++) {

				boolean enc = false;

				while (!enc) {

					Integer iC = new Integer(rnd.extractAmarbleFromMainBag());

					if (!cL.contains(iC)) {
						cL.add(iC);
						enc = true;
					}

				}

			}
			
			Collections.sort(cL);

		}
		
//		System.out.println("Elegidos: "+elegidos);
//		System.out.println("G = "+res[0]);
//		System.out.println("C = "+res[1]);
	
		return res;
		
	}//construyeGenesYcondiciones
	
	
	private List<Integer> [] fromCellToList(Set<Par> cells){
		
		@SuppressWarnings("unchecked")
		List<Integer> [] res = new List [2];
		List<Integer> r1 = new LinkedList<Integer> ();
		List<Integer> r2 = new LinkedList<Integer> ();
		
		if (cells.size()!=0){
						
			Iterator<Par> it = cells.iterator();
			
			while (it.hasNext()){
				
				Par par = it.next();
				Integer i = new Integer(par.i);
				Integer j = new Integer(par.j);
				
				if (!r1.contains(i)) r1.add(i);
				
				if (!r2.contains(j)) r2.add(j);				
			
			}
					
		}
				
		res[0] = r1;
		res[1] = r2;
		
		return res;
		
	}
	
	
	public List<Integer>[] buildGenesAndSamples(int n) {
		
		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();
		
		List<Integer> [] res = null;
		
		int demandadas = n;
		
		Set<Par> elegidos = new HashSet<Par>();
		
		int faltan = demandadas;		
		
		while (faltan>0){
			
			int ig = ALEATORIOS.getFromInterval(0, genes.size());
			int ic = ALEATORIOS.getFromInterval(0, condiciones.size());
			
			int g = genes.get(ig);
			int c = condiciones.get(ic);
			
			Par par = new Par(g,c);
				
			//System.out.println("jerarquia.contains(par) = "+jerarquia.contains(par));
			
			if (jerarquia.contains(par)){
				
				boolean insertado = elegidos.add(par);
				
				if (insertado)	faltan--;
				
			}//contains
								
		}//while
		
		//System.out.println("Elegidos: "+elegidos);
				
		res = fromCellToList(elegidos);
		
	
		return res;
		
	}
	
	/////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	public AlgorithmIndividual toIndividual (){
		
		AlgorithmIndividual res = null;
		
		AlgorithmConfiguration PARAM = AlgorithmConfiguration.getInstance();
		AlgorithmDataset DATA = PARAM.getData();
		TriGen TRI = TriGen.getInstance();
		
		List<Integer> lt = new LinkedList<Integer>();
		for (int i=0;i<DATA.getTimeSize();i++) 	lt.add(new Integer(i));
		
		Collections.sort(lt);
		Collections.sort(genes);
		Collections.sort(condiciones);
		
		try {
			res = (AlgorithmIndividual) (Class.forName(TRI.getIndividualClassName())).newInstance();
			res.initialize(lt,genes,condiciones);
			res.addEntry("data hierarchy remainder\n");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	
	
	public AlgorithmIndividual getRest(){
		return resto;
	}
	
	
	public boolean isAvailable () {
		return disponible;
	}
	
		
	public int getAvailableGenes (){
		return genes.size();
	}
	
	public int getAvailableSamples (){
		return condiciones.size();
	}
	
	public String toString(){
		
		String res = "";
				
		res+="genes or rows ( "+genes.size()+" ) : "+genes+"\n";
		res+="conditions or columns ( "+condiciones.size()+" ) : "+condiciones+"\n";
				
		Iterator<Par> it = jerarquia.iterator();
		
		int i = 1;
		
		while (it.hasNext()){
			
			Par p = it.next();
			if (i==20){
				res+="["+p.i+","+p.j+"]\n";
				i=1;
			}
			else{
				res+="["+p.i+","+p.j+"] ";
				i++;
			}
			
		}
			
		return res;
		
	}
	
//	public void appendHierarchicalString(String hierarchicalString) {
////		this.cadena_jerarquia += "**************\n"+toString();
//	}

//	public String getHierarchicalString() {
//		return cadena_jerarquia;
//	}
	
	public int getSize() {
		return jerarquia.size();
	}
	
	public List<Integer> getHierarchicalListOfTimes(int timeSize) {
		System.out.println("EN GRID2DATAHIERARCHY");
		List<Integer> res = new LinkedList<Integer>();
		for (int i=0;i<timeSize;i++) 	res.add(new Integer(i));
		return res;
	}

	public List<Integer> getHierarchicalListOfGenes(int geneSize) {
		return null;	
	}

	public List<Integer> getHierarchicalListOfSamples(int sampleSize) {
		return null;
	}
	
	// ******************************************************************************************
	// AUXILIARES
	// ******************************************************************************************
		
		@SuppressWarnings("rawtypes")
		private class Par implements Comparable {
			
			public int i;
			public int j;
			
			public Par (int i, int j){
				this.i = i;
				this.j = j;
			}
			
			
			
			public boolean equals (Object o){
				
				Par otro = (Par)o;
							
				boolean res = false;
				
				if ( (this.i==otro.i)&&(this.j==otro.j) )
					res = true;
				
				return res;
			}

			public int compareTo(Object o) {
				
				Par otro = (Par)o;
				
				int res = 0;
				
				if      (this.i>otro.i){
					res = 1;
				}
				else if (this.i<otro.i){
					res = -1;
				}
				else{
					
					if      (this.j>otro.j){
						res = 1;
					}
					else if (this.j<otro.j){
						res = -1;
					}
					else{
						res = 0;
					}
					
				}
				
				return res;
			}
			
			public String toString (){
				
				String res = "";
				res = "["+i+","+j+"]";			
				return res;
				
			}
				
		}//Par

		
		public String getPercentage() {
			
			AlgorithmConfiguration PARAM = AlgorithmConfiguration.getInstance();
			AlgorithmDataset DATA = PARAM.getData();
			int tam = jerarquia.size();
			int total = DATA.getGenSize()*DATA.getSampleSize();
			float numero = 100.0f*tam/total;
			return "J = "+numero+" %";
		
		}

		
		public String getHierarchicalReport() {
			// TODO Auto-generated method stub
			return null;
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


}// clase

