/**
 * 
 * @author Rachel Holman, Ryan Yu, Ben Collinson
 * @description: CSE 274 Final Project to create a program similar to a GPS system
 * 
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Dijkstra {

	private static int totalCost;

	/**
	 * This method features an algorithm used to compute the shortest path based on
	 * time or distance.
	 * 
	 * @param graph as a Graph object
	 * @param start as a Vertex object
	 * @param end as a Vertex object
	 * @return shortest path as a Path variable
	 */
	public static Path shortestPath(Graph graph, Vertex start, Vertex end) {
		
		HeapPriorityQueue<Path> pq = new HeapPriorityQueue<Path>();
		totalCost = 0;
		pq.add(new Path(start, totalCost, new LinkedList<Edge>()));
		ArrayList<Vertex> visited = new ArrayList<Vertex>();

		while(!pq.isEmpty()) {
			// get highest priority path and cost
			Path nextEntry = pq.remove();
			totalCost = nextEntry.getCost();
			
			// if we are at the ending vertex, end and return
			if(nextEntry.getVertex() == end)
				return nextEntry;
			
			// if the vertex has already been visited before, continue
			if(visited.contains(nextEntry.getVertex())) {
				continue;
			} else {
				// for each unvisited vertex, make new path and add it the the priority queue
				Vertex currVertex = nextEntry.getVertex();
				for(Edge e : graph.getGraph().get(currVertex)) {
					if(!visited.contains(e.getToVertex())) {
						LinkedList<Edge> newEdges = new LinkedList<Edge>(nextEntry.getEdges());
						newEdges.add(e);
						int newCost = totalCost + (Graph.useDistCost ? e.getDistanceCost() : e.getTimeCost());
						pq.add(new Path(graph.getVertex(e.getToVertex().getSymbol()), newCost, newEdges));
					}
				}
			}
			// add the current vertex to the list of visited vertices
			visited.add(nextEntry.getVertex());
		}

		// if no path is found, return null
		return null;
	}

}