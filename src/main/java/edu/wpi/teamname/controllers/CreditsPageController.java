package edu.wpi.teamname.controllers;

import java.sql.SQLException;
import javafx.fxml.FXML;

public class CreditsPageController {
  @FXML
  public void initialize() throws SQLException {
    ParentController.titleString.set("Credits");
  }
}
