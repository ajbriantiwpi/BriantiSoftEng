package edu.wpi.teamname.navigation;

import java.util.ArrayList;
import java.util.List;

public class Graph {

  private Node start;
  private Node target;
  private List<Node> nodes = new ArrayList<>();
  private List<Edge> edges = new ArrayList<>();

  Graph(List<Node> nodes, List<Edge> edges) {}

  public void addEdge(Edge e) {}

  public void setStart(Node n) {}

  public void setTarget(Node n) {}

  public List<Node> getNodes() {
    return nodes;
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

  public void printPath(ArrayList<Node> nodes) {}

  public ArrayList<Node> getAStarPath(Node n) {
    return null;
  }

  public String returnStringPath(Node n) {
    return "";
  }

  public void initializeNodes(ArrayList<Node> listNode) {}

  public void initializeEdges(ArrayList<Edge> listEdge) {}
}
