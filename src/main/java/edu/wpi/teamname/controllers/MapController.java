package edu.wpi.teamname.controllers;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.navigation.LocationName;
import edu.wpi.teamname.navigation.Map;
import edu.wpi.teamname.navigation.Node;
import edu.wpi.teamname.system.App;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import net.kurobako.gesturefx.GesturePane;
import org.controlsfx.control.PopOver;

public class MapController {

  Map map;
  @FXML GesturePane gp;

  @FXML TableView table;
  @FXML AnchorPane anchor;

  @FXML MFXButton exportButton;

  @FXML MFXButton importButton;
  @FXML MFXButton addNodeButton;

  @FXML MFXButton addLocationButton;

  @FXML MFXButton toggleTableButton;

  @FXML TableColumn longNameColumn;
  @FXML TableColumn shortNameColumn;
  @FXML TableColumn nodeTypeColumn;

  //  @FXML MFXComboBox<String> selectTable = new MFXComboBox<>();
  @FXML ComboBox<String> selectTable = new ComboBox<>();

  String defaultFloor = "L1";
  int defaultX = 0;
  int defaultY = 0;
  String defaultBuilding = "45 Francis";

  int clickCount = 0;
  Point2D firstClick = null;
  Point2D secondClick = null;

  boolean isMap = true;

  int selectedRowIndex = 0;

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

