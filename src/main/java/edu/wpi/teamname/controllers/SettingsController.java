package edu.wpi.teamname.controllers;

import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.Navigation;
import edu.wpi.teamname.Screen;
import edu.wpi.teamname.ThemeSwitch;
import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.employees.ClearanceLevel;
import edu.wpi.teamname.extras.Language;
import edu.wpi.teamname.extras.SFX;
import edu.wpi.teamname.extras.Song;
import edu.wpi.teamname.extras.Sound;
import edu.wpi.teamname.navigation.LocationName;
import edu.wpi.teamname.navigation.Node;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import lombok.Setter;

/**
 * The SettingsController class is responsible for managing the settings screen of the application.
 * It provides functionality for changing the database connection, managing application data, and
 * adjusting the application's volume.
 */
public class SettingsController {
  @FXML Label hardwareLabel;
  @FXML Label volumeLabel;
  @FXML Label songLabel;
  @FXML CheckBox darkToggle;
  @FXML AnchorPane root;
  private static boolean wpiSelected = true;
  @FXML Slider volumeSlide;
  @FXML MFXButton feedbackButton;
  @FXML Label dbConnectionLabel;
  @FXML Label appSettingsLabel;
  @FXML ComboBox songCombo;

  @FXML MFXButton dataManageButton;
  @FXML MFXButton viewFeedbackButton;
  @FXML RadioButton wpiButton;
  @FXML RadioButton awsButton;
  @FXML ComboBox<String> setLocationBox;
  @FXML Label defaultLocationLabel;
  @Getter @Setter private static SettingsController currController;

  /**
   * sets the language of all the labels
   *
   * @param lang language of current app
   */
  public void setLanguage(Language lang) {
    switch (lang) {
      case ENGLISH:
        setLocationBox.setPromptText("Set Location");
        ParentController.titleString.set("Settings");
        hardwareLabel.setText("Hardware Settings");
        volumeLabel.setText("Volume");
        songLabel.setText("Choose Song");
        appSettingsLabel.setText("App Settings");
        dbConnectionLabel.setText("Database Connection");
        dataManageButton.setText("Data");
        darkToggle.setText("Dark Mode");
        feedbackButton.setText("Submit Feedback");
        viewFeedbackButton.setText("View Feedback");
        break;
      case ITALIAN:
        setLocationBox.setPromptText("Imposta la posizione");
        ParentController.titleString.set("Impostazioni");
        hardwareLabel.setText("Impostazioni Hardware");
        volumeLabel.setText("Volume");
        Italian:
        darkToggle.setText("Modalit" + GlobalVariables.getAGrave() + " scura");
        songLabel.setText("Scegli Canzone");
        appSettingsLabel.setText("Impostazioni dell'App");
        dbConnectionLabel.setText("Connessione al Database");
        dataManageButton.setText("Dati");
        feedbackButton.setText("Invia feedback");
        viewFeedbackButton.setText("Visualizza Feedback");
        break;
      case FRENCH:
        setLocationBox.setPromptText("D" + GlobalVariables.getEAcute() + "finir l'emplacement");
        ParentController.titleString.set("Param" + GlobalVariables.getEGrave() + "tres");
        hardwareLabel.setText(
            "Param"
                + GlobalVariables.getEGrave()
                + "tres Mat"
                + GlobalVariables.getEAcute()
                + "riels");
        volumeLabel.setText("Volume");
        songLabel.setText("Choisir une chanson");
        appSettingsLabel.setText("Param" + GlobalVariables.getEGrave() + "tres de l'application");
        French:
        darkToggle.setText("Mode sombre");
        dbConnectionLabel.setText(
            "Connexion "
                + GlobalVariables.getAGrave()
                + " la base de donn"
                + GlobalVariables.getEAcute()
                + "es");
        dataManageButton.setText("Donn" + GlobalVariables.getEAcute() + "es");
        feedbackButton.setText("Soumettre des commentaires");
        viewFeedbackButton.setText("Voir les commentaires");
        break;
      case SPANISH:
        setLocationBox.setPromptText("Establecer ubicaci" + GlobalVariables.getOAcute() + "n");
        ParentController.titleString.set("Configuraci" + GlobalVariables.getOAcute() + "n");
        hardwareLabel.setText("Configuraci" + GlobalVariables.getOAcute() + "n de Hardware");
        volumeLabel.setText("Volumen");
        Spanish:
        darkToggle.setText("Modo oscuro");
        songLabel.setText("Elegir Canci" + GlobalVariables.getOAcute() + "n");
        appSettingsLabel.setText(
            "Configuraci"
                + GlobalVariables.getOAcute()
                + "n de la Aplicaci"
                + GlobalVariables.getOAcute()
                + "n");
        dbConnectionLabel.setText("Conexi" + GlobalVariables.getOAcute() + "n de Base de Datos");
        dataManageButton.setText("Datos");
        feedbackButton.setText("Enviar comentarios");
        viewFeedbackButton.setText("Ver Comentarios");
        break;
    }
  }

