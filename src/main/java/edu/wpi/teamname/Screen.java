package edu.wpi.teamname;

public enum Screen {
  // Enum Constants Calling the Enum Constructor
  ROOT("views/Root.fxml"),
  TEMPLATE("views/Template.fxml"),
  HOME("views/Home.fxml"),
  SERVICE_REQUEST("views/ServiceRequest2.fxml"),
  MAP("views/Map.fxml"),

  TEST("views/Test.fxml"),
  SIGNAGE("views/SignageLevels.fxml"),
  EDIT_SIGNAGE("views/SignageLevels.fxml"),

  LOGIN("views/Login.fxml"),

  SERVICE_REQUEST_VIEW("views/ServiceRequestView2.fxml"),
  CONF_VIEW("views/ConferenceView.fxml"),

  MAP_EDIT("views/MapEdit.fxml"),

  MOVE_TABLE("views/MoveTable.fxml"),
  EMPLOYEE_TABLE("views/EmployeeTable.fxml"),
  SIGNAGE_TABLE("views/editSignage.fxml"),
  CONFERENCE_ROOM("views/ConferenceRoom.fxml"),
  ALERT("views/AlertTableView.fxml"),
  SMILE("views/Smily.fxml"),
  SERVICE_REQUEST_ANALYTICS("views/ServiceRequestAnalytics.fxml"),
  ABOUT("views/AboutPage.fxml"),
  CREDITS("views/CreditsPage.fxml"),
  CONOR_POPUP("views/ConorPopup.fxml"),
  IAN_POPUP("views/IanPopup.fxml"),
  JASON_POPUP("views/JasonPopup.fxml"),
  ALESSANDRO_POPUP("views/AlessandroPopup.fxml"),
  ADDY_POPUP("views/AddyPopup.fxml"),
  ALEK_POPUP("views/AlekPopup.fxml"),
  SAM_POPUP("views/SamPopup.fxml"),
  RYAN_POPUP("views/RyanPopup.fxml"),
  HUNTER_POPUP("views/HunterPopup.fxml"),
  ARTURO_POPUP("views/ArturoPopup.fxml"),
  ANDREW_POPUP("views/AndrewPopup.fxml"),

  DATA_MANAGER("views/Data.fxml");
  DATA_MANAGER("views/Data.fxml"),
  SETTINGS("views/Settings.fxml"),
  FEEDBACK("views/Feedback.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
