package edu.wpi.teamname.navigation;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.navigation.AlgoStrategy.AStarAlgo;
import edu.wpi.teamname.navigation.AlgoStrategy.IStrategyAlgo;
import java.sql.SQLException;
import java.util.ArrayList;
import lombok.Getter;

public class Graph {
  @Getter private ArrayList<Node> nodes = new ArrayList<>();
  @Getter private ArrayList<Edge> Edges = new ArrayList<>();
  private IStrategyAlgo pathfindingAlgo;
  private final int FLOORCHANGEDIST = 100;

  /**
   * Creates a new Graph by retrieving Nodes and Edges from the database.
   *
   * @throws SQLException if there is an error accessing the database
   */
  public Graph() throws SQLException {
    nodes = this.getAllNodes(); // Changed based on DB team
    Edges = this.getAllEdges(); // Changed based on DB team
    this.initializeEdges();
    pathfindingAlgo = new AStarAlgo();
    // pathfindingAlgo = new BFSAlgo();

    pathfindingAlgo = new AStarAlgo();

    // pathfindingAlgo = new DijkstraAlgo();
  }

  public Graph(ArrayList<Node> nodes, ArrayList<Edge> edges) {
    this.nodes = nodes; // Changed based on DB team
    Edges = edges; // Changed based on DB team
    this.initializeEdges();
    pathfindingAlgo = null;
  }

  /**
   * Retrieves all the Nodes from the database.
   *
   * @return an ArrayList of Nodes
   * @throws SQLException if there is an error accessing the database
   */
  private ArrayList<Node> getAllNodes() throws SQLException {
    ArrayList<Node> nodes = DataManager.getAllNodes();
    for (Node n : nodes) {
      if (n.getFloor().equals("L2")) n.setZ(0);
      else if (n.getFloor().equals("L1")) n.setZ(FLOORCHANGEDIST);
      else if (n.getFloor().equals("1")) n.setZ(FLOORCHANGEDIST * 2);
      else if (n.getFloor().equals("2")) n.setZ(FLOORCHANGEDIST * 3);
      else if (n.getFloor().equals("3")) n.setZ(FLOORCHANGEDIST * 4);
    }
    return nodes;
  }

  /**
   * Retrieves all the Edges from the database.
   *
   * @return an ArrayList of Edges
   * @throws SQLException if there is an error accessing the database
   */
  private ArrayList<Edge> getAllEdges() throws SQLException {
    return DataManager.getAllEdges();
  }

  /**
   * Adds a new Edge to the graph.
   *
   * @param e the Edge to be added
   */
  public void addEdge(Edge e) {
    this.Edges.add(e);
  }

  /**
   * Finds the weight between two Nodes.
   *
   * @param a the first Node
   * @param b the second Node
   * @return the weight between the two Nodes
   */
  public double findWeight(Node a, Node b) {
    return 0;
  }

  /**
   * Prints a path from the start Node to the target Node using the A* algorithm.
   *
   * @param startNodeIndex the start Node
   * @param targetNodeIndex the target Node
   */
  public void printPath(int startNodeIndex, int targetNodeIndex) {
    ArrayList<Node> path = this.getPathBetween(startNodeIndex, targetNodeIndex);
    for (Node n : path) System.out.print(n.getId() + " ");
  }

  /**
   * Sets the G value of all Nodes in the graph.
   *
   * @param s the start Node
   * @param t the target Node
   */
  public void setAllG(Node s, Node t) {
    if (s == null || t == null) return;
    for (Node n : this.nodes) {
      n.setG(findWeight(t, s));
    }
    s.setG(0);
  }

  /**
   * This method returns the node with the given ID.
   *
   * @param nodeId The ID of the node to be returned
   * @return The node with the given ID
   */
  public Node findNodeByID(int nodeId) {
    return nodes.get(Node.idToIndex(nodeId));
  }

  /**
   * This method initializes the edges in the graph by creating and setting neighbors for each node.
   */
  private void initializeEdges() {
    // Initialize the nodes with the node lines data
    for (int i = 0; i < Edges.size(); i++) {
      Node startNode = this.findNodeByID(Edges.get(i).getStartNodeID());
      Node endNode = this.findNodeByID(Edges.get(i).getEndNodeID());

      startNode.getNeighbors().add(endNode);
      endNode.getNeighbors().add(startNode);
    }
  }

  public IStrategyAlgo getPathfindingAlgo() {
    return pathfindingAlgo;
  }

  public void setPathfindingAlgo(IStrategyAlgo algo) {
    this.pathfindingAlgo = algo;
  }

  public ArrayList<Node> getPathBetween(int startNodeId, int targetNodeId) {
    return pathfindingAlgo.getPathBetween(this, startNodeId, targetNodeId);
  }

  public ArrayList<Node> getPathBetween(Node startNode, Node endNode) {
    return pathfindingAlgo.getPathBetween(this, startNode.getId(), endNode.getId());
  }
}
