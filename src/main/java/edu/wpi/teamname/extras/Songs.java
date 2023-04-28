package edu.wpi.teamname.extras;

public enum Songs {
  JETPACKJOYRIDE("jetpackjoyridegoodloop.mp3"),
  TEMPLATE("Template.fxml"),
  HOME("Home.fxml");

  private final String filename;

  Songs(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return "src/main/resources/edu/wpi/teamname/sounds/" + filename;
  }
}