  public void logout() {
    viewFeedbackButton.setDisable(true);
    wpiButton.setDisable(true);
    awsButton.setDisable(true);
    appSettingsLabel.setVisible(false);
    dbConnectionLabel.setVisible(false);
    dataManageButton.setVisible(false);
    viewFeedbackButton.setVisible(false);
    wpiButton.setVisible(false);
    awsButton.setVisible(false);
    setLocationBox.setDisable(true);
    setLocationBox.setVisible(false);
    defaultLocationLabel.setVisible(false);
  }

  /**
   * Initializes the SettingsController and sets up the UI elements and functionality.
   *
   * @throws SQLException if there is an error connecting to the database
   */
  @FXML
  public void initialize() throws SQLException {

    // darkToggle.setOnAction(event -> GlobalVariables.getDarkMode().set(darkToggle.isSelected()));
    currController = this;
    ThemeSwitch.switchTheme(root);
    darkToggle.selectedProperty().bindBidirectional(GlobalVariables.getDarkMode());
    darkToggle.setOnAction(e -> Sound.playSFX(SFX.BUTTONCLICK));
    ParentController.titleString.set("Settings");
    ParentController.titleString.set("Service Request View");
    setLanguage(GlobalVariables.getB().getValue());
    GlobalVariables.b.addListener(
        (options, oldValue, newValue) -> {
          setLanguage(newValue);
        });
    viewFeedbackButton.setDisable(true);
    wpiButton.setDisable(true);
    awsButton.setDisable(true);
    appSettingsLabel.setVisible(false);
    dbConnectionLabel.setVisible(false);
    dataManageButton.setVisible(false);
    viewFeedbackButton.setVisible(false);
    wpiButton.setVisible(false);
    awsButton.setVisible(false);
    setLocationBox.setDisable(true);
    setLocationBox.setVisible(false);
    defaultLocationLabel.setVisible(false);
    if (GlobalVariables.userIsClearanceLevel(ClearanceLevel.ADMIN)) {
      viewFeedbackButton.setDisable(false);
      wpiButton.setDisable(false);
      awsButton.setDisable(false);
      appSettingsLabel.setVisible(true);
      dbConnectionLabel.setVisible(true);
      dataManageButton.setVisible(true);
      viewFeedbackButton.setVisible(true);
      wpiButton.setVisible(true);
      awsButton.setVisible(true);
      setLocationBox.setVisible(true);
      setLocationBox.setDisable(false);
      defaultLocationLabel.setVisible(true);
    } else if (GlobalVariables.userIsClearanceLevel(ClearanceLevel.STAFF)) {
      viewFeedbackButton.setDisable(false);
      appSettingsLabel.setVisible(true);
      dataManageButton.setVisible(true);
      viewFeedbackButton.setVisible(true);
    }
    // Add a listener to the volume slider
    volumeSlide.setValue(Sound.getVolume());
    volumeSlide
        .valueProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              // Change volume of the application
              setApplicationVolume(newValue.doubleValue());
            });

    HomeController.getLoggedIn()
        .addListener(
            (observable, old, newv) -> {
              if (!newv) {}
            });

    // Create a ToggleGroup to ensure only one button can be selected at a time
    ToggleGroup databaseToggleGroup = new ToggleGroup();

    wpiButton.setToggleGroup(databaseToggleGroup);
    awsButton.setToggleGroup(databaseToggleGroup);
    if (wpiSelected) {
      wpiButton.setSelected(true);
    } else {
      awsButton.setSelected(true);
    }

    // Hook up the methods to the toggle buttons
    wpiButton.setOnAction(
        e -> {
          try {
            DataManager.connectToWPI();
            wpiSelected = true;
          } catch (SQLException ex) {
            ex.printStackTrace();
          }
        });

    awsButton.setOnAction(
        e -> {
          try {
            DataManager.connectToAWS();
            wpiSelected = false;
          } catch (SQLException ex) {
            ex.printStackTrace();
          }
        });
    // Set up the songCombo ComboBox
    for (Song song : Song.values()) {
      if (song == Song.JETPACKJOYRIDE
          && !GlobalVariables.getCurrentUser().getUsername().equals("ian")) {
        continue; // Skip adding the JETPACKJOYRIDE song if the user is not "ian"
      }
      songCombo.getItems().add(song.getTitle());
    }

    // Set the initial selection of the ComboBox
    songCombo.getSelectionModel().select(Sound.getBackgroundSong().getTitle());

    // Add a listener to the songCombo ComboBox
    songCombo
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              // Change the background song of the application
              for (Song song : Song.values()) {
                if (song.getTitle().equals(newValue)) {
                  Sound.setSong(song);
                  break;
                }
              }
            });

    GlobalVariables.setHMap(
        DataManager.getAllLocationNamesMappedByNode(new Timestamp(System.currentTimeMillis())));

    setLocationBox.setPromptText("Select Location");
    setLocationBox.setItems(getAllNodeNames());
    setLocationBox.setOnAction(changeCurrentLocation);

    dataManageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.DATA_MANAGER));
    feedbackButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FEEDBACK));
    viewFeedbackButton.setOnMouseClicked(event -> Navigation.navigate(Screen.VIEW_FEEDBACK));
  }
  /**
   * Sets the volume of the application to the specified value.
   *
   * @param volume the new volume value
   */
  private void setApplicationVolume(double volume) {
    Sound.setVolume(volume);
  }

  public static ObservableList<String> getAllNodeNames() throws SQLException {
    ObservableList<String> nodeNames = FXCollections.observableArrayList();

    HashMap<Integer, ArrayList<LocationName>> hMap = GlobalVariables.getHMap();

    for (Integer i : hMap.keySet()) {
      // Gets rid of all Hallway locations
      if (!hMap.get(i).get(0).getNodeType().equals("HALL")) {
        nodeNames.add(hMap.get(i).get(0).getLongName());
      }
    }

    Collections.sort(nodeNames);

    return nodeNames;
  }

  EventHandler<ActionEvent> changeCurrentLocation =
      new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          Node nodeForLocation;
          // System.out.println("changed start " + LocationOne.getValue());
          // System.out.println(LocationOne.getValue());
          // System.out.println(EndPointSelect.getValue());
          String currentLName = setLocationBox.getValue();

          try {
            nodeForLocation =
                DataManager.getNodeByLocationName(
                    currentLName, new Timestamp(System.currentTimeMillis()));
          } catch (SQLException ex) {
            throw new RuntimeException(ex);
          }

          GlobalVariables.setCurrentLocationNode(nodeForLocation);
          // System.out.print("New Location: " + GlobalVariables.getCurrentLocationNode().getId());
        }
      };
}
