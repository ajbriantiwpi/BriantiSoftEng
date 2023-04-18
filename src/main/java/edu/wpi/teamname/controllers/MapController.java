package edu.wpi.teamname.controllers;

import edu.wpi.teamname.Navigation;
import edu.wpi.teamname.Screen;
import edu.wpi.teamname.navigation.AlgoStrategy.AStarAlgo;
import edu.wpi.teamname.navigation.AlgoStrategy.BFSAlgo;
import edu.wpi.teamname.navigation.AlgoStrategy.DFSAlgo;
import edu.wpi.teamname.navigation.AlgoStrategy.DijkstraAlgo;
import edu.wpi.teamname.navigation.Map;
import edu.wpi.teamname.navigation.MapNode;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
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
  @FXML MFXButton findPathButton = new MFXButton();
  @FXML ComboBox<String> FloorSelect = new ComboBox<>();
  @FXML ComboBox<String> AlgoSelect = new ComboBox<>();
  String defaultFloor = "L1";
  int clickCount = 0;
  Point2D firstClick = null;
  Point2D secondClick = null;
  String floor1;
  String floor2;
  String currFloor = "L1";
  int sNode = 0;
  int eNode = 0;

  String currentAlgo = "";

  EventHandler<MouseEvent> e =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          clickCount++;

          if (clickCount == 1) {

            // Capture the first click
            firstClick = new Point2D(event.getX(), event.getY());
            LocationOne.setOnAction(e -> {});

            floor1 = map.takeFloor(FloorSelect.getValue(), false);

          } else if (clickCount == 2) {
            // Capture the second click
            secondClick = new Point2D(event.getX(), event.getY());

            floor2 = map.takeFloor(FloorSelect.getValue(), false);

            List<MapNode> allMapNodes = map.graph.getMapNodes();

            //    System.out.println(firstClick);
            //    System.out.println(secondClick); // Coordinates in inner, now goes up to 5000

            int startIndex = -1;
            int endIndex = -1;
            double leastDistance;
            double nodeDist;

            Point2D currentClick;
            String currentFloor;
            int checkIndex;

            for (int j = 0; j < 2; j++) {
              if (j == 0) {
                // Start Node
                currentFloor = floor1;
                currentClick = firstClick;
              } else {
                // End Node
                currentFloor = floor2;
                currentClick = secondClick;
              }

              leastDistance = Double.MAX_VALUE;
              checkIndex = -1;

              for (int i = 0; i < allMapNodes.size(); i++) {
                if (i == startIndex) {
                  continue;
                } else {
                  MapNode currentMapNode = allMapNodes.get(i);
                  if (currentMapNode.getFloor().equals(currentFloor)) {
                    nodeDist = currentClick.distance(currentMapNode.getX(), currentMapNode.getY());
                    if (nodeDist < leastDistance) {
                      leastDistance = nodeDist;
                      checkIndex = i;
                    }
                  }
                }
              }

              if (j == 0) {
                // Start Node
                startIndex = checkIndex;
              } else {
                // End Node
                endIndex = checkIndex;
              }
            }

            //    Node startNode = allNodes.get(startIndex);
            //    Node endNode = allNodes.get(endIndex);
            System.out.println(startIndex + " " + endIndex);
            int startId = (startIndex * 5) + 100; // allNodes.get(startIndex).getId();
            int endId = (endIndex * 5) + 100; // allNodes.get(endIndex).getId();

            System.out.println("startId: " + startId);
            System.out.println("endId: " + endId);

            sNode = startId;
            eNode = endId;

            findPathButton.setVisible(true);

            // Call drawAStarPath with both points
            // map.drawPath(anchor, firstClick, secondClick, floor1, floor2);
            //            String[] parts = nToPars.split("_");
            //            int sInd = Integer.parseInt(parts[0]);
            //            int eInd = Integer.parseInt(parts[1]);
            // listPaths = map.drawAStarPath(anchor, sInd, eInd);
            //            int secInd = map.getAllFloors().indexOf(FloorSelect.getValue());
            //            System.out.println(FloorSelect.getValue() + " " + secInd);
            //            anchor.getChildren().addAll(map.getShapes().get(secInd));

            // clickCount = 0;
          }
        }
      };

  EventHandler<MouseEvent> deleteNodeButton =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          System.out.println("DN");
          //          if (!map.getPrevPath().isEmpty()) {
          //            for (int i = anchor.getChildren().size() - 1; i >= 0; i--) {
          //              if (map.getPrevPath().contains(anchor.getChildren().get(i))) {
          //                anchor.getChildren().remove(i);
          //              }
          //            }
          //            map.setPrevPath(null);
          //          }
          try {
            initialize();
          } catch (SQLException ex) {
            throw new RuntimeException(ex);
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          }
        }
      };

  EventHandler<MouseEvent> findPathWButton =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          map.drawPath(anchor, sNode, eNode);
          int secInd = map.getAllFloors().indexOf(FloorSelect.getValue());
          anchor.getChildren().addAll(map.getShapes().get(secInd));

          int indOfStart = MapNode.idToIndex(sNode);
          String floorForSNode =
              map.takeFloor(map.graph.getMapNodes().get(indOfStart).getFloor(), true);
          FloorSelect.setValue(floorForSNode);

          clickCount = 0;
        }
      };

  EventHandler<ActionEvent> changeStart =
      new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          System.out.println("changed start " + LocationOne.getValue());
          // System.out.println(LocationOne.getValue());
          // System.out.println(EndPointSelect.getValue());
          sNode = Integer.parseInt(LocationOne.getValue());
          if (eNode != 0 && sNode != 0) {
            findPathButton.setVisible(true);
            //            map.drawAStarPath(anchor, floor1, floor2, sNode, eNode);
            // map.drawPath(anchor, sNode, eNode);
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
          if (sNode != 0 && eNode != 0) {
            findPathButton.setVisible(true);
            //            map.drawAStarPath(anchor, floor1, floor2, sNode, eNode);
          }
        }
      };

  EventHandler<ActionEvent> changeFloor =
      new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          System.out.println("CF");

          //          if (!map.getPrevPath().isEmpty()) {
          //            for (int i = anchor.getChildren().size() - 1; i >= 0; i--) {
          //              if (map.getPrevPath().contains(anchor.getChildren().get(i))) {
          //                anchor.getChildren().remove(i);
          //              }
          //            }
          //            map.setPrevPath(null);
          //          }

          String floor = FloorSelect.getValue();

          System.out.println(floor);

          try {
            map.setCurrentDisplayFloor(floor, true);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }

          //          System.out.println(floor);
          //          currFloor = map.takeFloor(floor, true);

          //          if (!map.getShapes().isEmpty()) {
          //
          //            if (currFloor.equals("L1")) {
          //              //            for (int i = 0; i < map.getShapes().get(1).size(); i++) {
          //              //              System.out.print(" " + map.getShapes().get(1).get(i));
          //              //            }
          //
          //              anchor.getChildren().addAll(map.getShapes().get(1));
          //              map.setPrevPath(map.getShapes().get(1));
          //            } else if (currFloor.equals("L2") && clickCount == 0) {
          //              anchor.getChildren().addAll(map.getShapes().get(0));
          //              map.setPrevPath(map.getShapes().get(0));
          //            } else if (currFloor.equals("1") || currFloor.equals("G1")) {
          //              anchor.getChildren().addAll(map.getShapes().get(2));
          //              map.setPrevPath(map.getShapes().get(2));
          //            } else if (currFloor.equals("2") || currFloor.equals("G2")) {
          //              anchor.getChildren().addAll(map.getShapes().get(3));
          //              map.setPrevPath(map.getShapes().get(3));
          //            } else if (currFloor.equals("3") || currFloor.equals("G3")) {
          //              anchor.getChildren().addAll(map.getShapes().get(4));
          //              map.setPrevPath(map.getShapes().get(4));
          //            } else {
          //              System.out.println("What are you doing?");
          //            }
          //          }

          //          try {
          //            LocationOne.setItems(map.getAllNodeNames(map.takeFloor(floor, false)));
          //          } catch (SQLException ex) {
          //            throw new RuntimeException(ex);
          //          }
          //          try {
          //            EndPointSelect.setItems(map.getAllNodeNames(map.takeFloor(floor, false)));
          //          } catch (SQLException ex) {
          //            throw new RuntimeException(ex);
          //          }

          //          anchor.getStyleClass().remove(0);
          //          anchor.getStyleClass().add(map.takeFloor(floor, true));

          //                           System.out.println(LocationOne.getValue());

        }
      };

  EventHandler<ActionEvent> selectAlgo =
      new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          System.out.println("Algo Changed");
          currentAlgo = AlgoSelect.getValue();

          switch (AlgoSelect.getValue()) {
            case ("A-Star"):
              map.graph.setPathfindingAlgo(new AStarAlgo());
              break;
            case ("Breadth First Search"):
              map.graph.setPathfindingAlgo(new BFSAlgo());
              break;
            case ("Depth First Search"):
              map.graph.setPathfindingAlgo(new DFSAlgo());
              break;
            case ("Dijkstra's Algorithm"):
              map.graph.setPathfindingAlgo(new DijkstraAlgo());
              break;
            default:
              System.out.println("Not supposed to be here: Wrong Algo");
          }
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

    map = new Map(anchor);

    //    AnchorPane.setLeftAnchor(SelectCombo, 0.0);
    //    AnchorPane.setRightAnchor(SelectCombo, 0.0);
    //    AnchorPane.setTopAnchor(SelectCombo, 100.0);

    //    anchor.getChildren().add(SelectCombo);
    //    AnchorPane.setTopAnchor(anchor, 0.0);
    //    AnchorPane.setBottomAnchor(anchor, 1000.0);

    gp.setMinScale(0.11);

    //    gp.setOnMouseMoved(checkPoints);

    ArrayList<Node> currentFloorNodes = (map.makeAllFloorShapes(defaultFloor, true));
    anchor.getChildren().addAll(currentFloorNodes);
    map.setCurrentFloorShapes(currentFloorNodes);
    //  anchor.getChildren().addAll(map.makeAllFloorNodes(defaultFloor, true));

    map.centerAndZoom(gp);

    // DeleteNodeButton.setOnMouseClicked(deleteNodeButton);
    DeleteNodeButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP));
    findPathButton.setOnMouseClicked(findPathWButton);
    findPathButton.setVisible(false);

    //    LocationOne.setStyle("-fx-padding: 5 25 5 5;");
    LocationOne.setPromptText("Select start");
    LocationOne.setItems(
        map.getAllNodeNames("L1")); // change for when the floor changes to update the nodes shown
    LocationOne.setOnAction(changeStart);

    EndPointSelect.setPromptText("Select end");
    EndPointSelect.setItems(map.getAllNodeNames("L1")); // switched to every node in map
    EndPointSelect.setOnAction(changeEnd);

    FloorSelect.setPromptText("Select floor");
    FloorSelect.setItems(map.getAllFloors());
    FloorSelect.setOnAction(changeFloor);
    FloorSelect.setValue("Lower Level 1");

    AlgoSelect.setPromptText("Select Algorithm");
    AlgoSelect.setItems(map.getAllAlgos());
    AlgoSelect.setOnAction(selectAlgo);
    AlgoSelect.setValue("A-Star");

    anchor.setOnMouseClicked(e);

    //    System.out.println(getAllNodeNames("L1"));

    ParentController.titleString.set("Map");
  }
}
