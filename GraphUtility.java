package assign07;

import java.util.List;

/**
 * Contains static driver methods that build a graph from lists of edges and
 * call the graph's breadth-first search, Dijkstra's algorithm, and topological
 * sort.
 *
 * @author Vany Nguyen and Brooke Griffin
 * @version 10-29-2026
 */
public class GraphUtility {

	/**
	 * Finds the shortest path (fewest edges) from srcData to dstData using
	 * breadth-first search.
	 *
	 * @param <Type>       - the type of data stored in the vertices
	 * @param sources      - the start data of each edge
	 * @param destinations - the end data of each edge
	 * @param srcData      - the data of the start vertex
	 * @param dstData      - the data of the end vertex
	 * @return the data on the path from srcData to dstData
	 * @throws IllegalArgumentException if the lists have different sizes, if
	 *                                  srcData or dstData is not in the graph, or
	 *                                  if there is no path
	 */
	public static <Type> List<Type> shortestPath(List<Type> sources, List<Type> destinations, Type srcData,
			Type dstData) {
		checkSizes(sources, destinations);

		// Breadth-First Search ignores weights, so every edge gets weight 1
		Graph<Type> graph = new Graph<>();
		for (int i = 0; i < sources.size(); i++) {
			graph.addEdge(sources.get(i), destinations.get(i), 1);
		}

		checkVertices(graph, srcData, dstData);

		List<Type> path = graph.breadthFirstSearch(srcData, dstData);

		// An empty path means dstData cannot be reached
		if (path.isEmpty()) {
			throw new IllegalArgumentException("There is no path from " + srcData + " to " + dstData);
		}

		return path;
	}

	/**
	 * Finds the shortest weighted path (smallest total weight) from srcData to
	 * dstData using Dijkstra's algorithm.
	 *
	 * @param <Type>       - the type of data stored in the vertices
	 * @param sources      - the start data of each edge
	 * @param destinations - the end data of each edge
	 * @param weights      - the weight of each edge
	 * @param srcData      - the data of the start vertex
	 * @param dstData      - the data of the end vertex
	 * @return the data on the path from srcData to dstData
	 * @throws IllegalArgumentException if the lists have different sizes, if
	 *                                  srcData or dstData is not in the graph, or
	 *                                  if there is no path
	 */
	public static <Type> List<Type> shortestWeightedPath(List<Type> sources, List<Type> destinations,
			List<Double> weights, Type srcData, Type dstData) {
		checkSizes(sources, destinations);

		// Every edge needs its own weight
		if (weights.size() != sources.size()) {
			throw new IllegalArgumentException("Weights list size does not match the number of edges");
		}

		Graph<Type> graph = new Graph<>();
		for (int i = 0; i < sources.size(); i++) {
			graph.addEdge(sources.get(i), destinations.get(i), weights.get(i));
		}

		checkVertices(graph, srcData, dstData);

		List<Type> path = graph.dijkstrasAlgorithm(srcData, dstData);

		// An empty path means dstData cannot be reached
		if (path.isEmpty()) {
			throw new IllegalArgumentException("There is no path from " + srcData + " to " + dstData);
		}

		return path;
	}

	/**
	 * Sorts the vertices of the graph in topological order.
	 *
	 * @param <Type>       - the type of data stored in the vertices
	 * @param sources      - the start data of each edge
	 * @param destinations - the end data of each edge
	 * @return the data of all vertices in a valid topological order
	 * @throws IllegalArgumentException if the lists have different sizes, or if the
	 *                                  graph has a cycle
	 */
	public static <Type> List<Type> sort(List<Type> sources, List<Type> destinations) {
		checkSizes(sources, destinations);

		// topological sort ignores weights, so every edge gets weight 1
		Graph<Type> graph = new Graph<>();
		for (int i = 0; i < sources.size(); i++) {
			graph.addEdge(sources.get(i), destinations.get(i), 1);
		}

		// topoSort throws the exception itself if the graph has a cycle
		return graph.topoSort();
	}

	/**
	 * Checks that the sources and destinations lists have the same size.
	 *
	 * @param <Type>       - the type of data stored in the vertices
	 * @param sources      - the start data of each edge
	 * @param destinations - the end data of each edge
	 * @throws IllegalArgumentException if the sizes do not match
	 */
	private static <Type> void checkSizes(List<Type> sources, List<Type> destinations) {
		if (sources.size() != destinations.size()) {
			throw new IllegalArgumentException("Sources and destinations have different sizes");
		}
	}

	/**
	 * Checks that both srcData and dstData are in the graph.
	 *
	 * @param <Type>  - the type of data stored in the vertices
	 * @param graph   - the graph to check
	 * @param srcData - the data of the start vertex
	 * @param dstData - the data of the end vertex
	 * @throws IllegalArgumentException if srcData or dstData is not in the graph
	 */
	private static <Type> void checkVertices(Graph<Type> graph, Type srcData, Type dstData) {
		if (!graph.containsVertex(srcData)) {
			throw new IllegalArgumentException(srcData + " is not in the graph");
		}
		if (!graph.containsVertex(dstData)) {
			throw new IllegalArgumentException(dstData + " is not in the graph");
		}
	}
}