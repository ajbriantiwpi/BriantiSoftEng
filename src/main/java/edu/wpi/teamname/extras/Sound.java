package edu.wpi.teamname.extras;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.Getter;

public class Sound {

  @Getter private static Song backgroundSong = Song.OTJANBIRD;
  @Getter private static MediaPlayer backgroundMusicPlayer;
  @Getter private static MediaPlayer buttonPlayer;

  public static void playMusic() {
    if (backgroundMusicPlayer != null) {
      backgroundMusicPlayer.stop();
    }
    Media sound = new Media(new File(backgroundSong.getFilename()).toURI().toString());
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

  public static void stopMusic() {
    backgroundMusicPlayer.stop();
  }

  public static void setSong(Song song) {
    backgroundSong = song;
    playMusic();
  }

  public static void setVolume(float vol) {
    backgroundMusicPlayer.setVolume(vol);
    buttonPlayer.setVolume(vol);
  }

  public static void playOnButtonClick() {
    String filename = "src/main/resources/edu/wpi/teamname/sounds/buttonclick.mp3";
    Media sound = new Media(new File(filename).toURI().toString());
    buttonPlayer = new MediaPlayer(sound);
    buttonPlayer.play();
  }
}
