package edu.wpi.teamname.controllers;

public class ConferenceController {

  /**
   * @FXML ComboBox startBox; @FXML ComboBox endBox; @FXML ComboBox buildingBox; @FXML ComboBox
   * roomBox; @FXML DatePicker dateBox; @FXML RangeSlider sizeSlider; @FXML MFXButton
   * submitButton; @FXML VBox viewBox;
   *
   * <p>ObservableList<String> buildings; ObservableList<String> startTimes =
   * FXCollections.observableArrayList("8:00", "6:30"); ObservableList<String> endTimes =
   * FXCollections.observableArrayList("8:30", "7:00"); ObservableList<String> rooms;
   *
   * <p>private static Timestamp today = new Timestamp(System.currentTimeMillis()); @FXML public
   * void initialize() throws SQLException { buildings =
   * FXCollections.observableArrayList(DataManager.getConfBuildings()); rooms =
   * FXCollections.observableArrayList(DataManager.getConfRooms("all"));
   *
   * <p>buildingBox.setItems(buildings); startBox.setItems(startTimes); endBox.setItems(endTimes);
   * roomBox.setItems(rooms);
   *
   * <p>buildingBox.setOnAction( event -> { try { rooms = FXCollections.observableArrayList(
   * DataManager.getConfRooms(buildingBox.getValue().toString())); } catch (SQLException e) { throw
   * new RuntimeException(e); } roomBox.setItems(rooms); }); } *
   */
}
