package com.ego.test;
import java.util.List;
/**
 * This class is an abstract class that implement abstract methods describing network properties (directed or not)
 * @author sarah
 *
 */
public abstract class Network {

	public abstract int nodesNumber();
	public abstract int edgesNumber();
	public abstract void viewNetwork();
	public abstract void printNetwork();
	public abstract double density();
	public abstract double averageDegree();
	public abstract List<Integer> getNeighbors(int node);
	
	
	
}
