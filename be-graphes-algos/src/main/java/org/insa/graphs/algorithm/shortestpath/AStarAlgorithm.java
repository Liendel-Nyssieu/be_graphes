package org.insa.graphs.algorithm.shortestpath;

import java.util.List;
import java.util.ArrayList;
import org.insa.graphs.model.*;
import org.insa.graphs.algorithm.AbstractInputData;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    @Override
    void init(List<Node> nodes, ShortestPathData data)
    {
    	for (int i = 0; i < nodes.size(); i++) {
    		label_node_list.add(new LabelStar(nodes.get(i), null, data.getDestination(), data.getMode().equals(AbstractInputData.Mode.LENGTH)));
    	}
    }

}
