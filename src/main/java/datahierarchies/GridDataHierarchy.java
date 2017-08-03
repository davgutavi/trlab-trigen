package datahierarchies;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import algcore.AlgorithmIndividual;
import algcore.DataHierarchy;
import algutils.AlgorithmRandomUtilities;

public class GridDataHierarchy implements DataHierarchy {

	private SortedSet<Par> jerarquia;
	private String cadena_jerarquia;
	private int nGenes;
	private int nCondiciones;
	
	public GridDataHierarchy() {
					
	}
	
	public void  initializeDataHierarchy (int geneSize, int sampleSize, int timeSize) {
		
		initializeDataHierarchy(geneSize,sampleSize);
	}
	
	public void  initializeDataHierarchy (int geneSize, int sampleSize) {
		
		jerarquia = new TreeSet<Par> ();
		
		nGenes = geneSize;
		
		nCondiciones = sampleSize;
		
		cadena_jerarquia = "initial data hierarchy "+toString()+"\n";
		
		
	}
	


	

	public List<Integer> getHierarchicalListOfTimes(int timeSize) {
		
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

	public List<Integer> [] buildGenesAndSamples (int geneSize, int sampleSize){
		
		//System.out.println("Cosntruyendo");
		
		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();
		
		List<Integer> [] res = null;
		
		Set<Par> elegidos = new HashSet<Par>();
		
		int demandadas = geneSize*sampleSize;
		//int tam = jerarquia.size();
		//int libres = (nGenes*nCondiciones)-tam;
		
		//System.out.println("demandadas = "+demandadas+" tam = "+tam+" libres = "+libres+" entra? = "+(demandadas<libres));
		
		//if (demandadas<libres){
			
						
			int faltan = demandadas;		
			
			while (faltan>0){
								
				int g = ALEATORIOS.getFromInterval(0, nGenes);
				int c = ALEATORIOS.getFromInterval(0, nCondiciones);
				Par par = new Par(g,c);
					
				//System.out.println("!jerarquia.contains(par) = "+!jerarquia.contains(par));
				
				if (!jerarquia.contains(par)){
					
					boolean insertado = elegidos.add(par);
					
					if (insertado)	faltan--;
					
				}//contains
									
			}//while
			
		//}//if
		
		//System.out.println("Elegidos: "+elegidos);
		
		
		res = fromCellToList(elegidos);
		
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
	
	
	
	
		
	
	public void updateDataHierarchy(AlgorithmIndividual solution) {
		
		Iterator<Integer> it1 =  (solution.getGenes()).iterator();
		
		while (it1.hasNext()){
			
			int i = (it1.next()).intValue();
			
			Iterator<Integer> it2 = (solution.getSamples()).iterator();
			
			while (it2.hasNext()){
				
				int j = (it2.next()).intValue();
				
				Par aux = new Par(i,j);
								
				jerarquia.add(aux);
				
			}
			
		}
		
		
		appendHierarchicalString("");
		
	
	}

	public void appendHierarchicalString(String hierarchicalString) {

		this.cadena_jerarquia += "****************\n"+toString();
		

	}

	public String getHierarchicalString() {
		return cadena_jerarquia;
	}

	
	public String toString(){
		
		String res = "";
				
		Iterator<Par> it = jerarquia.iterator();
		
		//int i = 1;
		
		while (it.hasNext()){
			
			Par p = it.next();
			//if (i==12){
				res+="["+p.i+","+p.j+"]\n";
			//	i=1;
			//}
			//else{
			//	res+="["+p.i+","+p.j+"] ";
			//	i++;
			//s}
			
		}
			
		return res;
		
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


	
	public List<Integer>[] buildGenesAndSamples(int n) {
		//System.out.println("Cosntruyendo");
		
				AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();
		
				List<Integer> [] res = null;
				
				Set<Par> elegidos = new HashSet<Par>();
				
				int demandadas = n;
				//int tam = jerarquia.size();
				//int libres = (nGenes*nCondiciones)-tam;
				
				//System.out.println("demandadas = "+demandadas+" tam = "+tam+" libres = "+libres+" entra? = "+(demandadas<libres));
				
				//if (demandadas<=libres){
					
					
					int faltan = demandadas;		
					
					while (faltan>0){
										
						int g = ALEATORIOS.getFromInterval(0, nGenes);
						int c = ALEATORIOS.getFromInterval(0, nCondiciones);
						Par par = new Par(g,c);
							
						//System.out.println("!jerarquia.contains(par) = "+!jerarquia.contains(par));
						
						if (!jerarquia.contains(par)){
							
							boolean insertado = elegidos.add(par);
							
							if (insertado)	faltan--;
							
						}//contains
											
					}//while
					
				//}//if
				
				//System.out.println("Elegidos: "+elegidos);
							
				res = fromCellToList(elegidos);
				
				return res;
	}

	public int getSize() {
		// TODO Auto-generated method stub
		return jerarquia.size();
	}

	public boolean isAvailable() {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return " ";
	}

	
	public String getHierarchicalReport() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Map<Integer, Integer> getTimeHierarchy() {
		return null;
	}

	public Map<Integer, Integer> getGenHierarchy() {
		return null;
	}

	public Map<Integer, Integer> getSampleHierarchy() {
		return null;
	}
	
}