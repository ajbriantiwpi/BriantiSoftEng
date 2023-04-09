package edu.wpi.teamname.navigation;

import edu.wpi.teamname.database.DataManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import lombok.Getter;

public class Graph {

  //  @Getter @Setter private Node start;
  //  @Getter @Setter private Node target;
  //  private ArrayList<Node> nodeFromDB = new ArrayList<Node>();
  //  private ArrayList<Edge> edgeFromDB = new ArrayList<Edge>();
  @Getter private ArrayList<Node> Nodes = new ArrayList<>();
  private ArrayList<Edge> Edges = new ArrayList<>();

  public Graph() throws SQLException {
    //    this.start = null;
    //    this.target = null;

    Nodes = this.getAllNodes(); // Changed based on DB team
    Edges = this.getAllEdges(); // Changed based on DB team

    //    nodeFromDB = Node.getAllNodes();
    //    edgeFromDB = Edge.getAllEdges();
    //    nodeFromDB = DataManager.getAllNodes();
    //    edgeFromDB = DataManager.getAllNodes();

    //    initializeNodes(nodeFromDB);
    this.initializeEdges();
  }

  public ArrayList<Node> getAllNodes() throws SQLException {
    return DataManager.getAllNodes();
  }

  public ArrayList<Edge> getAllEdges() throws SQLException {
    return DataManager.getAllEdges();
  }

  public void addEdge(Edge e) {
    this.Edges.add(e);
  }

  //  public List<Node> getNodes() {
  //    return Nodes;
  //  }

  public double findWeight(Node a, Node b) {
    return 0;
  }

  //  private void assignEdges() {}

  //  public void printPath(ArrayList<Node> nodes) {
  //    System.out.println(returnStringPath(target));
  //  }

  public void printPath(Node s, Node t) {
    System.out.println(AStar(s, t));
  }

  public void setAllG(Node s, Node t) {
    if (s == null || t == null) return;
    for (Node n : this.Nodes) {
      n.setG(findWeight(n, s));
    }
    s.setG(0);
  }

  public ArrayList<Node> AStar(Node s, Node t) {
    setAllG(s, t);
    Node start = s;
    Node target = t;

    PriorityQueue<Node> closedList = new PriorityQueue<>();
    PriorityQueue<Node> openList = new PriorityQueue<>();

    start.setF(start.getG() + start.calculateHeuristic(target));
    openList.add(start);

    while (!openList.isEmpty()) {
      Node ex = openList.peek();
      if (ex == target) {
        System.out.println(closedList.size());
        return getPath(ex);
      }

      for (Node nei : ex.getNeighbors()) {
        double totalWeight = ex.getG() + nei.findWeight(ex);

        System.out.println(closedList.size());

        if (!openList.contains(nei) && !closedList.contains(nei)) {
          nei.setParent(ex);
          nei.setG(totalWeight);
          nei.setF(nei.getG() + nei.calculateHeuristic(target));
          openList.add(nei);
        } else {
          if (totalWeight < nei.getG()) {
            nei.setParent(ex);
            nei.setG(totalWeight);
            nei.setF(nei.getG() + nei.calculateHeuristic(target));

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

  //  public static String returnStringPath(Node target) {
  //    List<Node> ids = getPath(target);
  //
  //    String strPath = "";
  //
  //    for (Node id : ids) {
  //      strPath += (id.getId() + " ");
  //    }
  //    strPath += "\n";
  //
  //    return strPath;
  //  }

  private static ArrayList<Node> getPath(Node target) {
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

  //  public void initializeNodes(ArrayList<Node> NodeLines) {
  //    // Initialize the nodes with the node lines data
  //    int i = 0;
  //    while (!NodeLines.isEmpty()) {
  //      // String[] I = NodeLines.get(0).split(",");
  //      // NodeLines.remove(0);
  //      Nodes.add(
  //              new Node(
  //                      NodeLines.get(0).getId(),
  //                      NodeLines.get(0).getX(),
  //                      NodeLines.get(0).getY(),
  //                      NodeLines.get(0).getFloor(),
  //                      NodeLines.get(0).getBuilding()));
  //      NodeLines.remove(0);
  //    }
  //  }

  // Get index for node from nodeID
  public Node findNodeByID(int nodeId) {
    return Nodes.get((nodeId - 100) / 5);
  }

  public void initializeEdges() {
    // Initialize the nodes with the node lines data

    for (int i = 0; i < Edges.size(); i++) {
      Node StartNode = this.findNodeByID(Edges.get(i).getStartNodeID());
      Node EndNode = this.findNodeByID(Edges.get(i).getEndNodeID());

      StartNode.getNeighbors().add(EndNode);
      EndNode.getNeighbors().add(StartNode);
    }
  }
  //    while (!Edges.isEmpty()) {
  //      // String[] E = EdgeLines.get(0).split(",");
  //      // EdgeLines.remove(0);
  //      Edges.add(
  //              new Edge(
  //                      Edges.get(0).getStartNodeID(),
  //                      Edges.get(0).getEndNodeID()));
  //
  //
  // this.findNodeByID(getEndNodeID);
  //
  //
  //
  //      Edges.remove(0);
  //    }
  //  }
}
