package edu.wpi.teamname.controllers;

import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.controllers.JFXitems.RoomSelector;
import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.servicerequest.ConfReservation;
import edu.wpi.teamname.servicerequest.requestitem.ConfRoom;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import org.controlsfx.control.RangeSlider;

public class ConferenceController {

  @FXML ComboBox<String> startBox;
  @FXML ComboBox<String> endBox;
  @FXML ComboBox<String> buildingBox;
  @FXML ComboBox<String> roomBox;
  @FXML DatePicker dateBox;
  @FXML RangeSlider sizeSlider;
  @FXML MFXButton submitButton;
  @FXML MFXButton nameText;

  @FXML
  ListView<RoomSelector> listView;
  @FXML TableView<ConfRoom> confTable;
  ConfRoom uno = new ConfRoom(1, "Uno", 10);
  ConfRoom dos = new ConfRoom(2, "Dos", 20);
  ConfRoom tres = new ConfRoom(3, "Tres", 30);
  ObservableList<ConfRoom> rooms = FXCollections.observableArrayList();
  ObservableList<String> roomsString = FXCollections.observableArrayList();
  ObservableList<RoomSelector> selectors = FXCollections.observableArrayList();
  RoomSelector activeSelector;

  // -----------NON FXML-----------------
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

  private static Timestamp today = new Timestamp(System.currentTimeMillis());
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
    rooms.add(uno); rooms.add(dos); rooms.add(tres); for (ConfRoom room : rooms) { selectors.add(new
            RoomSelector(room, this)); } activeSelector = selectors.get(0); listView.setItems(selectors);


    buildings = FXCollections.observableArrayList(DataManager.getConfBuildings());
    roomsString = FXCollections.observableArrayList(DataManager.getConfRooms("all"));

    buildingBox.setItems(buildings);
    startBox.setItems(startTimes);
    endBox.setItems(endTimes);
    roomBox.setItems(roomsString);
    sizeSlider.setMax(0);
    sizeSlider.setMin(0);

    buildingBox.setOnAction(
        event -> {
          if (!buildingBox.getValue().toString().equals("")) { // if no building is selected
            try {
              roomsString = FXCollections.observableArrayList(DataManager.getConfRooms("all"));
            } catch (SQLException e) {
              System.out.println(e);
            }
          } else { // if building is selected only display correct rooms
            try {
              roomsString = FXCollections.observableArrayList(DataManager.getConfRooms(buildingBox.getValue().toString()));
            } catch (SQLException e) {
              System.out.println(e);
            }
          }
          roomBox.setItems(roomsString);
        });

    dateBox.setOnAction(
        event -> {
          String timeString = dateBox.getValue().toString();
          int hour = Integer.valueOf(timeString.split(":")[0]);
          int min = Integer.valueOf(timeString.split(":")[1]);
          LocalTime time = LocalTime.of(hour, min);
          dateBook = Timestamp.valueOf(dateBox.getValue().atTime(time));

          ArrayList<ConfReservation> requestsByDate;
//          try {
//            rooms = DataManager.setTable();
//          } catch (SQLException e) {
//            throw new RuntimeException(e);
//          }
          /**
           * SET THE ACTUAL TABLE TO THE RIGHT CONFREQUESTS FOR EACH ROOM BASED ON DATE HERE USING
           * THE ARRAY LIST OF CONFERENCErESERVATIONS WITH (int resID, String startTime, String
           * endTime)*
           */
        });

    startBox.setOnAction(
        event -> { // set start time
          startTime = startBox.getValue().toString();
        });

    endBox.setOnAction(
        event -> { // set end time
          endTime = endBox.getValue().toString();
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
          try {
            resID = DataManager.setResID();
            username = GlobalVariables.getCurrentUser().getUsername();
            ConfReservation c =
                new ConfReservation(
                    resID, startTime, endTime, dateBook, today, nameRes, username, staff, roomID);
            DataManager.addConfReservation(c);
          } catch (SQLException e) {
            System.out.println(e);
          }
        });
  }

 public void setActiveSelector(RoomSelector selector) { if (!activeSelector.equals(selector)) {
    activeSelector.setAllInRange(false); activeSelector.setSelected(0);
 selector.setAllInRange(true); } activeSelector = selector; }
}




