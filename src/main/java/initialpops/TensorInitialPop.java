package initialpops;

/**
 * Operador que genera la poblacion inicial con un porcentaje de individuos aleatorios recibido por parámetro.
 * El resto se construye usando la jerarquia de los datos de entrada.
 * @author DAVID GUTIERREZ AVILES
 */

import java.util.List;

import strinitialpop.HierarchycalStrategy;
import strinitialpop.RandomStrategy;
import strinitialpop.TensorStrategy;
import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.InitialPop;
import algcore.TriGen;

public class TensorInitialPop implements InitialPop {

	// Procedimientos
	private InitialPopStrategy aleatoria;
	private InitialPopStrategy jerarquica;
	private InitialPopStrategy tensores;

	public TensorInitialPop() {

		aleatoria = new RandomStrategy();
		jerarquica = new HierarchycalStrategy();
		tensores = new TensorStrategy ();

	}

	public List<AlgorithmIndividual> produceInitialPop() {

		TriGen     TRIGEN = TriGen.getInstance();
		AlgorithmConfiguration PARAM  = AlgorithmConfiguration.getInstance();
		
		List<AlgorithmIndividual> l = null;

		if (TRIGEN.getOngoingSolutionIndex() == 0) {

			int aleatorias = PARAM.getI()/2;
			
			int cubos      = PARAM.getI()-aleatorias;
			
			//ANTES
			//l = aleatoria.genera_individuos(PARAM.getNumIndividuosIniciales(),TRIGEN.getTipoIndividuo());
			//ANTES
			
			//AHORA
			l = aleatoria.generateIndividuals(aleatorias,TRIGEN.getIndividualClassName());
			//AHORA
			
			List<AlgorithmIndividual> aux = tensores.generateIndividuals(cubos,TRIGEN.getIndividualClassName());
			
			l.addAll(aux);
			
		}

		else {
			System.out.println("AQUI TENSOR");

			int n_aleatorio = (int) (PARAM.getAle() * PARAM.getI());
			
			int aleatorias = n_aleatorio/2;
			int cubos      = n_aleatorio-aleatorias;
						
			int n_jerarquico = PARAM.getI() - n_aleatorio;
			
			l = aleatoria.generateIndividuals(aleatorias, TRIGEN.getIndividualClassName());
			
			List<AlgorithmIndividual> aux = tensores.generateIndividuals(cubos,TRIGEN.getIndividualClassName());
			
			l.addAll(aux);
			
			List<AlgorithmIndividual> aux1 = jerarquica.generateIndividuals(n_jerarquico, TRIGEN.getIndividualClassName());
			
			l.addAll(aux1);

		}

		return l;

	}

}