package edu.wpi.teamname.extras;

public enum Song {
  OTJANBIRD("Otjanbird.mp3", "Otjánbird Pt. I"),
  JETPACKJOYRIDE("jetpackjoyridegoodloop.mp3", "Jetpack Joyride Main Theme");

  private final String filename;
  private final String title;

  Song(String filename, String title) {
    this.filename = filename;
    this.title = title;
  }

  public String getFilename() {
    return "src/main/resources/edu/wpi/teamname/sounds/" + filename;
  }

  public String getTitle() {
    return title;
  }
}
