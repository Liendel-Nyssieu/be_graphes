
package org.insa.graphs.algorithm.shortestpath;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;

import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;
//import java.lang.Enum;

public abstract class ShortestPathTest
{
	private static String carre, insa; // toulouse;
	
	private static GraphReader readcarre, readinsa; //readtoulouse;
	
	private static Graph graph_carre, graph_insa; //graph_toulouse;
	
	private static ShortestPathData data;
	
	
	@BeforeClass //run une fois avant les tests
	public static void init() throws IOException
	{
		carre = "../Maps/carre.mapgr";
		insa = "../Maps/insa.mapgr";
		//toulouse = "../Maps/toulouse.mapgr";
		
		readcarre = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(carre))));
		readinsa = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(insa))));
		//readtoulouse = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(toulouse))));
		
		graph_carre = readcarre.read();
		graph_insa = readinsa.read();
		//graph_toulouse = readtoulouse.read();
	}
	
	private static int randomint(int min, int max)
	{
		int x = (int)((Math.random()*((max-min)+1))+min);
		System.out.println("max = "+max+" | min = "+min+" | result = "+x);
		return x;
	}
	
	@Test
	public void test_no_filter_length()
	{
		System.out.println("test_no_filter_length()");
		test(graph_carre, 0);
		test(graph_insa, 0);
	}
	
	@Test
	public void test_car_length()
	{
		System.out.println("test_car_length");
		test(graph_carre, 1);
		test(graph_insa, 1);
	}
	
	@Test
	public void test_no_filter_time()
	{
		System.out.println("test_no_filter_time");
		test(graph_carre, 2);
		test(graph_insa, 2);
	}
	
	@Test
	public void test_car_time()
	{
		System.out.println("test_car_time");
		test(graph_carre, 3);
		test(graph_insa, 3);
	}
	
	@Test
	public void test_pedestrian_time()
	{
		System.out.println("test_pedestrian_time");
		test(graph_carre, 4);
		test(graph_insa, 4);
	}
	
	protected abstract ShortestPathSolution createShortestPath(ShortestPathData data);
	
	private void test(Graph graph, int mode)
	{
		/*
		modes de test :
		0 = longueur, pas de restriction
		1 = longueur, par voiture
		2 = temps, pas de restriction
		3 = temps, par voiture
		4 = temps, vélos et piétons (chemins non privés)
		*/
		
		int maxnode = graph.size()-1;
		
		for (int i = 0; i < 50; i++) {
			
			//initialisation de l'origine et de la destination aléatoirement
			int start = randomint(0, maxnode);
			int end = randomint(0, maxnode);
			
			//récupération des nodes correspondantes
			Node start_node = graph.getNodes().get(start);
			Node dest_node = graph.getNodes().get(end);
			
			//sélection de l'arcinspector en accord avec les restriction de recherche du path
			ArcInspector filter = ArcInspectorFactory.getAllFilters().get(mode);
			
			//initialisation de la variable data
			data = new ShortestPathData(graph, start_node, dest_node, filter);
			
			ShortestPathSolution solution = createShortestPath(data);
			
			BellmanFordAlgorithm algo_bf = new BellmanFordAlgorithm(data);
			ShortestPathSolution solution_bf = algo_bf.run();
			
			assertEquals(solution_bf.isFeasible(), solution.isFeasible());
			
			if (solution_bf.isFeasible()) {
				Path path_bf = solution_bf.getPath();
				Path path_dijkstra = solution.getPath();
				System.out.println("path_bf = "+path_bf);
				
				if (mode <= 1) {
					System.out.println("path_bf travel length : "+path_bf.getLength()+" ||| path dijkstra travel length : "+path_dijkstra.getLength());
					assertEquals(path_bf.getLength(), path_dijkstra.getLength(), 0);
				} else {
					System.out.println("path_bf travel time : "+path_bf.getMinimumTravelTime()+" ||| path dijkstra travel time : "+path_dijkstra.getMinimumTravelTime());
					assertEquals(path_bf.getMinimumTravelTime(), path_dijkstra.getMinimumTravelTime(), Math.pow(10, -5));
				}
			}
		}
	}
}