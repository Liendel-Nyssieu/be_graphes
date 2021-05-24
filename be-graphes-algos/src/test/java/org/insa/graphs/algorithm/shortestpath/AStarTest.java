package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;

public final class AStarTest extends ShortestPathTest
{
	protected ShortestPathSolution createShortestPath(ShortestPathData data)
	{
		AStarAlgorithm AStar_algo = new AStarAlgorithm(data);
		return AStar_algo.run();
	}
}