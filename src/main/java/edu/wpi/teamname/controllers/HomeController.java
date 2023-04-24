package edu.wpi.teamname.controllers;

import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.Navigation;
import edu.wpi.teamname.Screen;
import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.employees.ClearanceLevel;
import edu.wpi.teamname.navigation.Move;
import edu.wpi.teamname.servicerequest.ServiceRequest;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXNotificationCenter;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

public class HomeController {

  @FXML MFXButton helpButton;
  @FXML MFXButton mapButton;
  @FXML MFXButton editAlertButton;
  @FXML VBox actionVBox;
  @FXML VBox SRVBox;
  @FXML VBox mapVBox;
  @FXML GridPane homeGrid;
  @FXML MFXButton directionsButton;
  @FXML MFXButton makeRequestsButton;
  //  @FXML MFXButton makeRequestsButton1;
  //  @FXML MFXButton makeRequestsButton2;
  //  @FXML MFXButton makeRequestsButton3;
  @FXML MFXButton showRequestsButton;
  @FXML MFXButton editMapButton;
  @FXML MFXButton exitButton;
  // @FXML MFXButton navigateButton;
  @FXML MFXButton employeeButton;

  @FXML MFXButton activeRequests;
  @FXML MFXButton upcomingMoves;
  @FXML MFXButton doneRequests;
  @FXML MFXNotificationCenter notificationCenter;

  // test push
  @Setter @Getter private static ObservableBooleanValue loggedIn = new SimpleBooleanProperty(false);

  //  @FXML ImageView imageView;
  @FXML private AnchorPane rootPane;
  @FXML MFXButton loginButton;
  @FXML MFXButton logoutButton;
  @FXML MFXButton editMoveButton;

  /** logs the current user out of the application */
  private void logout() {
    loggedIn = new SimpleBooleanProperty(false);
    loginButton.setVisible(true);
    logoutButton.setVisible(false);
    GlobalVariables.logOut();
    disableButtonsWhenLoggedOut();
  }

  /** * Disables all the buttons that can not be accessed without logging in */
  private void disableButtonsWhenLoggedOut() {
    homeGrid.setConstraints(mapVBox, 1, 1);
    actionVBox.setVisible(false);
    SRVBox.setVisible(false);
    makeRequestsButton.setVisible(false);
    showRequestsButton.setVisible(false);
    editMapButton.setVisible(false); // these have to be set to visible false for staff/logged out
    editMoveButton.setVisible(false);
    employeeButton.setVisible(false);
    activeRequests.setVisible(false);
    upcomingMoves.setVisible(false);
    doneRequests.setVisible(false);
    showRequestsButton.setManaged(false);
    editMapButton.setManaged(false);
    editMoveButton.setManaged(false);
    employeeButton.setManaged(false);
    activeRequests.setManaged(false);
    upcomingMoves.setManaged(false);
    doneRequests.setManaged(false);
    makeRequestsButton.setManaged(false);
    actionVBox.setManaged(false);
    SRVBox.setManaged(false);
  }

