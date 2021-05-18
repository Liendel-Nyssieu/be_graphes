package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.insa.graphs.model.*;
import org.insa.graphs.algorithm.utils.*;
import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.AbstractSolution;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    //----------------------initialisation------------------------------------------------------------------------------------
    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        // TODO:
        List<Node> nodes = new ArrayList<Node>();
        nodes = data.getGraph().getNodes();
        List<Label> label_node_list = new ArrayList<Label>();
       
        for (int i = 0; i < nodes.size(); i++) {
        	label_node_list.add(new Label(nodes.get(i), null));  //Les nodes et les labels ont le même index dans leurs listes respectives
        }
        BinaryHeap<Label> dijkstra_heap = new BinaryHeap<>();
        label_node_list.get(nodes.indexOf(data.getOrigin())).setCost(0);
        dijkstra_heap.insert(label_node_list.get(nodes.indexOf(data.getOrigin())));
        notifyOriginProcessed(data.getOrigin());
        
        //--------------------------début de l'algo---------------------------------------------------------------------------
        
        while (!(label_node_list.get(nodes.indexOf(data.getDestination())).getmarque() == true)) {
        	Label save = dijkstra_heap.deleteMin();
        	label_node_list.get(label_node_list.indexOf(save)).passage();
        	for (Arc arc: save.getdest().getSuccessors()) {
        		if (!(data.isAllowed(arc))) {
        			continue;
        		}
        		Label dest = label_node_list.get(nodes.indexOf(arc.getDestination()));
        		if (dest.getmarque() == false) {
        			notifyNodeReached(arc.getDestination());
        			if (dest.get_tot_cost() > save.get_tot_cost()+data.getCost(arc)) {
        				dest.setCost(save.get_tot_cost()+data.getCost(arc));
        				dest.setpere(save.getdest());
        				
        			
        				try {
        					dijkstra_heap.remove(dest);
        				} catch(ElementNotFoundException error) {}
        				dijkstra_heap.insert(dest);
        			}
        		}
        	}
        }
        
        //------------------------création de la solution------------------------------------------------
        ShortestPathSolution solution;
        
        if (label_node_list.get(nodes.indexOf(data.getDestination())).getmarque() == false) {
        	solution = new ShortestPathSolution(data, AbstractSolution.Status.INFEASIBLE);
        } else {
        	notifyDestinationReached(data.getDestination());
        
        	List<Node> nodes_solution = new ArrayList<Node>();
        	nodes_solution.add(data.getDestination());
        	Node node = nodes_solution.get(0);
        	while (!(node.equals(data.getOrigin()))) {
        		Node father_node = nodes.get(label_node_list.get(nodes.indexOf(node)).getpere().getId());
        		nodes_solution.add(father_node);
        		node = father_node;
        	}
        	Collections.reverse(nodes_solution);
        	
        	
        	Path path_solution;
        	if (data.getMode().equals(AbstractInputData.Mode.LENGTH)) {
        		path_solution = Path.createShortestPathFromNodes(data.getGraph(), nodes_solution);
        	} else {
        		path_solution = Path.createFastestPathFromNodes(data.getGraph(), nodes_solution);
        	}
        	
        	solution = new ShortestPathSolution(data, AbstractSolution.Status.OPTIMAL, path_solution);
        	
        }
        return solution;
    }

}
