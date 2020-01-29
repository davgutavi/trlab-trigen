package algutils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.math3.stat.StatUtils;

public class FitnessUtilities {

	static private FitnessUtilities singleton = new FitnessUtilities();

	private FitnessUtilities() {
	}

	public static FitnessUtilities getInstance() {
		return singleton;
	}

	public double[] meanOfViews(List<Double> gCdifferences, List<Double> gTdifferences, List<Double> tGdifferences) {

		double[] res = new double[3];

		res[0] = StatUtils.mean(fromListToArray(gCdifferences));
		res[1] = StatUtils.mean(fromListToArray(gTdifferences));
		res[2] = StatUtils.mean(fromListToArray(tGdifferences));

		return res;
	}

	private double[] fromListToArray(List<Double> listOfDouble) {

		double[] res = new double[listOfDouble.size()];

		Iterator<Double> it = listOfDouble.iterator();

		int i = 0;

		while (it.hasNext()) {
			res[i] = (it.next()).doubleValue();
			i++;
		}

		return res;

	}

	public double[][][] buildAnglesCubeT1(double[][][] transformation) {

		int ngraficos = transformation[0][0].length;
		int nseries = transformation.length;
		int nejex = transformation[0].length;

		// CREAR CUBO DE ANGULOS de "nseries" filas, "ngraficos" columnas y "nejex-1" de
		// profundidad

		double[][][] angulos = new double[nseries][ngraficos][nejex - 1];

		// Para cada grafico

		for (int grafico = 0; grafico < ngraficos; grafico++) {

			// Para cada serie

			for (int serie = 0; serie < nseries; serie++) {

				// y para cada par de valores del eje X

				for (int ejex = 0; ejex < (nejex - 1); ejex++) {

					// Calcular pendiente

					double x1 = (double) (ejex);
					double x2 = (double) (ejex + 1);

					double y1 = 0.0;
					double y2 = 0.0;

					y1 = transformation[serie][ejex][grafico];
					y2 = transformation[serie][ejex + 1][grafico];

					double pendiente = (y2 - y1) / (x2 - x1);

					// Calcular arco tangente
					double atan = Math.atan(pendiente);

					double rotacion = angleTransformationT1(atan);

					// ANTERIOR
//					//Aplicar rotaci�n
//					double rotacion = atan;
//							
//					if (atan<0) rotacion = atan + (2*Math.PI);
					// ANTERIOR

					// Almacenar en tabla de angulos
					angulos[serie][grafico][ejex] = rotacion;

				} // For

			}

		}

		return angulos;

	}// construteCuboAngulos

	private double angleTransformationT1(double angle) {

		double r = angle;

		if (angle < 0)
			r = angle + (2 * Math.PI);

		return r;
	}

	// *************************************************************************************NUEVO->INICIO
	public double[][][] buildAnglesCubeT2(double[][][] transformacion) {

		int ngraficos = transformacion[0][0].length;
		int nseries = transformacion.length;
		int nejex = transformacion[0].length;

		// CREAR CUBO DE ANGULOS de "nseries" filas, "ngraficos" columnas y "nejex-1" de
		// profundidad

		double[][][] angulos = new double[nseries][ngraficos][nejex - 1];

		// Para cada grafico

		for (int grafico = 0; grafico < ngraficos; grafico++) {

			// Para cada serie

			for (int serie = 0; serie < nseries; serie++) {

				// y para cada par de valores del eje X

				for (int ejex = 0; ejex < (nejex - 1); ejex++) {

					// Calcular pendiente

					double x1 = (double) (ejex);
					double x2 = (double) (ejex + 1);

					double y1 = 0.0;
					double y2 = 0.0;

					y1 = transformacion[serie][ejex][grafico];
					y2 = transformacion[serie][ejex + 1][grafico];

					double pendiente = (y2 - y1) / (x2 - x1);

					// Calcular arco tangente
					double atan = Math.atan(pendiente);

					double rotacion = angleTransformationT2(atan);

					// ANTERIOR
//					//Aplicar rotaci�n
//					double rotacion = atan;
//							
//					if (atan<0) rotacion = atan + (2*Math.PI);
					// ANTERIOR

					// Almacenar en tabla de angulos
					angulos[serie][grafico][ejex] = rotacion;

				} // For

			}

		}

		return angulos;

	}// construteCuboAngulos

	private double angleTransformationT2(double angle) {

		return Math.abs(angle);

	}
	// *************************************************************************************NUEVO->FINAL

	public List<Double> buildTableOfDifferencies(double[][][] angles) {

		List<Double> res = new ArrayList<Double>();

		for (int i = 0; i < angles.length; i++) {

			for (int j = 0; j < angles[i].length; j++) {

				double dif = 0.0f;

				for (int columna = (j + 1); columna < angles[i].length; columna++) {

					dif = computeDifference(angles, i, j, i, columna);

					res.add(new Double(dif));

				}

				for (int fila = (i + 1); fila < angles.length; fila++) {

					dif = computeDifference(angles, i, j, fila, j);

					res.add(new Double(dif));

				}

			}

		}

		return res;

	}

