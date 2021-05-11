package org.insa.graphs.algorithm.shortestpath;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import org.insa.graphs.model.*;
import org.insa.graphs.algorithm.utils.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    //----------------------initialisation------------------------------------------------------------------------------------
    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        // TODO:
        List<Node> nodes = new ArrayList<Node>();
        nodes = data.getGraph().getNodes();
        List<Label> label_node_list = new ArrayList<Label>();
       
        for (int i = 0; i < nodes.size(); i++) {
        	label_node_list.add(new Label(nodes.get(i), null));  //Les nodes et les labels ont le même index dans leurs listes respectives
        }
        BinaryHeap dijkstra_heap = new BinaryHeap();
        dijkstra_heap.insert(label_node_list.get(nodes.indexOf(data.getOrigin())));
        label_node_list.get(nodes.indexOf(data.getOrigin())).setCost(0);
        
        //--------------------------début de l'algo---------------------------------------------------------------------------
        
        while (!(label_node_list.get(nodes.indexOf(data.getDestination())).marque == true)) {
        	Label save = (Label)dijkstra_heap.deleteMin();
        	label_node_list.get(label_node_list.indexOf(save)).passage();
        	for (Arc arc: save.currentvertex.getSuccessors()) {
        		if (!(data.isAllowed(arc))) {
        			continue;
        		}
        		Label dest = label_node_list.get(nodes.indexOf(arc.getDestination()));
        		if (dest.marque == false) {
        			if (dest.getCost() > save.getCost()+data.getCost(arc)) {
        				dest.setCost(save.getCost()+data.getCost(arc));
        				dest.setpere(save.currentvertex);
        				
        				try {
        					dijkstra_heap.remove(dest); 
        				} catch(ElementNotFoundException error) {}
        				dijkstra_heap.insert(dest);
        			}
        		}
        	}
        }
        
        return solution;
    }

}
