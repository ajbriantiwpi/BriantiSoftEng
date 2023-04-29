package edu.wpi.teamname.extras;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.Getter;

public class Sound {

  @Getter private static Song backgroundSong = Song.OTJANBIRD2;
  @Getter private static MediaPlayer backgroundMusicPlayer;
  @Getter private static MediaPlayer buttonPlayer;

  /**
   * * Starts playing the background music It will play whatever song is currently set and stop a
   * song that is already playing to prevent overlap
   */
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

  /** * Stops the current background music player */
  public static void stopMusic() {
    backgroundMusicPlayer.stop();
  }

  /**
   * * Sets the music to the new song and starts playing it
   *
   * @param song the new song to play
   */
  public static void setSong(Song song) {
    backgroundSong = song;
    playMusic();
  }

  /**
   * * Sets the volume of both the background music and button click sound effects
   *
   * @param vol the volume to set the media players to
   */
  public static void setVolume(float vol) {
    backgroundMusicPlayer.setVolume(vol);
    buttonPlayer.setVolume(vol);
  }

  /** * Plays the button click sound effect Call this function whenever a button is pressed */
  public static void playOnButtonClick() {
    String filename = "src/main/resources/edu/wpi/teamname/sounds/buttonclick.mp3";
    Media sound = new Media(new File(filename).toURI().toString());
    buttonPlayer = new MediaPlayer(sound);
    buttonPlayer.play();
  }
}
