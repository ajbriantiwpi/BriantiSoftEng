package edu.wpi.teamname.navigation;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

public class LocationName {

  @Getter @Setter private String longName;
  @Getter @Setter private String shortName;
  @Getter @Setter private String nodeType;

  public LocationName(String longName, String shortName, String nodeType) {
    this.shortName = shortName;
    this.longName = longName;
    this.nodeType = nodeType;
  }
}
