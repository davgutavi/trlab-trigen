package strfitness;

/**
 * @author David Gutiérrez-Avilés
 */

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.TriGen;
import fitnessfunctions.FitnessStrategy;

public class StrMatching implements FitnessStrategy {

	public double calculate(AlgorithmIndividual individual) {

		TriGen TRI = TriGen.getInstance();
		AlgorithmConfiguration PARAM = AlgorithmConfiguration.getInstance();
		
		double indice = 0.0;

		Collection<Integer> tiempos     = individual.getTimes();
		Collection<Integer> genes       = individual.getGenes();
		Collection<Integer> condiciones = individual.getSamples();

		double numero_tiempos     = 0.0;
		double numero_genes       = 0.0;
		double numero_condiciones = 0.0;

//		if (PARAM.isTrazasEVL()) System.out.println("Calculando ID del individuo "+TRI.getIndividuoEnEvaluacion()+"( Experimento "
//					+PARAM.getNumEjecucion()+" , Solución "+(TRI.getSolucionEnProceso()+1)+" , Generación "+(TRI.getGeneracionEnProceso()+1)+" )");
//		
		List<AlgorithmIndividual>     resultados = TRI.getSolutions();
		Iterator<AlgorithmIndividual> itres      = resultados.iterator();
		
		while (itres.hasNext()){
			
			AlgorithmIndividual resultado = itres.next();
			
			Iterator<Integer> it = (resultado.getTimes()).iterator();
			while (it.hasNext()) {
				Integer componente = it.next();
				if (!tiempos.contains(componente)) {
					numero_tiempos++;
				}
			}
			Iterator<Integer> ig = (resultado.getGenes()).iterator();
			while (ig.hasNext()) {
				Integer componente = ig.next();
				if (!genes.contains(componente)) {
					numero_genes++;
				}
			}
			Iterator<Integer> ic = (resultado.getSamples()).iterator();
			while (ic.hasNext()) {
				Integer componente = ic.next();
				if (!condiciones.contains(componente)) {
					numero_condiciones++;
				}
			}
		
		}

//		double nt = (double) tiempos.size();
//		double ng = (double) genes.size();
//		double nc = (double) condiciones.size();
//
//		double indice_tiempos = numero_tiempos / nt;
//		double indice_genes = numero_genes / ng;
//		double indice_condiciones = numero_condiciones / nc;
		
		
//*******************ANTERIOR		
//		double indice_tiempos = FuncionEvaluacion.aplicaAtanRotacion((double)numero_tiempos) ;
//		double indice_genes = FuncionEvaluacion.aplicaAtanRotacion((double)numero_genes);
//		double indice_condiciones = FuncionEvaluacion.aplicaAtanRotacion((double)numero_condiciones);
//		
//		indice = (indice_tiempos * PARAM.getIndiceTiempos()) + 
//				(indice_genes * PARAM.getIndiceGenes()) +
//				(indice_condiciones * PARAM.getIndiceCondiciones());
//*******************ANTERIOR		

		//*******************NUEVO
		
		double nSolsEncontradas = (TRI.getSolutions()).size();

		indice = 0.0;

		if (nSolsEncontradas != 0) {

			double wog = PARAM.getWog();
			double woc = PARAM.getWoc();
			double wot = PARAM.getWot();

			double nGr = numero_genes;
			double nCr = numero_condiciones;
			double nTr = numero_tiempos;

			double nGind = individual.getGeneSize();
			double nCind = individual.getSampleSize();
			double nTind = individual.getTimeSize();

			double denG = nGind * nSolsEncontradas;
			double denC = nCind * nSolsEncontradas;
			double denT = nTind * nSolsEncontradas;

			double den = wog + woc + wot;

			if (den != 0.0) {

				double numerador = (wog*(nGr/denG)) + (woc*(nCr/denC)) + (wot*(nTr/denT));
				
				indice = numerador / den;
				
			}
		
		}
		
		
		//*******************NUEVO

		return indice;

	}// procedimiento_evaluar
	
	
	
	

	public double[] getAmount (AlgorithmIndividual individuo){
		
		
		TriGen TRI = TriGen.getInstance();

		Collection<Integer> tiempos     = individuo.getTimes();
		Collection<Integer> genes       = individuo.getGenes();
		Collection<Integer> condiciones = individuo.getSamples();

		double numero_tiempos     = 0.0;
		double numero_genes       = 0.0;
		double numero_condiciones = 0.0;

		List<AlgorithmIndividual>     resultados = TRI.getSolutions();
		Iterator<AlgorithmIndividual> itres      = resultados.iterator();
		
		while (itres.hasNext()){
			
			AlgorithmIndividual resultado = itres.next();
			
			Iterator<Integer> it = (resultado.getTimes()).iterator();
			while (it.hasNext()) {
				Integer componente = it.next();
				if (!tiempos.contains(componente)) {
					numero_tiempos++;
				}
			}
			Iterator<Integer> ig = (resultado.getGenes()).iterator();
			while (ig.hasNext()) {
				Integer componente = ig.next();
				if (!genes.contains(componente)) {
					numero_genes++;
				}
			}
			Iterator<Integer> ic = (resultado.getSamples()).iterator();
			while (ic.hasNext()) {
				Integer componente = ic.next();
				if (!condiciones.contains(componente)) {
					numero_condiciones++;
				}
			}
		
		}	
		
		double[] r = new double[3];
				
		double nSolsEncontradas = (TRI.getSolutions()).size();

		r[0] = 0.0;
		r[1] = 0.0;
		r[2] = 0.0;

		if (nSolsEncontradas != 0) {

			double nGr = numero_genes;
			double nCr = numero_condiciones;
			double nTr = numero_tiempos;

			double nGind = individuo.getGeneSize();
			double nCind = individuo.getSampleSize();
			double nTind = individuo.getTimeSize();

			double denG = nGind * nSolsEncontradas;
			double denC = nCind * nSolsEncontradas;
			double denT = nTind * nSolsEncontradas;
		
			r[0] = nGr/denG;
			r[1] = nCr/denC;
			r[2] = nTr/denT;
	
		}
		

		return r;
		
	}
	
	
	

}// clase
