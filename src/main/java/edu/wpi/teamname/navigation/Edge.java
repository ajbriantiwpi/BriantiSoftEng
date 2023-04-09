package edu.wpi.teamname.navigation;

import lombok.Getter;
import lombok.Setter;

public class Edge {

  @Getter private final int originalStartNodeID;
  @Getter private final int originalEndNodeID;
  @Getter @Setter private int startNodeID;
  @Getter @Setter private int endNodeID;

  public Edge(int startNodeID, int endNodeID) {
    this.startNodeID = startNodeID;
    this.endNodeID = endNodeID;
    this.originalStartNodeID = startNodeID;
    this.originalEndNodeID = endNodeID;
  }

  /** @return */
  public String toString() {
    return "";
  }
}
