package edu.wpi.teamname.navigation.AlgoStrategy;

import edu.wpi.teamname.navigation.Graph;
import edu.wpi.teamname.navigation.MapNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class DFSAlgo implements IStrategyAlgo {
  @Override
  public ArrayList<MapNode> getPathBetween(Graph g, int startNodeId, int targetNodeId) {
    ArrayList<MapNode> mapNodes = g.getMapNodes();
    MapNode start = g.findNodeByID(startNodeId);
    MapNode target = g.findNodeByID(targetNodeId);

    int nodeNum = g.getMapNodes().size();
    MapNode[] prev = new MapNode[nodeNum];
    boolean[] visited = new boolean[nodeNum];
    Stack<MapNode> stack = new Stack<MapNode>();

    stack.push(start);
    visited[mapNodes.indexOf(start)] = true;
    prev[mapNodes.indexOf(start)] = null;

    while (!stack.isEmpty()) {
      MapNode curr = stack.pop();
      if (curr.equals(target)) break;
      for (MapNode neighbor : curr.getNeighbors()) {
        if (!visited[mapNodes.indexOf(neighbor)]) {
          stack.push(neighbor);
          prev[mapNodes.indexOf(neighbor)] = curr;
          visited[mapNodes.indexOf(neighbor)] = true;
        }
      }
    }

    ArrayList<MapNode> finalPath = new ArrayList<MapNode>();
    for (MapNode n = target; n != null; n = prev[mapNodes.indexOf(n)]) {
      finalPath.add(n);
    }

    Collections.reverse(finalPath);
    return finalPath;
  }
}
