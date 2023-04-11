package edu.wpi.teamname.controllers;

import edu.wpi.teamname.system.Navigation;
import edu.wpi.teamname.system.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.Setter;

public class ParentController {

  @FXML MFXButton homeButton;
  @FXML MFXButton helpButton;
  @FXML MFXButton mapButton;
  @FXML MFXButton directionsButton;
  @FXML MFXButton makeRequestsButton;
  @FXML MFXButton showRequestsButton;
  @FXML MFXButton editMapButton;
  @FXML MFXButton exitButton;

  @FXML Label titleLabel;

  @Setter public static StringProperty titleString = new SimpleStringProperty();

  @FXML
  public void initialize() throws IOException {
    System.out.println("Parent!");

    homeButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    //    helpButton.setOnMouseClicked(event -> Navigation.navigate(Screen.));
    mapButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP));
    //        directionsButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE));
    makeRequestsButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUEST));
    //        showRequestsButton.setOnMouseClicked(event ->
    // Navigation.navigate(Screen.SERVICE_REQUEST_VIEW));
    //        editMapButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP_EDIT));
    exitButton.setOnMouseClicked(event -> System.exit(0));

    // titleLabel.setText(titleString.getValue());
  }
}
