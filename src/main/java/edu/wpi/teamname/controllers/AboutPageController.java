package edu.wpi.teamname.controllers;

import java.io.IOException;

import edu.wpi.teamname.Navigation;
import edu.wpi.teamname.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static edu.wpi.teamname.Screen.CONOR_POPUP;

public class AboutPageController {

  @FXML ImageView ianButton;
  @FXML ImageView jasonButton;
  @FXML ImageView alessandroButton;
  @FXML ImageView adelynnButton;
  @FXML ImageView aleksandrButton;
  @FXML ImageView samuelButton;
  @FXML ImageView ryanButton;
  @FXML MFXButton conorButton;
  @FXML ImageView hunterButton;
  @FXML ImageView arturoButton;
  @FXML ImageView andrewButton;
  @FXML VBox memberVBox;
  @FXML AnchorPane aboutAnchorPane;

  public void initialize() {

   conorButton.setOnMouseClicked(event -> Navigation.navigate(CONOR_POPUP));

    aboutAnchorPane.setOnMouseExited(event -> hidePopup());

  }

  private void showPopupForTeamMember(String fxmlFile, ImageView imageView) {
    // Load and show the popup for the selected team member
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
      Parent root = loader.load();
      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.show();

      // Position the popup next to the clicked ImageView
      stage.setX(imageView.localToScreen(imageView.getBoundsInLocal()).getMaxX());
      stage.setY(imageView.localToScreen(imageView.getBoundsInLocal()).getMinY());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void showPopupForTeamMember(String fxmlFile) {
    // Load and show the popup for the selected team member
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
      Parent root = loader.load();
      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void hidePopup() {
    // Close the current popup (if one is open)
    if (currentPopup != null) {
      currentPopup.close();
      currentPopup = null;
    }
  }

  // Define a field to store the currently open popup
  private Stage currentPopup;
}
/**
 * aboutAnchorPane.setOnMouseEntered( event -> { // Check if the mouse click event occurred on one
 * of the ImageView controls if (event.getTarget() == conorButton) { // Show the popup for team
 * member 1 showPopupForTeamMember("ConorPopup.fxml"); } else if (event.getTarget() == ianButton)
 * { // Show the popup for team member 2 showPopupForTeamMember("IanPopup.fxml"); } else if
 * (event.getTarget() == jasonButton) { // Show the popup for team member 3
 * showPopupForTeamMember("JasonPopup.fxml"); } // Add more else if statements for other team
 * members }); aboutAnchorPane.setOnMouseExited( event -> { hidePopup(); }); } *
 */