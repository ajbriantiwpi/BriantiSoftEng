package edu.wpi.teamname.controllers;

import edu.wpi.teamname.system.Navigation;
import edu.wpi.teamname.system.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class ServiceRequestController {

  @FXML MFXButton backButton;

  @FXML
  public void initialize() {

    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    ParentController.titleString.set("Service Request");
  }
}
