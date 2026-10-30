package assign07;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a vertex in a generic, directed, weighted graph.
 * 
 * @author Vany Nguyen and Brooke Griffin
 * @version 10-29-2026
 */
public class Vertex<Type> {
	private Type data;
	private List<Edge<Type>> edges = new ArrayList<>();

	// fields used by the algorithms:
	private double distanceFromStart = Double.POSITIVE_INFINITY;
	private Vertex<Type> previous = null;
	private int indegree = 0;

	/**
	 * Constructs a new Vertex holding the given data
	 * 
	 * @param data - the data to store in this vertex
	 */
	public Vertex(Type data) {
		this.data = data;
	}

	/**
	 * Gets the data of the vertex
	 * 
	 * @return data stored in this vertex
	 */
	public Type getData() {
		return data;
	}

	/**
	 * Adds a directed edge from this vertex to the given destination with the given
	 * weight.
	 * 
	 * @param otherEnd - the vertex that the edge points to
	 * @param weight   - the cost of the edge
	 */
	public void addEdge(Vertex<Type> otherEnd, double weight) {
		edges.add(new Edge<>(otherEnd, weight));
	}

	/**
	 * Returns the outgoing edges of this vertex
	 * 
	 * @return the list of edges leaving this vertex
	 */
	public List<Edge<Type>> getEdges() {
		return edges;
	}

	/**
	 * Gets the distance from the start vertex.
	 * 
	 * @return the distance from the start vertex
	 */
	public double getDistanceFromStart() {
		return distanceFromStart;
	}

	/**
	 * Sets the value for the distance from the start vertex.
	 * 
	 * @param newDistanceFromStart - the new distance
	 */
	public void setDistanceFromStart(double newDistanceFromStart) {
		this.distanceFromStart = newDistanceFromStart;
	}

	/**
	 * Gets the vertex that came before this one on the path. It is null if there is
	 * none.
	 *
	 * @return the previous vertex on the path
	 */
	public Vertex<Type> getPrevious() {
		return previous;
	}

	/**
	 * Sets the vertex that came before this one on the path.
	 *
	 * @param newPrevious - the previous vertex on the path
	 */
	public void setPrevious(Vertex<Type> newPrevious) {
		this.previous = newPrevious;
	}

	/**
	 * Gets the number of edges that come into this vertex.
	 *
	 * @return the indegree of this vertex
	 */
	public int getIndegree() {
		return indegree;
	}

	/**
	 * Sets the indegree of this vertex.
	 *
	 * @param newIndegree - the new indegree
	 */
	public void setIndegree(int newIndegree) {
		this.indegree = newIndegree;
	}

	/**
	 * Resets distance to infinity, previous to null, and indegree to 0.
	 */
	public void reset() {
		this.distanceFromStart = Double.POSITIVE_INFINITY;
		this.previous = null;
		this.indegree = 0;
	}

	/**
	 * Returns the data of this vertex as a string.
	 *
	 * @return the string form of this vertex's data
	 */
	@Override
	public String toString() {
		return String.valueOf(data);
	}
}