  EventHandler<MouseEvent> exportCSV =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
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
          TextField xText = (TextField) ((Pane) (v.getChildren().get(0))).getChildren().get(1);
          TextField yText = (TextField) ((Pane) (v.getChildren().get(1))).getChildren().get(1);
          TextField floorText = (TextField) ((Pane) (v.getChildren().get(2))).getChildren().get(1);
          TextField buildingText =
              (TextField) ((Pane) (v.getChildren().get(3))).getChildren().get(1);

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
          String floor = defaultFloor;
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
          } catch (SQLException ex) {
            System.out.println(ex);
            //            throw new RuntimeException(ex);
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
              (TextField) ((Pane) (v.getChildren().get(0))).getChildren().get(1);
          TextField shortNameText =
              (TextField) ((Pane) (v.getChildren().get(1))).getChildren().get(1);
          TextField nodeTypeText =
              (TextField) ((Pane) (v.getChildren().get(2))).getChildren().get(1);

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
          } catch (SQLException ex) {
            System.out.println(ex);
            //            throw new RuntimeException(ex);
          }
        }
      };

  EventHandler<MouseEvent> addNodePopup =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

          MFXButton addButton = ((MFXButton) event.getSource());
          VBox outerPane = (VBox) addButton.getParent();

          final var resource = App.class.getResource("../views/ChangeNode.fxml");
          final FXMLLoader loader = new FXMLLoader(resource);
          VBox v;
          try {
            v = loader.load();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }

          MFXButton Submit = (MFXButton) ((Pane) (v.getChildren().get(7))).getChildren().get(1);
          // Remove the "Remove Node" Button
          ((Pane) (v.getChildren().get(7))).getChildren().remove(0);

          // This is theAdd Node Popup so remove the shortName and node type. and location name
          v.getChildren().remove(6);
          v.getChildren().remove(5);
          v.getChildren().remove(0);

          Submit.setOnMouseClicked(makeNewNode);

          outerPane.getChildren().add(v);
        }
      };

  EventHandler<MouseEvent> addLocationPopup =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

          MFXButton addButton = ((MFXButton) event.getSource());
          VBox outerPane = (VBox) addButton.getParent();

          final var resource = App.class.getResource("../views/ChangeNode.fxml");
          final FXMLLoader loader = new FXMLLoader(resource);
          VBox v;
          try {
            v = loader.load();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }

          MFXButton Submit = (MFXButton) ((Pane) (v.getChildren().get(7))).getChildren().get(1);
          // Remove the "Remove Node" Button
          ((Pane) (v.getChildren().get(7))).getChildren().remove(0);

          TextField locationText =
              (TextField) ((Pane) (v.getChildren().get(0))).getChildren().get(1);

          TextField shortNameText =
              (TextField) ((Pane) (v.getChildren().get(5))).getChildren().get(1);

          TextField nodeTypeText =
              (TextField) ((Pane) (v.getChildren().get(5))).getChildren().get(1);

          // This is theAdd Node Popup so remove the shortName and node type.
          v.getChildren().remove(4);
          v.getChildren().remove(3);
          v.getChildren().remove(2);
          v.getChildren().remove(1);

          Submit.setOnMouseClicked(makeNewLocation);

          outerPane.getChildren().add(v);
        }
      };

  EventHandler<MouseEvent> toggleTable =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          isMap = !isMap;
          if (isMap) {
            gp.setVisible(true);
            gp.setDisable(false);

            table.setVisible(false);
            table.setDisable(true);
          } else {
            gp.setVisible(false);
            gp.setDisable(true);

            table.setVisible(true);
            table.setDisable(false);
          }
        }
      };

  EventHandler<MouseEvent> saveLocationChanges =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
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
          } catch (SQLException ex) {
            System.out.println(ex);
            //            throw new RuntimeException(ex);
          }
        }
      };

  EventHandler<MouseEvent> removeLocation =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
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
          } catch (SQLException ex) {
            System.out.println(ex);
            //            throw new RuntimeException(ex);
          }
        }
      };

  EventHandler<MouseEvent> showChangeLocation =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

          TableRow<LocationName> row = ((TableRow<LocationName>) event.getSource());
          selectedRowIndex = row.getIndex();

          final var resource = App.class.getResource("../views/ChangeNode.fxml");

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

          MFXButton Submit = (MFXButton) ((Pane) (v.getChildren().get(7))).getChildren().get(1);
          Submit.setOnMouseClicked(saveLocationChanges);

          MFXButton Remove = (MFXButton) ((Pane) (v.getChildren().get(7))).getChildren().get(0);
          Remove.setOnMouseClicked(removeLocation);

          TextField locationText =
              (TextField) ((Pane) (v.getChildren().get(0))).getChildren().get(1);
          locationText.setText(currLocation.getLongName());
          TextField shortNameText =
              (TextField) ((Pane) (v.getChildren().get(5))).getChildren().get(1);
          shortNameText.setText(currLocation.getShortName());
          TextField nodeTypeText =
              (TextField) ((Pane) (v.getChildren().get(6))).getChildren().get(1);
          nodeTypeText.setText(currLocation.getNodeType());

          //                ((Pane) (v.getChildren().get(7))).getChildren().remove(0);
          v.getChildren().remove(4);
          v.getChildren().remove(3);
          v.getChildren().remove(2);
          v.getChildren().remove(1);

          PopOver pop = new PopOver(v);
          pop.show(row);
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

    exportButton.setOnMouseClicked(exportCSV);
    importButton.setOnMouseClicked(importCSV);
    addNodeButton.setOnMouseClicked(addNodePopup);
    addLocationButton.setOnMouseClicked(addLocationPopup);
    toggleTableButton.setOnMouseClicked(toggleTable);

    //    table;
    ObservableList<LocationName> locations =
        FXCollections.observableArrayList(DataManager.getAllLocationNames());

    longNameColumn.setCellValueFactory(new PropertyValueFactory<LocationName, String>("longName"));
    shortNameColumn.setCellValueFactory(
        new PropertyValueFactory<LocationName, String>("shortName"));
    nodeTypeColumn.setCellValueFactory(new PropertyValueFactory<LocationName, String>("nodeType"));

    table.setItems(locations);

    table.setRowFactory(
        param -> {
          TableRow<LocationName> row = new TableRow<>();
          row.setOnMouseClicked(showChangeLocation);
          row.getItem();

          //      row.getChildren

          return row;
        });

    //    table
    //        .getSelectionModel()
    //        .selectedItemProperty()
    //        .addListener(
    //            new ChangeListener() {
    //              @Override
    //              public void changed(ObservableValue observable, Object oldValue, Object
    // newValue) {
    //                // Pop up display
    //
    //                int index = table.getSelectionModel().getSelectedIndex();
    ////                table.getRow
    //
    //                final var resource = App.class.getResource("../views/ChangeNode.fxml");
    //                final FXMLLoader loader = new FXMLLoader(resource);
    //                VBox v;
    //                try {
    //                  v = loader.load();
    //                } catch (IOException e) {
    //                  throw new RuntimeException(e);
    //                }
    //
    //                                MFXButton Submit = (MFXButton) ((Pane)
    //                 (v.getChildren().get(7))).getChildren().get(1);
    //                                Submit.setOnMouseClicked(saveLocationChanges);
    //
    //                                MFXButton Remove = (MFXButton) ((Pane)
    //                 (v.getChildren().get(7))).getChildren().get(0);
    //                                Remove.setOnMouseClicked(removeLocation);
    //
    //                //                ((Pane) (v.getChildren().get(7))).getChildren().remove(0);
    //                v.getChildren().remove(4);
    //                v.getChildren().remove(3);
    //                v.getChildren().remove(2);
    //                v.getChildren().remove(1);
    //
    //                //                outerPane.getChildren().add(v);
    //
    //              }
    //            });
  }
}
