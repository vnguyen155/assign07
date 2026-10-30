package assign07;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;

/**
 * For testing Graph class.
 *
 * @author Vany Nguyen 
 * @version 10-29-2026
 */
public class GraphTest {
	private Graph<String> simpleGraph; 
	private Graph<String> weightedGraph;
	private Graph<String> cycleGraph;
	
	@BeforeEach
	public void setUp() {
		simpleGraph = new Graph<>();
		simpleGraph.addEdge("a", "b", 1); 
		simpleGraph.addEdge("a", "d", 1);
		simpleGraph.addEdge("b", "e", 1);
		simpleGraph.addEdge("d", "c", 1);
		
		weightedGraph = new Graph<>();
		weightedGraph.addEdge("a", "b", 3);
		weightedGraph.addEdge("a", "d", 8);
		weightedGraph.addEdge("b", "e", 4);
		weightedGraph.addEdge("e", "c", 3);
		weightedGraph.addEdge("e", "d", 2);
		weightedGraph.addEdge("d", "c", 1);

		cycleGraph = new Graph<>();
		cycleGraph.addEdge("a", "b", 1);
		cycleGraph.addEdge("b", "c", 1);
		cycleGraph.addEdge("c", "a", 1);
	}
	
	/*
	 * Breadth-First Search
	 */

		@Test
		public void testBFSPath() {
			assertEquals(List.of("a", "d", "c"), simpleGraph.breadthFirstSearch("a", "c"));
		}

		@Test
		public void testBFSSameStartAndEnd() {
			assertEquals(List.of("a"), simpleGraph.breadthFirstSearch("a", "a"));
		}

		@Test
		public void testBFSNoPathIsEmpty() {
			assertTrue(simpleGraph.breadthFirstSearch("c", "a").isEmpty());
		}

		@Test
		public void testBFSWorksWithCycle() {
			assertEquals(List.of("a", "b", "c"), cycleGraph.breadthFirstSearch("a", "c"));
		}

		/*
		 * Dijkstra's algorithm
		 */

		@Test
		public void testDijkstraPath() {
			assertEquals(List.of("a", "d", "c"), weightedGraph.dijkstrasAlgorithm("a", "c"));
		}

		@Test
		public void testDijkstraNoPathIsEmpty() {
			assertTrue(weightedGraph.dijkstrasAlgorithm("c", "a").isEmpty());
		}

		/*
		 * reset()
		 */

		@Test
		public void testRunningTwiceGivesSameResult() {
			List<String> first = weightedGraph.breadthFirstSearch("a", "c");
			weightedGraph.dijkstrasAlgorithm("a", "e");
			List<String> second = weightedGraph.breadthFirstSearch("a", "c");
			assertEquals(first, second);
		}

		/*
		 * Topological Sort
		 */

		@Test
		public void testTopoSortOrder() {
			List<String> result = simpleGraph.topoSort();
			assertEquals(5, result.size());

			// every edge must point forward in the result
			assertTrue(result.indexOf("a") < result.indexOf("b"));
			assertTrue(result.indexOf("a") < result.indexOf("d"));
			assertTrue(result.indexOf("b") < result.indexOf("e"));
			assertTrue(result.indexOf("d") < result.indexOf("c"));
		}

		@Test
		public void testTopoSortCycleThrows() {
			assertThrows(IllegalArgumentException.class, () -> cycleGraph.topoSort());
		}
}
