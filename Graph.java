/**
 * 
 * @author Rachel Holman, Ryan Yu, Ben Collinson
 * @description: CSE 274 Final Project to create a program similar to a GPS system
 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Graph {

	private HashMap<Vertex, Set<Edge>> graph;
	public static boolean useDistCost;
	public static boolean returnAddress;

	/**
	 * Creates the graph variable filled with information in the file
	 * @param fileName String with the graph information 
	 */
	public Graph(String fileName) {
		graph = new HashMap<Vertex, Set<Edge>>();
		readFile(fileName);
	}

	/**
	 * This method reads in the file to create vertex and edge objects.
	 * @param fileName String with the graph information 
	 */
	public void readFile(String fileName) {
		try {
			// Creates a scanner
			Scanner file = new Scanner(new File(fileName));
			String line = file.nextLine();

			Map<String, String> vertices = new HashMap<String, String>();

			// Skips lines until the Nodes are reached
			while (!line.equals("<Nodes>")) { line = file.nextLine(); }

			// Skips two lines of header text in the file
			file.nextLine();
			line = file.nextLine();

			// Creates Vertex objects (each of which contains a symbol and an address property)
			while (!line.equals("</Nodes>")) {
				String[] s = line.split("\t");
				vertices.put(s[0], s[1]);
				line = file.nextLine();
			}

			// Skips lines until Edges are reached
			while (!line.equals("<Edges>")) { line = file.nextLine(); }
			file.nextLine();

			// Creates Edge objects (each of which contains a source, destination, time cost, 
			// and distance cost property) and adds them to a set
			line = file.nextLine();
			String[] s = line.split("\t");
			while (!line.equals("</Edges>")) {
				Vertex v = new Vertex(s[0], vertices.get(s[0]));
				Set<Edge> edges = new HashSet<Edge>();

				do {
					Vertex destination = new Vertex(s[1], vertices.get(s[1]));
					edges.add(new Edge(v, destination, Integer.parseInt(s[2]), Integer.parseInt(s[3])));
					line = file.nextLine();
					s = line.split("\t");
				} while (s[0].equals(v.getSymbol()));

				// When the next line contains a different source Vertex, the current set of Edges are
				// added to the graph Map as values of the corresponding Vertex key (the source Vertex)
				graph.put(v, edges);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception f) {
			f.printStackTrace();
		}
	}

	/**
	 * Returns String representation of Graph
	 */
	@Override
	public String toString() {
		String ret = "";
		for(Vertex v : graph.keySet())
			for(Edge e : graph.get(v))
				ret += e.toString() + "\n";
		return ret;

	}

	/**
	 * This method returns a vertex on the graph based on the symbol entered
	 * @param symbol as a String variable
	 * @return Vertex corresponding to the symbol, or null if vertex not found
	 */
	public Vertex getVertex(String symbol) {
		for(Vertex v : graph.keySet())
			if(v.getSymbol().equals(symbol)) return v;
		return null;
	}

	/**
	 * This method gives access to the graph's private HashMap variable
	 * @return HashMap variable for the Graph
	 */
	public HashMap<Vertex, Set<Edge>> getGraph() {
		return this.graph;	
	}
}