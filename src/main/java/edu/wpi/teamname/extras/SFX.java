package edu.wpi.teamname.extras;

import edu.wpi.teamname.App;

public enum SFX {
  BUTTONCLICK("buttonclick.mp3", "Button Click"),
  ERROR("error-sound-fx.wav", "Error"),
  NOTIFICATION("alerts.mp3", "Notification"),
  SUCCESS("success.mp3", "Success"),
  VINE("vine-boom.mp3", "Vine");

  private final String filename;
  private final String title;

  SFX(String filename, String title) {
    this.filename = filename;
    this.title = title;
  }

  public String getFilename() {
    String fn = "sounds/sfx/" + filename;
    // return getClass().getResource(fn);
    return App.class.getResource(fn).toString();
  }

  public String getTitle() {
    return title;
  }
}
