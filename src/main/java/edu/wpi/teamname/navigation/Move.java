package edu.wpi.teamname.navigation;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

public class Move {
  @Getter @Setter private int nodeID;
  @Getter @Setter private String longName;
  @Getter @Setter private LocalDate date;

  /**
   * Constructor
   *
   * @param nodeID
   * @param longName
   * @param date
   */
  public Move(int nodeID, String longName, LocalDate date) {
    this.nodeID = nodeID;
    this.longName = longName;
    this.date = date;
  }
}
