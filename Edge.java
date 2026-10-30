package assign07;

/**
 * An edge in a directed, weighted graph. It points to one vertex and has a
 * weight.
 *
 * @author Vany Nguyen and Brooke Griffin
 * @version 10-29-2026
 */
public class Edge<Type> {
	private Vertex<Type> otherEnd;
	private double weight;

	/**
	 * Creates an edge that points to the given vertex with the given weight.
	 *
	 * @param otherEnd - the vertex this edge points to
	 * @param weight   - the cost of this edge
	 */
	public Edge(Vertex<Type> otherEnd, double weight) {
		this.otherEnd = otherEnd;
		this.weight = weight;
	}

	/**
	 * Gets the vertex this edge points to.
	 *
	 * @return the vertex this edge points to
	 */
	public Vertex<Type> getOtherEnd() {
		return otherEnd;
	}

	/**
	 * Gets the weight of this edge.
	 *
	 * @return the weight of this edge
	 */
	public double getWeight() {
		return weight;
	}

}
