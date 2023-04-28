package edu.wpi.teamname.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;

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
    MFXButton.class.getClassLoader().setDefaultAssertionStatus(false);
      ParentController.titleString.set("About");

    EventHandler<MouseEvent> PersonPopup =
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            MFXButton clickedButton = (MFXButton) event.getSource();
            String fxmlFileName = null;

            switch (clickedButton.getId()) {
              case "ianButton":
                fxmlFileName = "IanPopup.fxml";
                break;
              case "jasonButton":
                fxmlFileName = "JasonPopup.fxml";
                break;
              case "alessandroButton":
                fxmlFileName = "AlessandroPopup.fxml";
                break;
              case "adelynnButton":
                fxmlFileName = "AddyPopup.fxml";
                break;
              case "aleksandrButton":
                fxmlFileName = "AlekPopup.fxml";
                break;
              case "samuelButton":
                fxmlFileName = "SamPopup.fxml";
                break;
              case "ryanButton":
                fxmlFileName = "RyanPopup.fxml";
                break;
              case "conorButton":
                fxmlFileName = "ConorPopup.fxml";
                break;
              case "hunterButton":
                fxmlFileName = "HunterPopup.fxml";
                break;
              case "arturoButton":
                fxmlFileName = "ArturoPopup.fxml";
                break;
              case "andrewButton":
                fxmlFileName = "AndrewPopup.fxml";
                break;
              default:
                break;
            }

            if (fxmlFileName != null) {
              final var resource =
                  getClass().getResource("/edu/wpi/teamname/views/" + fxmlFileName);
              final FXMLLoader loader = new FXMLLoader(resource);
              VBox popupContent;

              try {
                popupContent = loader.load();
              } catch (IOException e) {
                throw new RuntimeException(e);
              }
              MFXButton close =
                  (MFXButton) ((Pane) popupContent.getChildren().get(3)).getChildren().get(0);

              PopOver popOver = new PopOver(popupContent);
              popOver.show(clickedButton);
              close.setOnMouseClicked(
                  event1 -> {
                    popOver.hide();
                  });
            }
          }
        };

    // Set the event handler on each button
    ianButton.setOnMouseClicked(PersonPopup);
    jasonButton.setOnMouseClicked(PersonPopup);
    alessandroButton.setOnMouseClicked(PersonPopup);
    adelynnButton.setOnMouseClicked(PersonPopup);
    aleksandrButton.setOnMouseClicked(PersonPopup);
    samuelButton.setOnMouseClicked(PersonPopup);
    ryanButton.setOnMouseClicked(PersonPopup);
    conorButton.setOnMouseClicked(PersonPopup);
    hunterButton.setOnMouseClicked(PersonPopup);
    arturoButton.setOnMouseClicked(PersonPopup);
    andrewButton.setOnMouseClicked(PersonPopup);

    // Set the event handler for each button and their associated FXML file
  }
}

    /**
     * EventHandler<MouseEvent> PersonPopup = new EventHandler<MouseEvent>() { @Override public void
     * handle(MouseEvent event) {
     *
     * <p>MFXButton createNewButton = ((MFXButton) event.getSource());
     *
     * <p>final var resource = App.class.getResource("IanPopup.fxml"); final FXMLLoader loader = new
     * FXMLLoader(resource); VBox v;
     *
     * <p>try { v = loader.load(); } catch ( IOException e) { throw new RuntimeException(e); }
     *
     * <p>PopOver pop = new PopOver(v);
     *
     * <p>pop.show(createNewButton); } }; } } *
     */
