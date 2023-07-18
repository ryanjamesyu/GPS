/**
 * 
 * @author Rachel Holman, Ryan Yu, Ben Collinson
 * @description: CSE 274 Final Project to create a program similar to a GPS system
 * 
 */

public class Edge {
	private Vertex fromVertex, toVertex;
	private int timeCost, distanceCost;

	/**
	 * Workhorse constructor for Edge object
	 * @param fromVertex Vertex object with starting vertex
	 * @param toVertex Vertex object with ending vertex
	 * @param timeCost integer object for total time cost
	 * @param distanceCost integer object for total distance cost
	 */
	public Edge(Vertex fromVertex, Vertex toVertex, int timeCost, int distanceCost) {
		this.fromVertex = fromVertex;
		this.toVertex = toVertex;
		this.timeCost = timeCost;
		this.distanceCost = distanceCost;
	}

	/**
	 * This method retrieves the starting vertex
	 * @return Vertex object the edge starts from
	 */
	public Vertex getFromVertex() {
		return this.fromVertex; 
	}

	/**
	 * This method retrieves the ending vertex
	 * @return Vertex object the edge ends at
	 */
	public Vertex getToVertex() {
		return this.toVertex; 
	}

	/**
	 * This method retrieves the total time cost
	 * @return int time cost
	 */
	public int getTimeCost() {
		return this.timeCost; 
	}

	/**
	 * This method retrieves the total distance cost
	 * @return int distance cost
	 */
	public int getDistanceCost() {
		return this.distanceCost; 
	}
	
	/**
	 * Returns String representation of Edge
	 */
	@Override
	public String toString() {
		String ret = "";
		ret += getFromVertex().toString() + " -> " + getToVertex().toString();
		ret += "\t(" + (Graph.useDistCost ? getDistanceCost() + " miles)" : getTimeCost() + " minutes)");
		return ret;
	}
}
