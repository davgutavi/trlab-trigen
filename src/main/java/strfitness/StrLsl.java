package strfitness;

import java.util.List;

import org.apache.commons.math3.stat.StatUtils;

import fitnessfunctions.FitnessStrategy;
import labutils.Conversions;
import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.AlgorithmDataset;
import algutils.FitnessUtilities;
import algutils.TriclusterUtilities;


/**
 * @author David Gutiérrez-Avilés
 */

public class StrLsl implements FitnessStrategy  {
	
	

	public double calculate(AlgorithmIndividual individual) {

		double res = 0.0;
		
		AlgorithmDataset dataset = (AlgorithmConfiguration.getInstance()).getData();		
		
		List<double[][][]> transformaciones = TriclusterUtilities.getInstance().original(individual, dataset);
		
		//////////VISTA GC (Genes-Condiciones)
	
		double [][] angulosGC = FitnessUtilities.getInstance().buildTableOfAngles(transformaciones.get(0));
		
		//Angulos.imprimeTabla(angulosGC,"\nTabla de �ngulos vista GC\n");
			
		List<Double> diferenciasGC = FitnessUtilities.getInstance().buildTableOfDifferencies(angulosGC);
				
		//Angulos.imprimeLista(diferenciasGC,"\nTabla de diferencias vista GC:\n");
						
		//////////VISTA GT (Genes-Tiempos)
		
		double [][] angulosGT = FitnessUtilities.getInstance().buildTableOfAngles(transformaciones.get(1));
				
		//Angulos.imprimeTabla(angulosGT,"\nTabla de �ngulos vista GT\n");
		
		List<Double> diferenciasGT = FitnessUtilities.getInstance().buildTableOfDifferencies(angulosGT);
											
		//Angulos.imprimeLista(diferenciasGT,"\nTabla de diferencias vista GT:\n");
		
		
		/////////VISTA TG (Tiempos-Genes)
		
		double [][] angulosTG = FitnessUtilities.getInstance().buildTableOfAngles(transformaciones.get(2));
				
		//Angulos.imprimeTabla(angulosTG,"\nTabla de �ngulos vista TG\n");
		
		List<Double> diferenciasTG = FitnessUtilities.getInstance().buildTableOfDifferencies(angulosTG);
											
		//Angulos.imprimeLista(diferenciasGT,"\nTabla de diferencias vista TG:\n");
				
		
		////////CALCULAR ESTADISTICAS	
		
		////BEFORE
		//double [] diferenciasTotales = FitnessUtilities.getTotalsArray(diferenciasGC,diferenciasGT,diferenciasTG);
		//res = StatUtils.mean(diferenciasTotales);
		////BEFORE
		
		////NOW
		double [] views = new double[3];
		views [0] = StatUtils.mean(Conversions.fromListOfDoubleToArray(diferenciasGC));
		views [1] = StatUtils.mean(Conversions.fromListOfDoubleToArray(diferenciasGT));
		views [2] = StatUtils.mean(Conversions.fromListOfDoubleToArray(diferenciasTG));
		res = StatUtils.mean(views);
		////NOW
		
		return res;

	}

}