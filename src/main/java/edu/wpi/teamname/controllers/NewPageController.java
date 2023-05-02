package edu.wpi.teamname.controllers;

import edu.wpi.teamname.Navigation;
import edu.wpi.teamname.Screen;
import edu.wpi.teamname.ThemeSwitch;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class NewPageController {

  @FXML MFXButton homebutton;
  @FXML AnchorPane rootPane;

  @FXML
  public void initialize() {
    ThemeSwitch.switchTheme(rootPane);
    homebutton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }
}
