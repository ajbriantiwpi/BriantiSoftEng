package edu.wpi.teamname.navigation.AlgoStrategy;

import edu.wpi.teamname.navigation.Graph;
import edu.wpi.teamname.navigation.MapNode;
import java.util.ArrayList;

public interface IStrategyAlgo {

  public ArrayList<MapNode> getPathBetween(Graph g, int startNodeId, int targetNodeId);
}
