package edu.wpi.teamname.navigation;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

public class Move {
  @Getter @Setter private int nodeID;
  @Getter @Setter private String longName;
  @Getter @Setter private Timestamp date;

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
  }
}
