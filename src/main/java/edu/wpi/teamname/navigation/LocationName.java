package edu.wpi.teamname.navigation;

import lombok.Getter;
import lombok.Setter;

public class LocationName {

  @Getter @Setter private String longName;
  @Getter @Setter private String shortName;
  @Getter @Setter private String nodeType;
  @Getter private final String originalLongName;

  /**
   *    Constructs a new LocationName object with the given long name, short name, and node type.
   *    @param longName the long name of the location.
   *    @param shortName the short name of the location.
   *    @param nodeType the type of the node the location represents.
   */
  public LocationName(String longName, String shortName, String nodeType) {
    this.shortName = shortName;
    this.longName = longName;
    this.nodeType = nodeType;
    this.originalLongName = longName;
  }

  /**
   *    Returns a string representation of this LocationName object, including its long name, short name, and node type.
   *    @return a string representation of this LocationName object.
   */
  public String toString() {
    return "[" + longName + ", " + shortName + ", " + nodeType + "]";
  }

  /**
   *    Compares this LocationName object to another object for equality based on their long names.
   *    @param other the other LocationName object to compare to.
   *    @return true if the two LocationName objects have the same long name, false otherwise.
   */
  public boolean equals(LocationName other) {
    return other.longName.equals(this.longName);
  }
}
