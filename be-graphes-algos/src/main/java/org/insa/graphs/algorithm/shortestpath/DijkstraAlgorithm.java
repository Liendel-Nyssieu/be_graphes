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
    
    Label[] init(List<Node> nodes, ShortestPathData data)
    {
    	Label[] liste = new Label[nodes.size()];
    	for (int i = 0; i < data.getGraph().size(); i++) {
    		liste[i] = new Label(nodes.get(i), null);
    	}
    	return liste;
    }
    
    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        // TODO:
        List<Node> nodes = new ArrayList<Node>();
        nodes = data.getGraph().getNodes();
        Label[] label_node_list = init(nodes, data); 
        		
        BinaryHeap<Label> dijkstra_heap = new BinaryHeap<>();
        label_node_list[data.getOrigin().getId()].setCost(0);
        dijkstra_heap.insert(label_node_list[data.getOrigin().getId()]);
        notifyOriginProcessed(data.getOrigin());
        
        //--------------------------début de l'algo---------------------------------------------------------------------------
        
        while (!(label_node_list[data.getDestination().getId()].getmarque() == true)) {
        	Label save;
        	try {
        		save = dijkstra_heap.findMin();
        	} catch (EmptyPriorityQueueException queue_vide) { //pas de nouvel élément à analyser (très certainement aucun chemin existant entre l'origine et la destination)
        		break;
        	}
        	
        	try {
        		dijkstra_heap.remove(save);
        	} catch (ElementNotFoundException not_found) {}
        	
        	label_node_list[save.getdest().getId()].passage();
    		notifyNodeMarked(save.getdest());
    		
        	for (Arc arc: save.getdest().getSuccessors()) {
        		if (!(data.isAllowed(arc))) {
        			continue;
        		}
        		
        		Label dest = label_node_list[arc.getDestination().getId()];
        		if (dest.getmarque() == false) {
        			if (dest.get_tot_cost() > save.getCost()+data.getCost(arc)+dest.get_heuristique()) {
        				try {
            				dijkstra_heap.remove(dest);
            			} catch(ElementNotFoundException error) {}
        				
        				dest.setCost(save.getCost()+data.getCost(arc));
        				dest.setpere(save.getdest()); 
        				
            			dijkstra_heap.insert(dest);
        				notifyNodeReached(arc.getDestination());
        				if (dijkstra_heap.isvalid(true) == false) {
            				System.out.println(dijkstra_heap.isvalid(true));
        				}
       					
        			} else if ((dest.get_tot_cost() == save.getCost()+data.getCost(arc)+dest.get_heuristique()) && (dest.getdest().getPoint().distanceTo(dest.getpere().getPoint()) > dest.getdest().getPoint().distanceTo(save.getdest().getPoint()))) {
        				try {
            				dijkstra_heap.remove(dest);
            			} catch(ElementNotFoundException error) {}
        				
        				dest.setpere(save.getdest());
        				
            			dijkstra_heap.insert(dest);
        				notifyNodeReached(arc.getDestination());
        				if (dijkstra_heap.isvalid(true) == false) {
            				System.out.println(dijkstra_heap.isvalid(true));
        				}        				
 
        			}
        				
        		}
        	}
        }
        
        //------------------------création de la solution------------------------------------------------
        ShortestPathSolution solution;
        
        if (label_node_list[data.getDestination().getId()].getmarque() == false) {
        	solution = new ShortestPathSolution(data, AbstractSolution.Status.INFEASIBLE);
        } else {
        	notifyDestinationReached(data.getDestination());
        
        	List<Node> nodes_solution = new ArrayList<Node>();
        	nodes_solution.add(data.getDestination());
        	Node node = nodes_solution.get(0);
        	while (!(node.equals(data.getOrigin()))) {
        		Node father_node = nodes.get(label_node_list[node.getId()].getpere().getId());
				System.out.println("heuristique : "+label_node_list[node.getId()].get_heuristique());
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
        notifyDestinationReached(data.getDestination());
        return solution;
    }

}
