package initialpops;

import java.util.List;

import algcore.AlgorithmIndividual;

/**
 * Interfaz que define una estrategia para generar los individuos de la
 * poblacion inicial.
 * 
 * @author David Gutiérrez-Avilés
 */

public interface InitialPopStrategy {

	/**
	 * Genera, con una táctica concreta, un determinado número de Individuos
	 * implementados, igualmente, con una Clase concreta.
	 * 
	 * @param numberOfIndividuals
	 *            Número de individuos que se van a generar con la táctica.
	 * @param individualClassName
	 *            Tipo concreto de la Clase que implementará a la Interfaz
	 *            individuo.
	 * @return Población Inicial (Lista de Individuos)
	 */

	public List<AlgorithmIndividual> generateIndividuals (int numberOfIndividuals, String individualClassName);

}
