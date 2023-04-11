package edu.wpi.teamname.controllers;

import edu.wpi.teamname.navigation.Map;
import edu.wpi.teamname.system.App;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import net.kurobako.gesturefx.GesturePane;

public class MapController {

  Map map;
  @FXML GesturePane gp;
  @FXML AnchorPane anchor;

  @FXML MFXButton exportButton;

  @FXML MFXButton importButton;
  @FXML MFXButton addNodeButton;

  @FXML MFXComboBox<String> selectTable = new MFXComboBox<>();

  String defaultFloor = "L1";

  int clickCount = 0;
  Point2D firstClick = null;
  Point2D secondClick = null;
  EventHandler<MouseEvent> e =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          clickCount++;

          if (clickCount == 1) {
            if (!map.getPrevPath().isEmpty()) {
              for (int i = anchor.getChildren().size() - 1; i >= 0; i--) {
                if (map.getPrevPath().contains(anchor.getChildren().get(i))) {
                  anchor.getChildren().remove(i);
                }
              }
              map.setPrevPath(null);
            }

            // Capture the first click
            firstClick = new Point2D(event.getX(), event.getY());
          } else if (clickCount == 2) {
            // Capture the second click
            secondClick = new Point2D(event.getX(), event.getY());

            // Reset click counter
            clickCount = 0;

            // Call drawAStarPath with both points
            map.drawAStarPath(anchor, firstClick, secondClick);
          }
        }
      };

  EventHandler<MouseEvent> export =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          String retStr = "";
          String table = selectTable.getValue();
          if (table == null) {
            retStr = "Null";
          }
          switch (table) {
            case ("Nodes"):
              retStr = "Nodes";
              break;
            case ("Edges"):
              retStr = "Edges";
              break;
            case ("Location Names"):
              retStr = "Location Names";
              break;
            case ("Moves"):
              retStr = "Moves";
              break;
          }
          System.out.println(retStr);
        }
      };

  EventHandler<MouseEvent> makeNewNode =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          MFXButton SubmitButton = ((MFXButton) event.getSource());
          VBox v = (VBox) ((HBox) SubmitButton.getParent()).getParent();

          TextField locText = (TextField) ((Pane) (v.getChildren().get(0))).getChildren().get(1);
          TextField xText = (TextField) ((Pane) (v.getChildren().get(1))).getChildren().get(1);
          TextField yText = (TextField) ((Pane) (v.getChildren().get(2))).getChildren().get(1);

          //          String locationNameValue = popupVbox.g

          System.out.println(locText.getText() + ", " + xText.getText() + ", " + yText.getText());
          // Connect to add Node/Location Name Sql query.

        }
      };

  EventHandler<MouseEvent> addNodePopup =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

          MFXButton addButton = ((MFXButton) event.getSource());
          AnchorPane outerPane = (AnchorPane) addButton.getParent();

          final var resource = App.class.getResource("../views/ChangeNode.fxml");
          final FXMLLoader loader = new FXMLLoader(resource);
          VBox v;
          try {
            v = loader.load();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }

          MFXButton Submit = (MFXButton) ((Pane) (v.getChildren().get(3))).getChildren().get(1);
          // Remove the "Remove Node" Button
          ((Pane) (v.getChildren().get(3))).getChildren().remove(0);
          Submit.setOnMouseClicked(makeNewNode);

          outerPane.getChildren().add(v);
        }
      };

  @FXML
  public void initialize() throws SQLException, IOException {

    map = new Map();

    gp.setMinScale(0.11);
    //    anchor.setOnMouseClicked(e);

    map.centerAndZoom(anchor);

    ParentController.titleString.set("Map");
    anchor.getChildren().addAll(map.makeAllFloorNodes(defaultFloor));

    ObservableList<String> tableNames = FXCollections.observableArrayList();
    tableNames.addAll("Nodes", "Edges", "Location Names", "Moves");

    selectTable.setItems(tableNames);
    //    selectTable.setDefault()
    //    selectTable.setOnAction(changeFloor);

    exportButton.setOnMouseClicked(export);
    importButton.setOnMouseClicked(export);
    addNodeButton.setOnMouseClicked(addNodePopup);
  }
}
