package strcrossover;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import crossovers.CrossoverStrategy;
import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.TriGen;
import algutils.AlgorithmRandomUtilities;

public class RelaxedStrategy implements CrossoverStrategy {

	public AlgorithmIndividual[] cross(AlgorithmIndividual father, AlgorithmIndividual mother, String individualClassName) {

		AlgorithmIndividual[] r = new AlgorithmIndividual[1];

		father.addEntry("father ["+((TriGen.getInstance()).getOngoingGenerationIndex()+1)+"]");
		
		mother.addEntry("mother ["+((TriGen.getInstance()).getOngoingGenerationIndex()+1)+"]");
	
		int [] g = getChromosome(father.getGenes(), mother.getGenes(), 1);
		
		int [] c = getChromosome(father.getSamples(), mother.getSamples(), 2);
		
		int [] t = getChromosome(father.getTimes(), mother.getTimes(), 3);
		
		r[0] = buildIndividual(g, c, t, individualClassName);

		return r;

	}

	private AlgorithmIndividual buildIndividual (int[] g,int[] c, int[] t, String individualClassName){
		
		AlgorithmIndividual individuo = null;
		
		try {
			
			individuo = (AlgorithmIndividual) (Class.forName(individualClassName)).newInstance();
			
			List<Integer> genes = new LinkedList<Integer>();
			for (int i = 0;i<g.length;i++) genes.add(new Integer(g[i]));
			
			List<Integer> condiciones = new LinkedList<Integer>();
			for (int i = 0;i<c.length;i++) condiciones.add(new Integer(c[i]));
			
			List<Integer> tiempos = new LinkedList<Integer>();
			for (int i = 0;i<t.length;i++) tiempos.add(new Integer(t[i]));
		
			individuo.initialize(genes, condiciones, tiempos);

			individuo.addEntry("from crossover ["+((TriGen.getInstance()).getOngoingGenerationIndex()+1)+"]");
		
		}
		
		catch (InstantiationException e) {e.printStackTrace();} 
		catch (IllegalAccessException e) {e.printStackTrace();} 
		catch (ClassNotFoundException e) {e.printStackTrace();}
		
		return individuo;
	}
	
	
	
	private int [] getChromosome (Collection<Integer> father, Collection<Integer> mother, int componentType){
		
		AlgorithmRandomUtilities ALEAT = AlgorithmRandomUtilities.getInstance();
		
		AlgorithmConfiguration PARAM = AlgorithmConfiguration.getInstance();
		
		ALEAT.newBag();
		ALEAT.putMarbles(father);
		ALEAT.putMarbles(mother);
		
		int minimo = 0;
		
		int maximo = ALEAT.bagSize();
		
		if      (componentType==1)  {
			
			minimo = PARAM.getMinG();       //Minimo GENES
			
			if (maximo>PARAM.getMaxG()){
			
				maximo = PARAM.getMaxG();
			
			}
		}
		else if (componentType==2)  {
			minimo = PARAM.getMinC(); //Minimo CONDICIONES
			
			if (maximo>PARAM.getMaxC()){
				
				maximo = PARAM.getMaxC();
			
			}
		}
		else if (componentType==3) {
			minimo = PARAM.getMinT();	    //Minimo TIEMPOS
			
			if (maximo>PARAM.getMaxT()){
				
				maximo = PARAM.getMaxT();
			
			}
			
		}
		 		
		int tam = ALEAT.getFromInterval(minimo, maximo);
				
		int [] tirada = ALEAT.extractNmarbles(tam);
		
		return tirada;
	}
	
}