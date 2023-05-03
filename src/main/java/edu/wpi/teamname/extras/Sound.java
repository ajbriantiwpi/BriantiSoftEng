package edu.wpi.teamname.extras;

import java.net.URISyntaxException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.Getter;

public class Sound {

  @Getter private static Song backgroundSong = Song.OTJANBIRD1;

  @Getter private static MediaPlayer backgroundMusicPlayer;

  private static Media soundSFX;

  static {
    soundSFX = new Media(SFX.BUTTONCLICK.getFilename());
  }

  @Getter private static MediaPlayer buttonPlayer = new MediaPlayer(soundSFX);
  @Getter private static double volume = 0.5;

  /**
   * * Starts playing the background music It will play whatever song is currently set and stop a
   * song that is already playing to prevent overlap
   */
  public static void playMusic() throws URISyntaxException {
    if (backgroundMusicPlayer != null) {
      backgroundMusicPlayer.stop();
    }
    // Media sound = new Media(new File(backgroundSong.getFilename()).toString());
    Media sound = new Media(backgroundSong.getFilename());
    backgroundMusicPlayer = new MediaPlayer(sound);
    backgroundMusicPlayer.setVolume(volume);
    // mediaPlayer2.seek(Duration.ZERO);
    backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    backgroundMusicPlayer.setOnEndOfMedia(
        () -> {
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
  public static void setSong(Song song) throws URISyntaxException {
    backgroundSong = song;
    playMusic();
  }

  /**
   * * Sets the volume of both the background music and button click sound effects
   *
   * @param vol the volume to set the media players to
   */
  public static void setVolume(double vol) {
    volume = vol;
    backgroundMusicPlayer.setVolume(vol);
    buttonPlayer.setVolume(vol);
  }

  /** * Plays the button click sound effect Call this function whenever a button is pressed */
  public static void playSFX(SFX sfx) throws URISyntaxException {
    // soundSFX = new Media(new File(sfx.getFilename()).toString());
    soundSFX = new Media(sfx.getFilename());
    buttonPlayer = new MediaPlayer(soundSFX);
    buttonPlayer.setVolume(volume);
    buttonPlayer.play();
  }
}
