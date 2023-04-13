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
   *    Constructs a new Move object with the given node ID, long name, and timestamp.
   *    @param nodeID the ID of the node associated with the move
   *    @param longName the long name of the location associated with the move
   *    @param date the timestamp associated with the move
   */
  public Move(int nodeID, String longName, Timestamp date) {
    this.nodeID = nodeID;
    this.longName = longName;
    this.date = date;
    this.originalNodeID = nodeID;
    this.originalLongName = longName;
    this.originalDate = date.toString();
  }

  /**
   *    Returns a string representation of this Move object.
   *    @return a string representation of this Move object
   */
  public String toString() {
    return "[" + nodeID + ", " + longName + ", " + date + "]";
  }

  /**
   *    Compares this Move object to another Move object for equality.
   *    @param other the Move object to compare to
   *    @return true if the two objects are equal, false otherwise
   */
  public Boolean equals(Move other) {
    return this.nodeID == other.getNodeID()
        && this.longName.equals(other.getLongName())
        && date.equals(other.getDate());
  }
}
