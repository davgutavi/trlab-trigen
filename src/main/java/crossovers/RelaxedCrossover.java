package crossovers;

import java.util.LinkedList;
import java.util.List;

import strcrossover.RelaxedStrategy;
import algcore.AlgorithmIndividual;
import algcore.Crossover;
import algcore.TriGen;
import algutils.AlgorithmRandomUtilities;

public class RelaxedCrossover implements Crossover {

	private CrossoverStrategy relajado;

	public RelaxedCrossover() {

		relajado = new RelaxedStrategy();

	}

	public List<AlgorithmIndividual> cross(int numberOfChildren,List<AlgorithmIndividual> selectedPopulation) {

		List<AlgorithmIndividual> r = new LinkedList<AlgorithmIndividual>();
			
		TriGen trigen = TriGen.getInstance();		
	
		
		for (int i = 0; i < numberOfChildren; i++) {
						
			//**before
			//Tricluster[] padres = selecciona_pareja_reproductora(base);
			//**before
			
			//**now
			AlgorithmIndividual[] padres = getReproductivePair(selectedPopulation);
			//**now
					
			
			AlgorithmIndividual padre = padres[0];
			AlgorithmIndividual madre = padres[1];
			AlgorithmIndividual [] hijos = relajado.cross(padre, madre,trigen.getIndividualClassName());
			r.add(hijos[0]);
		}
		
		//**before
		//r.addAll(seleccion);
		//**before
		
		return r;

	}

	private AlgorithmIndividual[] getReproductivePair(List<AlgorithmIndividual> population) {

		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();
		
		ALEATORIOS.newBag(population.size());
		
		AlgorithmIndividual[] r = new AlgorithmIndividual[2];
		
		int indPadre = ALEATORIOS.extractAmarble();
		
		int indMadre = ALEATORIOS.extractAmarble();
		
		r[0] = population.get(indPadre);
		
		r[1] = population.get(indMadre);
		
		return r;

	}

}