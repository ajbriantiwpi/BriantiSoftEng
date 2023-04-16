package edu.wpi.teamname.controllers;

import edu.wpi.teamname.navigation.Map;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import net.kurobako.gesturefx.GesturePane;

public class MapController {

  Map map;
  @FXML GesturePane gp;
  @FXML AnchorPane anchor;
  @FXML HBox SelectCombo = new HBox();
  @FXML ComboBox<String> LocationOne = new ComboBox<>();
  @FXML ComboBox<String> EndPointSelect = new ComboBox<>();
  @FXML MFXButton DeleteNodeButton = new MFXButton();
  @FXML ComboBox<String> FloorSelect = new ComboBox<>();
  String defaultFloor = "L1";
  int clickCount = 0;
  Point2D firstClick = null;
  Point2D secondClick = null;
  String floor1;
  String floor2;
  String currFloor = "L1";
  int sNode = 0;
  int eNode = 0;

  EventHandler<MouseEvent> e =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          clickCount++;

          if (clickCount == 1) {

            // Capture the first click
            firstClick = new Point2D(event.getX(), event.getY());
            LocationOne.setOnAction(e -> {});

            floor1 = takeFloor(FloorSelect.getValue(), true);

          } else if (clickCount == 2) {
            // Capture the second click
            secondClick = new Point2D(event.getX(), event.getY());

            floor2 = takeFloor(FloorSelect.getValue(), true);

            // Call drawAStarPath with both points
            //            map.drawAStarPath(anchor, firstClick, secondClick, floor1, floor2);

            System.out.println("CLICKPATH");
            map.drawPath(anchor, firstClick, secondClick, floor1, floor2);

            clickCount = 0;
          }
        }
      };

  EventHandler<MouseEvent> deleteNodeButton =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          System.out.println("DN");
          if (!map.getPrevPath().isEmpty()) {
            for (int i = anchor.getChildren().size() - 1; i >= 0; i--) {
              if (map.getPrevPath().contains(anchor.getChildren().get(i))) {
                anchor.getChildren().remove(i);
              }
            }
            map.setPrevPath(null);
          }
        }
      };

  EventHandler<ActionEvent> changeStart =
      new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          System.out.println("T");
          System.out.println(LocationOne.getValue());
          // System.out.println(EndPointSelect.getValue());
          sNode = Integer.parseInt(LocationOne.getValue());
          if (eNode != 0) {
            //            map.drawAStarPath(anchor, floor1, floor2, sNode, eNode);
            map.drawPath(anchor, sNode, eNode);
          }
        }
      };

  EventHandler<ActionEvent> changeEnd =
      new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          System.out.println("changed end " + EndPointSelect.getValue());
          System.out.println(EndPointSelect.getValue());
          eNode = Integer.parseInt(EndPointSelect.getValue());
          if (sNode != 0) {
            //            map.drawAStarPath(anchor, floor1, floor2, sNode, eNode);
          }
        }
      };

  public String takeFloor(String f, boolean flag) {
    String retStr = "";
    if (f == null) {
      return "L1";
    }
    //    System.out.println(f);
    switch (f) {
      case ("Lower Level 1"):
        retStr = "L1";
        return retStr;
      case ("Lower Level 2"):
        retStr = "L2";
        return retStr;
      case ("Ground Floor"):
        retStr = "GG";
        return retStr;
      case ("First Floor"):
        if (flag) {
          retStr = "G1";
        } else {
          retStr = "1";
        }
        return retStr;
      case ("Second Floor"):
        if (flag) {
          retStr = "G2";
        } else {
          retStr = "2";
        }
        return retStr;
      case ("Third Floor"):
        if (flag) {
          retStr = "G3";
        } else {
          retStr = "3";
        }
        return retStr;
      default:
        return "You should never see  this!!!";
    }
  }

  EventHandler<ActionEvent> changeFloor =
      new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          System.out.println("CF");
          String floor = FloorSelect.getValue();
          System.out.println(floor);
          try {
            LocationOne.setItems(map.getAllNodeNames(takeFloor(floor, false)));
          } catch (SQLException ex) {
            throw new RuntimeException(ex);
          }
          try {
            EndPointSelect.setItems(map.getAllNodeNames(takeFloor(floor, false)));
          } catch (SQLException ex) {
            throw new RuntimeException(ex);
          }

          anchor.getStyleClass().remove(0);
          anchor.getStyleClass().add(takeFloor(floor, true));

          //                           System.out.println(LocationOne.getValue());

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
  public void initialize() throws SQLException, IOException {

    map = new Map();

    //    AnchorPane.setLeftAnchor(SelectCombo, 0.0);
    //    AnchorPane.setRightAnchor(SelectCombo, 0.0);
    //    AnchorPane.setTopAnchor(SelectCombo, 100.0);

    //    anchor.getChildren().add(SelectCombo);
    //    AnchorPane.setTopAnchor(anchor, 0.0);
    //    AnchorPane.setBottomAnchor(anchor, 1000.0);

    gp.setMinScale(0.11);

    //    gp.setOnMouseMoved(checkPoints);

    anchor.getChildren().addAll(map.makeAllFloorNodes(defaultFloor, true));

    map.centerAndZoom(gp);

    DeleteNodeButton.setOnMouseClicked(deleteNodeButton);

    //    LocationOne.setStyle("-fx-padding: 5 25 5 5;");
    LocationOne.setPromptText("Select start");
    LocationOne.setItems(
        map.getAllNodeNames("L1")); // change for when the floor changes to update the nodes shown
    LocationOne.setOnAction(changeStart);

    EndPointSelect.setPromptText("Select end");
    EndPointSelect.setItems(map.getAllNodeNames("L1"));
    EndPointSelect.setOnAction(changeEnd);

    FloorSelect.setPromptText("Select floor");
    FloorSelect.setItems(map.getAllFloors("L1"));
    FloorSelect.setOnAction(changeFloor);

    anchor.setOnMouseClicked(e);

    //    System.out.println(getAllNodeNames("L1"));

    ParentController.titleString.set("Map");
  }
}
