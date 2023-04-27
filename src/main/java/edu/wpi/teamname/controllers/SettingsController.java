package edu.wpi.teamname.controllers;

import edu.wpi.teamname.Navigation;
import edu.wpi.teamname.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;

import java.sql.SQLException;

public class SettingsController {
    @FXML
    Slider volumeSlide;
    @FXML
    Slider brightnessSlide;

    @FXML
    MFXButton dataButton;

    @FXML
    public void initialize() throws SQLException {
        // Add a listener to the volume slider
        volumeSlide.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Change volume of the application
            setApplicationVolume(newValue.doubleValue());
        });

        // Add a listener to the brightness slider
        brightnessSlide.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Change brightness of the application
            setApplicationBrightness(newValue.doubleValue());
        });

        dataButton.setOnMouseClicked(event -> Navigation.navigate(Screen.DATA_MANAGER));
    }

    private void setApplicationVolume(double volume) {
        // Implement logic to control the volume of the specific application
    }

    private void setApplicationBrightness(double brightness) {
        // Implement logic to control the brightness of the specific application
    }
}
