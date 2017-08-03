package algutils;



import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import algcore.AlgorithmDataset;
import algcore.AlgorithmIndividual;

public class TransformationsUtilities {
	
	
	
	public static List<double [][][]> original (AlgorithmIndividual tricluster,AlgorithmDataset dataset){
	
		List<double [][][]> res = new LinkedList<double [][][]> (); 
		
		Collection<Integer> genes = tricluster.getGenes();
		Collection<Integer> condiciones = tricluster.getSamples();
		Collection<Integer> tiempos = tricluster.getTimes();
				
		double [][][] cgt = buildCube(condiciones,genes,tiempos,dataset,1);
		
		res.add(cgt);
				
		double [][][] tgc = buildCube(tiempos,genes,condiciones,dataset,2);
		
		res.add(tgc);
				
		double [][][] gtc = buildCube(genes,tiempos,condiciones,dataset,3);

		res.add(gtc);
		
		return res;

	}

	private static double [][][] buildCube (Collection<Integer> series, Collection<Integer> xAxis, Collection<Integer> graphics, AlgorithmDataset dataset,int type){
		
		double [][][] res = new double [series.size()][xAxis.size()][graphics.size()];
		
		Iterator<Integer> igr = graphics.iterator();
		
		int i = 0;
		
		while (igr.hasNext()){
			
			int j = 0;
			
			int grafico = (igr.next()).intValue();
			
			Iterator<Integer> ieje = xAxis.iterator();
			
			while (ieje.hasNext()){
				
				int k = 0;
				
				int ejex = (ieje.next()).intValue();
				
				Iterator<Integer> iser = series.iterator();
				
				while (iser.hasNext()){
					
					int serie = (iser.next()).intValue();
					
					double valor = 0;
					
					if (type==1){
						valor = dataset.getValue(ejex, serie, grafico);
					}
					else if (type==2){
						valor = dataset.getValue(ejex, grafico, serie);
					}
					else{
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
			
}
