package edu.wpi.teamname.controllers;

import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.extras.Language;
import edu.wpi.teamname.ThemeSwitch;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CreditsPageController {
  @FXML Label creditsLabel;

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
        ParentController.titleString.set("Crédits");
        creditsLabel.setText("Crédits");
        break;
      case SPANISH:
        ParentController.titleString.set("Créditos");
        creditsLabel.setText("Créditos");
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
