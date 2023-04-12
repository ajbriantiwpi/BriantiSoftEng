package edu.wpi.teamname.navigation;

import edu.wpi.teamname.database.DataManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import lombok.Getter;

public class Graph {
  @Getter private ArrayList<Node> Nodes = new ArrayList<>();
  private ArrayList<Edge> Edges = new ArrayList<>();

  /**
   *    Creates a new Graph by retrieving Nodes and Edges from the database.
   *    @throws SQLException if there is an error accessing the database
   */
  public Graph() throws SQLException {
    Nodes = this.getAllNodes(); // Changed based on DB team
    Edges = this.getAllEdges(); // Changed based on DB team
    this.initializeEdges();
  }

  /**
   *    Retrieves all the Nodes from the database.
   *    @return an ArrayList of Nodes
   *    @throws SQLException if there is an error accessing the database
   */
  public ArrayList<Node> getAllNodes() throws SQLException {
    return DataManager.getAllNodes();
  }

  /**
   *    Retrieves all the Edges from the database.
   *    @return an ArrayList of Edges
   *    @throws SQLException if there is an error accessing the database
   */
  public ArrayList<Edge> getAllEdges() throws SQLException {
    return DataManager.getAllEdges();
  }

  /**
   *    Adds a new Edge to the graph.
   *    @param e the Edge to be added
   */
  public void addEdge(Edge e) {
    this.Edges.add(e);
  }

  /**
   *    Finds the weight between two Nodes.
   *    @param a the first Node
   *    @param b the second Node
   *    @return the weight between the two Nodes
   */
  public double findWeight(Node a, Node b) {
    return 0;
  }

  /**
   * Prints a path from the start Node to the target Node using the A* algorithm.
   * @param s the start Node
   * @param t the target Node
   */
  public void printPath(Node s, Node t) {
    System.out.println(AStar(s, t));
  }

  /**
   * Sets the G value of all Nodes in the graph.
   * @param s the start Node
   * @param t the target Node
   */
  public void setAllG(Node s, Node t) {
    if (s == null || t == null) return;
    for (Node n : this.Nodes) {
      n.setG(findWeight(n, s));
    }
    s.setG(0);
  }

  /**
   * Returns the shortest path between two nodes using A* algorithm.
   * @param s the starting node.
   * @param t the ending node.
   * @return a list of nodes representing the shortest path between the starting node and the ending node.
   */
  public ArrayList<Node> AStar(Node s, Node t) {
    for (Node j : Nodes) {
      j.setParent(null);
    }

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

  /**
   *    This method returns the path from the target node to the start node.
   *
   *    @param target The target node to start the path from
   *
   *    @return The path from the target node to the start node
   */
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

  /**
   * This method returns the node with the given ID.
   * @param nodeId The ID of the node to be returned
   * @return The node with the given ID
   */
  public Node findNodeByID(int nodeId) {
    return Nodes.get((nodeId - 100) / 5);
  }

  /**
   * This method initializes the edges in the graph by creating and setting neighbors for each node.
   */
  public void initializeEdges() {
    // Initialize the nodes with the node lines data
    for (int i = 0; i < Edges.size(); i++) {
      Node StartNode = this.findNodeByID(Edges.get(i).getStartNodeID());
      Node EndNode = this.findNodeByID(Edges.get(i).getEndNodeID());

      StartNode.getNeighbors().add(EndNode);
      EndNode.getNeighbors().add(StartNode);
    }
  }
}
