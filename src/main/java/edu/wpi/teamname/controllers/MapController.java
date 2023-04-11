package edu.wpi.teamname.controllers;

import edu.wpi.teamname.navigation.Map;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import net.kurobako.gesturefx.GesturePane;

public class MapController {

  Map map;
  @FXML GesturePane gp;
  @FXML AnchorPane anchor;
  @FXML MFXComboBox<String> LocationOne = new MFXComboBox<>();

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

  EventHandler<ActionEvent> changeStart =
      new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          System.out.println("T");
          System.out.println(LocationOne.getValue());
        }
      };

  EventHandler<MouseEvent> checkPoints =
      new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
          System.out.println("M");
        }
      };

  @FXML
  public void initialize() throws SQLException {

    map = new Map();

    gp.setMinScale(0.11);
    anchor.setOnMouseClicked(e);

    //    gp.setOnMouseMoved(checkPoints);

    anchor.getChildren().addAll(map.makeAllFloorNodes(defaultFloor));

    map.centerAndZoom(anchor);

    LocationOne.setItems(map.getAllNodeNames("L1"));

    LocationOne.setOnAction(changeStart);
    //    System.out.println(getAllNodeNames("L1"));

    ParentController.titleString.set("Map");
  }
}
