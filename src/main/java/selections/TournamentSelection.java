package selections;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import algcore.AlgorithmIndividual;
import algcore.Selection;
import algcore.TriGen;
import algutils.AlgorithmRandomUtilities;

public class TournamentSelection implements Selection {

	public List<AlgorithmIndividual> select(int numberOfSelections, List<AlgorithmIndividual> population) {

		TriGen     trigen     = TriGen.getInstance();
		
		AlgorithmRandomUtilities randomSupport = AlgorithmRandomUtilities.getInstance();
				
		List<AlgorithmIndividual> res = new LinkedList<AlgorithmIndividual>();
		
		int popSize = population.size();

		int numberOfGroups = 3;
		
		if (popSize <= 2) numberOfGroups = 2;
		
		
		//CREAR PRIMER INTENTO DE GRUPOS
		
		@SuppressWarnings("unchecked")
		List<AlgorithmIndividual> [] gruposPrimerIntento = new List [numberOfGroups];
		
		for (int i = 0;i<gruposPrimerIntento.length;i++) gruposPrimerIntento[i] = new LinkedList<AlgorithmIndividual> ();
		
		Iterator<AlgorithmIndividual> it = population.iterator();
		
		while (it.hasNext()) {
			
			AlgorithmIndividual tri = it.next();
				
			int grupo = randomSupport.getFromInterval(0,gruposPrimerIntento.length-1);
			
			(gruposPrimerIntento[grupo]).add(tri);
						
		}
				
		//CREAR UN NUEVO CONJUNTO DE GRUPOS QUITANDO LOS GRUPOS VACIOS
		
		List<List<AlgorithmIndividual>> grupos = new LinkedList<List<AlgorithmIndividual>> ();
		
		for (int i = 0;i<gruposPrimerIntento.length;i++){
			
			if (gruposPrimerIntento[i].size()!=0)
				grupos.add(gruposPrimerIntento[i]);
		
		}

		//ORDENAR LOS GRUPOS
		
		Iterator<List<AlgorithmIndividual>> itg1 = grupos.iterator();
		
		while (itg1.hasNext()) Collections.sort(itg1.next());
		
		
		//ELEGIR LOS MEJORES DE CADA GRUPO
		
		Iterator<List<AlgorithmIndividual>> itg2 = grupos.iterator();
		
		//int aux = 0;
		
		while (itg2.hasNext()){
			
			List<AlgorithmIndividual> grupo = itg2.next();
			
			//System.out.println("\nGrupo "+aux+" : "+grupo.size());
			//aux++;
			
			AlgorithmIndividual primero = grupo.get(0);
			
			res.add(primero);
			
			primero.addEntry("selected ["+(trigen.getOngoingGenerationIndex()+1)+"]");
			
			grupo.remove(primero);
			
			numberOfSelections--;
						
		}
			
		
		//SACAR ALEATORIAMENTE DE CADA GRUPO
			
		for (int i = 0; i < numberOfSelections; i++) {
			
			int NumeroGrupo = randomSupport.getFromInterval(0,grupos.size()-1);
			
			List<AlgorithmIndividual> grupo = grupos.get(NumeroGrupo);
			
			while (grupo.size()==0){
				NumeroGrupo = randomSupport.getFromInterval(0,grupos.size()-1);
				grupo = grupos.get(NumeroGrupo);
			}
			
			AlgorithmIndividual tri = grupo.get(0);
			
			res.add(tri);
			
			tri.addEntry("selected ["+(trigen.getOngoingGenerationIndex()+1)+"]");
			
			grupo.remove(tri);		
						
		}
		
		return res;

	}

}