package edu.wpi.teamname.navigation.AlgoStrategy;

import edu.wpi.teamname.navigation.Graph;
import edu.wpi.teamname.navigation.MapNode;
import java.util.*;

public class BFSAlgo implements IStrategyAlgo {
  @Override
  public ArrayList<MapNode> getPathBetween(Graph g, int startNodeId, int targetNodeId) {
    ArrayList<MapNode> mapNodes = g.getMapNodes();
    MapNode start = g.findNodeByID(startNodeId);
    MapNode target = g.findNodeByID(targetNodeId);

    Set<MapNode> visited = new HashSet<>();
    Queue<ArrayList<MapNode>> queue = new LinkedList<>();
    queue.offer(new ArrayList<>(Collections.singletonList(start)));

    while (!queue.isEmpty()) {
      ArrayList<MapNode> path = queue.poll();
      MapNode n = path.get(path.size() - 1);
      if (n.equals(target)) return path;
      if (!visited.contains(n)) {
        visited.add(n);
        List<MapNode> neighbors = n.getNeighbors();
        if (neighbors != null) {
          for (MapNode a : neighbors) {
            ArrayList<MapNode> path2 = new ArrayList<>(path);
            path2.add(a);
            queue.offer(path2);
          }
        }
      }
    }
    return null;
  }
}
