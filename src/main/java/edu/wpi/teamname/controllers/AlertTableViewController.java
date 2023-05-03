package edu.wpi.teamname.controllers;

import edu.wpi.teamname.App;
import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.ThemeSwitch;
import edu.wpi.teamname.alerts.Alert;
import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.employees.EmployeeType;
import edu.wpi.teamname.extras.Language;
import edu.wpi.teamname.extras.SFX;
import edu.wpi.teamname.extras.Sound;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;

public class AlertTableViewController {
  @FXML AnchorPane alertTableViewPage;
  @FXML Label filterTable;
  @FXML Label urgencyLabel;
  @FXML Label staffTypeLabel;
  @FXML Label searchLabel;
  @FXML TableView<Alert> table; // 15

  @FXML TableColumn notificationIDCol; // 7
  @FXML TableColumn Description; // 8
  @FXML TableColumn authorCol; // 9
  @FXML TableColumn startDateCol; // 10
  @FXML TableColumn endDateCol; // 11
  @FXML TableColumn staffTypeCol; // 12
  @FXML TableColumn announcementCol; // 13
  @FXML TableColumn urgencyCol; // 14
  @FXML TextField searchTextField; // 16
  //  @FXML SearchableComboBox<Integer> alertIDAssign; // 4
  //  @FXML SearchableComboBox<String> assignStaffText; // 5
  //  @FXML MFXButton submitButton; // 6
  @FXML Label detailsLabel;
  @FXML Label detailsLabel1;
  @FXML Label totalLabel;
  @FXML MFXButton backButton;
  @FXML VBox cartBox;
  @FXML AnchorPane summaryPane;
  @FXML MFXButton ViewButton;
  @FXML MFXButton createNewAlert; // 3
  @FXML ComboBox<EmployeeType> staffTypeCombo; // 2
  @FXML ComboBox<Alert.Urgency> urgencyCombo; // 1

  public void makeNewAlert(
      int id,
      Timestamp start,
      Timestamp end,
      EmployeeType type,
      String description,
      String announcement,
      Alert.Urgency urgency)
      throws SQLException {
    Alert newAlert =
        new Alert(
            id,
            start,
            end,
            GlobalVariables.getCurrentUser().getUsername(),
            description,
            announcement,
            type,
            urgency);
    DataManager.addAlert(newAlert);
  }

  /**
   *  sets the language of the labels
   * @param lang language to set it to
   */
  public void setLanguage(Language lang) {
    switch (lang) {
      case SPANISH:
        ParentController.titleString.set("Alertas");
        filterTable.setText("Filtrar Tabla");
        urgencyLabel.setText("Urgencia de la Alerta");
        urgencyCombo.setPromptText("Urgencia");
        staffTypeLabel.setText("Tipo de Personal");
        staffTypeCombo.setPromptText("Elegir Tipo de Personal");
        createNewAlert.setText("Crear Nueva Alerta");
        searchLabel.setText("Buscar");
        notificationIDCol.setText("ID de Notificación");
        Description.setText("Descripci" + GlobalVariables.getOAcute() + "n");
        authorCol.setText("Autor");
        startDateCol.setText("Fecha de Inicio");
        endDateCol.setText("Fecha de Finalización");
        staffTypeCol.setText("Tipo de Personal");
        announcementCol.setText("Anuncio");
        urgencyCol.setText("Urgencia");
        break;
      case ITALIAN:
        ParentController.titleString.set("Allarme");
        filterTable.setText("Filtrare la Tabella");
        urgencyLabel.setText("Urgenza dell'Allarme");
        urgencyCombo.setPromptText("Urgenza");
        staffTypeLabel.setText("Tipo di Personale");
        staffTypeCombo.setPromptText("Scegliere il Tipo di Personale");
        createNewAlert.setText("Creare un Nuovo Allarme");
        searchLabel.setText("Cercare");
        notificationIDCol.setText("ID della Notifica");
        Description.setText("Descrizione");
        authorCol.setText("Autore");
        startDateCol.setText("Data di Inizio");
        endDateCol.setText("Data di Fine");
        staffTypeCol.setText("Tipo di Personale");
        announcementCol.setText("Annuncio");
        urgencyCol.setText("Urgenza");
        break;
      case FRENCH:
        ParentController.titleString.set("Alertes");
        filterTable.setText("Filtrer la Table");
        urgencyLabel.setText("Urgence de l'Alerte");
        urgencyCombo.setPromptText("Urgence");
        staffTypeLabel.setText("Type de Personnel");
        staffTypeCombo.setPromptText("Choisir le Type de Personnel");
        createNewAlert.setText("Cr" + GlobalVariables.getEAcute() + "er une Nouvelle Alerte");
        searchLabel.setText("Rechercher");
        notificationIDCol.setText("ID Notification");
        Description.setText("Description");
        authorCol.setText("Auteur");
        startDateCol.setText("Date de D" + GlobalVariables.getEAcute() + "but");
        endDateCol.setText("Date de Fin");
        staffTypeCol.setText("Type de Personnel");
        announcementCol.setText("Annonce");
        urgencyCol.setText("Urgence");
        break;
      case ENGLISH:
        ParentController.titleString.set("Alerts");
        filterTable.setText("Filter Table");
        urgencyLabel.setText("Alert Urgency");
        urgencyCombo.setPromptText("Urgency");
        staffTypeLabel.setText("Staff Type");
        staffTypeCombo.setPromptText("Choose Staff Type");
        createNewAlert.setText("Create New Alert");
        searchLabel.setText("Search");
        notificationIDCol.setText("Notification ID");
        Description.setText("Description");
        authorCol.setText("Author");
        startDateCol.setText("Start Date");
        endDateCol.setText("End Date");
        staffTypeCol.setText("Staff Type");
        announcementCol.setText("Announcement");
        urgencyCol.setText("Urgency");
        break;
    }
    ObservableList<EmployeeType> staffTypes =
        FXCollections.observableArrayList(EmployeeType.values());
    staffTypes.add(null);
    staffTypeCombo.setItems(staffTypes);
    ObservableList<Alert.Urgency> urgencyComboList =
        FXCollections.observableArrayList(Alert.Urgency.values());
    urgencyComboList.add(null);
    urgencyCombo.setItems(urgencyComboList);
  }

