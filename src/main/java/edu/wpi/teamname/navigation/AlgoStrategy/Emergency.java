package edu.wpi.teamname.navigation.AlgoStrategy;

import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.navigation.Graph;
import edu.wpi.teamname.navigation.LocationName;
import edu.wpi.teamname.navigation.Node;
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
    if (n == null) {
      System.out.print("N IS NULL");
      return ids;
    }

    while (n.getParent() != null) {
      ids.add(n);
      n = n.getParent();
    }
    ids.add(n);
    Collections.reverse(ids);

    System.out.println("Path LEn:" + ids.size());
    return ids;
  }

  //  @Override
  //  public ArrayList<Node> getPathBetween(Graph g, int startNodeId, int targetNodeId)
  //      throws SQLException {
  //    System.out.println("Emergency");
  //
  //    ArrayList<Node> nodes = g.getNodes();
  //    // Node s = nodes.get(Node.idToIndex(startNodeId));
  //    // Node t = nodes.get(Node.idToIndex(targetNodeId));
  //
  //    for (Node j : nodes) {
  //      j.setParent(null);
  //      j.setG(Double.MAX_VALUE);
  //    }
  //    Node start = nodes.get(Node.idToIndex(startNodeId));
  //    // Node start = GlobalVariables.getCurrentLocationNode();
  //    start.setG(0);
  //
  //    // g.setAllG(s, t);
  //    // Node start = s;
  //    // Node target = t;
  //
  //    PriorityQueue<Node> closedList = new PriorityQueue<>();
  //    PriorityQueue<Node> openList = new PriorityQueue<>();
  //
  //    start.setF(start.getG()); // get rid of h value
  //    openList.add(start);
  //
  //    while (!openList.isEmpty()) {
  //      Node ex = openList.peek();
  //      if (exits.contains(ex.getId())) { // this A* method will terminate when we are at an exit
  //        // System.out.println(closedList.size());
  //        System.out.println("Emergency Done: " + closedList.size());
  //        return getPath(ex);
  //      }
  //
  //      for (Node nei : ex.getNeighbors()) {
  //        // Do not want to take elevators in emergency
  //        //        ArrayList<LocationName> listA = GlobalVariables.getHMap().get(ex.getId());
  //        //        ArrayList<LocationName> listB = GlobalVariables.getHMap().get(nei.getId());
  //        //        if (listA != null && listB != null) {
  //        //          LocationName a = listA.get(0);
  //        //          LocationName b = listB.get(0);
  //        //          if (a.getNodeType().equals("ELEV") && b.getNodeType().equals("ELEV")) {
  //        //            // System.out.print("Continued");
  //        //            continue;
  //        //          }
  //        //        }
  //
  //        double totalWeight = ex.getG() + nei.findWeight(ex);
  //
  //        // System.out.println(closedList.size());
  //
  //        if (!openList.contains(nei) && !closedList.contains(nei)) {
  //          nei.setParent(ex);
  //          nei.setG(totalWeight);
  //          nei.setF(nei.getG()); // get rid of h value
  //          openList.add(nei);
  //        } else {
  //          if (totalWeight < nei.getG()) {
  //            nei.setParent(ex);
  //            nei.setG(totalWeight);
  //            nei.setF(nei.getG()); // get rid of h value
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
  //
  //    return null;
  //  }
  public ArrayList<Node> getPathBetween(Graph g, int startNodeId, int targetNodeId) {
    System.out.println("Emergency");

    ArrayList<Node> nodes = g.getNodes();

    // Node s = nodes.get(Node.idToIndex(startNodeId));
    // Node t = nodes.get(Node.idToIndex(targetNodeId));

    for (Node j : nodes) {
      j.setParent(null);
      j.setG(Double.MAX_VALUE);
    }
    // g.setAllG(s, t);
    Node start = nodes.get(Node.idToIndex(startNodeId));
    start.setG(0);
    // Node target = t;

    PriorityQueue<Node> closedList = new PriorityQueue<>();
    PriorityQueue<Node> openList = new PriorityQueue<>();

    start.setF(start.getG());
    openList.add(start);

    while (!openList.isEmpty()) {
      Node ex = openList.peek();

      if (exits.contains(ex.getId())) { // this A* method will terminate when we are at an exit
        // System.out.println(closedList.size());
        System.out.println("Emergency Done: " + closedList.size());
        return getPath(ex);
      }

      for (Node nei : ex.getNeighbors()) {

        //        ArrayList<LocationName> listA = GlobalVariables.getHMap().get(ex.getId());
        //        ArrayList<LocationName> listB = GlobalVariables.getHMap().get(nei.getId());
        //        if (listA != null && listB != null) {
        //          LocationName a = listA.get(0);
        //          LocationName b = listB.get(0);
        //          if (a.getNodeType().equals("STAI") && b.getNodeType().equals("STAI")) {
        //            // System.out.print("Continued");
        //            continue;
        //          }
        //        }

        double totalWeight = ex.getG() + nei.findWeight(ex);

        //        System.out.println(closedList.size());

        if (!openList.contains(nei) && !closedList.contains(nei)) {
          nei.setParent(ex);
          nei.setG(totalWeight);
          nei.setF(nei.getG());
          openList.add(nei);
        } else {
          if (totalWeight < nei.getG()) {
            nei.setParent(ex);
            nei.setG(totalWeight);
            nei.setF(nei.getG());

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
