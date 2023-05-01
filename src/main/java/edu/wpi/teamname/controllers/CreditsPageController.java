package edu.wpi.teamname.controllers;

import edu.wpi.teamname.ThemeSwitch;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class CreditsPageController {
  @FXML AnchorPane root;

  @FXML
  public void initialize() throws SQLException {
    ThemeSwitch.switchTheme(root);
    ParentController.titleString.set("Credits");
  }
}
