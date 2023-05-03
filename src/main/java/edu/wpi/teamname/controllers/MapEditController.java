package edu.wpi.teamname.controllers;

import edu.wpi.teamname.App;
import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.ThemeSwitch;
import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.extras.Language;
import edu.wpi.teamname.extras.SFX;
import edu.wpi.teamname.extras.Sound;
import edu.wpi.teamname.navigation.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;
import net.kurobako.gesturefx.GesturePane;
import org.controlsfx.control.PopOver;

public class MapEditController {
  @FXML Label mapSymbolsLabel;
  @FXML TitledPane EditingTitledPane;
  @FXML TitledPane TickTitlePane;
  @FXML TitledPane FloorTitlePane;
  @FXML Label conferenceRoomLabel;

  @FXML Label departmentLabel;

  @FXML Label labLabel;

  @FXML Label infoLabel;

  @FXML Label bathroomLabel;

  @FXML Label serviceLabel;

  @FXML Label elevatorLabel;

  @FXML Label stairsLabel;

  @FXML Label exitLabel;

  @FXML Label startLabel;

  @FXML Label pathLabel;

  @FXML Label destinationLabel;
  @FXML Label currentFloorStart;
  @FXML Label currentFloorDestLabel;
  @FXML HBox root;
  Map map;
  @FXML GesturePane gp;
  @FXML TableView<LocationName> table;
  @FXML AnchorPane anchor;
  //  @FXML MFXButton exportButton;
  //  @FXML MFXButton importButton;
  @FXML MFXButton addNodeButton;
  @FXML MFXButton addLocationButton;
  @FXML MFXButton addEdgeButton;
  @FXML MFXButton toggleTableButton;
  @FXML TableColumn longNameColumn;
  @FXML TableColumn shortNameColumn;
  @FXML TableColumn nodeTypeColumn;
  //  @FXML MFXComboBox<String> selectTable = new MFXComboBox<>();
  @FXML ComboBox<String> selectTable = new ComboBox<>();
  //  @FXML ComboBox<String> FloorSelect = new ComboBox<>();

  //  @FXML MFXButton downFloor;
  //  @FXML MFXButton upFloor;

  //  @FXML HBox floorSelector;

  @FXML MFXButton ThirdFloorButton;
  @FXML MFXButton SecondFloorButton;
  @FXML MFXButton FirstFloorButton;
  @FXML MFXButton LowerLevelOneButton;
  @FXML MFXButton LowerLevelTwoButton;
  ArrayList<MFXButton> floorButtons = new ArrayList<>();

  @FXML CheckBox LongNameSelector;
  @FXML CheckBox ShortNameSelector;
  @FXML CheckBox IdSelector;
  ArrayList<CheckBox> nameSelectBoxes = new ArrayList<>();
  @FXML CheckBox EdgeSelector;
  @FXML CheckBox HallNamesSelector;
  @FXML CheckBox NodeSelector;
  @FXML CheckBox LegendSelector;

  @FXML AnchorPane OuterMapAnchor;

  String defaultFloor = "L1";
  int defaultX = 0;
  int defaultY = 0;
  String defaultBuilding = "45 Francis";
  boolean isMap = true;
  int selectedRowIndex = 0;
  Point2D clickPos;
  Point2D realClickPos;
  Pane nodeDragP;
  ArrayList<Point2D> adjacentNodes;
  @FXML VBox Legend;

  //  EventHandler<MouseEvent> e =
  //      new EventHandler<MouseEvent>() {
  //        @Override
  //        public void handle(MouseEvent event) {
  //          clickCount++;
  //
  //          if (clickCount == 1) {
  //            if (!map.getPrevPath().isEmpty()) {
  //              for (int i = anchor.getChildren().size() - 1; i >= 0; i--) {
  //                if (map.getPrevPath().contains(anchor.getChildren().get(i))) {
  //                  anchor.getChildren().remove(i);
  //                }
  //              }
  //              map.setPrevPath(null);
  //            }
  //
  //            // Capture the first click
  //            firstClick = new Point2D(event.getX(), event.getY());
  //          } else if (clickCount == 2) {
  //            // Capture the second click
  //            secondClick = new Point2D(event.getX(), event.getY());
  //
  //            // Reset click counter
  //            clickCount = 0;
  //
  //            // Call drawAStarPath with both points
  //            map.drawAStarPath(anchor, firstClick, secondClick);
  //          }
  //        }
  //      };

  EventHandler<MouseEvent> exportCSV =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          Sound.playSFX(SFX.BUTTONCLICK);
          String retStr = "";
          String table = selectTable.getValue();

          final FileChooser fileChooser = new FileChooser();
          //          fileChooser.showOpenDialog(stage);
          File file = fileChooser.showSaveDialog(App.getPrimaryStage());

          System.out.println(file.getAbsolutePath());

