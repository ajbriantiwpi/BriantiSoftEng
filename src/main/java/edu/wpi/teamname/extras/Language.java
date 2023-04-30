package edu.wpi.teamname.extras;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public enum Language implements Observable {
  ENGLISH("English"),
  FRENCH("Français"),
  ITALIAN("Italiano"),
  SPANISH("Espanol"),
  JAPANESE("日本語");

  private final String type;

  Language(String type) {
    this.type = type;
  }

  public String getString() {
    return type;
  }

  @Override
  public void addListener(InvalidationListener listener) {}

  @Override
  public void removeListener(InvalidationListener listener) {}
}
