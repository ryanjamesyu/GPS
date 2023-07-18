/**
 * 
 * @author Rachel Holman, Ryan Yu, Ben Collinson
 * @description: CSE 274 Final Project to create a program similar to a GPS system
 * 
 */

public class Vertex {
	
	private String symbol;
	private String address;

	/**
	 * Vertex workhorse constructor
	 * @param symbol String with one character in alphabet
	 * @param address String full address
	 */
	public Vertex(String symbol, String address) {
		this.symbol = symbol;
		this.address = address;
	}

	/**
	 * Retrieves the symbol
	 * @return string symbol
	 */
	public String getSymbol() {
		return this.symbol; 
	}

	/**
	 * Retrieves full address
	 * @return address String
	 */
	public String getAddress() {
		return this.address; 
	}
	
	/**
	 * Returns String representation of Vertex
	 */
	@Override
	public String toString() {
		return (Graph.returnAddress ? getAddress() : getSymbol());
	}
}