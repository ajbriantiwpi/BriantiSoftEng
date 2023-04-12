package edu.wpi.teamname.navigation;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

public class Move {
  @Getter @Setter private int nodeID;
  @Getter @Setter private String longName;
  @Getter @Setter private Timestamp date;
  @Getter private final int originalNodeID;
  @Getter private final String originalLongName;
  @Getter private final String originalDate;

  /**
   * Constructor
   *
   * @param nodeID
   * @param longName
   * @param date
   */
  public Move(int nodeID, String longName, Timestamp date) {
    this.nodeID = nodeID;
    this.longName = longName;
    this.date = date;
    this.originalNodeID = nodeID;
    this.originalLongName = longName;
    this.originalDate = date.toString();
  }

  public String toString() {
    return "[" + nodeID + ", " + longName + ", " + date + "]";
  }

  public Boolean equals(Move other) {
    return this.nodeID == other.getNodeID()
        && this.longName.equals(other.getLongName())
        && date.equals(other.getDate());
  }
}
