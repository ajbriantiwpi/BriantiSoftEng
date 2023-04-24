package edu.wpi.teamname.controllers;

import edu.wpi.teamname.database.DataManager;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import java.sql.Timestamp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import org.controlsfx.control.RangeSlider;

public class ConferenceController {

  @FXML ComboBox startBox;
  @FXML ComboBox endBox;
  @FXML ComboBox buildingBox;
  @FXML ComboBox roomBox;
  @FXML DatePicker dateBox;
  @FXML RangeSlider sizeSlider;
  @FXML MFXButton submitButton;
  @FXML VBox viewBox;

  ObservableList<String> buildings;
  ObservableList<String> startTimes = FXCollections.observableArrayList("8:00", "6:30");
  ObservableList<String> endTimes = FXCollections.observableArrayList("8:30", "7:00");
  ObservableList<String> rooms;

  private static Timestamp today = new Timestamp(System.currentTimeMillis());

  @FXML
  public void initialize() throws SQLException {
    buildings = FXCollections.observableArrayList(DataManager.getConfBuildings());
    rooms = FXCollections.observableArrayList(DataManager.getConfRooms("all"));

    buildingBox.setItems(buildings);
    startBox.setItems(startTimes);
    endBox.setItems(endTimes);
    roomBox.setItems(rooms);

    buildingBox.setOnAction(
        event -> {
          try {
            rooms =
                FXCollections.observableArrayList(
                    DataManager.getConfRooms(buildingBox.getValue().toString()));
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
          roomBox.setItems(rooms);
        });
  }
}
