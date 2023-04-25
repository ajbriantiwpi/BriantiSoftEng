package edu.wpi.teamname.controllers;

import edu.wpi.teamname.App;
import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.Navigation;
import edu.wpi.teamname.Screen;
import edu.wpi.teamname.alerts.Alert;
import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.employees.ClearanceLevel;
import edu.wpi.teamname.navigation.Move;
import edu.wpi.teamname.servicerequest.ServiceRequest;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXNotificationCenter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import lombok.Getter;
import lombok.Setter;
import org.controlsfx.control.PopOver;

public class HomeController {
  @FXML MFXButton notificationPopupButtonSimple;
  @FXML MFXNotificationCenter notifsButton;
  @FXML MFXButton helpButton;
  @FXML MFXButton mapButton;
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
  @FXML MFXButton employeeButton;

  @FXML MFXButton viewSignageButton;
  @FXML MFXButton editSignageButton;
  @FXML MFXButton requestRoomButton;
  @FXML MFXButton viewAlertsButton; // TAKE OUT LATER

  @FXML MFXButton activeRequests;
  @FXML MFXButton upcomingMoves;
  @FXML MFXButton doneRequests;
  @FXML MFXButton dataButton;

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
    editSignageButton.setVisible(false);
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
    editSignageButton.setManaged(false);
  }

  @FXML
  public void initialize() throws SQLException {

    EventHandler<MouseEvent> NotificationPopupEvent =
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            ObservableList<Alert> alertList = null;

            MFXButton createNewButton = ((MFXButton) event.getSource());
            HBox outerPane = (HBox) createNewButton.getParent();

            final var resource = App.class.getResource("views/NotificationPane.fxml");
            final FXMLLoader loader = new FXMLLoader(resource);
            VBox v;
            try {
              v = loader.load();
            } catch (IOException e) {
              throw new RuntimeException(e);
            }

            if (GlobalVariables.getCurrentUser() != null
                && !(GlobalVariables.getCurrentUser().getType().toString().equals("NONE"))) {

              try {
                alertList = FXCollections.observableList(DataManager.getAllAlerts());
              } catch (SQLException e) {
                throw new RuntimeException(e);
              }
              alertList.stream()
                  .filter(
                      (alert) -> alert.getType().equals(GlobalVariables.getCurrentUser().getType()))
                  .toList();
              System.out.println(alertList.size());
              System.out.println(GlobalVariables.getCurrentUser().getType());
              alertList =
                  FXCollections.observableList(
                      alertList.stream()
                          .filter(
                              (alert) ->
                                  alert.getStartDisplayDate().toInstant().isBefore(Instant.now()))
                          .toList());
              alertList =
                  FXCollections.observableList(
                      alertList.stream()
                          .filter(
                              (alert) ->
                                  alert.getEndDisplayDate().toInstant().isAfter(Instant.now()))
                          .toList());
              for (int i = 0; i < alertList.size(); i++) {
                HBox temp = new HBox();
                Label description = new Label();
                description.setText(alertList.get(i).getDescription());
                description.getStylesheets().add("@../stylesheets/RowLabel.css");
                description.getStylesheets().add("@../stylesheets/Colors/lightTheme.css");
                Label announcement = new Label();
                announcement.setText(alertList.get(i).getAnnouncement());
                announcement.getStylesheets().add("@../stylesheets/RowLabel.css");
                announcement.getStylesheets().add("@../stylesheets/Colors/lightTheme.css");
                announcement.wrapTextProperty().set(true);
                description.wrapTextProperty().set(true);

                HBox.setHgrow(description, Priority.SOMETIMES);
                HBox.setHgrow(announcement, Priority.ALWAYS);
                temp.setSpacing(50);
                temp.getChildren().add(announcement);
                temp.getChildren().add(description);
                VBox.setVgrow(temp, Priority.ALWAYS);
                v.getChildren().add(temp);
              }
            }

            PopOver pop = new PopOver(v);
            pop.show(createNewButton);
          }
        };

    //    notifsButton.getNotifications().clear();

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
      editSignageButton.setVisible(true);
      editSignageButton.setManaged(true);

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
      editSignageButton.setVisible(true);
      editSignageButton.setManaged(true);
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
    makeRequestsButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUEST));
    showRequestsButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUEST_VIEW));
    editMapButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP_EDIT));
    exitButton.setOnMouseClicked(
        event -> {
          try {
            Connection connection = DataManager.DbConnection();
            connection.close();
          } catch (SQLException e) {
            System.out.println(e.getMessage());
          }
          System.exit(0);
        });
    editMoveButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MOVE_TABLE));
    employeeButton.setOnMouseClicked(event -> Navigation.navigate(Screen.EMPLOYEE_TABLE));
    editSignageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_TABLE));
    viewSignageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE));
    viewAlertsButton.setOnMouseClicked(event -> Navigation.navigate(Screen.ALERT));
    requestRoomButton.setOnMouseClicked(event -> Navigation.navigate(Screen.CONFERENCE_ROOM));
    dataButton.setOnMouseClicked(event -> Navigation.navigate(Screen.DATA_MANAGER));
    notificationPopupButtonSimple.setOnMouseClicked(NotificationPopupEvent);
    //    notifsButton.setOnMouseClicked(event -> Navigation.navigate(Screen.ALERT));
  }
}
