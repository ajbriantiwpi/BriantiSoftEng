package edu.wpi.teamname.navigation;

import java.util.List;

public class Node {

  public int id;
  public String floor;
  public String building;
  private int x;
  private int y;
  private Node parent = null;
  // f: sum of g and h;
  public double f = Double.MAX_VALUE;
  // g: Distance from start and node n
  public double g = Double.MAX_VALUE;
  // heuristic: WILL NEED A FUNCTION TO FIND THIS
  public double h;

  /**
   * Constructor
   *
   * @param ID
   * @param x
   * @param y
   * @param Floor
   * @param Building
   */
  public Node(int ID, int x, int y, String Floor, String Building) {}

  /** @return */
  public Node getParent() {
    return null;
  }

  /** @param parent */
  public void setParent(Node parent) {}

  /** @return */
  public List<Edge> getEdges() {
    return null;
  }

  /**
   * @param n
   * @return
   */
  public int compareTo(Node n) {
    return 0;
  }

  /** @return */
  public int getX() {
    return 0;
  }

  /** @return */
  public int getY() {
    return 0;
  }

  /** @return */
  public String getFloor() {
    return null;
  }

  /** @return */
  public String getBuilding() {
    return null;
  }

  /** @return */
  public List<Node> getNeighbors() {
    return null;
  }

  /** @param n */
  public void setNeighbor(Node n) {}

  /**
   * @param edge
   * @param s
   * @param e
   */
  public void addEdge(Edge edge, Node s, Node e) {}

  /**
   * @param b
   * @return
   */
  public double findWeight(Node b) {
    return 0;
  }

  /** @return */
  public int getId() {
    return 0;
  }

  /** @return */
  public String toString() {
    return null;
  }

  /**
   * @param target
   * @return
   */
  public double calculateHeuristic(Node target) {
    // Heuristic will return distance from target
    return 0;
  }
}
