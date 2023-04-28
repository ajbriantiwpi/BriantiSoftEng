package edu.wpi.teamname.controllers;

import edu.wpi.teamname.Navigation;
import edu.wpi.teamname.Screen;
import edu.wpi.teamname.database.DataManager;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;

public class SettingsController {
  private static boolean wpiSelected = true;
  @FXML Slider volumeSlide;

  @FXML MFXButton dataManageButton;
  @FXML RadioButton wpiButton;
  @FXML RadioButton awsButton;

  @FXML
  public void initialize() throws SQLException {
    // Add a listener to the volume slider
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

    dataManageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.DATA_MANAGER));
  }

  private void setApplicationVolume(double volume) {
    // Implement logic to control the volume of the specific application
  }
}
