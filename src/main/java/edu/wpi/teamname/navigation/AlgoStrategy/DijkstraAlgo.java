package edu.wpi.teamname.navigation.AlgoStrategy;

import edu.wpi.teamname.navigation.Graph;
import edu.wpi.teamname.navigation.MapNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class DijkstraAlgo implements IStrategyAlgo {
  // This method is different from the A* method in only one way: every getHeuristic method is
  // deleted (Same as having it return 0)
  @Override
  public ArrayList<MapNode> getPathBetween(Graph g, int startNodeId, int targetNodeId) {
    System.out.println("ASTAR T");

    ArrayList<MapNode> mapNodes = g.getMapNodes();
    MapNode s = mapNodes.get(MapNode.idToIndex(startNodeId));
    MapNode t = mapNodes.get(MapNode.idToIndex(targetNodeId));

    for (MapNode j : mapNodes) {
      j.setParent(null);
    }

    g.setAllG(s, t);
    MapNode start = s;
    MapNode target = t;

    PriorityQueue<MapNode> closedList = new PriorityQueue<>();
    PriorityQueue<MapNode> openList = new PriorityQueue<>();

    start.setF(start.getG()); // get rid of h value
    openList.add(start);

    while (!openList.isEmpty()) {
      MapNode ex = openList.peek();
      if (ex == target) {
        System.out.println(closedList.size());
        return getPath(ex);
      }

      for (MapNode nei : ex.getNeighbors()) {
        double totalWeight = ex.getG() + nei.findWeight(ex);

        System.out.println(closedList.size());

        if (!openList.contains(nei) && !closedList.contains(nei)) {
          nei.setParent(ex);
          nei.setG(totalWeight);
          nei.setF(nei.getG()); // get rid of h value
          openList.add(nei);
        } else {
          if (totalWeight < nei.getG()) {
            nei.setParent(ex);
            nei.setG(totalWeight);
            nei.setF(nei.getG()); // get rid of h value

            if (closedList.contains(nei)) {
              closedList.remove(nei);
              openList.add(nei);
            }
          }
        }
      }
      openList.remove(ex);
      closedList.add(ex);
      // System.out.println(closedList.size());
    }
    return null;
  }

  /**
   * This method returns the path from the target node to the start node.
   *
   * @param target The target node to start the path from
   * @return The path from the target node to the start node
   */
  public ArrayList<MapNode> getPath(MapNode target) {
    System.out.println("Path");
    MapNode n = target;

    ArrayList<MapNode> ids = new ArrayList<>();
    if (n == null) return ids;

    while (n.getParent() != null) {
      ids.add(n);
      n = n.getParent();
    }
    ids.add(n);
    Collections.reverse(ids);

    System.out.println("Path LEn:" + ids.size());
    return ids;
  }
}
