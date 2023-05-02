package edu.wpi.teamname.extras;

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
    return "src/main/resources/edu/wpi/teamname/sounds/sfx/" + filename;
  }

  public String getTitle() {
    return title;
  }
}
