package edu.wpi.teamname.navigation;

import java.util.ArrayList;
import java.util.List;

public enum Direction {
  STRAIGHT("Straight"),
  LEFT("Left"),
  RIGHT("Right"),
  BACK("Back"),
  UP("Up"),
  STOPHERE("Stop Here"),
  START("Start"),
  END("End"),
  DOWN("Down");

  private final String type;

  Direction(String type) {
    this.type = type;
  }

  public String getString() {
    return type;
  }

  public static List<String> getAll() {
    List<String> allDirections = new ArrayList<>();
    for (Direction direction : values()) {
      allDirections.add(direction.getString());
    }
    return allDirections;
  }
}
