package edu.wpi.teamname.extras;

public enum Song {
  OTJANBIRD1("Otjanbird-Pt.-I.mp3", "Otjánbird Pt. I"),
  OTJANBIRD2("Otjanbird-Pt.-II.mp3", "Otjánbird Pt. II"),
  HOMEWORK("homework.mp3", "Homework"),
  CRUISINALONG("cruisin-along.mp3", "Cruisin Along"),
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
