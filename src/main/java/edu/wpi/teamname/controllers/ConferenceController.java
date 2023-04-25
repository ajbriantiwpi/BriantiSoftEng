package edu.wpi.teamname.controllers;

import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.controllers.JFXitems.RoomSelector;
import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.servicerequest.ConfReservation;
import edu.wpi.teamname.servicerequest.RoomManager;
import edu.wpi.teamname.servicerequest.requestitem.ConfRoom;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.RangeSlider;

public class ConferenceController {

  @FXML AnchorPane root;
  @FXML ComboBox<String> startBox;
  @FXML ComboBox<String> endBox;
  @FXML ComboBox<String> buildingBox;
  @FXML ComboBox<String> roomBox;
  @FXML DatePicker dateBox;
  @FXML RangeSlider sizeSlider;
  @FXML MFXButton submitButton;
  @FXML TextField nameText;

  @FXML ListView<RoomSelector> listView;
  @FXML TableView<ConfRoom> confTable;
  ConfRoom uno = new ConfRoom(1, "Uno", 10);
  ConfRoom dos = new ConfRoom(2, "Dos", 20);
  ConfRoom tres = new ConfRoom(3, "Tres", 30);
  ObservableList<ConfRoom> rooms = FXCollections.observableArrayList();
  ObservableList<String> roomsString = FXCollections.observableArrayList();
  ObservableList<RoomSelector> selectors = FXCollections.observableArrayList();
  RoomSelector activeSelector;

  // -----------NON FXML-----------------
  RoomManager roomManager;
  ObservableList<String> buildings;
  ObservableList<String> startTimes =
      FXCollections.observableArrayList(
          "8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30",
          "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30",
          "18:00", "18:30");
  ObservableList<String> endTimes =
      FXCollections.observableArrayList(
          "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00",
          "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00",
          "18:30", "19:00");

  //  private static Timestamp today = new Timestamp(System.currentTimeMillis());
  private static Timestamp dateBook;
  private static String startTime;
  private static String endTime;
  private static String room;
  private static int roomID;
  private static int resID;
  private static String nameRes;
  private static String username;
  private static String staff = "None";

