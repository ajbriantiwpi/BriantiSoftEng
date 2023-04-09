package edu.wpi.teamname.navigation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Graph {

  private Node start;
  private Node target;
  private ArrayList<Node> nodeFromDB = new ArrayList<Node>();
  private ArrayList<Edge> edgeFromDB = new ArrayList<Edge>();
  public List<Node> Nodes = new ArrayList<>();
  public List<Edge> Edges = new ArrayList<>();

  Graph(List<Node> nodes, List<Edge> edges) throws SQLException {
    this.start = null;
    this.target = null;

    nodeFromDB = Node.getAllNodes();
    edgeFromDB = Edge.getAllEdges();

    initializeNodes(nodeFromDB);
    initializeEdges(edgeFromDB);
  }

  public void addEdge(Edge e) {
    this.Edges.add(e);
  }

  public void setStart(Node n) {}

  public void setTarget(Node n) {}

  public List<Node> getNodes() {
    return Nodes;
  }

  public Node getStart() {
    return start;
  }

  public Node getTarget() {
    return null;
  }

  public double findWeight(Node a, Node b) {
    return 0;
  }

  private void assignEdges() {}

  public void printPath(ArrayList<Node> nodes) {
    System.out.println(returnStringPath(target));
  }

  public void setAllG() {
    if (this.target == null || this.start == null) return;
    for (Node n : this.Nodes) {
      n.setG(findWeight(n, this.start));
    }
    start.setG(0);
  }

  public Node AStar(Node s, Node n) {
    setAllG();
    Node start = s;
    Node target = n;

    PriorityQueue<Node> closedList = new PriorityQueue<>();
    PriorityQueue<Node> openList = new PriorityQueue<>();

    start.setF(start.getG() + start.calculateHeuristic(target));
    openList.add(start);

    while (!openList.isEmpty()) {
      Node ex = openList.peek();
      if (ex == target) {
        return ex;
      }

      for (Node nei : n.getNeighbors()) {
        double totalWeight = n.getG() + nei.findWeight(n);

        if (!openList.contains(nei) && !closedList.contains(nei)) {
          nei.setParent(n);
          nei.setG(totalWeight);
          nei.setF(nei.getG() + nei.calculateHeuristic(target));
          openList.add(nei);
        } else {
          if (totalWeight < nei.getG()) {
            nei.setParent(n);
            nei.setG(totalWeight);
            nei.setF(nei.getG() + nei.calculateHeuristic(target));

            if (closedList.contains(nei)) {
              closedList.remove(nei);
              openList.add(nei);
            }
          }
        }
      }
      openList.remove(n);
      closedList.add(n);
    }
  }

  public static String returnStringPath(Node target) {
    List<Node> ids = getPath(target);

    String strPath = "";

    for (Node id : ids) {
      strPath += (id.getId() + " ");
    }
    strPath += "\n";

    return strPath;
  }

  public static ArrayList<Node> getPath(Node target) {
    Node n = target;

    ArrayList<Node> ids = new ArrayList<>();
    if (n == null) return ids;

    while (n.getParent() != null) {
      ids.add(n);
      n = n.getParent();
    }
    ids.add(n);
    Collections.reverse(ids);

    return ids;
  }

  public void initializeNodes(ArrayList<Node> NodeLines) {
    // Initialize the nodes with the node lines data
    int i = 0;
    while (!NodeLines.isEmpty()) {
      // String[] I = NodeLines.get(0).split(",");
      // NodeLines.remove(0);
      Nodes.add(
              new Node(
                      NodeLines.get(0).getId(),
                      NodeLines.get(0).getX(),
                      NodeLines.get(0).getY(),
                      NodeLines.get(0).getFloor(),
                      NodeLines.get(0).getBuilding()));
      NodeLines.remove(0);
    }
  }

  public void initializeEdges(ArrayList<Edge> EdgeLines) {
    // Initialize the nodes with the node lines data
    int i = 0;
    while (!EdgeLines.isEmpty()) {
      // String[] E = EdgeLines.get(0).split(",");
      // EdgeLines.remove(0);
      Edges.add(
              new Edge(
                      EdgeLines.get(0).getStartNodeID(),
                      EdgeLines.get(0).getEndNodeID()));
      EdgeLines.remove(0);
    }
  }
}
