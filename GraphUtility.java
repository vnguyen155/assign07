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
		if (sources == null || destinations == null || sources.size() != destinations.size()) {
			throw new IllegalArgumentException("Uneven number of sources and destinations");
		}

		Graph<Type> graph = new Graph<>(); // construct a graph from the given information
		for (int i = 0; i < sources.size(); i++) {
			graph.addEdge(sources.get(i), destinations.get(i), 1);
		}

		// check if sources and destinations exists
		if (!graph.containsVertex(srcData)) {
			throw new IllegalArgumentException("Source does not exist in the graph");
		}
		if (!graph.containsVertex(dstData)) {
			throw new IllegalArgumentException("Destination does not exist in the graph");
		}

		// BFS to track shortest path
		List<Type> result = graph.breadthFirstSearch(srcData, dstData);

		// check if no path is found (result list is empty)
		if (result.isEmpty()) {
			throw new IllegalArgumentException("No path exists between the source and destination.");
		}

		return result;
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
		if (sources == null || destinations == null || weights == null || sources.size() != destinations.size()
				|| sources.size() != weights.size()) {
			throw new IllegalArgumentException("Number of sources, destinations, and weights must be the same.");
		}

		Graph<Type> graph = new Graph<>(); // construct a graph from the given information
		for (int i = 0; i < sources.size(); i++) {
			graph.addEdge(sources.get(i), destinations.get(i), weights.get(i));
		}

		// check if sources and destinations exists
		if (!graph.containsVertex(srcData)) {
			throw new IllegalArgumentException("Source does not exist in the graph");
		}
		if (!graph.containsVertex(dstData)) {
			throw new IllegalArgumentException("Destination does not exist in the graph");
		}

		List<Type> result = graph.dijkstrasAlgorithm(srcData, dstData);

		// check if no path is found (result list is empty)
		if (result.isEmpty()) {
			throw new IllegalArgumentException("No path exists between the source and destination.");
		}

		return result;
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
		if (sources == null || destinations == null || sources.size() != destinations.size()) {
			throw new IllegalArgumentException("Uneven number of sources and destinations");
		}
		
		Graph<Type> graph = new Graph<>();
		for(int i = 0; i < sources.size(); i++) {
			graph.addEdge(sources.get(i), destinations.get(i), 1);
		}
		
		return graph.topoSort();
	}

}
