package edu.wpi.teamname.controllers;

import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.Navigation;
import edu.wpi.teamname.Screen;
import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.employees.ClearanceLevel;
import edu.wpi.teamname.extras.Language;
import edu.wpi.teamname.extras.Sound;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import lombok.Setter;

public class ParentController {
  @FXML ComboBox<Language> languageChooser;
  @FXML CheckBox darkToggle;
  @FXML MFXButton homeButton;
  @FXML MFXButton helpButton;
  @FXML MFXButton mapButton;
  // @FXML MFXButton directionsButton;
  @FXML MFXButton makeRequestsButton;
  @FXML MFXButton showRequestsButton;
  @FXML MFXButton editMapButton;
  @FXML MFXButton exitButton;
  @FXML MFXButton logoutButton;
  @FXML MFXButton loginButton;
  @FXML MFXButton editMoveButton;
  @FXML MFXButton showEmployeesButton;
  @FXML MFXButton requestRoomButton;
  @FXML MFXButton viewSignageButton;
  @FXML MFXButton editSignageButton;
  @FXML MFXButton viewAlertsButton;
  @FXML Label titleLabel;

  ArrayList<Screen> secureScreens =
      new ArrayList<>(
          Arrays.asList(
              Screen.MAP_EDIT,
              Screen.MOVE_TABLE,
              Screen.SERVICE_REQUEST,
              Screen.SERVICE_REQUEST_VIEW,
              Screen.EMPLOYEE_TABLE,
              Screen.CONFERENCE_ROOM,
              Screen.ALERT,
              Screen.DATA_MANAGER,
              Screen.SIGNAGE_TABLE,
              Screen.SERVICE_REQUEST_ANALYTICS,
              Screen.CONF_VIEW,
              Screen.SMILE));

  @Setter public static StringProperty titleString = new SimpleStringProperty();

  /** * Disables all the buttons that can not be accessed without logging in */
  public void disableButtonsWhenNotLoggedIn() {
    makeRequestsButton.setVisible(false);
    showRequestsButton.setVisible(false);
    editMapButton.setVisible(false);
    editMoveButton.setVisible(false);
    showEmployeesButton.setVisible(false);
    editSignageButton.setVisible(false);
    viewAlertsButton.setVisible(false);
    requestRoomButton.setVisible(false);
  }

  /** logs the current user out of the application */
  private void logout() {
    HomeController.setLoggedIn(new SimpleBooleanProperty(false));
    loginButton.setVisible(true);
    logoutButton.setVisible(false);
    GlobalVariables.logOut();
    disableButtonsWhenNotLoggedIn();

    if (secureScreens.contains(GlobalVariables.getCurrentScreen())) {
      Navigation.navigate(Screen.HOME);
    } else {
      Sound.playOnButtonClick();
    }
  }

  public void setLanguage(Language lang) {
    switch (lang) {
      case ENGLISH:
        homeButton.setText("Home");
        mapButton.setText("Map");
        viewSignageButton.setText("View Signage");
        makeRequestsButton.setText("Make Requests");
        requestRoomButton.setText("Request Room");
        showRequestsButton.setText("View Requests");
        editMoveButton.setText("View Moves");
        editSignageButton.setText("Edit Signage");
        editMapButton.setText("Edit Map");
        showEmployeesButton.setText("Show Employees");
        viewAlertsButton.setText("View Alerts");
        loginButton.setText("Login");
        logoutButton.setText("Logout");
        exitButton.setText("Exit");
        break;
      case FRENCH:
        homeButton.setText("Page D’accueil");
        mapButton.setText("Carte");
        viewSignageButton.setText("Voir la signalisation");
        makeRequestsButton.setText("Faire des demandes");
        requestRoomButton.setText("Demander une chambre");
        showRequestsButton.setText("Afficher les demandes");
        editMoveButton.setText("Voir les mouvements");
        editSignageButton.setText("Modifier la signalisation");
        editMapButton.setText("Modifier la carte");
        showEmployeesButton.setText("Afficher les employés");
        viewAlertsButton.setText("Afficher les alertes");
        loginButton.setText("Connexion");
        logoutButton.setText("Se déconnecter");
        exitButton.setText("Sortie");
        break;
      case ITALIAN:
        homeButton.setText("Pagina Iniziale");
        mapButton.setText("Mappa");
        viewSignageButton.setText("Segnaletica");
        makeRequestsButton.setText("Fare Una Richiesta");
        requestRoomButton.setText("Richiedi Una Camera");
        showRequestsButton.setText("Visualizzare Le Richieste");
        editMoveButton.setText("Visualizza Mosse");
        editSignageButton.setText("Modifica Segnaletica");
        editMapButton.setText("Modifica Mappa");
        showEmployeesButton.setText("Mostra Dipendenti");
        viewAlertsButton.setText("Visualizza Avvisi");
        loginButton.setText("Login");
        logoutButton.setText("Disconnettersi");
        exitButton.setText("Uscire");
        break;
      case SPANISH:
        homeButton.setText("Página de Inicio");
        mapButton.setText("Mapa");
        viewSignageButton.setText("Ver señalización");
        makeRequestsButton.setText("Hacer peticiones");
        requestRoomButton.setText("Solicitar Habitación");
        showRequestsButton.setText("Ver solicitudes");
        editMoveButton.setText("Ver movimientos");
        editSignageButton.setText("Editar señalización");
        editMapButton.setText("Editar mapa");
        showEmployeesButton.setText("Mostrar empleados");
        viewAlertsButton.setText("Ver alertas");
        loginButton.setText("Acceso");
        logoutButton.setText("Cerrar sesión");
        exitButton.setText("Salir");
        break;
    }
  }

