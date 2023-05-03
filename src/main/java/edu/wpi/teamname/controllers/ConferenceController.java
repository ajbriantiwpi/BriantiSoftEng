package edu.wpi.teamname.controllers;

import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.Navigation;
import edu.wpi.teamname.Screen;
import edu.wpi.teamname.ThemeSwitch;
import edu.wpi.teamname.controllers.JFXitems.RoomSelector;
import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.extras.Language;
import edu.wpi.teamname.extras.SFX;
import edu.wpi.teamname.extras.Sound;
import edu.wpi.teamname.servicerequest.ConfReservation;
import edu.wpi.teamname.servicerequest.RoomManager;
import edu.wpi.teamname.servicerequest.requestitem.ConfRoom;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import lombok.Setter;
import org.controlsfx.control.RangeSlider;

/** Controller for the UI to reserve a conference room */
public class ConferenceController {
  @FXML Label dateLabel;
  @FXML Label buildingLabel;
  @FXML Label startTimeLabel;
  @FXML Label endTimeLabel;
  @FXML Label roomSizeLabel;
  @FXML Label nameLabel;
  @FXML Label roomLabel;
  @FXML AnchorPane rootPane;
  @FXML ComboBox<String> startBox;
  @FXML ComboBox<String> endBox;
  @FXML ComboBox<String> buildingBox;
  @FXML ComboBox<String> roomBox;
  @FXML DatePicker dateBox;
  @FXML RangeSlider sizeSlider;
  @FXML MFXButton submitButton;
  @FXML TextField nameText;
  @Getter @Setter boolean fromSelector;
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
          "7:00", "7:30", "8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30",
          "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30",
          "17:00", "17:30", "18:00", "18:30");
  ObservableList<String> endTimes =
      FXCollections.observableArrayList(
          "7:30", "8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00",
          "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00",
          "17:30", "18:00", "18:30", "19:00");

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

  /**
   * sets the language of the labels
   *
   * @param lang language to set it to
   */
  public void setLanguage(Language lang) {
    switch (lang) {
      case ENGLISH:
        ParentController.titleString.set("Conference Room Request");
        dateLabel.setText("Date");
        buildingLabel.setText("Building");
        buildingBox.setPromptText("Choose Building");
        startTimeLabel.setText("Start Time");
        startBox.setPromptText("Choose Start Time");
        endTimeLabel.setText("End Time");
        endBox.setPromptText("Choose End Time");
        roomSizeLabel.setText("Room Size");
        nameLabel.setText("Name");
        roomLabel.setText("Room");
        roomBox.setPromptText("Choose Room");
        submitButton.setText("Request");
        break;
      case ITALIAN:
        ParentController.titleString.set("Richiesta sala conferenze");
        dateLabel.setText("Data");
        buildingLabel.setText("Edificio");
        buildingBox.setPromptText("Scegli Edificio");
        startTimeLabel.setText("Orario di inizio");
        startBox.setPromptText("Scegli orario di inizio");
        endTimeLabel.setText("Orario di fine");
        endBox.setPromptText("Scegli orario di fine");
        roomSizeLabel.setText("Dimensioni della stanza");
        nameLabel.setText("Nome");
        roomLabel.setText("Stanza");
        roomBox.setPromptText("Scegli la stanza");
        submitButton.setText("Richiesta");
        break;
      case FRENCH:
        ParentController.titleString.set(
            "Demande de salle de conf" + GlobalVariables.getEAcute() + "rence");
        dateLabel.setText("Date");
        buildingLabel.setText("B" + GlobalVariables.getACircumflex() + "timent");
        buildingBox.setPromptText("Choisir un b" + GlobalVariables.getACircumflex() + "timent");
        startTimeLabel.setText("Heure de d" + GlobalVariables.getEAcute() + "but");
        startBox.setPromptText("Choisir une heure de d" + GlobalVariables.getEAcute() + "but");
        endTimeLabel.setText("Heure de fin");
        endBox.setPromptText("Choisir une heure de fin");
        roomSizeLabel.setText("Taille de la salle");
        nameLabel.setText("Nom");
        roomLabel.setText("Salle");
        roomBox.setPromptText("Choisir une salle");
        submitButton.setText("Demande");
        break;
      case SPANISH:
        ParentController.titleString.set("Solicitud de Sala de Conferencias");
        dateLabel.setText("Fecha");
        buildingLabel.setText("Edificio");
        buildingBox.setPromptText("Seleccione un edificio");
        startTimeLabel.setText("Hora de inicio");
        startBox.setPromptText("Seleccione la hora de inicio");
        endTimeLabel.setText("Hora de finalizaci" + GlobalVariables.getOAcute() + "n");
        endBox.setPromptText(
            "Seleccione la hora de finalizaci" + GlobalVariables.getOAcute() + "n");
        roomSizeLabel.setText("Tama" + GlobalVariables.getNTilda() + "o de la sala");
        nameLabel.setText("Nombre");
        roomLabel.setText("Sala");
        roomBox.setPromptText("Seleccione la sala");
        submitButton.setText("Solicitud");
        break;
    }
    buildingBox.setItems(buildings);
    startBox.setItems(startTimes);
    endBox.setItems(endTimes);
    roomBox.setItems(roomsString);
  }

  @FXML
  public void initialize() throws SQLException {
    ParentController.titleString.set("Conference Room Request");
    setLanguage(GlobalVariables.getB().getValue());
    GlobalVariables.b.addListener(
        (options, oldValue, newValue) -> {
          setLanguage(newValue);
        });
    ThemeSwitch.switchTheme(rootPane);
    buildings = FXCollections.observableArrayList(DataManager.getConfBuildings());
    buildings.add("None");
    roomsString = FXCollections.observableArrayList();
    roomManager = new RoomManager();
    buildingBox.setItems(buildings);
    startBox.setItems(startTimes);
    endBox.setItems(endTimes);
    roomBox.setItems(roomsString);
    sizeSlider.setMax(100);
    sizeSlider.setMin(0);
    sizeSlider.highValueProperty().bindBidirectional(roomManager.getHigh());
    sizeSlider.lowValueProperty().bindBidirectional(roomManager.getLow());
    sizeSlider.setLowValue(0);
    sizeSlider.setHighValue(100);

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
          dateBook = Timestamp.valueOf(dateBox.getValue().atStartOfDay());

          refreshRooms(dateBook);
          /**
           * SET THE ACTUAL TABLE TO THE RIGHT CONFREQUESTS FOR EACH ROOM BASED ON DATE HERE USING
           * THE ARRAY LIST OF CONFERENCErESERVATIONS WITH (int resID, String startTime, String
           * endTime)*
           */
        });

    startBox.setOnAction(
        event -> { // set start time
          if (!fromSelector) {
            startTime = startBox.getValue();
            roomManager.setStart(startTime);
            refreshRooms();
          }
        });

    endBox.setOnAction(
        event -> { // set end time
          if (!fromSelector) {
            endTime = endBox.getValue();
            roomManager.setEnd(endTime);
            refreshRooms();
          }
        });

    sizeSlider.setOnMouseClicked(
        event -> {
          refreshRooms();
        });

    roomBox.setOnAction(
        event -> { // when room chosen set size slider and store into room variable
          room = roomBox.getValue();
          setActiveSelector(findSelector(room));
          if (startBox.getValue().contains(":") && endBox.getValue().contains(":")) {
            if (RoomSelector.timeToID(endBox.getValue())
                <= RoomSelector.timeToID(startBox.getValue())) {
              String tEnd = endTime;
              endBox.setValue(startTime);
              startBox.setValue(tEnd);
              //              return;
            }
            activeSelector.setStart(RoomSelector.timeToID(startBox.getValue()));
            activeSelector.setEnd(RoomSelector.timeToID(endBox.getValue()));
            activeSelector.setAllInRange(true);
          }
        });

    nameText.setOnMouseExited(
        event -> {
          nameRes = nameText.getText();
        });

    submitButton.setOnMouseClicked(
        event -> { // add to db and make new relation in array in confroomrequests
          if (roomManager.isViableRoom(activeSelector.getRoom(), dateBook)) {

            try {
              resID = DataManager.setResID();
              username = GlobalVariables.getCurrentUser().getUsername();
              ConfReservation c =
                  new ConfReservation(
                      Instant.now().get(ChronoField.MICRO_OF_SECOND),
                      startBox.getValue(),
                      endBox.getValue(),
                      dateBook,
                      Timestamp.from(Instant.now()),
                      nameRes,
                      username,
                      staff,
                      findSelector(roomBox.getValue()).getRoom().getRoomID());
              DataManager.addConfReservation(c);
              Sound.playSFX(SFX.SUCCESS);
              Navigation.navigate(Screen.SMILE);
            } catch (Exception e) {
              System.out.println(e);
              Sound.playSFX(SFX.ERROR);
            }
          }
        });
  }

  private RoomSelector findSelector(String room) {
    for (RoomSelector selector : selectors) {
      if (selector.getRoom().getLocationName().split(",")[0].equals(room)) {
        return selector;
      }
    }
    return null;
  }

  /**
   * Refreshes the rooms on the screen to
   *
   * @param date date to refresh the screen to
   */
  private void refreshRooms(Timestamp date) {
    String roomName = roomBox.getValue();
    selectors.clear();
    rooms.clear();
    roomsString.clear();
    System.out.println("Refreshing Selectors");
    for (ConfRoom room : roomManager.getViableRooms(date)) {
      selectors.add(new RoomSelector(room, this, date));
      rooms.add(room);
      roomsString.add(room.getLocationName().split(",")[0]);
    }
    if (findSelector(roomName) == null) {
      activeSelector = selectors.get(0);
    } else {
      activeSelector = findSelector(roomName);
      roomBox.setValue(roomName);
    }
  }

  /** Refreshes the rooms on the screen to. defaulting date to the datebox */
  private void refreshRooms() {
    refreshRooms(Timestamp.valueOf(dateBox.getValue().atStartOfDay()));
  }

  /**
   * sets the active selector to the RoomSelector that is currently being worked on
   *
   * @param selector RoomSelector currently being used
   */
  public void setActiveSelector(RoomSelector selector) {
    if (!activeSelector.equals(selector)) {
      activeSelector.setAllInRange(false);
      activeSelector.setSelected(0);

      selector.setAllInRange(true);
    }
    activeSelector = selector;
    roomBox.setValue(activeSelector.getRoom().getLocationName().split(",")[0]);
  }

  /**
   * sets the value of the start box as well as the start value in RoomManager
   *
   * @param time starting time
   */
  public void setStartBox(String time) {
    startBox.setValue(time);
    roomManager.setStart(time);
  }

  /**
   * sets the value of the end box as well as the end value in RoomManager
   *
   * @param time end time
   */
  public void setEndBox(String time) {
    endBox.setValue(time);
    roomManager.setEnd(time);
  }
}

/*
 * @FXML public void initialize() throws SQLException { System.out.println("Initializing");
 * rooms.add(uno); rooms.add(dos); rooms.add(tres); for (ConfRoom room : rooms) { selectors.add(new
 * RoomSelector(room, this)); } activeSelector = selectors.get(0); listView.setItems(selectors); }
 *
 * <p>public void setActiveSelector(RoomSelector selector) { if (!activeSelector.equals(selector)) {
 * activeSelector.setAllInRange(false); activeSelector.setSelected(0);
 *
 * <p>selector.setAllInRange(true); } activeSelector = selector; }
 */