          if (table == null) {
            retStr = "Null";
          }
          switch (table) {
            case ("Nodes"):
              retStr = "Nodes";

              try {
                DataManager.exportNodeToCSV(file.getAbsolutePath());
              } catch (SQLException ex) {
                throw new RuntimeException(ex);
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }

              break;
            case ("Edges"):
              retStr = "Edges";

              try {
                DataManager.exportEdgeToCSV(file.getAbsolutePath());
              } catch (SQLException ex) {
                throw new RuntimeException(ex);
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }

              break;
            case ("Location Names"):
              retStr = "Location Names";

              try {
                DataManager.exportLocationNameToCSV(file.getAbsolutePath());
              } catch (SQLException ex) {
                throw new RuntimeException(ex);
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }

              break;
            case ("Moves"):
              retStr = "Moves";

              try {
                DataManager.exportMoveToCSV(file.getAbsolutePath());
              } catch (SQLException ex) {
                throw new RuntimeException(ex);
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }

              break;
          }
          System.out.println(retStr);
        }
      };

  EventHandler<MouseEvent> importCSV =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          Sound.playSFX(SFX.BUTTONCLICK);
          String retStr = "";
          String table = selectTable.getValue();

          final FileChooser fileChooser = new FileChooser();
          //          fileChooser.showOpenDialog(stage);
          File file = fileChooser.showOpenDialog(App.getPrimaryStage());

          System.out.println(file.getAbsolutePath());

          if (table == null) {
            retStr = "Null";
          }
          switch (table) {
            case ("Nodes"):
              retStr = "Nodes";

              try {
                DataManager.uploadNode(file.getAbsolutePath());
              } catch (SQLException ex) {
                throw new RuntimeException(ex);
              } catch (ParseException ex) {
                throw new RuntimeException(ex);
              }

              break;
            case ("Edges"):
              retStr = "Edges";

              try {
                DataManager.uploadEdge(file.getAbsolutePath());
              } catch (SQLException ex) {
                throw new RuntimeException(ex);
              }

              break;
            case ("Location Names"):
              retStr = "Location Names";

              try {
                DataManager.uploadLocationName(file.getAbsolutePath());
              } catch (ParseException | SQLException ex) {
                throw new RuntimeException(ex);
              }

              break;
            case ("Moves"):
              retStr = "Moves";

              try {
                DataManager.uploadMove(file.getAbsolutePath());
              } catch (ParseException | SQLException ex) {
                throw new RuntimeException(ex);
              }

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

          //          TextField locText = (TextField) ((Pane)
          // (v.getChildren().get(0))).getChildren().get(1);
          TextField xText = (TextField) ((Pane) (v.getChildren().get(0))).getChildren().get(2);
          TextField yText = (TextField) ((Pane) (v.getChildren().get(1))).getChildren().get(2);
          TextField floorText = (TextField) ((Pane) (v.getChildren().get(2))).getChildren().get(2);
          TextField buildingText =
              (TextField) ((Pane) (v.getChildren().get(3))).getChildren().get(2);

          //          String locationNameValue = popupVbox.g

          //          System.out.println(locText.getText() + ", " + xText.getText() + ", " +
          // yText.getText());
          // Connect to add Node/Location Name Sql query.

          int xPos = defaultX;
          if (!(xText.getText().equals(""))) {
            xPos = (int) Double.parseDouble(xText.getText());
          }
          int yPos = defaultY;
          if (!(yText.getText().equals(""))) {
            yPos = (int) Double.parseDouble(yText.getText());
          }
          //          String floor = defaultFloor;
          String floor = map.takeFloor(map.getCurrentDisplayFloor(), false);
          if (!(floorText.getText().equals(""))) {
            floor = floorText.getText();
          }
          String building = defaultBuilding;
          if (!(buildingText.getText().equals(""))) {
            building = buildingText.getText();
          }

          ArrayList<Node> allNodes;
          try {
            allNodes = DataManager.getAllNodes();
          } catch (SQLException ex) {
            throw new RuntimeException(ex);
          }

          // This is working on the assumption that We still want all id's to be a difference of 5
          // and that the last one in the table is the biggest ID
          int highestID = allNodes.get(allNodes.size() - 1).getId();

          Node n = new Node(highestID + 5, xPos, yPos, floor, building);

          try {
            DataManager.addNode(n);
            map.setCurrentDisplayFloor(map.getCurrentDisplayFloor());
            //                        changeFloor();
          } catch (SQLException ex) {
            System.out.println(ex);
            //            throw new RuntimeException(ex);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      };

  EventHandler<MouseEvent> makeNewLocation =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          MFXButton SubmitButton = ((MFXButton) event.getSource());
          VBox v = (VBox) ((HBox) SubmitButton.getParent()).getParent();

          //          TextField locText = (TextField) ((Pane)
          // (v.getChildren().get(0))).getChildren().get(1);
          TextField locationText =
              (TextField) ((Pane) (v.getChildren().get(0))).getChildren().get(2);
          TextField shortNameText =
              (TextField) ((Pane) (v.getChildren().get(1))).getChildren().get(2);
          TextField nodeTypeText =
              (TextField) ((Pane) (v.getChildren().get(2))).getChildren().get(2);

          //          String locationNameValue = popupVbox.g

          //          System.out.println(locText.getText() + ", " + xText.getText() + ", " +
          // yText.getText());
          // Connect to add Node/Location Name Sql query.

          //              String longName = defaultLongName;
          if ((locationText.getText().equals(""))) {
            // Dont make a new one.
            return;
          }

          String longName = locationText.getText();
          String shortName = longName;
          if (!(shortNameText.getText().equals(""))) {
            shortName = shortNameText.getText();
          }
          String nodeType = "Hall";
          if (!(nodeTypeText.getText().equals(""))) {
            nodeType = nodeTypeText.getText();
          }

          ArrayList<Node> allNodes;
          try {
            allNodes = DataManager.getAllNodes();
          } catch (SQLException ex) {
            throw new RuntimeException(ex);
          }

          // This is working on the assumption that We still want all id's to be a difference of 5
          // and that the last one in the table is the biggest ID
          int highestID = allNodes.get(allNodes.size() - 1).getId();

          //              Node n = new Node(highestID + 5, xPos, yPos, floor, building);
          LocationName l = new LocationName(longName, shortName, nodeType);
          try {
            DataManager.addLocationName(l);
            map.setCurrentDisplayFloor(map.getCurrentDisplayFloor());
            ObservableList<LocationName> locations =
                FXCollections.observableArrayList(DataManager.getAllLocationNames());
            table.setItems(locations);
            //            changeFloor();
          } catch (SQLException ex) {
            System.out.println(ex);
            //            throw new RuntimeException(ex);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      };

  EventHandler<MouseEvent> makeNewEdge =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          MFXButton SubmitButton = ((MFXButton) event.getSource());
          VBox v = (VBox) ((SubmitButton.getParent()).getParent());

          int startId = -1;
          int endId = -1;

          TextField startNodeIdText =
              (TextField) ((Pane) (v.getChildren().get(0))).getChildren().get(2);
          TextField endNodeIdText =
              (TextField) ((Pane) (v.getChildren().get(1))).getChildren().get(2);

          if (!startNodeIdText.getText().equals("")) {
            startId = Integer.parseInt(startNodeIdText.getText());
          }
          if (!endNodeIdText.getText().equals("")) {
            endId = Integer.parseInt(endNodeIdText.getText());
          }

          if (!(startId == -1 || endId == -1)) {
            Edge e = new Edge(startId, endId);

            try {
              DataManager.addEdge(e);
              map.setCurrentDisplayFloor(map.getCurrentDisplayFloor());
              //              changeFloor();
            } catch (SQLException ex) {
              System.out.println(ex);
              //            throw new RuntimeException(ex);
            } catch (IOException ex) {
              throw new RuntimeException(ex);
            }
          } else {
            System.out.println("Not enough info to make an edge");
          }
        }
      };

  EventHandler<MouseEvent> addNodePopup =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          Sound.playSFX(SFX.BUTTONCLICK);

          MFXButton addButton = ((MFXButton) event.getSource());
          //          VBox outerPane = (VBox) addButton.getParent();

          final var resource = App.class.getResource("views/ChangeNode.fxml");
          final FXMLLoader loader = new FXMLLoader(resource);
          VBox v;
          try {
            v = loader.load();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }

          MFXButton Submit = (MFXButton) ((Pane) (v.getChildren().get(7))).getChildren().get(2);
          // Remove the "Remove Node" Button
          ((Pane) (v.getChildren().get(7))).getChildren().remove(0);

          // This is theAdd Node Popup so remove the shortName and node type. and location name
          v.getChildren().remove(6);
          v.getChildren().remove(5);
          v.getChildren().remove(0);

          Submit.setOnMouseClicked(makeNewNode);
          ThemeSwitch.switchTheme(v);

          //          outerPane.getChildren().add(v);
          PopOver pop = new PopOver(v);
          pop.show(addButton);
          v.setOnMouseExited(event2 -> pop.hide());
        }
      };

  EventHandler<MouseEvent> addLocationPopup =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

          Sound.playSFX(SFX.BUTTONCLICK);
          MFXButton addButton = ((MFXButton) event.getSource());
          //          VBox outerPane = (VBox) addButton.getParent();

          final var resource = App.class.getResource("views/ChangeNode.fxml");
          final FXMLLoader loader = new FXMLLoader(resource);
          VBox v;
          try {
            v = loader.load();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }

          MFXButton Submit = (MFXButton) ((Pane) (v.getChildren().get(7))).getChildren().get(2);
          // Remove the "Remove Node" Button
          ((Pane) (v.getChildren().get(7))).getChildren().remove(0);

          TextField locationText =
              (TextField) ((Pane) (v.getChildren().get(0))).getChildren().get(2);

          TextField shortNameText =
              (TextField) ((Pane) (v.getChildren().get(5))).getChildren().get(2);

          TextField nodeTypeText =
              (TextField) ((Pane) (v.getChildren().get(5))).getChildren().get(2);

          // This is theAdd Node Popup so remove the shortName and node type.
          v.getChildren().remove(4);
          v.getChildren().remove(3);
          v.getChildren().remove(2);
          v.getChildren().remove(1);

          Submit.setOnMouseClicked(makeNewLocation);
          ThemeSwitch.switchTheme(v);

          //          outerPane.getChildren().add(v);
          PopOver pop = new PopOver(v);
          pop.show(addButton);
          v.setOnMouseExited(event2 -> pop.hide());
        }
      };

  EventHandler<MouseEvent> addEdgePopup =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          Sound.playSFX(SFX.BUTTONCLICK);
          MFXButton addButton = ((MFXButton) event.getSource());

          final var resource = App.class.getResource("views/EditEdge.fxml");
          // StartNodeID, EndNodeID, RemoveEdge, Submit
          final FXMLLoader loader = new FXMLLoader(resource);

          VBox v;

          try {
            v = loader.load();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }

          MFXButton Submit = (MFXButton) ((Pane) (v.getChildren().get(2))).getChildren().get(2);

          // Remove Remove Button
          ((Pane) (v.getChildren().get(2))).getChildren().remove(0);

          Submit.setOnMouseClicked(makeNewEdge);

          PopOver pop = new PopOver(v);
          pop.show(addButton);
          v.setOnMouseExited(event2 -> pop.hide());
        }
      };

  EventHandler<MouseEvent> toggleTable =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          Sound.playSFX(SFX.BUTTONCLICK);
          isMap = !isMap;
          if (isMap) {
            gp.setVisible(true);
            gp.setDisable(false);

            table.setVisible(false);
            table.setDisable(true);

            Legend.setVisible(true);
            Legend.setDisable(false);

            //            floorSelector.setVisible(true);
            //            floorSelector.setDisable(false);
          } else {
            gp.setVisible(false);
            gp.setDisable(true);

            table.setVisible(true);
            table.setDisable(false);

            Legend.setVisible(false);
            Legend.setDisable(true);

            //            floorSelector.setVisible(false);
            //            floorSelector.setDisable(true);
          }
        }
      };

  EventHandler<MouseEvent> saveLocationChanges =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          Sound.playSFX(SFX.BUTTONCLICK);
          System.out.println("Save Loc");

          MFXButton SubmitButton = ((MFXButton) event.getSource());
          VBox v = (VBox) ((HBox) SubmitButton.getParent()).getParent();

          TextField locationText =
              (TextField) ((Pane) (v.getChildren().get(0))).getChildren().get(1);
          TextField shortNameText =
              (TextField) ((Pane) (v.getChildren().get(1))).getChildren().get(1);
          TextField nodeTypeText =
              (TextField) ((Pane) (v.getChildren().get(2))).getChildren().get(1);

          if ((locationText.getText().equals(""))) {
            // Dont make a new one.
            return;
          }

          String longName = locationText.getText();
          System.out.println("LN: " + longName);
          String shortName = longName;
          if (!(shortNameText.getText().equals(""))) {
            shortName = shortNameText.getText();
          }
          String nodeType = "Hall";
          if (!(nodeTypeText.getText().equals(""))) {
            nodeType = nodeTypeText.getText();
          }

          ArrayList<Node> allNodes;
          try {
            allNodes = DataManager.getAllNodes();
          } catch (SQLException ex) {
            throw new RuntimeException(ex);
          }

          LocationName currLocation;
          try {
            currLocation = DataManager.getAllLocationNames().get(selectedRowIndex);
          } catch (SQLException ex) {
            throw new RuntimeException(ex);
          }

          String oldLn = currLocation.getLongName();
          //              Node n = new Node(highestID + 5, xPos, yPos, floor, building);
          LocationName l = new LocationName(oldLn, shortName, nodeType);
          l.setLongName(longName);

          try {
            DataManager.syncLocationName(l);
            map.setCurrentDisplayFloor(map.getCurrentDisplayFloor());
          } catch (SQLException | IOException ex) {
            System.out.println(ex);
            //            throw new RuntimeException(ex);
          }
        }
      };

  EventHandler<MouseEvent> removeLocation =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          Sound.playSFX(SFX.BUTTONCLICK);
          System.out.println("Rem");

          LocationName currLocation;
          try {
            currLocation = DataManager.getAllLocationNames().get(selectedRowIndex);
          } catch (SQLException ex) {
            throw new RuntimeException(ex);
          }

          String oldLn = currLocation.getLongName();

          LocationName l = new LocationName(oldLn, "", "");

          try {
            DataManager.deleteLocationName(l);
            map.setCurrentDisplayFloor(map.getCurrentDisplayFloor());
            ObservableList<LocationName> locations =
                FXCollections.observableArrayList(DataManager.getAllLocationNames());
            table.setItems(locations);
          } catch (SQLException ex) {
            System.out.println(ex);
            //            throw new RuntimeException(ex);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      };

  EventHandler<MouseEvent> showChangeLocation =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          Sound.playSFX(SFX.BUTTONCLICK);
          TableRow<LocationName> row = ((TableRow<LocationName>) event.getSource());
          selectedRowIndex = row.getIndex();

          final var resource = App.class.getResource("views/ChangeNode.fxml");

          final FXMLLoader loader = new FXMLLoader(resource);
          VBox v;
          try {
            v = loader.load();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }

          LocationName currLocation;
          try {
            currLocation = DataManager.getAllLocationNames().get(selectedRowIndex);
          } catch (SQLException ex) {
            throw new RuntimeException(ex);
          }

          MFXButton Remove = (MFXButton) ((Pane) (v.getChildren().get(7))).getChildren().get(0);
          Remove.setText("Remove Location");

          Remove.setOnMouseClicked(removeLocation);

          ((Pane) (v.getChildren().get(7))).getChildren().remove(2);
          ((Pane) (v.getChildren().get(7))).getChildren().remove(1);

          v.getChildren().remove(6);
          v.getChildren().remove(5);
          v.getChildren().remove(4);
          v.getChildren().remove(3);
          v.getChildren().remove(2);
          v.getChildren().remove(1);
          v.getChildren().remove(0);

          PopOver pop = new PopOver(v);
          pop.show(row);
          v.setOnMouseExited(event2 -> pop.hide());
        }
      };

  //  public void changeFloor(String floor) {
  //    System.out.println("CF");
  //    String floor = FloorSelect.getValue();
  //    System.out.println(floor);
  //
  //    try {
  //      map.setCurrentDisplayFloor(floor, false);
  //    } catch (SQLException e) {
  //      throw new RuntimeException(e);
  //    } catch (IOException e) {
  //      throw new RuntimeException(e);
  //    }
  //  }
  //
  //  EventHandler<ActionEvent> triggerChangeFloor =
  //      new EventHandler<ActionEvent>() {
  //
  //        @Override
  //        public void handle(ActionEvent event) {
  //
  //          changeFloor(map.getCurrentDisplayFloor());
  //          // Update Nodes
  //        }
  //      };

  EventHandler<MouseEvent> changeFloors =
      new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
          Sound.playSFX(SFX.BUTTONCLICK);
          MFXButton newButton = ((MFXButton) event.getSource());

          String oldFloor = map.getCurrentDisplayFloor();
          //          System.out.println("OF: " + oldFloor);

          for (MFXButton floorButton : floorButtons) {
            //            System.out.println("F: " + floorButton.getId());
            if (floorButton.getId().equals(oldFloor)) {
              //              System.out.println("Old");
              floorButton.getStyleClass().remove("primary");
              floorButton.getStyleClass().add("primary-container");
            }
          }

          // re-color new button
          newButton.getStyleClass().remove("primary-container");
          newButton.getStyleClass().add("primary");

          try {
            map.setCurrentDisplayFloor(newButton.getId());
          } catch (SQLException e) {
            throw new RuntimeException(e);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      };

  EventHandler<MouseEvent> changeFloorUp =
      new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
          System.out.println("CFU");

          ObservableList<String> floors = map.getAllFloors();
          int currFlorIndex = floors.indexOf(map.getCurrentDisplayFloor());
          String newFloor = floors.get(((currFlorIndex + 1) % floors.size()));

          try {
            map.setCurrentDisplayFloor(newFloor);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      };

  EventHandler<MouseEvent> changeFloorDown =
      new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
          System.out.println("CFU");

          ObservableList<String> floors = map.getAllFloors();
          int currFlorIndex = floors.indexOf(map.getCurrentDisplayFloor());
          String newFloor = floors.get(((currFlorIndex - 1) % floors.size()));

          try {
            map.setCurrentDisplayFloor(newFloor);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      };

  EventHandler<MouseEvent> changeLabels =
      new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
          Sound.playSFX(SFX.BUTTONCLICK);
          CheckBox newCheck = ((CheckBox) event.getSource());

          int oldLabel = map.getLabelTextType();

          if (oldLabel != -1) {
            CheckBox oldCheck = nameSelectBoxes.get(oldLabel);
            oldCheck.setSelected(false);
          }

          int newLabel = Integer.parseInt(newCheck.getId());
          if (newLabel == oldLabel) {
            newLabel = -1;
          }

          map.setLabelTextType(newLabel);

          try {
            map.setCurrentDisplayFloor(map.getCurrentDisplayFloor());
          } catch (SQLException e) {
            throw new RuntimeException(e);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      };

  EventHandler<MouseEvent> toggleEdges =
      new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
          Sound.playSFX(SFX.BUTTONCLICK);
          map.setShowEdges(EdgeSelector.isSelected());
          try {
            map.refresh();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      };

  EventHandler<MouseEvent> toggleNodes =
      new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
          Sound.playSFX(SFX.BUTTONCLICK);
          //        map.setShowEdges(EdgeSelector.isSelected());
          map.setShowNodes(NodeSelector.isSelected());

          try {
            map.refresh();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      };

  EventHandler<MouseEvent> toggleLegend =
      new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
          Sound.playSFX(SFX.BUTTONCLICK);
          //        map.setShowEdges(EdgeSelector.isSelected());
          map.setShowLegend(LegendSelector.isSelected()); // && NodeSelector.isSelected());
          Legend.setVisible(map.getShowLegend());

          try {
            map.refresh();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      };

  EventHandler<MouseEvent> toggleHalls =
      new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
          Sound.playSFX(SFX.BUTTONCLICK);
          map.setShowTypeLabels(new boolean[] {HallNamesSelector.isSelected()});

          try {
            map.refresh();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      };

  EventHandler<MouseEvent> checkAddNode =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

          if (event.getButton() == MouseButton.PRIMARY && map.getMovingNodeId() != -1) {
            System.out.println("Drop");
            final var resource = App.class.getResource("views/NodeSinglePopup.fxml");

            final FXMLLoader loader = new FXMLLoader(resource);
            VBox v;
            try {
              v = loader.load();
            } catch (IOException e) {
              throw new RuntimeException(e);
            }

            MFXButton Cancel = (MFXButton) ((Pane) (v.getChildren().get(0))).getChildren().get(0);
            MFXButton AddNode = (MFXButton) ((Pane) (v.getChildren().get(0))).getChildren().get(1);
            AddNode.setText("Confirm new Location");

            clickPos = new Point2D(event.getX(), event.getY());
            realClickPos = new Point2D(event.getScreenX(), event.getScreenY());

            PopOver pop = new PopOver(v);

            AddNode.setOnMouseClicked(updateNodePosition);
            Cancel.setOnMouseClicked(
                event1 -> {
                  pop.hide();
                  map.setMovingNodeId(-1);
                });

            v.setOnMouseExited(event2 -> pop.hide());

            pop.show(gp, realClickPos.getX(), realClickPos.getY());
          }

          if (event.getButton() == MouseButton.SECONDARY) {
            final var resource = App.class.getResource("views/NodeSinglePopup.fxml");

            final FXMLLoader loader = new FXMLLoader(resource);
            VBox v;
            try {
              v = loader.load();
            } catch (IOException e) {
              throw new RuntimeException(e);
            }

            MFXButton Cancel = (MFXButton) ((Pane) (v.getChildren().get(0))).getChildren().get(0);
            MFXButton AddNode = (MFXButton) ((Pane) (v.getChildren().get(0))).getChildren().get(1);
            AddNode.setText("Add Node Here");

            clickPos = new Point2D(event.getX(), event.getY());
            realClickPos = new Point2D(event.getScreenX(), event.getScreenY());

            PopOver pop = new PopOver(v);

            AddNode.setOnMouseClicked(addNode);
            Cancel.setOnMouseClicked(event1 -> pop.hide());

            v.setOnMouseExited(event2 -> pop.hide());

            pop.show(gp, realClickPos.getX(), realClickPos.getY());
          }
        }
      };

  EventHandler<MouseEvent> addNode =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          Sound.playSFX(SFX.BUTTONCLICK);
          int xPos = (int) clickPos.getX();
          int yPos = (int) clickPos.getY();
          String floor = map.takeFloor(map.getCurrentDisplayFloor(), false);
          String building = defaultBuilding;

          ArrayList<Node> allNodes;
          try {
            allNodes = DataManager.getAllNodes();
          } catch (SQLException ex) {
            throw new RuntimeException(ex);
          }

          // This is working on the assumption that We still want all id's to be a difference of 5
          // and that the last one in the table is the biggest ID
          // This is confirmed because getAllNodes sorts by ID
          int highestID = allNodes.get(allNodes.size() - 1).getId();

          Node n = new Node(highestID + 5, xPos, yPos, floor, building);

          try {
            DataManager.addNode(n);
            map.refresh();
            //            map.setCurrentDisplayFloor(map.getCurrentDisplayFloor());
            //                        changeFloor();

          } catch (SQLException ex) {
            System.out.println(ex);
            //            throw new RuntimeException(ex);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      };

  EventHandler<MouseEvent> updateNodePosition =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          Sound.playSFX(SFX.BUTTONCLICK);
          int xPos = (int) clickPos.getX();
          int yPos = (int) clickPos.getY();
          String floor = map.takeFloor(map.getCurrentDisplayFloor(), false);
          String building = defaultBuilding;

          Node n = new Node(map.getMovingNodeId(), xPos, yPos, floor, building);

          try {
            DataManager.syncNode(n);
            map.refresh();

          } catch (SQLException ex) {
            System.out.println(ex);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }

          map.setMovingNodeId(-1);
        }
      };

  EventHandler<MouseEvent> dragNode =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          int movingID = map.getMovingNodeId();
          if (movingID != -1) {
            if (nodeDragP == null) {

              float opacity = 0.4f;

              System.out.println("MID: " + movingID);

              ArrayList<LocationName> loc = GlobalVariables.getHMap().get(movingID);

              String nodeType;

              if (loc != null) {
                nodeType = loc.get(0).getNodeType();
              } else {
                nodeType = "";
              }

              ArrayList<Shape> nodeShapes = NodeCircle.makeNodeShape(nodeType);

              Shape outer = nodeShapes.get(0);
              Shape inner = nodeShapes.get(1);

              outer.setOpacity(opacity);
              inner.setOpacity(opacity);

              System.out.println("new P");
              nodeDragP = new Pane();

              adjacentNodes = new ArrayList<>();

              ArrayList<Node> AllNodes;
              try {
                AllNodes = DataManager.getAllNodes();
              } catch (SQLException e) {
                throw new RuntimeException(e);
              }

              ArrayList<Edge> AllEdges;
              try {
                AllEdges = DataManager.getAllEdges();
              } catch (SQLException e) {
                throw new RuntimeException(e);
              }

              for (int i = 0; i < AllEdges.size(); i++) {
                Edge e = AllEdges.get(i);
                if (e.getStartNodeID() == movingID) {
                  Node adj = AllNodes.get(Node.idToIndex(e.getEndNodeID()));
                  if (adj.getFloor().equals(map.takeFloor(map.getCurrentDisplayFloor(), true))) {
                    adjacentNodes.add(new Point2D(adj.getX(), adj.getY()));
                  }
                } else if (e.getEndNodeID() == movingID) {
                  Node adj = AllNodes.get(Node.idToIndex(e.getStartNodeID()));
                  if (adj.getFloor().equals(map.takeFloor(map.getCurrentDisplayFloor(), true))) {
                    adjacentNodes.add(new Point2D(adj.getX(), adj.getY()));
                  }
                }
              }

              Path path;

              for (Point2D adj : adjacentNodes) {
                for (int j = 0; j < 2; j++) {
                  path = new Path();
                  if (j == 0) {
                    path.setStroke(GlobalVariables.getBorderColor());
                  } else {
                    path.setStroke(GlobalVariables.getInsideColor());
                  }
                  path.setStrokeWidth(
                      GlobalVariables.getLineT() - (GlobalVariables.getStrokeThickness() * 2 * j));

                  path.getElements().add(new MoveTo(0, 0));

                  path.getElements()
                      .add(new LineTo(adj.getX() - event.getX(), adj.getY() - event.getY()));

                  path.setStrokeLineJoin(StrokeLineJoin.ROUND);
                  path.setOpacity(opacity);
                  nodeDragP.getChildren().add(path);
                }
              }

              nodeDragP.getChildren().add(outer);
              nodeDragP.getChildren().add(inner);

              anchor.getChildren().add(nodeDragP);
            }

            //            System.out.println("set P");

            nodeDragP.setLayoutX(event.getX());
            nodeDragP.setLayoutY(event.getY());

            for (int i = 0; i < adjacentNodes.size(); i++) {
              Point2D adj = adjacentNodes.get(i);
              for (int j = 0; j < 2; j++) {
                Path p = (Path) nodeDragP.getChildren().get(i * 2 + j);
                LineTo l = (LineTo) p.getElements().get(1);
                l.setX(adj.getX() - event.getX());
                l.setY(adj.getY() - event.getY());
              }
            }

          } else {
            if (nodeDragP != null) {
              anchor.getChildren().remove(nodeDragP);
              nodeDragP = null;
              adjacentNodes = null;
            }
          }
        }
      };

  /**
   * changes the language of the app
   *
   * @param lang language to set it to
   * @throws SQLException when the datamanager throws one
   */
  public void setLanguage(Language lang) throws SQLException {
    switch (lang) {
      case ENGLISH:
        ParentController.titleString.set("Map Editor");
        TickTitlePane.setText("TickBoxes");
        FloorTitlePane.setText("Floors");
        EditingTitledPane.setText("Editing");
        addNodeButton.setText("Add Node");
        addLocationButton.setText("Add Location");
        addEdgeButton.setText("Add Edge");
        toggleTableButton.setText("Modify Locations");
        ThirdFloorButton.setText("Third Floor");
        SecondFloorButton.setText("Second Floor");
        FirstFloorButton.setText("First Floor");
        LowerLevelOneButton.setText("Lower Level 1");
        LowerLevelTwoButton.setText("Lower Level 2");
        LongNameSelector.setText("Long");
        ShortNameSelector.setText("Short");
        IdSelector.setText("ID");
        HallNamesSelector.setText("Hall Names");
        EdgeSelector.setText("Show Edges");
        NodeSelector.setText("Node");
        LegendSelector.setText("Unique Shapes");
        mapSymbolsLabel.setText("Map Symbols");
        conferenceRoomLabel.setText("Conference Room");
        departmentLabel.setText("Department");
        labLabel.setText("Label");
        infoLabel.setText("Info");
        bathroomLabel.setText("Bathroom/Restroom");
        serviceLabel.setText("Service/Retail");
        elevatorLabel.setText("Elevator");
        stairsLabel.setText("Stairs");
        exitLabel.setText("Exit");
        startLabel.setText("Start");
        pathLabel.setText("Path");
        destinationLabel.setText("Destination");
        currentFloorStart.setText("Current Floor Start");
        currentFloorDestLabel.setText("Current Floor Destination");
        break;
      case ITALIAN:
        ParentController.titleString.set("Editor di Mappe");
        TickTitlePane.setText("Caselle di Controllo");
        FloorTitlePane.setText("Piani");
        EditingTitledPane.setText("Modifica");
        addNodeButton.setText("Aggiungi Nodo");
        addLocationButton.setText("Aggiungi Posizione");
        addEdgeButton.setText("Aggiungi Bordo");
        toggleTableButton.setText("Modifica Posizioni");
        ThirdFloorButton.setText("Terzo Piano");
        SecondFloorButton.setText("Secondo Piano");
        FirstFloorButton.setText("Primo Piano");
        LowerLevelOneButton.setText("Livello Inferiore 1");
        LowerLevelTwoButton.setText("Livello Inferiore 2");
        LongNameSelector.setText("Lungo");
        ShortNameSelector.setText("Breve");
        IdSelector.setText("ID");
        HallNamesSelector.setText("Nomi Corridoio");
        EdgeSelector.setText("Mostra Bordi");
        NodeSelector.setText("Nodo");
        LegendSelector.setText("Forme Uniche");
        mapSymbolsLabel.setText("Simboli della Mappa");
        conferenceRoomLabel.setText("Sala Conferenze");
        departmentLabel.setText("Dipartimento");
        labLabel.setText("Laboratorio");
        infoLabel.setText("Informazioni");
        bathroomLabel.setText("Bagno/Toilette");
        serviceLabel.setText("Servizio/Rivendita");
        elevatorLabel.setText("Ascensore");
        stairsLabel.setText("Scale");
        exitLabel.setText("Uscita");
        startLabel.setText("Inizio");
        pathLabel.setText("Percorso");
        destinationLabel.setText("Destinazione");
        currentFloorStart.setText("Inizio Piano Corrente");
        currentFloorDestLabel.setText("Destinazione Piano Corrente");
        break;
      case SPANISH:
        ParentController.titleString.set("Editor de Mapas");
        TickTitlePane.setText("Casillas de Verificaci" + GlobalVariables.getOAcute() + "n");
        FloorTitlePane.setText("Pisos");
        EditingTitledPane.setText("Edici" + GlobalVariables.getOAcute() + "n");
        addNodeButton.setText("Agregar Nodo");
        addLocationButton.setText("Agregar Ubicaci" + GlobalVariables.getOAcute() + "n");
        addEdgeButton.setText("Agregar Borde");
        toggleTableButton.setText("Modificar Ubicaciones");
        ThirdFloorButton.setText("Tercer Piso");
        SecondFloorButton.setText("Segundo Piso");
        FirstFloorButton.setText("Primer Piso");
        LowerLevelOneButton.setText("Nivel Inferior 1");
        LowerLevelTwoButton.setText("Nivel Inferior 2");
        LongNameSelector.setText("Largo");
        ShortNameSelector.setText("Corto");
        IdSelector.setText("ID");
        HallNamesSelector.setText("Nombres de Pasillo");
        EdgeSelector.setText("Mostrar Bordes");
        NodeSelector.setText("Nodo");
        LegendSelector.setText("Formas " + GlobalVariables.getBigUAcute() + "nicas");
        mapSymbolsLabel.setText("Símbolos del Mapa");
        conferenceRoomLabel.setText("Sala de Conferencias");
        departmentLabel.setText("Departamento");
        labLabel.setText("Laboratorio");
        infoLabel.setText("Informaci" + GlobalVariables.getOAcute() + "n");
        bathroomLabel.setText("Ba" + GlobalVariables.getNTilda() + "o/Aseo");
        serviceLabel.setText("Servicio/Venta");
        elevatorLabel.setText("Ascensor");
        stairsLabel.setText("Escaleras");
        exitLabel.setText("Salida");
        startLabel.setText("Inicio");
        pathLabel.setText("Ruta");
        destinationLabel.setText("Destino");
        currentFloorStart.setText("Inicio Piso Actual");
        currentFloorDestLabel.setText("Destino Piso Actual");
        break;
      case FRENCH:
        ParentController.titleString.set(GlobalVariables.getBigEACute() + "diteur de Cartes");
        TickTitlePane.setText("Cases " + GlobalVariables.getAGrave() + " Cocher");
        FloorTitlePane.setText(GlobalVariables.getBigEACute() + "tages");
        EditingTitledPane.setText("Modification");
        addNodeButton.setText("Ajouter un N" + GlobalVariables.getOe() + "ud");
        addLocationButton.setText("Ajouter un Emplacement");
        addEdgeButton.setText("Ajouter un Bord");
        toggleTableButton.setText("Modifier les Emplacements");
        ThirdFloorButton.setText(
            "Troisi"
                + GlobalVariables.getEGrave()
                + "me "
                + GlobalVariables.getBigEACute()
                + "tage");
        SecondFloorButton.setText(
            "Deuxi"
                + GlobalVariables.getEGrave()
                + "me "
                + GlobalVariables.getBigEACute()
                + "tage");
        FirstFloorButton.setText("Premier " + GlobalVariables.getBigEACute() + "tage");
        LowerLevelOneButton.setText("Niveau inf" + GlobalVariables.getEAcute() + "rieur 1");
        LowerLevelTwoButton.setText("Niveau inf" + GlobalVariables.getEAcute() + "rieur 2");

        LongNameSelector.setText("Long");
        ShortNameSelector.setText("Court");
        IdSelector.setText("ID");
        HallNamesSelector.setText("Noms des Couloirs");
        EdgeSelector.setText("Afficher les Bords");
        NodeSelector.setText("N" + GlobalVariables.getOe() + "ud");
        LegendSelector.setText("Formes Uniques");
        mapSymbolsLabel.setText("Symboles de la Carte");
        conferenceRoomLabel.setText("Salle de conf" + GlobalVariables.getEAcute() + "rence");
        departmentLabel.setText("D" + GlobalVariables.getEAcute() + "partement");
        labLabel.setText("Laboratoire");
        infoLabel.setText("Information");
        bathroomLabel.setText("Salle de Bain/Toilettes");
        serviceLabel.setText("Service/Vente");
        elevatorLabel.setText("Ascenseur");
        stairsLabel.setText("Escaliers");
        exitLabel.setText("Sortie");
        startLabel.setText("D" + GlobalVariables.getEAcute() + "part");
        pathLabel.setText("Chemin");
        destinationLabel.setText("Destination");
        currentFloorStart.setText(
            "D"
                + GlobalVariables.getEAcute()
                + "part "
                + GlobalVariables.getBigEACute()
                + "tage Actuel");
        currentFloorDestLabel.setText(
            "Destination " + GlobalVariables.getBigEACute() + "tage Actuel");
        break;
    }
  }

  @FXML
  public void initialize() throws SQLException, IOException {
    ThemeSwitch.switchTheme(root);
    ParentController.titleString.set("Map Editor");
    setLanguage(GlobalVariables.getB().getValue());
    GlobalVariables.b.addListener(
        (options, oldValue, newValue) -> {
          try {
            setLanguage(newValue);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });

    map = new Map(anchor, false);
    //

    gp.setMinScale(0.11);

    //    map.centerAndZoom(gp, OuterMapAnchor);
    Platform.runLater(() -> map.centerAndZoom(gp, OuterMapAnchor));

    ArrayList<javafx.scene.Node> currentFloorNodes = (map.makeAllFloorShapes(defaultFloor));
    anchor.getChildren().addAll(currentFloorNodes);
    map.setCurrentFloorShapes(currentFloorNodes);

    //        anchor.getChildren().addAll(map.makeAllFloorNodes(defaultFloor, false));

    ObservableList<String> tableNames = FXCollections.observableArrayList();
    tableNames.addAll("Nodes", "Edges", "Location Names", "Moves");

    selectTable.setItems(tableNames);
    //    selectTable.setDefault()
    //    selectTable.setOnAction(changeFloor);

    //    exportButton.setOnMouseClicked(exportCSV);
    //    importButton.setOnMouseClicked(importCSV);
    addNodeButton.setOnMouseClicked(addNodePopup);
    addLocationButton.setOnMouseClicked(addLocationPopup);
    addEdgeButton.setOnMouseClicked(addEdgePopup);
    toggleTableButton.setOnMouseClicked(toggleTable);

    //    table;

    longNameColumn.setCellValueFactory(new PropertyValueFactory<LocationName, String>("longName"));
    shortNameColumn.setCellValueFactory(
        new PropertyValueFactory<LocationName, String>("shortName"));
    nodeTypeColumn.setCellValueFactory(new PropertyValueFactory<LocationName, String>("nodeType"));

    table.setEditable(true);

    longNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    shortNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    nodeTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());

    longNameColumn.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent>() {
          @Override
          public void handle(TableColumn.CellEditEvent event) {
            LocationName l =
                (LocationName)
                    event.getTableView().getItems().get(event.getTablePosition().getRow());
            l.setLongName((String) event.getNewValue());
            try {
              DataManager.syncLocationName(l);
              map.setCurrentDisplayFloor(map.getCurrentDisplayFloor());
            } catch (SQLException e) {
              System.err.println("Error updating long name: " + e.getMessage());
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
          }
        });

    shortNameColumn.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent>() {
          @Override
          public void handle(TableColumn.CellEditEvent event) {
            LocationName l =
                (LocationName)
                    event.getTableView().getItems().get(event.getTablePosition().getRow());
            l.setShortName((String) event.getNewValue());
            try {
              DataManager.syncLocationName(l);
              map.setCurrentDisplayFloor(map.getCurrentDisplayFloor());
            } catch (SQLException e) {
              System.err.println("Error updating long name: " + e.getMessage());
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
          }
        });

    nodeTypeColumn.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent>() {
          @Override
          public void handle(TableColumn.CellEditEvent event) {
            LocationName l =
                (LocationName)
                    event.getTableView().getItems().get(event.getTablePosition().getRow());
            l.setNodeType((String) event.getNewValue());
            try {
              DataManager.syncLocationName(l);
              map.setCurrentDisplayFloor(map.getCurrentDisplayFloor());
            } catch (SQLException e) {
              System.err.println("Error updating long name: " + e.getMessage());
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
          }
        });

    ObservableList<LocationName> locations =
        FXCollections.observableArrayList(DataManager.getAllLocationNames());
    table.setItems(locations);

    table.setRowFactory(
        param -> {
          TableRow<LocationName> row = new TableRow<>();
          row.setOnMouseClicked(showChangeLocation);
          row.getItem();

          return row;
        });

    floorButtons.add(ThirdFloorButton);
    floorButtons.add(SecondFloorButton);
    floorButtons.add(FirstFloorButton);
    floorButtons.add(LowerLevelOneButton);
    floorButtons.add(LowerLevelTwoButton);

    for (MFXButton floorButton : floorButtons) {
      floorButton.setOnMouseClicked(changeFloors);
    }

    nameSelectBoxes.add(LongNameSelector);
    nameSelectBoxes.add(ShortNameSelector);
    nameSelectBoxes.add(IdSelector);

    for (CheckBox selectBox : nameSelectBoxes) {
      selectBox.setOnMouseClicked(changeLabels);
    }

    EdgeSelector.setOnMouseClicked(toggleEdges);
    HallNamesSelector.setOnMouseClicked(toggleHalls);

    NodeSelector.setOnMouseClicked(toggleNodes);
    LegendSelector.setOnMouseClicked(toggleLegend);

    //    DirectionsTitlePane.setExpanded(false);
    //    FloorTitlePane.setExpanded(false);
    //    TickTitlePane.setExpanded(false);

    anchor.setOnMouseClicked(checkAddNode);

    anchor.setOnMouseMoved(dragNode);
  }
}
