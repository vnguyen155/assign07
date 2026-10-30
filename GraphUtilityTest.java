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
 * For testing GraphUtility class.
 *
 * @author Vany Nguyen
 * @version 10-29-2026
 */

public class GraphUtilityTest {

	private List<String> sources;
	private List<String> destinations;
	private List<Double> weights;

	@BeforeEach
	public void setUp() {
		// edges: a→b, a→d, b→e, e→c, e→d, d→c
		sources = List.of("a", "a", "b", "e", "e", "d");
		destinations = List.of("b", "d", "e", "c", "d", "c");
		weights = List.of(3.0, 8.0, 4.0, 3.0, 2.0, 1.0);
	}

	/*
	 * shortestPath
	 */
	@Test
	public void testShortestPath() {
		assertEquals(List.of("a", "d", "c"), GraphUtility.shortestPath(sources, destinations, "a", "c"));
	}

	@Test
	public void testShortestPathNoPathThrows() {
		assertThrows(IllegalArgumentException.class, () -> GraphUtility.shortestPath(sources, destinations, "c", "a"));
	}

	@Test
	public void testShortestPathMissingVertexThrows() {
		assertThrows(IllegalArgumentException.class, () -> GraphUtility.shortestPath(sources, destinations, "a", "z"));
	}

	@Test
	public void testShortestPathDifferentSizesThrows() {
		assertThrows(IllegalArgumentException.class, () -> GraphUtility.shortestPath(sources, List.of("b"), "a", "b"));
	}

	/*
	 * shortestWeightedPath
	 */
	@Test
	public void testShortestWeightedPath() {
		assertEquals(List.of("a", "d", "c"),
				GraphUtility.shortestWeightedPath(sources, destinations, weights, "a", "c"));
	}

	@Test
	public void testWeightedPathDiffersFromBFS() {
		// SLC graph from the Graphs III slide
		List<String> src = List.of("SLC", "SLC", "NY", "Dallas", "Dallas", "NY", "NY", "DC", "Orlando");
		List<String> dst = List.of("Dallas", "NY", "SLC", "NY", "DC", "DC", "Orlando", "Orlando", "SLC");
		List<Double> w = List.of(6.0, 9.0, 11.0, 5.0, 4.0, 4.0, 13.0, 10.0, 14.0);

		// fewest edges: 2 edges, total weight 22
		assertEquals(List.of("SLC", "NY", "Orlando"), GraphUtility.shortestPath(src, dst, "SLC", "Orlando"));

		// smallest total weight: 3 edges, total weight 20
		assertEquals(List.of("SLC", "Dallas", "DC", "Orlando"),
				GraphUtility.shortestWeightedPath(src, dst, w, "SLC", "Orlando"));
	}

	@Test
	public void testWeightsDifferentSizeThrows() {
		assertThrows(IllegalArgumentException.class,
				() -> GraphUtility.shortestWeightedPath(sources, destinations, List.of(1.0), "a", "c"));
	}

	/*
	 * sort()
	 */
	@Test
	public void testSort() {
		List<String> result = GraphUtility.sort(sources, destinations);
		assertEquals(5, result.size());

		// every edge must point forward in the result
		for (int i = 0; i < sources.size(); i++) {
			assertTrue(result.indexOf(sources.get(i)) < result.indexOf(destinations.get(i)));
		}
	}

	@Test
	public void testSortCycleThrows() {
		assertThrows(IllegalArgumentException.class, () -> GraphUtility.sort(List.of("a", "b"), List.of("b", "a")));
	}

}
