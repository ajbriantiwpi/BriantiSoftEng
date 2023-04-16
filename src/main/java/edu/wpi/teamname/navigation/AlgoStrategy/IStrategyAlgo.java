package edu.wpi.teamname.navigation.AlgoStrategy;

import edu.wpi.teamname.navigation.Graph;
import edu.wpi.teamname.navigation.Node;
import java.util.ArrayList;

public interface IStrategyAlgo {

  public ArrayList<Node> getPathBetween(Graph g, int startNodeId, int targetNodeId);
}
