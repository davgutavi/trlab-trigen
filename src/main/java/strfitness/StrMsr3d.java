package strfitness;

import java.util.Collection;
import java.util.Iterator;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.AlgorithmDataset;
import fitnessfunctions.FitnessStrategy;



/**
 * @author David Gutiérrez-Avilés
 */

public class StrMsr3d implements FitnessStrategy {

	public double calculate(AlgorithmIndividual individual) {

		double residuo = 0.0;
		int t = individual.getTimeSize();
		int g = individual.getGeneSize();
		int c = individual.getSampleSize();
		residuo = partialResidue(individual) / (t * g * c);
		return residuo;

	}

	private double partialResidue(AlgorithmIndividual individual) {

		double rp = 0.0;
		Collection<Integer> lt = individual.getTimes();
		Collection<Integer> lg = individual.getGenes();
		Collection<Integer> lc = individual.getSamples();

		double[][][] valores = buildArrayOfValues(lt, lg, lc);
		
		//List<double[][][]> datos = Transformaciones.originales(individuo, dataset);
	
		Auxiliar aux1 = buildMeansVerticalPlanesRowsAndTotal(valores, lt.size(), lg.size(), lc.size());
		double[] medias_planos_verticales = aux1.planos;
		double[][] medias_filas = aux1.segmentos;
		double media_total = aux1.total;

		Auxiliar aux2 = buildMeansHorizontalPlanesAndSteps(valores, lt.size(), lg.size(), lc.size());
		double[] medias_planos_horizontales = aux2.planos;
		double[][] medias_travesanyos = aux2.segmentos;

		Auxiliar aux3 = buildMeansTransversePlanesAndColumns(valores, lt.size(), lg.size(), lc.size());
		double[] medias_planos_transversales = aux3.planos;
		double[][] medias_columnas = aux3.segmentos;

		for (int t = 0; t < lt.size(); t++) {
			for (int g = 0; g < lg.size(); g++) {
				for (int c = 0; c < lc.size(); c++) {
					double aux = valores[t][g][c]
							+ medias_planos_horizontales[g]
							+ medias_planos_transversales[c]
							+ medias_planos_verticales[t]
							- medias_travesanyos[g][c] - medias_filas[t][g]
							- medias_columnas[t][c] - media_total;
					// System.out.println(valores[t][g][c]+";"+medias_planos_horizontales[g]+";"+medias_planos_transversales[c]+";"+medias_planos_verticales[t]+";"+medias_travesanyos[g][c]+";"+medias_filas[t][g]+";"+medias_columnas[t][c]+";"+media_total);
					rp += aux * aux;
				}
			}
		}
		return rp;
	}// residuos_parciales

	private double[][][] buildArrayOfValues(Collection<Integer> lt,	Collection<Integer> lg, Collection<Integer> lc) {

		AlgorithmDataset DATOS = (AlgorithmConfiguration.getInstance()).getData();
		
		double[][][] aux = new double[lt.size()][lg.size()][lc.size()];
		
		int i = 0, j = 0, k = 0;
		
		Iterator<Integer> i1 = lt.iterator();
		
		while (i1.hasNext()) {
			int vi = (i1.next()).intValue();
			Iterator<Integer> i2 = lg.iterator();
			j = 0;
			while (i2.hasNext()) {
				int vj = (i2.next()).intValue();
				Iterator<Integer> i3 = lc.iterator();
				k = 0;
				while (i3.hasNext()) {
					int vk = (i3.next()).intValue();
					aux[i][j][k] = DATOS.getValue(vj,vk,vi);
					k++;
				}
				j++;
			}
			i++;
		}
		
		return aux;
	}

	// ****************************************************************
	// ****************************************************************

	// en auxiliar queda:
	// medias_planos_vericales(planos),medias_filas(segmentos),media_total(total)
	
	//construye_medias_planos_verticales_filas_y_total
	
	private Auxiliar buildMeansVerticalPlanesRowsAndTotal(double[][][] values, int nTimes, int nGenes, int nSamples) {

		double media_total = 0.0f;
		double[] medias_plano = new double[nTimes];
		double[][] medias_segmento = new double[nTimes][nGenes];

		int denominador_plano = nGenes * nSamples;
		int denominador_segmento = nSamples;

		for (int t = 0; t < medias_plano.length; t++) {
			double suma_plano = 0.0;
			for (int i = 0; i < nGenes; i++) {
				double suma_segmento = 0.0;
				for (int j = 0; j < nSamples; j++) {
					suma_plano += values[t][i][j];
					suma_segmento += values[t][i][j];
				}
				medias_segmento[t][i] = suma_segmento / denominador_segmento;
			}
			media_total += suma_plano;
			medias_plano[t] = suma_plano / denominador_plano;
		}

		media_total = media_total / (nTimes * nGenes * nSamples);

		return new Auxiliar(medias_plano, medias_segmento, media_total);
	}

	// ****************************************************************
	// ****************************************************************

	// en auxiliar queda:
	// medias_planos_horizontales(planos),medias_travesanyos(segmentos),0.0f(total)
	//construye_medias_planos_horizontales_y_travesanyos
	private Auxiliar buildMeansHorizontalPlanesAndSteps (double[][][] values, int nTimes, int nGenes, int nSamples) {

		double[] medias_plano = new double[nGenes];
		double[][] medias_segmento = new double[nGenes][nSamples];

		int denominador_plano = nTimes * nSamples;
		int denominador_segmento = nTimes;

		for (int g = 0; g < medias_plano.length; g++) {
			double suma_plano = 0.0;
			for (int i = 0; i < nSamples; i++) {
				double suma_segmento = 0.0;
				for (int j = 0; j < nTimes; j++) {
					suma_plano += values[j][g][i];
					suma_segmento += values[j][g][i];
				}
				medias_segmento[g][i] = suma_segmento / denominador_segmento;
			}
			medias_plano[g] = suma_plano / denominador_plano;
		}
		return new Auxiliar(medias_plano, medias_segmento, 0.0f);
	}

	// ****************************************************************
	// ****************************************************************

	// en auxiliar queda:
	// medias_planos_transversales(planos),medias_columnas(segmentos),0.0f(total)
	//construye_medias_planos_transversales_y_columnas
	private Auxiliar buildMeansTransversePlanesAndColumns(double[][][] values, int nTimes, int nGenes, int nSamples) {

		double[] medias_plano = new double[nSamples];
		double[][] medias_segmento = new double[nTimes][nSamples];

		int denominador_plano = nGenes * nTimes;
		int denominador_segmento = nGenes;

		for (int c = 0; c < medias_plano.length; c++) {
			double suma_plano = 0.0;
			for (int i = 0; i < nTimes; i++) {
				double suma_segmento = 0.0;
				for (int j = 0; j < nGenes; j++) {
					suma_plano += values[i][j][c];
					suma_segmento += values[i][j][c];
				}
				medias_segmento[i][c] = suma_segmento / denominador_segmento;
			}
			medias_plano[c] = suma_plano / denominador_plano;
		}

		return new Auxiliar(medias_plano, medias_segmento, 0.0f);
	}

	private class Auxiliar {

		public double[] planos;
		public double[][] segmentos;
		public double total;

		public Auxiliar(double[] planes, double[][] segments, double total) {
			this.planos = planes;
			this.segmentos = segments;
			this.total = total;
		}
	}

}// clase
