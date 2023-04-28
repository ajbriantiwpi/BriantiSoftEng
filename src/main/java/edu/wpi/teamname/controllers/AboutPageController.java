package edu.wpi.teamname.controllers;

import static edu.wpi.teamname.Screen.*;

import edu.wpi.teamname.Navigation;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AboutPageController {

  @FXML MFXButton ianButton;
  @FXML MFXButton jasonButton;
  @FXML MFXButton alessandroButton;
  @FXML MFXButton adelynnButton;
  @FXML MFXButton aleksandrButton;
  @FXML MFXButton samuelButton;
  @FXML MFXButton ryanButton;
  @FXML MFXButton conorButton;
  @FXML MFXButton hunterButton;
  @FXML MFXButton arturoButton;
  @FXML MFXButton andrewButton;
  @FXML VBox memberVBox;
  @FXML AnchorPane aboutAnchorPane;

  public void initialize() {

    /**
     * final var resource = App.class.getResource("ConorPopup.fxml"); final FXMLLoader loader = new
     * FXMLLoader(resource); VBox v;
     *
     * <p>try { v = loader.load(); } catch (IOException e) { throw new RuntimeException(e); }
     * PopOver pop = new PopOver(v); *
     */
    conorButton.setOnMouseClicked(
        event -> Navigation.navigate(CONOR_POPUP)); // change this to popup
    ianButton.setOnMouseClicked(event -> Navigation.navigate(IAN_POPUP)); // change this to popup
    jasonButton.setOnMouseClicked(
        event -> Navigation.navigate(JASON_POPUP)); // change this to popup
    alessandroButton.setOnMouseClicked(
        event -> Navigation.navigate(ALESSANDRO_POPUP)); // change this to popup
    adelynnButton.setOnMouseClicked(
        event -> Navigation.navigate(ADDY_POPUP)); // change this to popup
    aleksandrButton.setOnMouseClicked(
        event -> Navigation.navigate(ALEK_POPUP)); // change this to popup
    samuelButton.setOnMouseClicked(event -> Navigation.navigate(SAM_POPUP)); // change this to popup
    ryanButton.setOnMouseClicked(event -> Navigation.navigate(RYAN_POPUP)); // change this to popup
    hunterButton.setOnMouseClicked(
        event -> Navigation.navigate(HUNTER_POPUP)); // change this to popup
    arturoButton.setOnMouseClicked(
        event -> Navigation.navigate(ARTURO_POPUP)); // change this to popup
    andrewButton.setOnMouseClicked(
        event -> Navigation.navigate(ANDREW_POPUP)); // change this to popup
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
 * member 1 showPopupForTeamMember("ConorPopup.fxml"); } else if (event.getTarget() == ianButton) {
 * // Show the popup for team member 2 showPopupForTeamMember("IanPopup.fxml"); } else if
 * (event.getTarget() == jasonButton) { // Show the popup for team member 3
 * showPopupForTeamMember("JasonPopup.fxml"); } // Add more else if statements for other team
 * members }); aboutAnchorPane.setOnMouseExited( event -> { hidePopup(); }); } *
 */