  @FXML
  public void initialize() throws SQLException {
    ParentController.titleString.set("Conference Room Request");
    if (GlobalVariables.getDarkMode().get()) {
      root.getStylesheets().remove(0);
    } else {
      root.getStylesheets().remove(1);
    }
    buildings = FXCollections.observableArrayList(DataManager.getConfBuildings());
    buildings.add("None");
    roomsString = FXCollections.observableArrayList();

    buildingBox.setItems(buildings);
    startBox.setItems(startTimes);
    endBox.setItems(endTimes);
    roomBox.setItems(roomsString);
    sizeSlider.setMax(100);
    sizeSlider.setMin(0);

    roomManager = new RoomManager();

    //    startBox.valueProperty().bindBidirectional(roomManager.getStart());
    //    endBox.valueProperty().bindBidirectional(roomManager.getEnd());
    //    buildingBox.valueProperty().bindBidirectional(roomManager.getBuilding());
    dateBook = Timestamp.valueOf(LocalDateTime.now());
    dateBox.setValue(LocalDate.now());
    refreshRooms();
    //    for (ConfRoom room : roomManager.getRooms()) {
    //      selectors.add(new RoomSelector(room, this));
    //    }
    listView.setItems(selectors);
    activeSelector = selectors.get(0);

    listView.setPadding(new Insets(0));

    buildingBox.setOnAction(
        event -> {
          if (!buildingBox.getValue().toString().equals("")) { // if no building is selected
            roomManager.setBuilding(buildingBox.getValue());
            refreshRooms();
          } else { // if building is selected only display correct rooms
            try {
              roomsString =
                  FXCollections.observableArrayList(
                      DataManager.getConfRooms(buildingBox.getValue().toString()));
            } catch (SQLException e) {
              System.out.println(e);
            }
          }
          roomBox.setItems(roomsString);
        });

    dateBox.setOnAction(
        event -> {
          //          String timeString = dateBox.getValue().toString();
          //          int hour = Integer.valueOf(timeString.split(":")[0]);
          //          int min = Integer.valueOf(timeString.split(":")[1]);
          //          LocalTime time = LocalTime.of(hour, min);
          dateBook = Timestamp.valueOf(dateBox.getValue().atTime(LocalTime.of(0, 0)));

          refreshRooms(dateBook);
          /**
           * SET THE ACTUAL TABLE TO THE RIGHT CONFREQUESTS FOR EACH ROOM BASED ON DATE HERE USING
           * THE ARRAY LIST OF CONFERENCErESERVATIONS WITH (int resID, String startTime, String
           * endTime)*
           */
        });

    startBox.setOnMouseClicked(
        event -> { // set start time
          startTime = startBox.getValue().toString();
          roomManager.setStart(startTime);
          refreshRooms();
        });

    endBox.setOnMouseClicked(
        event -> { // set end time
          endTime = endBox.getValue().toString();
          roomManager.setStart(endTime);
          refreshRooms();
        });

    roomBox.setOnAction(
        event -> { // when room chosen set size slider and store into room variable
          room = roomBox.getValue().toString();
          try {
            roomID = DataManager.getRoomID(room);
            sizeSlider.setMax(DataManager.getSeats(room));
          } catch (SQLException e) {
            System.out.println(e);
          }
        });

    nameText.setOnMouseExited(
        event -> {
          nameRes = nameText.getText().toString();
        });

    submitButton.setOnMouseClicked(
        event -> { // add to db and make new relation in array in confroomrequests
          if (roomManager.isViableRoom(activeSelector.getRoom(), dateBook)) {
            try {
              resID = DataManager.setResID();
              username = GlobalVariables.getCurrentUser().getUsername();
              ArrayList<String> times = activeSelector.getTimes();
              ConfReservation c =
                  new ConfReservation(
                      resID,
                      times.get(0),
                      times.get(1),
                      dateBook,
                      Timestamp.from(Instant.now()),
                      nameRes,
                      username,
                      staff,
                      activeSelector.getRoom().getRoomID());
              DataManager.addConfReservation(c);
            } catch (SQLException e) {
              System.out.println(e);
            }
          }
        });
  }

  private void refreshRooms(Timestamp date) {
    selectors.clear();
    rooms.clear();
    roomsString.clear();
    System.out.println("Refreshing Selectors");
    for (ConfRoom room : roomManager.getViableRooms(date)) {
      selectors.add(new RoomSelector(room, this, date));
      rooms.add(room);
      roomsString.add(room.getLocationName());
    }
    activeSelector = selectors.get(0);
  }

  private void refreshRooms() {
    refreshRooms(Timestamp.valueOf(dateBox.getValue().atStartOfDay()));
  }

  public void setActiveSelector(RoomSelector selector) {
    if (!activeSelector.equals(selector)) {
      activeSelector.setAllInRange(false);
      activeSelector.setSelected(0);

      selector.setAllInRange(true);
    }
    activeSelector = selector;
  }

  public void setStartBox(String time) {
    startBox.setValue(time);
    roomManager.setStart(time);
  }

  public void setEndBox(String time) {
    endBox.setValue(time);
    roomManager.setEnd(time);
  }
}

/**
 * @FXML public void initialize() throws SQLException { System.out.println("Initializing");
 * rooms.add(uno); rooms.add(dos); rooms.add(tres); for (ConfRoom room : rooms) { selectors.add(new
 * RoomSelector(room, this)); } activeSelector = selectors.get(0); listView.setItems(selectors); }
 *
 * <p>public void setActiveSelector(RoomSelector selector) { if (!activeSelector.equals(selector)) {
 * activeSelector.setAllInRange(false); activeSelector.setSelected(0);
 *
 * <p>selector.setAllInRange(true); } activeSelector = selector; }
 */
