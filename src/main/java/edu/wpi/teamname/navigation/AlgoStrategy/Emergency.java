package edu.wpi.teamname.navigation.AlgoStrategy;

import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.navigation.Graph;
import edu.wpi.teamname.navigation.LocationName;
import edu.wpi.teamname.navigation.Node;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.PriorityQueue;

public class Emergency implements IStrategyAlgo {
  private ArrayList<Integer> exits = new ArrayList<>();

  public Emergency() {
    for (Map.Entry<Integer, ArrayList<LocationName>> entry : GlobalVariables.getHMap().entrySet()) {
      ArrayList<LocationName> list = entry.getValue();
      if (list != null) {
        LocationName location = list.get(0);
        if (location.getNodeType().equals("EXIT")) {
          this.exits.add(entry.getKey());
          // System.out.print(location.getLongName() + " ");
        }
      }
    }
  }

  //  @Override
  //  public ArrayList<Node> getPathBetween(Graph g, int startNodeId, int targetNodeId)
  //      throws SQLException {
  //    System.out.println("EMERGENCY EXIT");
  //
  //    ArrayList<Node> nodes = g.getNodes();
  //
  //    // Node s = nodes.get(Node.idToIndex(startNodeId));
  //    // Node t = nodes.get(Node.idToIndex(300));
  //
  //    for (Node j : nodes) {
  //      j.setParent(null);
  //    }
  //
  //    Node start = nodes.get(Node.idToIndex(startNodeId));
  //    Node target = nodes.get(Node.idToIndex(300)); // default exit
  //
  //    double m = -1;
  //    for (Integer i : exits) {
  //      Node n = DataManager.getNode(i);
  //      if (start.calculateHeuristic(n) > m) {
  //        m = start.calculateHeuristic(n);
  //        target = n; // if there is a closer exit set this to the target
  //      }
  //    }
  //
  //    g.setAllG(start, target);
  //
  //    PriorityQueue<Node> closedList = new PriorityQueue<>();
  //    PriorityQueue<Node> openList = new PriorityQueue<>();
  //
  //    start.setF(start.getG() + start.calculateHeuristic(target));
  //    openList.add(start);
  //
  //    while (!openList.isEmpty()) {
  //      Node ex = openList.peek();
  //
  //      if (exits.contains(ex.getId())) { // this A* method will terminate when we are at an exit
  //        // System.out.println(closedList.size());
  //        System.out.println("Emergency Done: " + closedList.size());
  //        return getPath(ex);
  //      }
  //
  //      for (Node nei : ex.getNeighbors()) {
  //
  //        // Do not want to take elevators in emergency
  //        ArrayList<LocationName> listA = GlobalVariables.getHMap().get(ex.getId());
  //        ArrayList<LocationName> listB = GlobalVariables.getHMap().get(nei.getId());
  //        if (listA != null && listB != null) {
  //          LocationName a = listA.get(0);
  //          LocationName b = listB.get(0);
  //          if (a.getNodeType().equals("ELEV") && b.getNodeType().equals("ELEV")) {
  //            // System.out.print("Continued");
  //            continue;
  //          }
  //        }
  //
  //        double totalWeight = ex.getG() + nei.findWeight(ex);
  //
  //        //        System.out.println(closedList.size());
  //
  //        if (!openList.contains(nei) && !closedList.contains(nei)) {
  //          nei.setParent(ex);
  //          nei.setG(totalWeight);
  //          nei.setF(nei.getG() + nei.calculateHeuristic(target));
  //          openList.add(nei);
  //        } else {
  //          if (totalWeight < nei.getG()) {
  //            nei.setParent(ex);
  //            nei.setG(totalWeight);
  //            nei.setF(nei.getG() + nei.calculateHeuristic(target));
  //
  //            if (closedList.contains(nei)) {
  //              closedList.remove(nei);
  //              openList.add(nei);
  //            }
  //          }
  //        }
  //      }
  //      openList.remove(ex);
  //      closedList.add(ex);
  //      // System.out.println(closedList.size());
  //    }
  //    return null;
  //  }

  /**
   * This method returns the path from the target node to the start node.
   *
   * @param target The target node to start the path from
   * @return The path from the target node to the start node
   */
  public ArrayList<Node> getPath(Node target) {
    System.out.println("Path");
    Node n = target;

    ArrayList<Node> ids = new ArrayList<>();
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

  @Override
  public ArrayList<Node> getPathBetween(Graph g, int startNodeId, int targetNodeId)
      throws SQLException {
    System.out.println("ASTAR T");

    ArrayList<Node> nodes = g.getNodes();
    Node s = nodes.get(Node.idToIndex(startNodeId));
    Node t = nodes.get(Node.idToIndex(targetNodeId));

    for (Node j : nodes) {
      j.setParent(null);
    }

    g.setAllG(s, t);
    Node start = s;
    // Node target = t;

    PriorityQueue<Node> closedList = new PriorityQueue<>();
    PriorityQueue<Node> openList = new PriorityQueue<>();

    start.setF(start.getG()); // get rid of h value
    openList.add(start);

    while (!openList.isEmpty()) {
      Node ex = openList.peek();
      if (exits.contains(ex.getId())) { // this A* method will terminate when we are at an exit
        // System.out.println(closedList.size());
        System.out.println("Emergency Done: " + closedList.size());
        return getPath(ex);
      }

      for (Node nei : ex.getNeighbors()) {
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
}
