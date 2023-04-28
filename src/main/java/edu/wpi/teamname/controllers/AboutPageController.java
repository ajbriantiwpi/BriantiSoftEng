package edu.wpi.teamname.controllers;

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

  @FXML ImageView ianButton;
  @FXML ImageView jasonButton;
  @FXML ImageView alessandroButton;
  @FXML ImageView adelynnButton;
  @FXML ImageView aleksandrButton;
  @FXML ImageView samuelButton;
  @FXML ImageView ryanButton;
  @FXML ImageView conorButton;
  @FXML ImageView hunterButton;
  @FXML ImageView arturoButton;
  @FXML ImageView andrewButton;
  @FXML VBox memberVBox;
  @FXML AnchorPane aboutAnchorPane;

  public void initialize() {
    ianButton.setOnMouseEntered(event -> showPopupForTeamMember("IanPopup.fxml", ianButton));
    jasonButton.setOnMouseEntered(event -> showPopupForTeamMember("JasonPopup.fxml", jasonButton));
    alessandroButton.setOnMouseEntered(
        event -> showPopupForTeamMember("AlessandroPopup.fxml", alessandroButton));
    adelynnButton.setOnMouseEntered(
        event -> showPopupForTeamMember("AdelynnPopup.fxml", adelynnButton));
    aleksandrButton.setOnMouseEntered(
        event -> showPopupForTeamMember("AleksandrPopup.fxml", aleksandrButton));
    samuelButton.setOnMouseEntered(
        event -> showPopupForTeamMember("SamuelPopup.fxml", samuelButton));
    ryanButton.setOnMouseEntered(event -> showPopupForTeamMember("RyanPopup.fxml", ryanButton));
    conorButton.setOnMouseEntered(event -> showPopupForTeamMember("ConorPopup.fxml", conorButton));
    hunterButton.setOnMouseEntered(
        event -> showPopupForTeamMember("HunterPopup.fxml", hunterButton));
    arturoButton.setOnMouseEntered(
        event -> showPopupForTeamMember("ArturoPopup.fxml", arturoButton));
    andrewButton.setOnMouseEntered(
        event -> showPopupForTeamMember("AndrewPopup.fxml", andrewButton));

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