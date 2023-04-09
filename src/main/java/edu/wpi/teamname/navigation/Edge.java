package edu.wpi.teamname.navigation;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Edge {

  @Getter @Setter private int startNodeID;
  @Getter @Setter private int endNodeID;

  public Edge(int startNodeID, int endNodeID) {
    this.startNodeID = startNodeID;
    this.endNodeID = endNodeID;
  }

  /** @return */
  public String toString() {
    return "";
  }
}
