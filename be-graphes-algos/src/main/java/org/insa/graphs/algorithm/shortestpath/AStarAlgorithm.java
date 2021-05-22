package org.insa.graphs.algorithm.shortestpath;

import java.util.List;
import org.insa.graphs.model.*;
import org.insa.graphs.algorithm.AbstractInputData;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    @Override
    LabelStar[] init(List<Node> nodes, ShortestPathData data)
    {
    	LabelStar[] liste = new LabelStar[nodes.size()];
    	for (int i = 0; i < nodes.size(); i++) {
    		liste[i] = new LabelStar(nodes.get(i), null, data.getDestination(), data.getMode().equals(AbstractInputData.Mode.LENGTH));
    	}
    	return liste;
    }

}
