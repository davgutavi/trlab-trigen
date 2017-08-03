package strinitialpop;

/**
 * Estrategia que crea Individuos de forma totalmete aleatoria. 
 * @author DAVID GUTIERREZ AVILES
 */

import initialpops.InitialPopStrategy;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.AlgorithmDataset;
import algutils.AlgorithmRandomUtilities;

public class RandomStrategy implements InitialPopStrategy {

	public List<AlgorithmIndividual> generateIndividuals(int numberOfIndividuals, String individualClassName) {

		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();
		
		AlgorithmConfiguration PARAM  = AlgorithmConfiguration.getInstance();
				
		List<AlgorithmIndividual> l = new LinkedList<AlgorithmIndividual>();

		for (int n = 0; n < numberOfIndividuals; n++) {

			int numeroGenes       = ALEATORIOS.getFromInterval(PARAM.getMinG(), PARAM.getMaxG());
			int numeroCondiciones = ALEATORIOS.getFromInterval(PARAM.getMinC(), PARAM.getMaxC());
			int numeroTiempos     = ALEATORIOS.getFromInterval(PARAM.getMinT(), PARAM.getMaxT());
			
			Collection<Integer> g = newComponent(numeroGenes,1);
			Collection<Integer> c = newComponent(numeroCondiciones,2);
			Collection<Integer> t = newComponent(numeroTiempos,3);
						
			AlgorithmIndividual nuevo = null;
			try {
				nuevo = (AlgorithmIndividual) (Class.forName(individualClassName)).newInstance();
				nuevo.initialize(g,c,t);
				nuevo.addEntry("from initial population: random");
				l.add(nuevo);
			} 
			catch (InstantiationException e) {e.printStackTrace();}
			catch (IllegalAccessException e) {e.printStackTrace();}
			catch (ClassNotFoundException e) {e.printStackTrace();}

		}// for

		return l;

	}

	private Collection<Integer> newComponent(int numberOfCoordinates, int componentType) {
		
		Collection<Integer> nueva = new LinkedList<Integer>();
		
		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();
		AlgorithmDataset      DATOS      = (AlgorithmConfiguration.getInstance()).getData();
		
		ALEATORIOS.newBag();
		
		if      (componentType==1) ALEATORIOS.putMarbles(DATOS.getGenesBag());
		else if (componentType==2) ALEATORIOS.putMarbles(DATOS.getSamplesBag());	
		else if (componentType==3) ALEATORIOS.putMarbles(DATOS.getTimesBag());
		
		int [] bolas = ALEATORIOS.extractNmarbles(numberOfCoordinates);
		
		for (int i = 0;i<bolas.length;i++){
			nueva.add(new Integer(bolas[i]));
		}
				
		return nueva;
	}

}// clase
