package algutils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.random.RandomDataGenerator;

public class AlgorithmRandomUtilities {

	static private AlgorithmRandomUtilities singleton = new AlgorithmRandomUtilities();
	
	private  RandomDataGenerator random;
	
	private Set<Integer> mainBag;
	
	private Set<Point> pointsBag;
	
	private List<Set<Integer>> server;
	

	private AlgorithmRandomUtilities() {
		random = new RandomDataGenerator();
		server = new LinkedList<Set<Integer>>();
	}

	public static AlgorithmRandomUtilities getInstance() {return singleton;}
	
	public  RandomDataGenerator getRandomObject () {return random;}
	
	
	//CREAR BOLSA
	
	public int newBagOnServer (int bagSize){
		
		Set<Integer> aux = new HashSet<Integer>();
		
		for (int i = 0; i<bagSize;i++){
			aux.add(new Integer(i));			
		}
		
		server.add(aux);
		
		return server.indexOf(aux);		
	
	}
	
	
	public void newBag (int bagSize){
		
		mainBag = new HashSet<Integer>();
		
		for (int i = 0; i<bagSize;i++){
			mainBag.add(new Integer(i));			
		}
		
	}
	
	public void newBag (){
		mainBag = new HashSet<Integer>();
	}
	
	public void newPointsBag (){
		pointsBag = new HashSet<Point>();
	}
	
	public void newPointsBag (Set<Point> marbles){
		pointsBag = new HashSet<Point>(marbles);
	}
	
	//METER BOLAS
		
	public void putMarbles (Collection<Integer> marbles){
		mainBag.addAll(marbles);
	}
	
	public void putMarbles (Set<Integer> bag, Collection<Integer> removedMarbles){
		
		mainBag.addAll(bag);
		mainBag.removeAll(removedMarbles);
		
	}
	
	public void putMarbles (Set<Integer> bag){
		
		mainBag.addAll(bag);
		
	}
	
	public void putMarbles (Collection<Integer> marbles, int serverSlotIndex){
		(server.get(serverSlotIndex)).addAll(marbles);
	}
	
	public void putPointMarbles (Set<Point> bag){
		
		pointsBag.addAll(bag);
		
	}
	
	
	//CONSULTAR INFORMACION DE LA BOLSA
	
	public int pointBagSize(){
		return pointsBag.size();
	}
	
	public int bagSize(){
		return mainBag.size();
	}
	
	public int bagSize(int serverSlotIndex){
		return (server.get(serverSlotIndex)).size();
	}
	
	public void printBag (){
		
		Iterator<Integer> it = mainBag.iterator();
		
		System.out.print("\n");
		System.out.print("Bag with "+mainBag.size()+" marbles :");
		
		while (it.hasNext()){
			int bola = (it.next()).intValue();
			System.out.print(bola+"  ");
		}
		
	}
	
	public void printBag (int serverSlotIndex){
		
		Iterator<Integer> it = (server.get(serverSlotIndex)).iterator();
		
		System.out.print("\n");
		System.out.print("Bag with "+(server.get(serverSlotIndex)).size()+" marbles ["+serverSlotIndex+"]:");
		
		while (it.hasNext()){
			int bola = (it.next()).intValue();
			System.out.print(bola+"  ");
		}
		
	}

		
	//SACAR BOLAS
	
	public int extractAmarble (){
		
		int res = 0;
		
		Object[] tirada = random.nextSample(mainBag, 1);
		
		Integer bola = (Integer) tirada [0];
		
		mainBag.remove(bola);
		
		res = bola.intValue();
		
		return res;
		
	}
	
	public int extractAmarble (int serverSlotIndex){
		
		int res = 0;
		
		Object[] tirada = random.nextSample((server.get(serverSlotIndex)), 1);
		
		Integer bola = (Integer) tirada [0];
		
		(server.get(serverSlotIndex)).remove(bola);
		
		res = bola.intValue();
		
		return res;
		
	}
	
	public int[] extractNmarbles (int n){
		
		int [] res = new int [n];
		
		Object[] tirada = random.nextSample(mainBag, n);
		
		for (int i=0;i<n;i++){
			
			Integer bola = (Integer)tirada [i];
			
			mainBag.remove(bola);
			
			res[i] = bola.intValue();
		
		}
				
		return res;
		
	}
	
	public int[] extractNmarbles (int n, int serverSlotIndex){
		
		int [] res = new int [n];
		
		Object[] tirada = random.nextSample((server.get(serverSlotIndex)), n);
		
		for (int i=0;i<n;i++){
			
			Integer bola = (Integer)tirada [i];
			
			(server.get(serverSlotIndex)).remove(bola);
			
			res[i] = bola.intValue();
		
		}
				
		return res;
		
	}
	
	public Point[] extractNpointMarbles (int n){
		
		Point [] res = new Point [n];
		
		Object[] tirada = random.nextSample(pointsBag, n);
		
		for (int i=0;i<n;i++){
			
			Point bola = (Point) tirada [i];
			
			pointsBag.remove(bola);
			
			res[i] = bola;
		
		}
				
		return res;
		
	}
	
	
	
	
	public double getPercentage (){
		return random.nextUniform(0,1);
	}
	
	public int getFromInterval (int lower, int greater){
		
		int res = lower;
		
		if (lower<greater)
			res = random.nextInt(lower, greater);
		
		return res;
	}
	
	public double getFromInterval (double lower, double greater){
		
		double res = lower;
		
		if (lower<greater)
			res = random.nextUniform(lower, greater);
		
		return res;
	}
	
	
}