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

  /**
   *    Represents a room with its information including node ID, long name, timestamp, x coordinate, y coordinate, floor,
   *    building, short name, and node type.
   *    @param nodeID the ID of the room node
   *    @param longName the long name of the room
   *    @param date the date the room information was collected
   *    @param xcoord the x-coordinate of the room
   *    @param ycoord the y-coordinate of the room
   *    @param floor the floor where the room is located
   *    @param building the building where the room is located
   *    @param shortName the short name of the room
   *    @param nodeType the type of the room node
   */
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

  /**
   *    Returns a string representation of the room with its information.
   *    @return a string representation of the room
   */
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
