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

    public Node(int ID, int x, int y, String Floor, String Building) {

    }


    public Node getParent() {
        return null;
    }

    public void setParent(Node parent) {

    }

    public List<Edge> getEdges() {
        return null;
    }

    public int compareTo(Node n) {
        return 0;
    }

    public int getX() {
        return 0;
    }

    public int getY() {
        return 0;
    }

    public String getFloor() {
        return null;
    }

    public String getBuilding() {
        return null;
    }

    public List<Node> getNeighbors() {
        return null;
    }

    public void setNeighbor(Node n) {

    }

    public void addEdge(Edge edge, Node s, Node e) {

    }

    public double findWeight(Node b) {
        return 0;
    }

    public int getId() {
        return 0;
    }

    public String toString() {
        return null;
    }

    public double calculateHeuristic(Node target) {
        // Heuristic will return distance from target
        return 0;
    }
}
