package edu.wpi.teamname.controllers;

import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.ThemeSwitch;
import edu.wpi.teamname.extras.Language;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CreditsPageController {
  @FXML Label creditsLabel;

  /**
   * sets the language of the labels
   *
   * @param lang language to set it to
   */
  public void setLanguage(Language lang) {
    switch (lang) {
      case ENGLISH:
        ParentController.titleString.set("Credits");
        creditsLabel.setText("Credits");
        break;
      case ITALIAN:
        ParentController.titleString.set("Crediti");
        creditsLabel.setText("Crediti");
        break;
      case FRENCH:
        ParentController.titleString.set("Cr" + GlobalVariables.getEAcute() + "dits");
        creditsLabel.setText("Cr" + GlobalVariables.getEAcute() + "dits");
        break;
      case SPANISH:
        ParentController.titleString.set("Cr" + GlobalVariables.getEAcute() + "ditos");
        creditsLabel.setText("Cr" + GlobalVariables.getEAcute() + "ditos");
        break;
    }
  }

  @FXML AnchorPane root;

  @FXML
  public void initialize() throws SQLException {
    ThemeSwitch.switchTheme(root);
    ParentController.titleString.set("Credits");
    setLanguage(GlobalVariables.getB().getValue());
    GlobalVariables.b.addListener(
        (options, oldValue, newValue) -> {
          setLanguage(newValue);
        });
  }
}