  @FXML
  public void initialize() throws SQLException {
    ThemeSwitch.switchTheme(alertTableViewPage);
    ParentController.titleString.set("Alerts");
    setLanguage(GlobalVariables.getB().getValue());
    GlobalVariables.b.addListener(
        (options, oldValue, newValue) -> {
          setLanguage(newValue);
        });

    ObservableList<EmployeeType> staffTypes =
        FXCollections.observableArrayList(EmployeeType.values());
    staffTypes.add(null);
    staffTypeCombo.setItems(staffTypes);

    ObservableList<Alert.Urgency> urgencyComboList =
        FXCollections.observableArrayList(Alert.Urgency.values());
    urgencyComboList.add(null);
    urgencyCombo.setItems(urgencyComboList);

    //    alertIDAssign.setItems(FXCollections.observableList(DataManager.getAllAlertIDs()));
    //    assignStaffText.setItems(FXCollections.observableList(DataManager.getAllUsernames()));

    staffTypeCombo.setOnAction(
        event -> {
          try {
            // update the table when the status combo box is changed
            table.setItems(tableFilter(urgencyCombo.getValue(), staffTypeCombo.getValue()));
          } catch (SQLException e) {
            e.printStackTrace();
          }
        });

    urgencyCombo.setOnAction(
        event -> {
          try {
            // update the table when the status combo box is changed
            table.setItems(tableFilter(urgencyCombo.getValue(), staffTypeCombo.getValue()));
          } catch (SQLException e) {
            e.printStackTrace();
          }
        });
    EventHandler<MouseEvent> makeNewAlert =
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            MFXButton SubmitButton = ((MFXButton) event.getSource());
          }
        };
    EventHandler<MouseEvent> addAlertPopup =
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            Sound.playSFX(SFX.BUTTONCLICK);
            MFXButton createNewButton = ((MFXButton) event.getSource());
            VBox outerPane = (VBox) createNewButton.getParent();

            final var resource = App.class.getResource("views/newAlert.fxml");
            final FXMLLoader loader = new FXMLLoader(resource);
            VBox v;

            try {
              v = loader.load();
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
            MFXButton submit = (MFXButton) ((Pane) (v.getChildren().get(5))).getChildren().get(1);
            MFXButton cancel = (MFXButton) ((Pane) (v.getChildren().get(5))).getChildren().get(0);
            ComboBox<EmployeeType> types =
                (ComboBox) ((Pane) (v.getChildren().get(4))).getChildren().get(0);
            types.setItems(staffTypes);
            ComboBox<Alert.Urgency> urgencies =
                (ComboBox) ((Pane) (v.getChildren().get(4))).getChildren().get(1);
            urgencies.setItems(urgencyComboList);
            TextField announcement = (TextField) ((v.getChildren().get(2)));
            submit.disableProperty().bind(Bindings.isEmpty(announcement.textProperty()));
            TextArea description = (TextArea) ((v.getChildren().get(3)));
            submit.disableProperty().bind(Bindings.isEmpty(description.textProperty()));
            DatePicker start = (DatePicker) ((Pane) (v.getChildren().get(1))).getChildren().get(0);
            submit.disableProperty().bind(Bindings.isNull(start.valueProperty()));
            DatePicker end = (DatePicker) ((Pane) (v.getChildren().get(1))).getChildren().get(1);
            submit.disableProperty().bind(Bindings.isNull(end.valueProperty()));
            submit.disableProperty().bind(Bindings.isNull(types.valueProperty()));
            PopOver pop = new PopOver(v);
            submit.setOnMouseClicked(
                event1 -> {
                  Sound.playSFX(SFX.BUTTONCLICK);
                  LocalDate startDateDate = start.getValue();
                  LocalTime startTime = LocalTime.of(0, 0);
                  LocalTime endTime = LocalTime.of(23, 59);
                  LocalDateTime startDateTime = startDateDate.atTime(startTime);
                  Timestamp startDate = Timestamp.valueOf(startDateTime);
                  LocalDate endDateDate = end.getValue();
                  LocalDateTime endDateTime = endDateDate.atTime(endTime);
                  Timestamp endDate = Timestamp.valueOf(endDateTime);
                  try {
                    if (urgencies.valueProperty().getValue() == null) {
                      makeNewAlert(
                          Instant.now().get(ChronoField.MICRO_OF_SECOND),
                          startDate,
                          endDate,
                          types.valueProperty().getValue(),
                          description.getText(),
                          announcement.getText(),
                          Alert.Urgency.NONE);
                    } else {
                      makeNewAlert(
                          Instant.now().get(ChronoField.MICRO_OF_SECOND),
                          startDate,
                          endDate,
                          types.valueProperty().getValue(),
                          description.getText(),
                          announcement.getText(),
                          urgencies.valueProperty().getValue());
                    }
                    table.setItems(tableFilter(urgencyCombo.getValue(), staffTypeCombo.getValue()));
                  } catch (SQLException e) {
                    throw new RuntimeException(e);
                  }

                  pop.hide();
                });

            cancel.setOnMouseClicked(
                event1 -> {
                  Sound.playSFX(SFX.BUTTONCLICK);
                  pop.hide();
                });
            pop.show(createNewButton);
          }
        };
    createNewAlert.setOnMouseClicked(addAlertPopup);

    ObservableList<Alert> alertsList = FXCollections.observableList(DataManager.getAllAlerts());
    FilteredList<Alert> alerts1 = new FilteredList<>(alertsList);
    SortedList<Alert> sortedListAlerts = new SortedList<>(alerts1);
    notificationIDCol.setCellValueFactory(new PropertyValueFactory<Alert, String>("id"));
    startDateCol.setCellValueFactory(new PropertyValueFactory<Alert, String>("startDisplayDate"));
    endDateCol.setCellValueFactory(new PropertyValueFactory<Alert, String>("endDisplayDate"));
    authorCol.setCellValueFactory(new PropertyValueFactory<Alert, String>("creator"));
    staffTypeCol.setCellValueFactory(new PropertyValueFactory<Alert, String>("type"));
    Description.setCellValueFactory(new PropertyValueFactory<Alert, String>("description"));
    announcementCol.setCellValueFactory(new PropertyValueFactory<Alert, String>("announcement"));
    urgencyCol.setCellValueFactory(new PropertyValueFactory<Alert, String>("urgency"));
    table.setItems(sortedListAlerts);

    searchTextField
        .textProperty()
        .addListener((observable, oldValue, newValue) -> filterTable(newValue));
  }

  public ObservableList<Alert> tableFilter(Alert.Urgency one, EmployeeType two)
      throws SQLException {
    ObservableList<Alert> alertList = FXCollections.observableList(DataManager.getAllAlerts());
    if (!(one == (null)) && !(one.toString().equals(""))) {
      alertList =
          FXCollections.observableList(
              alertList.stream()
                  .filter((alert) -> alert.getUrgency().toString().equals(one.toString()))
                  .toList());
    }
    if (!(two == (null)) && !(two.toString().equals(""))) {
      alertList =
          FXCollections.observableList(
              alertList.stream()
                  .filter((alert) -> alert.getType().toString().equals(two.toString()))
                  .toList());
    }
    return alertList;
  }

  private void filterTable(String searchText) {
    DataManager employeeDAO = new DataManager();
    if (searchText == null || searchText.isEmpty()) {
      try {
        ArrayList<Alert> alerts = employeeDAO.getAllAlerts();
        table.setItems(FXCollections.observableArrayList(alerts));
      } catch (SQLException e) {
        System.err.println("Error getting employees from database: " + e.getMessage());
      }
    } else {
      ObservableList<Alert> allAlerts = table.getItems();
      ObservableList<Alert> filteredAlerts = FXCollections.observableArrayList();

      for (Alert alert : allAlerts) {
        if (String.valueOf(alert.getId()).contains(searchText)
            || alert.getCreator().toLowerCase().contains(searchText.toLowerCase())
            || alert.getDescription().toLowerCase().contains(searchText.toLowerCase())
            || alert.getAnnouncement().toLowerCase().contains(searchText.toLowerCase())
            || alert.getType().getString().toLowerCase().contains(searchText.toLowerCase())
            || alert.getUrgency().getString().toLowerCase().contains(searchText.toLowerCase())) {
          filteredAlerts.add(alert);
        }
      }

      table.setItems(filteredAlerts);
    }
  }
}