	public double[][] buildTableOfAngles(double[][][] transformation) {

		int ngraficos = transformation[0][0].length;
		int nseries = transformation.length;
		int nejex = transformation[0].length;

		double sumX = 0.0f;
		double sumXX = 0.0f;

		for (int i = 0; i < nejex; i++) {
			sumX += (i + 1);
			sumXX += (i + 1) * (i + 1);
		}

		double[][] angulos = new double[nseries][ngraficos];

		// Para cada gr�fica

		for (int grafico = 0; grafico < ngraficos; grafico++) {

			// Para cada serie: Calcular las pendientes de minimos cuadrados de cada serie

			for (int serie = 0; serie < nseries; serie++) {

				// Calcular pendiente recta m�nimos cuadrados
				double mrmc = computeSlope(serie, grafico, transformation, sumX, sumXX);

				// Calcular arco tangente
				double atan = Math.atan(mrmc);

				// Aplicar rotaci�n
				double rotacion = angleTransformationT1(atan);

				// Almacenar en tabla de angulos
				angulos[serie][grafico] = rotacion;

			}

		}

		return angulos;

	}

	public double[][] buildTableOfAnglesT2(double[][][] transformation) {

		int ngraficos = transformation[0][0].length;
		int nseries = transformation.length;
		int nejex = transformation[0].length;

		double sumX = 0.0f;
		double sumXX = 0.0f;

		for (int i = 0; i < nejex; i++) {
			sumX += (i + 1);
			sumXX += (i + 1) * (i + 1);
		}

		double[][] angulos = new double[nseries][ngraficos];

		// Para cada gr�fica

		for (int grafico = 0; grafico < ngraficos; grafico++) {

			// Para cada serie: Calcular las pendientes de minimos cuadrados de cada serie

			for (int serie = 0; serie < nseries; serie++) {

				// Calcular pendiente recta m�nimos cuadrados
				double mrmc = computeSlope(serie, grafico, transformation, sumX, sumXX);

				// Calcular arco tangente
				double atan = Math.atan(mrmc);

				// Aplicar rotaci�n
				double rotacion = angleTransformationT2(atan);

				// Almacenar en tabla de angulos
				angulos[serie][grafico] = rotacion;

			}

		}

		return angulos;

	}

	public List<Double> buildTableOfDifferencies(double[][] angles) {

		List<Double> res = new ArrayList<Double>();

		for (int i = 0; i < angles.length; i++) {

			for (int j = 0; j < angles[i].length; j++) {

				double anguloA = angles[i][j];

				double dif = 0.0f;

				for (int fila = (i + 1); fila < angles.length; fila++) {

					double anguloB = angles[fila][j];

					dif = Math.max(anguloA, anguloB) - Math.min(anguloA, anguloB);

					res.add(new Double(dif));

				}

				for (int columna = (j + 1); columna < angles[i].length; columna++) {

					double anguloB = angles[i][columna];

					dif = Math.max(anguloA, anguloB) - Math.min(anguloA, anguloB);

					res.add(new Double(dif));

				}

			}

		}

		return res;

	}

	public double[] getTotalsArray(List<Double> gCdifferencies, List<Double> gTdifferencies,
			List<Double> tGdifferencies) {

		List<Double> completo = new ArrayList<Double>();

		completo.addAll(gCdifferencies);
		completo.addAll(gTdifferencies);
		completo.addAll(tGdifferencies);

		double[] res = new double[completo.size()];

		Iterator<Double> itcompleto = completo.iterator();

		int i = 0;

		while (itcompleto.hasNext()) {

			Double v = itcompleto.next();

			res[i] = v.doubleValue();

			i++;
		}

		return res;
	}

	//////////////////////////////////////////////////////////////// PRIVADOS

	private double computeSlope(int series, int graphic, double[][][] transformation, double xSum, double xxSum) {

		double res = 0.0f;

		int nejex = transformation[0].length;

		double n = (double) nejex;

		double sumXY = 0.0f;

		double sumY = 0.0f;

		int x = 1;

		for (int ejex = 0; ejex < nejex; ejex++) {

			sumY += transformation[series][ejex][graphic];

			sumXY += x * transformation[series][ejex][graphic];

			x++;
		}

		res = (n * sumXY - xSum * sumY) / (n * xxSum - (xSum * xSum));

		return res;

	}

	private double computeDifference(double[][][] angles, int aRow, int aColumn, int bRow, int bColumn) {

		double res = 0.0;

		double[] diferencias = new double[angles[0][0].length];

		for (int i = 0; i < angles[0][0].length; i++) {

			double max = Math.max(angles[aRow][aColumn][i], angles[bRow][bColumn][i]);

			double min = Math.min(angles[aRow][aColumn][i], angles[bRow][bColumn][i]);

			diferencias[i] = max - min;

		}

		res = StatUtils.mean(diferencias);

		return res;
	}

	public double applyAtanRotation(double valor) {

		double atan = Math.atan(valor);

		if (atan < 0)
			atan = (atan + (2 * Math.PI));

		return atan;

	}

}// CLASE
