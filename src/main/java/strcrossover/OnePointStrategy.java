package strcrossover;

import java.util.Collection;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import crossovers.CrossoverStrategy;
import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.AlgorithmDataset;
import algcore.TriGen;
import algutils.AlgorithmRandomUtilities;

public class OnePointStrategy implements CrossoverStrategy {
	
//	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(OnePointStrategy.class);

	public AlgorithmIndividual[] cross(AlgorithmIndividual father, AlgorithmIndividual mother, String individualClassName) {
				
		AlgorithmIndividual[] r = new AlgorithmIndividual[2];

		father.addEntry("father ["+((TriGen.getInstance()).getOngoingGenerationIndex()+1)+"]");
		
		mother.addEntry("mother ["+((TriGen.getInstance()).getOngoingGenerationIndex()+1)+"]");
		
		int[] aux = getCrossoverIndexes(father, mother);
		
		int indGenesPadre       = aux[0];
		int indGenesMadre       = aux[1];
		int indCondicionesPadre = aux[2];
		int indCondicionesMadre = aux[3];
		int indTiemposPadre     = aux[4];
		int indTiemposMadre     = aux[5];
				
		Collection<Integer> genesHijo = buildComponent(father,mother,indGenesPadre,indGenesMadre,1,1);
		Collection<Integer> genesHija = buildComponent(father,mother,indGenesPadre,indGenesMadre,1,2);
		
		Collection<Integer> condicionesHijo = buildComponent(father,mother,indCondicionesPadre,indCondicionesMadre,2,1);
		Collection<Integer> condicionesHija = buildComponent(father,mother,indCondicionesPadre,indCondicionesMadre,2,2);
		
		Collection<Integer> tiemposHijo = buildComponent(father,mother,indTiemposPadre,indTiemposMadre,3,1);
		Collection<Integer> tiemposHija = buildComponent(father,mother,indTiemposPadre,indTiemposMadre,3,2);
		
		AlgorithmIndividual hijo = buildIndividual(genesHijo,condicionesHijo,tiemposHijo,individualClassName);
		AlgorithmIndividual hija = buildIndividual(genesHija,condicionesHija,tiemposHija,individualClassName);
		
		r[0] = (AlgorithmIndividual) hijo;
		r[1] = (AlgorithmIndividual) hija;
		
		return r;

	}
	
  private AlgorithmIndividual buildIndividual (Collection<Integer>  g,Collection<Integer>  c, Collection<Integer>  t, String individualClassName){
		
		AlgorithmIndividual individuo = null;
		
		try {
			LOG.debug("INdname="+individualClassName);
			individuo = (AlgorithmIndividual) (Class.forName(individualClassName)).newInstance();
			
			individuo.initialize(g,c,t);
			
			individuo.addEntry("from crossover ["+((TriGen.getInstance()).getOngoingGenerationIndex()+1)+"]");
		} 
		catch (InstantiationException e) {e.printStackTrace();} 
		catch (IllegalAccessException e) {e.printStackTrace();} 
		catch (ClassNotFoundException e) {e.printStackTrace();}
		
		return individuo;
	}
	
	
	private Collection<Integer> buildComponent (AlgorithmIndividual father, AlgorithmIndividual mother,
			int fatherIndex, int motherIndex, int componentType, int descendantType){
		
		AlgorithmConfiguration PARAM = AlgorithmConfiguration.getInstance();
		AlgorithmDataset      DATOS = PARAM.getData();
		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();
		
		Collection<Integer> componente = new LinkedList<Integer>();
		
		int minimo = 0;
		int maximo = 0;
		
		if      (componentType==1) {//GENES
			minimo = PARAM.getMinG();
			maximo = PARAM.getMaxG();
		}
		else if (componentType==2) {//CONDICIONES
			minimo = PARAM.getMinC();
			maximo = PARAM.getMaxC();
		}
		else if (componentType==3){//TIEMPOS
			minimo = PARAM.getMinT();
			maximo = PARAM.getMaxT();
		}
		
		//hijo = padre [inferior = 0        , superior = indPadre-1]   madre [inferior = indMadre , superior = size-1    ] 
		//hija = padre [inferior = indPadre , superior = size-1    ]   madre [inferior = 0        , superior = indMadre-1] 
		
		int infPadre = 0;
		int supPadre = 0;
		int infMadre = 0;
		int supMadre = 0;
		
		if      (descendantType==1){     //HIJO
			infPadre = 0;
			supPadre = fatherIndex;
			infMadre = motherIndex;
			if      (componentType==1) supMadre = mother.getGeneSize();       //GENES
			else if (componentType==2) supMadre = mother.getSampleSize(); //CONDICIONES
			else if (componentType==3) supMadre = mother.getTimeSize();     //TIEMPOS
						
		}
		else if (descendantType==2){//HIJA
			infPadre = fatherIndex;
			if      (componentType==1) supPadre = father.getGeneSize();       //GENES
			else if (componentType==2) supPadre = father.getSampleSize(); //CONDICIONES
			else if (componentType==3) supPadre = father.getTimeSize();     //TIEMPOS
			infMadre = 0;
			supMadre = motherIndex;
		}
		
		//CONSTRICCION
				
		for (int j = infPadre; j < supPadre; j++) {
			
			int v = 0;
			
			if      (componentType==1) v = father.getGene(j);       //GENES
			else if (componentType==2) v = father.getSample(j); //CONDICIONES
			else if (componentType==3) v = father.getTime(j);    //TIEMPOS
			
			Integer n = new Integer(v);
			
			if (!componente.contains(n)) componente.add(n);
		}
		
		for (int j = infMadre; j < supMadre; j++) {
			
			int v = 0;
			
			if      (componentType==1) v = mother.getGene(j);       //GENES
			else if (componentType==2) v = mother.getSample(j); //CONDICIONES
			else if (componentType==3) v = mother.getTime(j);    //TIEMPOS
			
			Integer n = new Integer(v);
			
			if (!componente.contains(n)) componente.add(n);
			
		}
		
		//RESPETAR TAMANYOS
		
		int difMin = minimo - componente.size();
		int difMax = componente.size() - maximo;
		
		
		
		//FALTAN?
		
		if (difMin > 0) {
			
			int tam = 0;
			if      (componentType==1) tam = DATOS.getGenSize();       //GENES
			else if (componentType==2) tam = DATOS.getSampleSize(); //CONDICIONES
			else if (componentType==3) tam = DATOS.getTimeSize();     //TIEMPOS
					
			ALEATORIOS.newBag(tam);
			
			for (int i = 0; i < difMin; i++) {
				
				int bola = ALEATORIOS.extractAmarble();
				
				Integer coordenada = new Integer(bola);
				
				while (componente.contains(coordenada)) {
					
					bola = ALEATORIOS.extractAmarble();
					coordenada = new Integer(bola);
				}
				
				componente.add(coordenada);
			}
		}

		//SOBRAN?
		
		if (difMax > 0) {
			
			ALEATORIOS.newBag(componente.size());
			
			for (int i = 0; i < difMax; i++) {
				
				int bola = ALEATORIOS.extractAmarble();
				componente.remove(bola);
			}
		}
				
		return componente;
		
	}
	
	
	

