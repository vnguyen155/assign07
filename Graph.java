package assign07;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;

/**
 * Represents a sparse, directed graph, as well as its vertices that each hold
 * an instance of the generic type and the directed edges that connect vertices.
 * 
 * @author Vany Nguyen and Brooke Griffin
 * @version 10-29-2026
 */
public class Graph<Type> {
	// maps each data to the vertex that stores it
	private Map<Type, Vertex<Type>> vertices = new HashMap<>();

	/**
	 * Adds a directed edge from source (src) to destination (dst). Creates the
	 * vertices if they are not in the graph yet.
	 * 
	 * @param src    - the data of the vertex where the edge starts
	 * @param dst    - the data of the vertex the edge points to
	 * @param weight - the cost of the edge
	 */
	public void addEdge(Type src, Type dst, double weight) {
		// only create a vertex if it is not in the graph yet
		if (!vertices.containsKey(src)) {
			vertices.put(src, new Vertex<>(src));
		}

		if (!vertices.containsKey(dst)) {
			vertices.put(dst, new Vertex<>(dst));
		}

		// directed graph, so only the src vertex gets the edge
		Vertex<Type> srcVertex = vertices.get(src);
		Vertex<Type> dstVertex = vertices.get(dst);
		srcVertex.addEdge(dstVertex, weight);
	}

	/**
	 * Checks if the graph has a vertex with the given data.
	 *
	 * @param data - the data to look for
	 * @return true if the graph has this data, false otherwise
	 */
	public boolean containsVertex(Type data) {
		return vertices.containsKey(data);
	}

	/**
	 * Finds the shortest path (fewest edges) from source to destination using
	 * breadth-first search. Edge weights are ignored.
	 *
	 * @param source      - the data of the start vertex
	 * @param destination - the data of the end vertex
	 * @return the data on the path from source to destination, or an empty list if
	 *         there is no path
	 */
	public List<Type> breadthFirstSearch(Type source, Type destination) {
		// reset every vertex before searching
		for (Vertex<Type> v : vertices.values()) {
			v.reset();
		}

		// the start is 0 steps away from itself
		Vertex<Type> start = vertices.get(source);
		start.setDistanceFromStart(0);

		Queue<Vertex<Type>> queue = new LinkedList<>();
		queue.offer(start);

		// visit the closest vertices first
		while (!queue.isEmpty()) {
			Vertex<Type> current = queue.poll();

			for (Edge<Type> edge : current.getEdges()) {
				Vertex<Type> neighbor = edge.getOtherEnd();

				// infinity means the neighbor is not visited yet
				if (neighbor.getDistanceFromStart() == Double.POSITIVE_INFINITY) {
					neighbor.setDistanceFromStart(current.getDistanceFromStart() + 1);
					neighbor.setPrevious(current);
					queue.offer(neighbor);
				}
			}
		}

		Vertex<Type> end = vertices.get(destination); // get end vertex
		LinkedList<Type> path = new LinkedList<>(); // create an empty list of path

		// cannot reach the end, so return an empty list
		if (end.getDistanceFromStart() == Double.POSITIVE_INFINITY) {
			return path;
		}

		// go back from the end, add to the front so the path is in order
		Vertex<Type> current = end;
		while (current != null) {
			path.addFirst(current.getData()); // add to the front of the list
			current = current.getPrevious(); // step back to the previous vertex
		}

		return path;
	}

	/**
	 * Finds the shortest path from source to destination using Dijkstra's
	 * algorithm, which tries to find the unvisited vertex with the smallest total
	 * cost from the start.
	 *
	 * @param source      - the data of the start vertex
	 * @param destination - the data of the end vertex
	 * @return the data on the path from source to destination, or an empty list if
	 *         there is no path
	 */
	public List<Type> dijkstrasAlgorithm(Type source, Type destination) {
		List<Vertex<Type>> unvisited = new ArrayList<>();

		// reset everything before searching and add that vertex to the unvisited list.
		for (Vertex<Type> v : vertices.values()) {
			v.reset();
			unvisited.add(v);
		}

		// the start is 0 steps away from itself
		Vertex<Type> start = vertices.get(source);
		start.setDistanceFromStart(0);

		// Descending by distance -> the last element is the smallest distance
		Comparator<Vertex<Type>> desc = (a, b) -> Double.compare(b.getDistanceFromStart(), a.getDistanceFromStart());

		// Check if the unvisited list is empty or not
		while (!unvisited.isEmpty()) {
			// Unvisited list is empty, so sort unvisited descending
			unvisited.sort(desc);
			Vertex<Type> current = unvisited.get(unvisited.size() - 1); // current = last element

			// Skip if current vertex cannot be reached
			if (current.getDistanceFromStart() != Double.POSITIVE_INFINITY) {

				// For each edge of current, neighbor equals the edge's other end
				for (Edge<Type> edge : current.getEdges()) {
					Vertex<Type> neighbor = edge.getOtherEnd();
					double newDistance = current.getDistanceFromStart() + edge.getWeight();

					if (newDistance < neighbor.getDistanceFromStart()) {
						neighbor.setDistanceFromStart(newDistance);
						neighbor.setPrevious(current);
					}
				}
			}
			unvisited.remove(unvisited.size() - 1);
		}

		Vertex<Type> end = vertices.get(destination);
		LinkedList<Type> path = new LinkedList<>();

		// Cannot reach the end, so return an empty list
		if (end.getDistanceFromStart() == Double.POSITIVE_INFINITY) {
			return path;
		}

		// Go back from the end, add to the front so the path is in order
		Vertex<Type> current = end;
		while (current != null) {
			path.addFirst(current.getData());
			current = current.getPrevious();
		}

		return path;
	}

	/**
	 * Sorts the vertices of the graph in topological order, so that for every edge,
	 * the vertex where it starts comes before the vertex it points to. A graph can
	 * have more than one valid order.
	 *
	 * @return the data of all vertices in a valid topological order
	 * @throws IllegalArgumentException if the graph has a cycle
	 */
	public List<Type> topoSort() {
		// reset everything before searching.
		for (Vertex<Type> v : vertices.values()) {
			v.reset();
		}

		// count indegrees: each edge adds 1 to the vertex it points to
		for (Vertex<Type> v : vertices.values()) {
			for (Edge<Type> edge : v.getEdges()) {
				Vertex<Type> neighbor = edge.getOtherEnd();
				neighbor.setIndegree(neighbor.getIndegree() + 1);
			}
		}

		// vertices with indegree 0 have nothing before them, so they go first
		Queue<Vertex<Type>> queue = new LinkedList<>();
		for (Vertex<Type> v : vertices.values()) {
			if (v.getIndegree() == 0) {
				queue.offer(v);
			}
		}

		List<Type> result = new ArrayList<>();
		while (!queue.isEmpty()) {
			Vertex<Type> current = queue.poll();
			result.add(current.getData());

			// remove the edges of current by lowering each neighbor's indegree
			for (Edge<Type> edge : current.getEdges()) {
				Vertex<Type> neighbor = edge.getOtherEnd();
				neighbor.setIndegree(neighbor.getIndegree() - 1);

				// indegree 0 means everything before this neighbor is sorted
				if (neighbor.getIndegree() == 0) {
					queue.offer(neighbor);
				}
			}
		}

		// vertices in a cycle never reach indegree 0, so some are missing
		if (result.size() != vertices.size()) {
			throw new IllegalArgumentException("Graph has a cycle -> No topological order");
		}

		return result;
	}
}
