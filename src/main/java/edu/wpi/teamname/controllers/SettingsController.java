package edu.wpi.teamname.controllers;

import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.Navigation;
import edu.wpi.teamname.Screen;
import edu.wpi.teamname.ThemeSwitch;
import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.employees.ClearanceLevel;
import edu.wpi.teamname.extras.Song;
import edu.wpi.teamname.extras.Sound;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

/**
 * The SettingsController class is responsible for managing the settings screen of the application.
 * It provides functionality for changing the database connection, managing application data, and
 * adjusting the application's volume.
 */
public class SettingsController {
  @FXML AnchorPane root;
  private static boolean wpiSelected = true;
  @FXML Slider volumeSlide;
  @FXML MFXButton feedbackButton;
  @FXML Label dbConnectionLabel;
  @FXML Label appSettingsLabel;
  @FXML ComboBox songCombo;

  @FXML MFXButton dataManageButton;
  @FXML MFXButton viewFeedbackButton;
  @FXML RadioButton wpiButton;
  @FXML RadioButton awsButton;
  /**
   * Initializes the SettingsController and sets up the UI elements and functionality.
   *
   * @throws SQLException if there is an error connecting to the database
   */
  @FXML
  public void initialize() throws SQLException {
    ThemeSwitch.switchTheme(root);
    ParentController.titleString.set("Settings");
    viewFeedbackButton.setDisable(true);
    wpiButton.setDisable(true);
    awsButton.setDisable(true);
    feedbackButton.setDisable(true);
    appSettingsLabel.setVisible(false);
    dbConnectionLabel.setVisible(false);
    dataManageButton.setVisible(false);
    viewFeedbackButton.setVisible(false);
    wpiButton.setVisible(false);
    feedbackButton.setVisible(false);
    awsButton.setVisible(false);
    if (GlobalVariables.userIsClearanceLevel(ClearanceLevel.ADMIN)) {
      viewFeedbackButton.setDisable(false);
      wpiButton.setDisable(false);
      awsButton.setDisable(false);
      feedbackButton.setDisable(false);
      appSettingsLabel.setVisible(true);
      dbConnectionLabel.setVisible(true);
      dataManageButton.setVisible(true);
      viewFeedbackButton.setVisible(true);
      wpiButton.setVisible(true);
      feedbackButton.setVisible(true);
      awsButton.setVisible(true);
    } else if (GlobalVariables.userIsClearanceLevel(ClearanceLevel.STAFF)) {
      viewFeedbackButton.setDisable(false);
      wpiButton.setDisable(false);
      awsButton.setDisable(false);
      feedbackButton.setDisable(false);
      appSettingsLabel.setVisible(true);
      dbConnectionLabel.setVisible(true);
      dataManageButton.setVisible(true);
      viewFeedbackButton.setVisible(true);
      wpiButton.setVisible(true);
      feedbackButton.setVisible(true);
      awsButton.setVisible(true);
    }
    // Add a listener to the volume slider
    volumeSlide.setValue(Sound.getVolume());
    volumeSlide
        .valueProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              // Change volume of the application
              setApplicationVolume(newValue.doubleValue());
            });

    // Create a ToggleGroup to ensure only one button can be selected at a time
    ToggleGroup databaseToggleGroup = new ToggleGroup();

    wpiButton.setToggleGroup(databaseToggleGroup);
    awsButton.setToggleGroup(databaseToggleGroup);
    if (wpiSelected) {
      wpiButton.setSelected(true);
    } else {
      awsButton.setSelected(true);
    }

    // Hook up the methods to the toggle buttons
    wpiButton.setOnAction(
        e -> {
          try {
            DataManager.connectToWPI();
            wpiSelected = true;
          } catch (SQLException ex) {
            ex.printStackTrace();
          }
        });

    awsButton.setOnAction(
        e -> {
          try {
            DataManager.connectToAWS();
            wpiSelected = false;
          } catch (SQLException ex) {
            ex.printStackTrace();
          }
        });
    // Set up the songCombo ComboBox
    for (Song song : Song.values()) {
      if (song == Song.JETPACKJOYRIDE
          && !GlobalVariables.getCurrentUser().getUsername().equals("ian")) {
        continue; // Skip adding the JETPACKJOYRIDE song if the user is not "ian"
      }
      songCombo.getItems().add(song.getTitle());
    }

    // Set the initial selection of the ComboBox
    songCombo.getSelectionModel().select(Sound.getBackgroundSong().getTitle());

    // Add a listener to the songCombo ComboBox
    songCombo
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              // Change the background song of the application
              for (Song song : Song.values()) {
                if (song.getTitle().equals(newValue)) {
                  Sound.setSong(song);
                  break;
                }
              }
            });

    dataManageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.DATA_MANAGER));
    feedbackButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FEEDBACK));
    viewFeedbackButton.setOnMouseClicked(event -> Navigation.navigate(Screen.VIEW_FEEDBACK));
  }
  /**
   * Sets the volume of the application to the specified value.
   *
   * @param volume the new volume value
   */
  private void setApplicationVolume(double volume) {
    Sound.setVolume(volume);
  }
}