  @FXML
  public void initialize() throws SQLException {
    editAlertButton.setOnMouseClicked(event -> Navigation.navigate(Screen.ALERT));
    //    notificationCenter.setHeaderTextProperty("");
    //    notificationCenter.set

    // set the width and height to be bound to the panes width and height
    //    imageView.fitWidthProperty().bind(rootPane.widthProperty());
    //    imageView.fitHeightProperty().bind(rootPane.heightProperty());
    // this allows for the image to stay at the same size of the rootPane, which is the parent pane
    // of the Home.fxml

    // Param is EventHandler
    // Lambda Expression. parameter -> expression
    // Basically just runs the Navigation.navigate Function
    // "event" is a parameter, but there is no
    helpButton.setVisible(false);
    if (loggedIn.getValue()) {
      loginButton.setVisible(false);
      logoutButton.setVisible(true);
      logoutButton.setDisable(false);
      ObservableList<ServiceRequest> requestList =
          FXCollections.observableList(
              DataManager.getAllServiceRequests().stream()
                  .filter(
                      (request) ->
                          request
                              .getStaffName()
                              .equals(GlobalVariables.getCurrentUser().getUsername()))
                  .toList());
      ObservableList<ServiceRequest> processingRequestsList =
          FXCollections.observableList(
              requestList.stream()
                  .filter((request) -> request.getStatus().getStatusString().equals("PROCESSING"))
                  .toList());
      ObservableList<ServiceRequest> doneRequestsList =
          FXCollections.observableList(
              requestList.stream()
                  .filter((request) -> request.getStatus().getStatusString().equals("DONE"))
                  .toList());
      int processingSize = processingRequestsList.size();
      int doneSize = doneRequestsList.size();
      activeRequests.setText(processingSize + " Active Request(s)");
      doneRequests.setText(doneSize + " Done Request(s)");
      ObservableList<Move> allMoves = FXCollections.observableArrayList(DataManager.getAllMoves());
      LocalDate today = LocalDate.now();
      int futureMoves = 0;
      for (Move move : allMoves) {
        if (move.getDate().toLocalDateTime().toLocalDate().isAfter(today)
            || move.getDate().toLocalDateTime().toLocalDate().isEqual(today)) {
          futureMoves++;
        }
      }
      upcomingMoves.setText(futureMoves + " Upcoming Moves");

    } else {
      activeRequests.setText("Log in to see Active Requests");
      doneRequests.setText("Log in to see Done Request(s)");
      upcomingMoves.setText("Log in to see Upcoming Moves");
      loginButton.setVisible(true);
      logoutButton.setVisible(false);
      logoutButton.setDisable(true);
    }
    loginButton.setOnMouseClicked(event -> Navigation.navigate(Screen.LOGIN));
    logoutButton.setOnMouseClicked(event -> logout());
    //    homeButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));

    //        helpButton.setOnMouseClicked(event -> Navigation.navigate(Screen));
    disableButtonsWhenLoggedOut();

    /** * Disables all the buttons that can not be accessed as Staff */
    if (GlobalVariables.userIsClearanceLevel(ClearanceLevel.STAFF)) {
      homeGrid.setConstraints(mapVBox, 1, 1);
      homeGrid.setConstraints(actionVBox, 0, 1);
      homeGrid.setConstraints(SRVBox, 2, 1);
      actionVBox.setVisible(true);
      SRVBox.setVisible(true);
      makeRequestsButton.setVisible(true);
      makeRequestsButton.setManaged(true);
      editMoveButton.setVisible(true);
      editMoveButton.setManaged(true);
      employeeButton.setVisible(false);
      employeeButton.setManaged(false);
      editMapButton.setVisible(false);
      editMapButton.setManaged(false);
      activeRequests.setVisible(true);
      activeRequests.setManaged(true);
      upcomingMoves.setVisible(true);
      upcomingMoves.setManaged(true);
      doneRequests.setVisible(true);
      doneRequests.setManaged(true);
      showRequestsButton.setVisible(true);
      showRequestsButton.setManaged(true);
      actionVBox.setManaged(true);
      SRVBox.setManaged(true);

      /** * Enables all buttons for the Admin login */
    } else if (GlobalVariables.userIsClearanceLevel(ClearanceLevel.ADMIN)) {
      homeGrid.setConstraints(mapVBox, 1, 1);
      homeGrid.setConstraints(actionVBox, 0, 1);
      homeGrid.setConstraints(SRVBox, 2, 1);
      actionVBox.setVisible(true);
      SRVBox.setVisible(true);
      editMapButton.setVisible(true);
      editMapButton.setDisable(false);
      editMapButton.setManaged(true);
      editMoveButton.setVisible(true);
      editMoveButton.setDisable(false);
      editMoveButton.setManaged(true);
      employeeButton.setVisible(true);
      employeeButton.setDisable(false);
      employeeButton.setManaged(true);
      activeRequests.setVisible(true);
      activeRequests.setDisable(false);
      activeRequests.setManaged(true);
      upcomingMoves.setVisible(true);
      upcomingMoves.setDisable(false);
      upcomingMoves.setManaged(true);
      doneRequests.setVisible(true);
      doneRequests.setDisable(false);
      doneRequests.setManaged(true);
      activeRequests.setVisible(true);
      activeRequests.setDisable(false);
      activeRequests.setManaged(true);
      doneRequests.setVisible(true);
      doneRequests.setDisable(false);
      doneRequests.setManaged(true);
      showRequestsButton.setVisible(true);
      showRequestsButton.setDisable(false);
      showRequestsButton.setManaged(true);
      makeRequestsButton.setVisible(true);
      makeRequestsButton.setDisable(false);
      makeRequestsButton.setManaged(true);
      editMoveButton.setVisible(true);
      editMoveButton.setDisable(false);
      editMoveButton.setManaged(true);
      actionVBox.setManaged(true);
      SRVBox.setManaged(true);
    }

    upcomingMoves.setOnMouseClicked(
        event -> {
          GlobalVariables.setFutureMovesPressed(true);
          Navigation.navigate(Screen.MOVE_TABLE);
        });
    activeRequests.setOnMouseClicked(
        event -> {
          GlobalVariables.setActiveRequestsPressed(true);
          Navigation.navigate(Screen.SERVICE_REQUEST_VIEW);
        });
    doneRequests.setOnMouseClicked(
        event -> {
          GlobalVariables.setDoneRequestsPressed(true);
          Navigation.navigate(Screen.SERVICE_REQUEST_VIEW);
        });

    mapButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP));
    // directionsButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE));
    makeRequestsButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUEST));
    //    makeRequestsButton1.setOnMouseClicked(event ->
    // Navigation.navigate(Screen.SERVICE_REQUEST));
    //    makeRequestsButton2.setOnMouseClicked(event ->
    // Navigation.navigate(Screen.SERVICE_REQUEST));
    //    makeRequestsButton3.setOnMouseClicked(event ->
    // Navigation.navigate(Screen.SERVICE_REQUEST));
    showRequestsButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUEST_VIEW));
    editMapButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP_EDIT));
    exitButton.setOnMouseClicked(event -> System.exit(0));
    // navigateButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP));
    editMoveButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MOVE_TABLE));
    employeeButton.setOnMouseClicked(event -> Navigation.navigate(Screen.EMPLOYEE_TABLE));
  }
}
