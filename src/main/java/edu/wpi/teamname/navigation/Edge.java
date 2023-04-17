package edu.wpi.teamname.navigation;

import edu.wpi.teamname.database.*;
import lombok.Getter;
import lombok.Setter;

public class Edge {

  @Getter private final int originalStartNodeID;
  @Getter private final int originalEndNodeID;
  @Getter @Setter private int startNodeID;
  @Getter @Setter private int endNodeID;
  @Getter @Setter private float weight;

  /**
   * Constructs a new Edge object with the specified starting and ending node IDs.
   *
   * @param startNodeID the ID of the starting node
   * @param endNodeID the ID of the ending node
   */
  public Edge(int startNodeID, int endNodeID) {
    this.startNodeID = startNodeID;
    this.endNodeID = endNodeID;
    this.originalStartNodeID = startNodeID;
    this.originalEndNodeID = endNodeID;
  }

  public Edge(int startNodeID, int endNodeID, float weight) {
    this.startNodeID = startNodeID;
    this.endNodeID = endNodeID;
    this.originalStartNodeID = startNodeID;
    this.originalEndNodeID = endNodeID;
    this.weight = weight;
  }

  /**
   * Determines whether this edge is equal to another edge.
   *
   * @param other the other edge to compare to this edge
   * @return true if the edges are equal, false otherwise
   */
  public Boolean equals(Edge other) {
    return this.startNodeID == other.getStartNodeID() && this.endNodeID == other.getEndNodeID();
  }

  /**
   * Returns a string representation of this edge, including its starting and ending node IDs.
   *
   * @return a string representation of this edge
   */
  public String toString() {
    return "StartNodeID: " + startNodeID + " EndNodeID: " + endNodeID;
  }
}
