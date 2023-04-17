package edu.wpi.teamname.controllers;

import edu.wpi.teamname.Navigation;
import edu.wpi.teamname.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class NewPageController {

  @FXML MFXButton homebutton;

  @FXML
  public void initialize() {
    homebutton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }
}