  /**
   * template for when we want to add a new language, so it is easier to copy paste First block is
   * the list of words to be translated, second is the method to set the text for each, where you
   * change the letter to be the translated version
   */
  /*
  Home
  Map
  View Signage
  Make Requests
  Request Room
  View Requests
  View Moves
  Edit Signage
  Edit Map
  Show Employees
  View Alerts
  Login
  Logout
  Exit
     */
  /*
  homeButton.setText("A");
        mapButton.setText("B");
        viewSignageButton.setText("C");
        makeRequestsButton.setText("D");
        requestRoomButton.setText("E");
        showRequestsButton.setText("F");
        editMoveButton.setText("G");
        editSignageButton.setText("H");
        editMapButton.setText("I");
        showEmployeesButton.setText("J");
        viewAlertsButton.setText("K");
        loginButton.setText("L");
        logoutButton.setText("M");
        exitButton.setText("N");
   */

  @FXML
  public void initialize() throws IOException {
    titleString.addListener((observable, oldValue, newValue) -> titleLabel.setText(newValue));
    languageChooser.setItems(
        FXCollections.observableList(Arrays.stream(Language.values()).toList()));
    languageChooser.setValue(GlobalVariables.getB().getValue());
    languageChooser
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (options, oldValue, newValue) -> {
              setLanguage(newValue);
              GlobalVariables.b.setValue(newValue);
            });
    setLanguage(GlobalVariables.getB().getValue());
    titleLabel.setText(titleString.getValue());
    System.out.println("Parent!");
    darkToggle.selectedProperty().bindBidirectional(GlobalVariables.getDarkMode());
    darkToggle.setVisible(false);
    if (HomeController.getLoggedIn().getValue()) {
      // disableButtonsWhenNotLoggedIn();
      loginButton.setVisible(false);
      logoutButton.setVisible(true);
    } else {
      loginButton.setVisible(true);
      logoutButton.setVisible(false);
      viewSignageButton.setVisible(true);
      makeRequestsButton.setVisible(false);
      showRequestsButton.setVisible(false);
      editMoveButton.setVisible(false);
      editMapButton.setVisible(false);
      showEmployeesButton.setVisible(false);
      viewAlertsButton.setVisible(false);
      editSignageButton.setVisible(false);
      requestRoomButton.setVisible(false);
    }

    if (GlobalVariables.userIsClearanceLevel(ClearanceLevel.STAFF)) {
      makeRequestsButton.setDisable(false);
      showRequestsButton.setDisable(false);
      editMoveButton.setVisible(true);
      editMapButton.setVisible(false);
      showEmployeesButton.setVisible(false);
      viewAlertsButton.setVisible(false);
      editSignageButton.setVisible(false);
    }
    if (GlobalVariables.userIsClearanceLevel(ClearanceLevel.ADMIN)) {
      editMapButton.setDisable(false);
      showEmployeesButton.setDisable(false);
      makeRequestsButton.setDisable(false);
      showRequestsButton.setDisable(false);
      editMoveButton.setDisable(false);
      viewAlertsButton.setDisable(false);
      editSignageButton.setDisable(false);
    }
    homeButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    //    helpButton.setOnMouseClicked(event -> Navigation.navigate(Screen.));
    mapButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP));
    //        directionsButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE));
    makeRequestsButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUEST));
    showRequestsButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUEST_VIEW));
    loginButton.setOnMouseClicked(event -> Navigation.navigate(Screen.LOGIN));
    showEmployeesButton.setOnMouseClicked(event -> Navigation.navigate(Screen.EMPLOYEE_TABLE));
    logoutButton.setOnMouseClicked(event -> logout());
    // Navigation.navigate(Screen.SERVICE_REQUEST_VIEW));
    editMapButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP_EDIT));
    editMoveButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MOVE_TABLE));
    viewSignageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE));
    editSignageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_TABLE));
    viewAlertsButton.setOnMouseClicked(event -> Navigation.navigate(Screen.ALERT));
    requestRoomButton.setOnMouseClicked(event -> Navigation.navigate(Screen.CONFERENCE_ROOM));
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
    // darkToggle.setOnAction(event -> GlobalVariables.setDarkMode(darkToggle.isSelected()));

    // titleLabel.setText(titleString.getValue());
  }
}
