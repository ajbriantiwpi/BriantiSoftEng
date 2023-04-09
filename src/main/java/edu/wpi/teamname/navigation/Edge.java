package edu.wpi.teamname.navigation;

import edu.wpi.teamname.database.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

  // Returns all the attributes of a Node as a String
  public String toString() {
    return "StartNodeID: " + startNodeID + " EndNodeID: " + endNodeID;
  }
}
