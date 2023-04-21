package edu.wpi.teamname.navigation;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

public class PathMessage {
  @Getter @Setter private int startNodeID;
  @Getter @Setter private int endNodeID;
  @Getter @Setter private String algorithm;
  @Getter @Setter private Timestamp date;
  @Getter @Setter private int adminID;
  @Getter @Setter private String message;

  public PathMessage(
      int startNodeID,
      int endNodeID,
      String algorithm,
      Timestamp date,
      int adminID,
      String message) {
    this.startNodeID = startNodeID;
    this.endNodeID = endNodeID;
    this.algorithm = algorithm;
    this.date = date;
    this.adminID = adminID;
    this.message = message;
  }

  public PathMessage(String message, int adminID, Timestamp date) {
    this.date = date;
    this.adminID = adminID;
    this.message = message;
  }

  /**
   * Returns a string representation of this PathMessage object.
   *
   * @return a string representation of this PathMessage object
   */
  public String toString() {
    return "["
        + startNodeID
        + ", "
        + endNodeID
        + ", "
        + algorithm
        + ", "
        + date
        + ", "
        + adminID
        + ", "
        + message
        + "]";
  }

  /**
   * Compares this PathMessage object to another PathMessage object for equality.
   *
   * @param other the PathMessage object to compare to
   * @return true if the two objects are equal, false otherwise
   */
  public Boolean equals(PathMessage other) {
    return this.startNodeID == other.getStartNodeID()
        && this.endNodeID == (other.getEndNodeID())
        && this.algorithm.equals(other.getAlgorithm());
  }
}
