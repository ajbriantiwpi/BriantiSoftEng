package edu.wpi.teamname.controllers;

import javafx.fxml.FXML;

public class ServiceRequestController {

  //  @FXML MFXButton backButton;

  @FXML
  public void initialize() {

    //    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    ParentController.titleString.set("Service Request");
  }
}
