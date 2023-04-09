package edu.wpi.teamname.navigation;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Edge {

  @Getter final private int originalStartNodeID;
  @Getter final private int originalEndNodeID;
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
