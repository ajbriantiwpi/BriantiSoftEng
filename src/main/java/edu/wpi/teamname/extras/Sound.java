package edu.wpi.teamname.extras;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

public class Sound {
  @Getter @Setter
  private static String musicFile =
      "src/main/resources/edu/wpi/teamname/sounds/eco-technology-145636.mp3";

  private static MediaPlayer backgroundMusicPlayer;

  public static void playMusic() {
    if (backgroundMusicPlayer != null) {
      backgroundMusicPlayer.stop();
    }
    Media sound = new Media(new File(musicFile).toURI().toString());
    backgroundMusicPlayer = new MediaPlayer(sound);

    // mediaPlayer2.seek(Duration.ZERO);
    backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    backgroundMusicPlayer.setOnEndOfMedia(
        () -> {
          System.out.println("Media has finished playing.");
          backgroundMusicPlayer.seek(Duration.ZERO);
          backgroundMusicPlayer.play();
        });
    backgroundMusicPlayer.play();
  }
}