	private int[] getCrossoverIndexes(AlgorithmIndividual father, AlgorithmIndividual mother) {

		int[] r = new int[6]; //0: genes padre, 1: genes madre, 2: condiciones padre, 3: condiciones madre, 4: tiempos padre, 5: tiempos madre
		
		int [] g = getIndexesFromComponent(father.getGenes(),mother.getGenes(),1);
		
		int [] c = getIndexesFromComponent(father.getSamples(),mother.getSamples(),2);
		
		int [] t = getIndexesFromComponent(father.getTimes(),mother.getTimes(),3);
		
		r [0] = g[0];
		r [1] = g[1];
		r [2] = c[0];
		r [3] = c[1];
		r [4] = t[0];
		r [5] = t[1];		

		return r;
	}

	
	private int [] getIndexesFromComponent (Collection<Integer> fatherComponent, Collection<Integer> motherComponent, int componentType){
		
		AlgorithmConfiguration PARAM  = AlgorithmConfiguration.getInstance();
		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();
		
		int [] res = new int [2]; //0: indice padre, 1: indice madre
		
		int minimo = 0;
		
		if      (componentType==1)  {
			minimo = PARAM.getMinG();       //Minimo GENES
		}
		else if (componentType==2)  {
			minimo = PARAM.getMinC(); //Minimo CONDICIONES
		}
		else if (componentType==3) {
			minimo = PARAM.getMinT();	    //Minimo TIEMPOS
		}
				
		int indicePadre = 0;
		int indiceMadre = 0;
		
		int tamPadre = fatherComponent.size();
		int tamMadre = motherComponent.size();
		
		
		if (minimo != 1) { //IF 1
						
			if (tamPadre == tamMadre) { //IF 2
			
				double porcentaje = ALEATORIOS.getPercentage();
				
				indicePadre = (int) (porcentaje * tamPadre);
				indiceMadre = (int) (porcentaje * tamMadre);
				
				res[0] = indicePadre;
				res[1] = indiceMadre;
			
			} //IF 2
			
			else { //ELSE 2
				
				double porcentaje = ALEATORIOS.getPercentage();
				
				boolean correcto = true;
				
				if (porcentaje != 0.0) { //IF 3
					
					indicePadre = (int) (porcentaje * tamPadre);
					indiceMadre = (int) (porcentaje * tamMadre);
					
					int tamHijo = indicePadre + (tamMadre - indiceMadre);
					int tamHija = (tamPadre - indicePadre) + indiceMadre;
					
					if (tamHijo < minimo || tamHija < minimo)
						correcto = false;
				} //IF 3
						
				
				while (porcentaje == 0.0 || !correcto) { //WHILE 2

					porcentaje =  ALEATORIOS.getPercentage();
					
					//System.out.println("porcentaje = "+porcentaje);
					
					correcto = true;
					
					if (porcentaje != 0.0) { //IF 4
						
						indicePadre = (int) (porcentaje * tamPadre);
						indiceMadre = (int) (porcentaje * tamMadre);
						
						int tamHijo = indicePadre + (tamMadre - indiceMadre);
						int tamHija = (tamPadre - indicePadre) + indiceMadre;
						
//						System.out.println("tamPadre = "+tamPadre+" tamMadre = "+tamMadre);
//						
//						System.out.println("tamHijo = "+tamHijo+" tamHija = "+tamHija+" minimo = "+minimo);
												
						if (tamHijo < minimo || tamHija < minimo)
							correcto = false;
					} //IF 4
				
				}//WHILE 2
				
				res[0] = indicePadre;
				res[1] = indiceMadre;
			
			} //ELSE 2
			
		} //IF 1
		
		else { //ELSE 1
			
			double porcentaje = ALEATORIOS.getPercentage();
			
			indicePadre = (int) (porcentaje * tamPadre);
			indiceMadre = (int) (porcentaje * tamMadre);
			
			res[0] = indicePadre;
			res[1] = indiceMadre;
			
		} //ELSE 1
		
		
		return res;
		
	}
		
}