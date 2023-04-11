package edu.wpi.teamname.navigation;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

public class Room {
  @Getter @Setter private int nodeID;
  @Getter @Setter private String longName;
  @Getter @Setter private Timestamp date;
  @Getter @Setter private int xcoord;
  @Getter @Setter private int ycoord;
  @Getter @Setter private String floor;
  @Getter @Setter private String building;
  @Getter @Setter private String shortName;
  @Getter @Setter private String nodeType;

  public Room(
      int nodeID,
      String longName,
      Timestamp date,
      int xcoord,
      int ycoord,
      String floor,
      String building,
      String shortName,
      String nodeType) {
    this.nodeID = nodeID;
    this.longName = longName;
    this.date = date;
    this.xcoord = xcoord;
    this.ycoord = ycoord;
    this.floor = floor;
    this.building = building;
    this.shortName = shortName;
    this.nodeType = nodeType;
  }

  @Override
  public String toString() {
    return "Room{"
        + "nodeID="
        + nodeID
        + ", longName='"
        + longName
        + '\''
        + ", date="
        + date
        + ", xcoord="
        + xcoord
        + ", ycoord="
        + ycoord
        + ", floor='"
        + floor
        + '\''
        + ", building='"
        + building
        + '\''
        + ", shortName='"
        + shortName
        + '\''
        + ", nodeType='"
        + nodeType
        + '\''
        + '}';
  }
}
