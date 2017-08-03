package strfitness;

/**
 * @author David Gutiérrez-Avilés
 */

import java.util.List;

import org.apache.commons.math3.stat.StatUtils;

import fitnessfunctions.FitnessStrategy;
import labutils.Conversions;
import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.AlgorithmDataset;
import algutils.FitnessUtilities;
import algutils.TransformationsUtilities;

public class StrMsl implements FitnessStrategy  {

	public double calculate(AlgorithmIndividual individual) {

		double res = 0.0;
		
		AlgorithmDataset dataset = (AlgorithmConfiguration.getInstance()).getData();		
		
		List<double[][][]> transformaciones = TransformationsUtilities.original(individual, dataset);
				
		//////////VISTA GC (Genes-Condiciones)
			
		double [][][] angulosGC = FitnessUtilities.buildAnglesCubeT1(transformaciones.get(0));
		List<Double> diferenciasGC = FitnessUtilities.buildTableOfDifferencies(angulosGC);
				
		//Angulos.imprimeCubo (angulosGC,"\nCubo de �ngulos vista GC\n");
		//Angulos.imprimeLista(diferenciasGC,"\nTabla de diferencias vista GC:\n");
		
		//////////VISTA GT (Genes-Tiempos)	
		
		double [][][] angulosGT = FitnessUtilities.buildAnglesCubeT1(transformaciones.get(1));
		List<Double> diferenciasGT = FitnessUtilities.buildTableOfDifferencies(angulosGT);
		
		//Angulos.imprimeCubo (angulosGT,"\nCubo de �ngulos vista GT\n");
		//Angulos.imprimeLista(diferenciasGT,"\nTabla de diferencias vista GT:\n");
		
		//////////VISTA TG (Tiempos-Genes)	
		
		double [][][] angulosTG = FitnessUtilities.buildAnglesCubeT1(transformaciones.get(2));
		List<Double> diferenciasTG = FitnessUtilities.buildTableOfDifferencies(angulosTG);
		
		//Angulos.imprimeCubo (angulosTG,"\nCubo de �ngulos vista TG\n");
		//Angulos.imprimeLista(diferenciasTG,"\nTabla de diferencias vista TG:\n");
		
		/////////CALCULAR ESTADISTICAS
		
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