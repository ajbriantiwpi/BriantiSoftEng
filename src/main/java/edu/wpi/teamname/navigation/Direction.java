package edu.wpi.teamname.navigation;

import edu.wpi.teamname.GlobalVariables;
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

  public String getTranslatedString() {
    switch (type) {
      case "Straight":
        switch (GlobalVariables.getB().getValue()) {
          case ENGLISH:
            return "Straight";
          case ITALIAN:
            return "Dritto";
          case FRENCH:
            return "Droit";
          case SPANISH:
            return "Recto";
        }
        break;
      case "Left":
        switch (GlobalVariables.getB().getValue()) {
          case ENGLISH:
            return "Left";
          case ITALIAN:
            return "Sinistra";
          case FRENCH:
            return "Gauche";
          case SPANISH:
            return "Izquierda";
        }
        break;
      case "Right":
        switch (GlobalVariables.getB().getValue()) {
          case ENGLISH:
            return "Right";
          case ITALIAN:
            return "Addestra";
          case FRENCH:
            return "Droite";
          case SPANISH:
            return "Derecha";
        }
        break;
      case "Back":
        switch (GlobalVariables.getB().getValue()) {
          case ENGLISH:
            return "Back";
          case ITALIAN:
            return "Ritornare";
          case FRENCH:
            return "Arrière";
          case SPANISH:
            return "Retrocede";
        }
        break;
      case "Up":
        switch (GlobalVariables.getB().getValue()) {
          case ENGLISH:
            return "Up";
          case ITALIAN:
            return "Su";
          case FRENCH:
            return "En haut";
          case SPANISH:
            return "Arriba";
        }
        break;
      case "Stop Here":
        switch (GlobalVariables.getB().getValue()) {
          case ENGLISH:
            return "Stop Here";
          case ITALIAN:
            return "Fermati Qui";
          case FRENCH:
            return "Arrêtez ici";
          case SPANISH:
            return "Deténgase Aquí";
        }
        break;
      case "Start":
        switch (GlobalVariables.getB().getValue()) {
          case ENGLISH:
            return "Start";
          case ITALIAN:
            return "Inizio";
          case FRENCH:
            return "Début";
          case SPANISH:
            return "Comenzar";
        }
        break;
      case "End":
        switch (GlobalVariables.getB().getValue()) {
          case ENGLISH:
            return "End";
          case ITALIAN:
            return "Fine";
          case FRENCH:
            return "Fin";
          case SPANISH:
            return "Final";
        }
        break;
      case "Down":
        switch (GlobalVariables.getB().getValue()) {
          case ENGLISH:
            return "Down";
          case ITALIAN:
            return "Giù";
          case FRENCH:
            return "En bas";
          case SPANISH:
            return "Abajo";
        }
        break;
    }
    return type;
  }

  public String getString() {

    return type;
  }

  public Direction getInverse() {
    if (this == Direction.UP) {
      return Direction.DOWN;
    } else if (this == Direction.DOWN) {
      return Direction.UP;
    } else if (this == Direction.LEFT) {
      return Direction.RIGHT;
    } else if (this == Direction.RIGHT) {
      return Direction.LEFT;
    } else {
      return this;
    }
  }

  public static List<String> getAll() {
    List<String> allDirections = new ArrayList<>();
    for (Direction direction : values()) {
      allDirections.add(direction.getString());
    }
    return allDirections;
  }
}
