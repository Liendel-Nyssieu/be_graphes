package org.insa.graphs.algorithm.shortestpath;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import org.insa.graphs.model.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        // TODO:
        List<Arc> arcs = new ArrayList<Arc>();
        List<Label> label_arc_list = new ArrayList<Label>();
        for (int j = 0; j < data.getGraph().size(); j++) {
        	arcs.addAll(data.getGraph().get(j).getSuccessors());
        }
        for (int i = 0; i < arcs.size(); i++) {
        	Label label_arc = new Label(arcs.get(i).getDestination(), arcs.get(i).getOrigin(), i);
        	label_arc_list.add(label_arc);
        	label_arc.setCost(Math.pow(2, 63)-1);
        }
        
        Label save = label_arc_list.get(0);
        int save_index = 0;
        for (int j = 0; j < data.getGraph().getNodes().size(); j++) {
        	for (Label label : label_arc_list) {
        		if (label.getnum_arc() == save_index && label.getCost() < save.getCost()) {
        			save = label;
        			save_index = label.getdest().getId();
        		}
        	}
        }
        
        
        return solution;
    }

}
