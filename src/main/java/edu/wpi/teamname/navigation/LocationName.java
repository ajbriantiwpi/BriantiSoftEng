package edu.wpi.teamname.navigation;

import lombok.Getter;
import lombok.Setter;

public class LocationName {

  @Getter @Setter private String longName;
  @Getter @Setter private String shortName;
  @Getter @Setter private String nodeType;
  @Getter private final String originalLongName;

  public LocationName(String longName, String shortName, String nodeType) {
    this.shortName = shortName;
    this.longName = longName;
    this.nodeType = nodeType;
    this.originalLongName = longName;
  }

  public String toString() {
    return "[" + longName + ", " + shortName + ", " + nodeType + "]";
  }

  public boolean equals(LocationName other) {
    return other.longName.equals(this.longName);
  }
}
