package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;

public final class DijkstraTest extends ShortestPathTest
{
	protected ShortestPathSolution createShortestPath(ShortestPathData data)
	{
		DijkstraAlgorithm dijkstra_algo = new DijkstraAlgorithm(data);
		return dijkstra_algo.run();
	}
}