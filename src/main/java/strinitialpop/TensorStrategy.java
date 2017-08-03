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

public class TensorStrategy implements InitialPopStrategy {

	public List<AlgorithmIndividual> generateIndividuals(int numberOfIndividuals, String individualClassName) {

		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();
		
		AlgorithmConfiguration PARAM      = AlgorithmConfiguration.getInstance();
		AlgorithmDataset      DATOS      = PARAM.getData();
				
		List<AlgorithmIndividual> l = new LinkedList<AlgorithmIndividual>();

		for (int i = 0;i<numberOfIndividuals;i++){
			
			int coordenadaGen       = ALEATORIOS.getFromInterval(0,DATOS.getGenSize()-1);
			int coordenadaCondicion = ALEATORIOS.getFromInterval(0,DATOS.getSampleSize()-1);
			int coordenadaTiempo    = ALEATORIOS.getFromInterval(0,DATOS.getTimeSize()-1);
			
			int [] radios = obtieneRadios (coordenadaGen,coordenadaCondicion,coordenadaTiempo);
			
			Collection<Integer> genes = new LinkedList<Integer>();
			for (int g = radios[0];g<=radios[1];g++) 
				genes.add(new Integer (g));
			
			Collection<Integer> condiciones = new LinkedList<Integer>();
			for (int c = radios[2];c<=radios[3];c++) 
				condiciones.add(new Integer (c));
			
			Collection<Integer> tiempos = new LinkedList<Integer>();
			for (int t = radios[4];t<=radios[5];t++) 
				tiempos.add(new Integer (t));
			
			AlgorithmIndividual nuevo = null;
			try {
				nuevo = (AlgorithmIndividual) (Class.forName(individualClassName)).newInstance();
				nuevo.initialize(genes,condiciones,tiempos);
											
				nuevo.addEntry("from initial population: tensor");
		
				l.add(nuevo);
			} 
			catch (InstantiationException e) {e.printStackTrace();}
			catch (IllegalAccessException e) {e.printStackTrace();}
			catch (ClassNotFoundException e) {e.printStackTrace();}
			
		}

		return l;

	}// procedimiento

	@SuppressWarnings("unused")
	private void comprueba(AlgorithmIndividual nuevo) {
		AlgorithmConfiguration PARAM      = AlgorithmConfiguration.getInstance();
		String cadena = "";
		if (nuevo.getGeneSize()<PARAM.getMinG()||nuevo.getGeneSize()>PARAM.getMaxG()) 
			cadena+=" G = "+nuevo.getGeneSize();
		if (nuevo.getSampleSize()<PARAM.getMinC()||nuevo.getSampleSize()>PARAM.getMaxC())
			cadena+=" C = "+nuevo.getSampleSize();
		if (nuevo.getTimeSize()<PARAM.getMinT()||nuevo.getTimeSize()>PARAM.getMaxT()) 
			cadena+=" T = "+nuevo.getTimeSize();
		
		if (!cadena.equalsIgnoreCase(""))
			System.out.println(cadena);
		
	}

	private int[] obtieneRadios(int g, int c, int t) {
		
		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();
		AlgorithmConfiguration PARAM      = AlgorithmConfiguration.getInstance();
		AlgorithmDataset      DATOS      = PARAM.getData();
		
		int [] res = new int [6]; 
		
		//GENES
	
		int tamG = ALEATORIOS.getFromInterval(PARAM.getMinG(),PARAM.getMaxG());
		int [] gen = fronteras (g,tamG,DATOS.getGenSize());
		
		int tamC = ALEATORIOS.getFromInterval(PARAM.getMinC(),PARAM.getMaxC());
		int [] condicion = fronteras (c,tamC,DATOS.getSampleSize());
		
		int tamT = ALEATORIOS.getFromInterval(PARAM.getMinT(),PARAM.getMaxT());
		int [] tiempo = fronteras (t,tamT,DATOS.getTimeSize());
		
		
		
		res[0] = gen[0];
		res[1] = gen[1];
		res[2] = condicion[0];
		res[3] = condicion[1];
		res[4] = tiempo[0];
		res[5] = tiempo[1];		
		
		//System.out.println("["+res[0]+","+res[1]+"] ,["+res[2]+","+res[3]+"] ,["+res[4]+","+res[5]+"]");
		
		return res;
	}

	
	private int [] fronteras (int centro, int tam, int total){
		
		int [] res = new int[2];
		
		int numeroIzquierda = centro + 1;
		
		int numeroDerecha   = total - (centro+1);
		
		int reparto1 = tam/2;
		
		int reparto2 = tam - reparto1;
		
		boolean dispIzquierda = reparto1<=numeroIzquierda;
		
		boolean dispDerecha   = reparto2<=numeroDerecha;
		
		
		if      (dispIzquierda==false&&dispDerecha==true){
		
			res[0] = 0;
			res[1] = centro + reparto2 + (reparto1-numeroIzquierda);
		
		}

		else if (dispIzquierda==true&&dispDerecha==false){
		
			res[0] = centro - (reparto1-1) - (reparto2-numeroDerecha);
			res[1] = total-1;
			
		}
		
		else if (dispIzquierda==true&&dispDerecha==true){
			
			res[0] = centro - (reparto1-1);
			res[1] = centro + reparto2;			
		
		}
			
		
		return res;
		
	}


}// clase
