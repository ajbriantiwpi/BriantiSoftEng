package edu.wpi.teamname.extras;

import lombok.Getter;
import lombok.Setter;

public class Joke {
  @Getter @Setter int id;
  @Getter @Setter String type;
  @Getter @Setter String setup;
  @Getter @Setter String punchline;

  public Joke(int id, String type, String setup, String punchline) {
    this.id = id;
    this.type = type;
    this.setup = setup;
    this.punchline = punchline;
  }
}
