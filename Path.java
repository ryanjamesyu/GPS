/**
 * 
 * @author Rachel Holman, Ryan Yu, Ben Collinson
 * @description: CSE 274 Final Project to create a program similar to a GPS system
 * 
 */

import java.util.LinkedList;

public class Path implements Comparable<Path> {
	private Vertex vertex;
	private int cost;
	private LinkedList<Edge> edges;

	/**
	 * Path workhorse constructor 
	 * @param vertex object 
	 * @param cost total int cost
	 * @param edges LinkedList of edges
	 */
	public Path(Vertex vertex, int cost, LinkedList<Edge> edges) {
		this.vertex = vertex;
		this.cost = cost;
		this.edges = edges;
	}

	/**
	 * Comparison method to determine smaller cost
	 */
	public int compareTo(Path other) {
		return other.cost - cost;    // lower cost goes first
	}

	/**
	 * This method retrieves a Vertex object
	 * @return Vertex object
	 */
	public Vertex getVertex() {
		return this.vertex; 
	}

	/**
	 * This method retrieves LinkedList of Edge objects
	 * @return LinkedList of Edge objects
	 */
	public LinkedList<Edge> getEdges() {
		return this.edges; 
	}

	/**
	 * This method retrieves cost
	 * @return int cost
	 */
	public int getCost() {
		return this.cost; 
	}
	
	/**
	 * toString() method for returning path directions output
	 */
	@Override
	public String toString() {
		String ret = "";
		for(Edge e : edges)
			ret += e.toString() + "\n";
		ret += "\nTotal cost: " + getCost() + (Graph.useDistCost ? " miles" : " minutes");
		return ret;
	}
}