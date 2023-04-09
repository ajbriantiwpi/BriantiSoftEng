package edu.wpi.teamname.navigation;

import edu.wpi.teamname.database.DataManager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Node {

  @Getter @Setter public int id;
  @Getter @Setter public String floor;
  @Getter public String building;
  @Getter private int x;
  @Getter private int y;
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
  public Node(int ID, int x, int y, String Floor, String Building) {
    this.id = ID;
    this.x = x;
    this.y = y;
    this.floor = Floor;
    this.building = Building;
  }

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
  public String toString() {
    return "[" + id + ", " + x + ", " + y + ", " + floor + ", " + building + "]";
  }

  /**
   * @param target
   * @return
   */
  public double calculateHeuristic(Node target) {
    // Heuristic will return distance from target
    return 0;
  }

  /**
   * Checks if this.nodeID has switched with given nodeID to swap positions, floor and building in
   * the table with it in the edge table also swaps correlating information as well as switching the
   * longNames with eachother in LocationName table
   *
   * @param move
   * @return Boolean
   */
  public boolean moveNode(Move move) throws SQLException {
    Connection connection = DataManager.DbConnection();
    boolean done = false;
    int swapNodeID = move.getNodeID();
    String swapLongN = move.getLongName();
    int thisNode = this.id;

    String getLn = "Select \"longName\" from \"Move\" where \"nodeID\" = " + thisNode;
    try (PreparedStatement s = connection.prepareStatement(getLn)) {
      ResultSet rowsUpdated = s.executeQuery();
      String longN = rowsUpdated.getString("longName");
    } catch (SQLException e2) {
      System.out.println("Error getting long name.");
    }

    /** Might use sync functions with this feature */
    // A starting node, B is node being swapped with

    // rowAlocN = select longName from LocationName where longN = longN
    // rowBlocN = select longName from LocationName where longN = swapLongN
    // rowAnode = select floor, building from Node where thisNode = thisNode
    // rowBnode = select floor, building from Node where thisNode = swapNodeID

    // put rowAlocN where longN = swapLongN
    // put rowAnode where thisNode = swapNodeID
    // insert nodeID, longN, date into Move

    // put rowBnode where thisNode = thisNode
    // put rowBlocN where longN = longN

    // insert swapNodeID, swapLongN, date into Move

    String query = "";

    try (PreparedStatement pstmtUpdate = connection.prepareStatement(query)) {
      int rowsUpdated = pstmtUpdate.executeUpdate();
      if (rowsUpdated > 0) {
        System.out.println("successfully updated");
      } else {
        System.out.println("not updated");
      }
    } catch (SQLException e) {
      System.out.println("Error updating LocationName record for node ID " + thisNode);
    }

    return done;
  }
}
