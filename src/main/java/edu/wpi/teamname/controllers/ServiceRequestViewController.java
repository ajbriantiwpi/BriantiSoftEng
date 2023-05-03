package edu.wpi.teamname.controllers;

import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.ThemeSwitch;
import edu.wpi.teamname.controllers.JFXitems.ReqMenuItems;
import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.extras.Language;
import edu.wpi.teamname.extras.SFX;
import edu.wpi.teamname.extras.Sound;
import edu.wpi.teamname.servicerequest.ItemsOrdered;
import edu.wpi.teamname.servicerequest.RequestType;
import edu.wpi.teamname.servicerequest.ServiceRequest;
import edu.wpi.teamname.servicerequest.Status;
import edu.wpi.teamname.servicerequest.requestitem.RequestItem;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.SearchableComboBox;

public class ServiceRequestViewController {
  @FXML Label filterTableLabel;
  @FXML Label requestTypeLabel;
  @FXML Label statusLabel;
  @FXML Label assignedLabel;
  @FXML Label dateLabel;
  @FXML Label assignStaffLabel;
  @FXML Label requestIDLabel;
  @FXML Label staffLabel;
  @FXML Label setStatusLabel;
  @FXML Label requestDetailsLabel;
  @FXML AnchorPane root;
  @FXML TableView<ServiceRequest> table;
  @FXML TableColumn requestIDCol;
  @FXML TableColumn patientNameCol;
  @FXML TableColumn roomNumCol;
  @FXML TableColumn requesterIDCol;
  @FXML TableColumn requestedAtCol;
  @FXML TableColumn requestedForCol;
  @FXML TableColumn assignedStaffCol;
  @FXML TableColumn statusCol;
  @FXML SearchableComboBox<String> requestIDText;
  @FXML SearchableComboBox<String> assignStaffText;
  @FXML ComboBox<Status> requestStatusText;

  @FXML MFXButton submitButton;

  @FXML Label detailsLabel;
  @FXML Label detailsLabel1;
  @FXML Label totalLabel;
  @FXML MFXButton backButton;
  @FXML VBox cartBox;
  @FXML AnchorPane summaryPane;
  @FXML MFXButton ViewButton;
  @FXML DatePicker dateBox;
  @FXML MFXButton refreshButton;
  private double totalPrice = 0.0;

  @FXML ComboBox<RequestType> requestTypeCombo;
  @FXML ComboBox<String> requestStaffCombo;
  //  ObservableList<String> serviceType =
  //      FXCollections.observableArrayList(
  //          "", "Meal Request", "Flower Request", "Furniture Request", "Office Supply Request");
  ObservableList<String> statusValue =
      FXCollections.observableArrayList("", "PROCESSING", "BLANK", "DONE");
  @FXML ComboBox<Status> requestStatusCombo;

  private static String room;

  /**
   * filters the list of service requests to add it to the table
   *
   * @param one the request type that we want to see
   * @param two the status that we want to see
   * @return the list of filtered items
   * @throws SQLException if there is an error when connecting to the database
   */
  public static ObservableList<ServiceRequest> tableFilter(
      RequestType one, Status two, Timestamp date, String username) throws SQLException {
    ObservableList<ServiceRequest> requestList =
        FXCollections.observableList(DataManager.getAllServiceRequests());
    if (!(one == (null)) && !(one.toString().equals(""))) {
      requestList =
          FXCollections.observableList(
              requestList.stream()
                  .filter((request) -> request.getRequestType().toString().equals(one.toString()))
                  .toList());
    }
    if (!(two == (null)) && !(two.toString().equals(""))) {
      requestList =
          FXCollections.observableList(
              requestList.stream()
                  .filter((request) -> request.getStatus().toString().equals(two.toString()))
                  .toList());
    }
    try {
      if (!(date == (null))) {
        requestList =
            FXCollections.observableList(
                requestList.stream()
                    .filter(
                        (request) ->
                            (request.getDeliverBy().getDate() == date.getDate())
                                && (request.getDeliverBy().getMonth() == date.getMonth()))
                    .toList());
      }
    } catch (NullPointerException e) {
      System.out.println("No Date");
    }
    if (!(username == (null)) && !(username.toString().equals(""))) {
      requestList =
          FXCollections.observableList(
              requestList.stream()
                  .filter((request) -> request.getStaffName().equals(username))
                  .toList());
    }
    if (!(room.equals(""))) {
      requestList =
          FXCollections.observableList(
              requestList.stream()
                  .filter((request) -> request.getRoomNumber().equals(room))
                  .toList());
    }
    return requestList;
  }

  /**
   * changes the language of the app
   *
   * @param lang language to it to
   * @throws SQLException when the datamanager throws one
   */
  public void setLanguage(Language lang) throws SQLException {

    switch (lang) {
      case ENGLISH:
        ParentController.titleString.set("Service Request View");
        filterTableLabel.setText("Filter Table");
        requestTypeLabel.setText("Request Type");
        requestTypeCombo.setPromptText("Choose Request Type");
        statusLabel.setText("Request Status");
        requestStatusCombo.setPromptText("Choose Status");
        assignedLabel.setText("Assigned Staff");
        requestStaffCombo.setPromptText("Choose Staff");
        dateLabel.setText("Date");
        refreshButton.setText("Refresh");
        assignStaffLabel.setText("Assign Staff to Request");
        requestIDLabel.setText("Request ID");
        requestIDText.setPromptText("Select Request ID");
        staffLabel.setText("Assign Staff");
        assignStaffText.setPromptText("Select Staff");
        setStatusLabel.setText("Set Request Status");
        requestStatusText.setPromptText("Select Request Status");
        backButton.setText("Back");
        submitButton.setText("Assign");
        ViewButton.setText("View");
        requestIDCol.setText("Request ID");
        patientNameCol.setText("Patient Name");
        roomNumCol.setText("Room #");
        requesterIDCol.setText("Requester ID");
        requestedAtCol.setText("Requested At");
        requestedForCol.setText("Requested For");
        assignedStaffCol.setText("Assigned Staff");
        statusCol.setText("Status");
        ViewButton.setText("View");
        break;
      case ITALIAN:
        ParentController.titleString.set("Vista Richiesta Servizio");
        filterTableLabel.setText("Filtrare la Tabella");
        requestTypeLabel.setText("Tipo di Richiesta");
        requestTypeCombo.setPromptText("Scegli Tipo di Richiesta");
        statusLabel.setText("Stato Richiesta");
        requestStatusCombo.setPromptText("Scegli Stato");
        assignedLabel.setText("Personale Assegnato");
        requestStaffCombo.setPromptText("Scegli Personale");
        dateLabel.setText("Data");
        refreshButton.setText("Aggiorna");
        assignStaffLabel.setText("Assegna Personale alla Richiesta");
        requestIDLabel.setText("ID Richiesta");
        requestIDText.setPromptText("Seleziona ID Richiesta");
        staffLabel.setText("Assegna Personale");
        assignStaffText.setPromptText("Seleziona Personale");
        setStatusLabel.setText("Imposta Stato Richiesta");
        requestStatusText.setPromptText("Seleziona Stato Richiesta");
        backButton.setText("Ritorna");
        submitButton.setText("Assegna");
        ViewButton.setText("Visualizza");
        requestIDCol.setText("ID Richiesta");
        patientNameCol.setText("Nome Paziente");
        roomNumCol.setText("Numero di Stanza");
        requesterIDCol.setText("ID Richiedente");
        requestedAtCol.setText("Richiesta Fatta il");
        requestedForCol.setText("Richiesta per");
        assignedStaffCol.setText("Personale Assegnato");
        statusCol.setText("Stato");
        ViewButton.setText("Visualizza");
        break;
      case FRENCH:
        ParentController.titleString.set("Vue de la Demande de Service");
        filterTableLabel.setText("Filtrer le Tableau");
        requestTypeLabel.setText("Type de Demande");
        requestTypeCombo.setPromptText("Choisir le Type de Demande");
        statusLabel.setText("Statut de la Demande");
        requestStatusCombo.setPromptText("Choisir le Statut");
        assignedLabel.setText("Personnel Assign" + GlobalVariables.getEAcute());
        requestStaffCombo.setPromptText("Choisir le Personnel");
        dateLabel.setText("Date");
        refreshButton.setText("Actualiser");
        assignStaffLabel.setText(
            "Assigner du Personnel " + GlobalVariables.getAGrave() + " la Demande");
        requestIDLabel.setText("ID de la Demande");
        requestIDText.setPromptText(
            "S" + GlobalVariables.getEAcute() + "lectionner l'ID de la Demande");
        staffLabel.setText("Assigner du Personnel");
        assignStaffText.setPromptText(
            "S" + GlobalVariables.getEAcute() + "lectionner du Personnel");
        setStatusLabel.setText("D" + GlobalVariables.getEAcute() + "finir le Statut de la Demande");
        requestStatusText.setPromptText(
            "S" + GlobalVariables.getEAcute() + "lectionner le Statut de la Demande");
        backButton.setText("Retour");
        submitButton.setText("Assigner");
        ViewButton.setText("Voir");
        requestIDCol.setText("ID de la Demande");
        patientNameCol.setText("Nom du Patient");
        roomNumCol.setText("Nombre de Chambre");
        requesterIDCol.setText("ID du Demandeur");
        requestedAtCol.setText("Demand" + GlobalVariables.getEAcute() + " le");
        requestedForCol.setText("Demand" + GlobalVariables.getEAcute() + " pour");
        assignedStaffCol.setText("Personnel Assigné");
        statusCol.setText("Statut");
        ViewButton.setText("Voir");
        break;
      case SPANISH:
        ParentController.titleString.set("Vista de la Solicitud de Servicio");
        filterTableLabel.setText("Filtrar Tabla");
        requestTypeLabel.setText("Tipo de Solicitud");
        requestTypeCombo.setPromptText("Elegir Tipo de Solicitud");
        statusLabel.setText("Estado de la Solicitud");
        requestStatusCombo.setPromptText("Elegir Estado");
        assignedLabel.setText("Personal Asignado");
        requestStaffCombo.setPromptText("Elegir Personal");
        dateLabel.setText("Fecha");
        refreshButton.setText("Actualizar");
        assignStaffLabel.setText("Asignar Personal a la Solicitud");
        requestIDLabel.setText("ID de la Solicitud");
        requestIDText.setPromptText("Seleccionar ID de la Solicitud");
        staffLabel.setText("Asignar Personal");
        assignStaffText.setPromptText("Seleccionar Personal");
        setStatusLabel.setText("Establecer Estado de la Solicitud");
        requestStatusText.setPromptText("Seleccionar Estado de la Solicitud");
        backButton.setText("Volver");
        submitButton.setText("Asignar");
        ViewButton.setText("Ver");
        requestIDCol.setText("ID de la Solicitud");
        patientNameCol.setText("Nombre del Paciente");
        roomNumCol.setText("Numero de Habitación");
        requesterIDCol.setText("ID del Solicitante");
        requestedAtCol.setText("Solicitado el");
        requestedForCol.setText("Solicitado para");
        assignedStaffCol.setText("Personal Asignado");
        statusCol.setText("Estado");
        ViewButton.setText("Ver");

        break;
    }
    //    assignStaffText.setValue(assignStaffText.getValue());
    if (assignStaffText.getValue() == null) {
      assignStaffText.setItems(FXCollections.observableList(DataManager.getAllUsernames()));
      assignStaffText.getSelectionModel().clearSelection();
    }
    if (requestStatusText.getValue() == null) {
      requestStatusText.getSelectionModel().clearSelection();
      ObservableList<Status> requestStatuses2 = FXCollections.observableArrayList(Status.values());
      requestStatusText.setItems(requestStatuses2);
    }
    if (requestIDText.getValue() == null) {
      requestIDText.getSelectionModel().clearSelection();
      requestIDText.setItems(FXCollections.observableList(DataManager.getAllRequestIDs()));
    }
    if (requestTypeCombo.getValue() == null) {
      requestTypeCombo.getSelectionModel().clearSelection();
      ObservableList<RequestType> requestTypes =
          FXCollections.observableArrayList(RequestType.values());
      requestTypes.add(null);
      requestTypeCombo.setItems(requestTypes);
    }
    if (requestStaffCombo.getValue() == null) {
      ObservableList<String> staffNames =
          FXCollections.observableArrayList(DataManager.getAllUsernames());
      staffNames.add(null);
      requestStaffCombo.setItems(staffNames);
      requestStaffCombo.getSelectionModel().clearSelection();
    }
    if (requestStatusCombo.getValue() == null) {
      ObservableList<Status> requestStatuses = FXCollections.observableArrayList(Status.values());
      requestStatuses.add(null);
      requestStatusCombo.setItems(requestStatuses);
      requestStatusCombo.getSelectionModel().clearSelection();
    }
  }
  /*
       * How to set the labels for everything we want translated
  ParentController.titleString.set("Title");
      filterTableLabel.setText("A");
          requestTypeLabel.setText("B");
          requestTypeCombo.setPromptText("C");
          statusLabel.setText("D");
          requestStatusCombo.setPromptText("E");
          assignedLabel.setText("F");
          requestStaffCombo.setPromptText("G");
          dateLabel.setText("H");
          refreshButton.setText("I");
          assignStaffLabel.setText("J");
          requestIDLabel.setText("K");
          requestIDText.setPromptText("L");
          staffLabel.setText("M");
          assignStaffText.setPromptText("N");
          setStatusLabel.setText("O");
          requestStatusText.setPromptText("P");
          backButton.setText("Q");
          submitButton.setText("R");
          ViewButton.setText("S");
          requestIDCol.setText("T");
          patientNameCol.setText("U");
          roomNumCol.setText("V");
          requestIDCol.setText("W");
          requestedAtCol.setText("X");
          requestedForCol.setText("Y");
          assignedStaffCol.setText("Z");
          statusCol.setText("AA");
          requesterIDCol.setText("AB");
          ViewButton.setText("AC");
       */
  /*
  * List of Text that we need translated:
  *

  */

  /**
   * assigns a staff and status to a request
   *
   * @param id id we want to assign to
   * @param assignStaff staff we want to assign the request to
   * @param requestStatus status we want to assign the request to
   * @throws SQLException if there is an error connecting to the database
   */
  public void assignStuff(String id, String assignStaff, Status requestStatus) throws SQLException {
    DataManager.uploadStatusToServiceRequest(Integer.parseInt(id), requestStatus.getStatusString());
    DataManager.uploadStaffNameToServiceRequest(Integer.parseInt(id), assignStaff);
    requestIDText.setValue(null);
    assignStaffText.setValue(null);
    requestStatusText.setValue(null);
    refreshTable();
  }

  /**
   * Refreshes the service requests shown on the table
   *
   * @throws SQLException
   */
  public void refreshTable() throws SQLException {
    ObservableList<ServiceRequest> serviceRequests =
        FXCollections.observableList(DataManager.getAllServiceRequests());
    FilteredList<ServiceRequest> serviceRequests1 = new FilteredList<>(serviceRequests);
    //    serviceRequests1.predicateProperty().bind(table.predicateProperty());
    SortedList<ServiceRequest> sortedServiceReq = new SortedList<>(serviceRequests1);
    Timestamp date;
    try {
      date = Timestamp.valueOf(dateBox.getValue().atStartOfDay());
    } catch (NullPointerException e) {
      date = null;
    }
    table.setItems(
        tableFilter(
            requestTypeCombo.getValue(),
            requestStatusCombo.getValue(),
            date,
            requestStaffCombo.getValue()));
  }

  /**
   * initializes the serviceRequestView page
   *
   * @throws SQLException if there is an error connecting to the database
   */
  @FXML
  public void initialize() throws SQLException {
    room = "";
    ThemeSwitch.switchTheme(root);
    ParentController.titleString.set("Service Request View");
    setLanguage(GlobalVariables.getB().getValue());
    GlobalVariables.b.addListener(
        (options, oldValue, newValue) -> {
          try {
            setLanguage(newValue);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });

    submitButton.disableProperty().bind(Bindings.isNull(requestIDText.valueProperty()));
    submitButton.disableProperty().bind(Bindings.isNull(assignStaffText.valueProperty()));
    submitButton.disableProperty().bind(Bindings.isNull(requestStatusText.valueProperty()));

    submitButton.setOnMouseClicked(
        event -> {
          Sound.playSFX(SFX.BUTTONCLICK);
          try {
            assignStuff(
                requestIDText.valueProperty().getValue(),
                assignStaffText.valueProperty().getValue(),
                requestStatusText.valueProperty().getValue());
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
    ObservableList<RequestType> requestTypes =
        FXCollections.observableArrayList(RequestType.values());
    requestTypes.add(null);
    requestTypeCombo.setItems(requestTypes);

    ObservableList<String> staffNames =
        FXCollections.observableArrayList(DataManager.getAllUsernames());
    staffNames.add(null);
    requestStaffCombo.setItems(staffNames);
    ObservableList<Status> requestStatuses = FXCollections.observableArrayList(Status.values());
    requestStatuses.add(null);
    requestStatusCombo.setItems(requestStatuses);

    requestIDText.setItems(FXCollections.observableList(DataManager.getAllRequestIDs()));
    assignStaffText.setItems(FXCollections.observableList(DataManager.getAllUsernames()));
    ObservableList<Status> requestStatuses2 = FXCollections.observableArrayList(Status.values());

    requestStatusText.setItems(requestStatuses2);

    ObservableList<ServiceRequest> serviceRequests =
        FXCollections.observableList(DataManager.getAllServiceRequests());
    FilteredList<ServiceRequest> serviceRequests1 = new FilteredList<>(serviceRequests);
    //    serviceRequests1.predicateProperty().bind(table.predicateProperty());
    SortedList<ServiceRequest> sortedServiceReq = new SortedList<>(serviceRequests1);
    requestIDCol.setCellValueFactory(new PropertyValueFactory<ServiceRequest, String>("requestID"));
    roomNumCol.setCellValueFactory(new PropertyValueFactory<ServiceRequest, String>("roomNumber"));
    assignedStaffCol.setCellValueFactory(
        new PropertyValueFactory<ServiceRequest, String>("staffName"));
    patientNameCol.setCellValueFactory(
        new PropertyValueFactory<ServiceRequest, String>("patientName"));
    requestedAtCol.setCellValueFactory(
        new PropertyValueFactory<ServiceRequest, String>("requestedAt"));
    requestedForCol.setCellValueFactory(
        new PropertyValueFactory<ServiceRequest, String>("deliverBy"));
    statusCol.setCellValueFactory(new PropertyValueFactory<ServiceRequest, String>("status"));
    requesterIDCol.setCellValueFactory(
        new PropertyValueFactory<ServiceRequest, String>("requestMadeBy"));

    requestStatusCombo.setOnAction(
        event -> {
          try {
            Timestamp date;
            try {
              date = Timestamp.valueOf(dateBox.getValue().atStartOfDay());
            } catch (NullPointerException e) {
              date = null;
            }
            // update the table when the status combo box is changed
            table.setItems(
                tableFilter(
                    requestTypeCombo.getValue(),
                    requestStatusCombo.getValue(),
                    date,
                    requestStaffCombo.getValue()));
          } catch (SQLException e) {
            e.printStackTrace();
          }
        });

    dateBox.setOnAction(
        event -> {
          try {
            Timestamp date;
            try {
              date = Timestamp.valueOf(dateBox.getValue().atStartOfDay());
            } catch (NullPointerException e) {
              date = null;
            }
            // update the table when the status combo box is changed
            table.setItems(
                tableFilter(
                    requestTypeCombo.getValue(),
                    requestStatusCombo.getValue(),
                    date,
                    requestStaffCombo.getValue()));
          } catch (SQLException e) {
            e.printStackTrace();
          }
        });

    requestTypeCombo.setOnAction(
        event -> {
          try {
            Timestamp date;
            try {
              date = Timestamp.valueOf(dateBox.getValue().atStartOfDay());
            } catch (NullPointerException e) {
              date = null;
            }
            // update the table when the status combo box is changed
            table.setItems(
                tableFilter(
                    requestTypeCombo.getValue(),
                    requestStatusCombo.getValue(),
                    date,
                    requestStaffCombo.getValue()));
          } catch (SQLException e) {
            e.printStackTrace();
          }
        });

    refreshButton.setOnAction(
        event -> {
          dateBox.cancelEdit();
          dateBox.setValue(null);
          try {
            table.setItems(
                tableFilter(
                    requestTypeCombo.getValue(),
                    requestStatusCombo.getValue(),
                    null,
                    requestStaffCombo.getValue()));
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });

    requestStaffCombo.setOnAction(
        event -> {
          try {
            Timestamp date;
            try {
              date = Timestamp.valueOf(dateBox.getValue().atStartOfDay());
            } catch (NullPointerException e) {
              date = null;
            }
            // update the table when the staff combo box is changed
            table.setItems(
                tableFilter(
                    requestTypeCombo.getValue(),
                    requestStatusCombo.getValue(),
                    date,
                    requestStaffCombo.getValue()));
          } catch (SQLException e) {
            e.printStackTrace();
          }
        });

    table
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            new ChangeListener<ServiceRequest>() {
              @Override
              public void changed(
                  ObservableValue<? extends ServiceRequest> observable,
                  ServiceRequest oldValue,
                  ServiceRequest newValue) {

                table.setOnMouseClicked(
                    event -> {
                      ViewButton.setVisible(true);
                      ViewButton.setDisable(false);
                      ViewButton.setText("View Order #" + newValue.getRequestID());
                    });

                if (newValue != null) {
                  ViewButton.setOnMouseClicked(
                      event -> {
                        Sound.playSFX(SFX.BUTTONCLICK);
                        table.setVisible(false);
                        table.setDisable(true);
                        ViewButton.setVisible(false);
                        ViewButton.setDisable(true);
                        summaryPane.setVisible(true);
                        summaryPane.setDisable(false);
                        backButton.setVisible(true);
                        backButton.setDisable(false);
                        try {
                          System.out.println("Display");
                          String f = "";
                          int req = newValue.getRequestID();
                          totalPrice = 0.0;
                          fillPane(req, f);
                        } catch (SQLException e) {
                          System.out.println(e);
                        }
                      });
                }
              }
            });

    table.setItems(sortedServiceReq);

    backButton.setOnMouseClicked(
        event -> {
          Sound.playSFX(SFX.BUTTONCLICK);
          totalPrice = 0.0;
          System.out.println("Back " + totalPrice);
          table.setVisible(true);
          table.setDisable(false);
          summaryPane.setVisible(false);
          summaryPane.setDisable(true);
          backButton.setVisible(false);
          backButton.setDisable(true);
          ViewButton.setVisible(true);
          ViewButton.setDisable(false);
          cartBox.getChildren().clear();
        });
    if (GlobalVariables.isDoneRequestsPressed()) {
      GlobalVariables.setDoneRequestsPressed(false);
      requestStatusCombo.setValue(Status.DONE);
      requestStaffCombo.setValue(GlobalVariables.getCurrentUser().getUsername());
      Timestamp date;
      try {
        date = Timestamp.valueOf(dateBox.getValue().atStartOfDay());
      } catch (NullPointerException e) {
        date = null;
      }
      table.setItems(
          tableFilter(
              requestTypeCombo.getValue(),
              requestStatusCombo.getValue(),
              date,
              requestStaffCombo.getValue()));
    } else if (GlobalVariables.isActiveRequestsPressed()) {
      requestStatusCombo.setValue(Status.PROCESSING);
      requestStaffCombo.setValue(GlobalVariables.getCurrentUser().getUsername());
      GlobalVariables.setActiveRequestsPressed(false);
      Timestamp date;
      try {
        date = Timestamp.valueOf(dateBox.getValue().atStartOfDay());
      } catch (NullPointerException e) {
        date = null;
      }
      table.setItems(
          tableFilter(
              requestTypeCombo.getValue(),
              requestStatusCombo.getValue(),
              date,
              requestStaffCombo.getValue()));
    } else if (GlobalVariables.isRequestFromMap()) {
      room = GlobalVariables.getRoomFromMap();
      dateBox.setValue(GlobalVariables.getDateFromMap().toLocalDateTime().toLocalDate());
      table.setItems(
          tableFilter(
              requestTypeCombo.getValue(),
              requestStatusCombo.getValue(),
              Timestamp.valueOf(dateBox.getValue().atStartOfDay()),
              requestStaffCombo.getValue()));
      GlobalVariables.setRequestFromMap(false);
    }
  }

  /**
   * fills the pane of the cart view of an individual service request
   *
   * @param reqID request ID number to retrieve all the ordered items from
   * @param folder folder to get the images for a specific service request type
   * @throws SQLException
   */
  private void fillPane(int reqID, String folder) throws SQLException {
    ServiceRequest request = DataManager.getServiceRequest(reqID);
    ArrayList<ItemsOrdered> orderedItems = new ArrayList<>();
    ArrayList<RequestItem> tempItems = new ArrayList<>();
    orderedItems = DataManager.getItemsFromReq(reqID);
    for (int i = 0; i < orderedItems.size(); i++) {
      ItemsOrdered item = orderedItems.get(i);
      if (item.getItemID() / 100 == 10) { // flower
        folder = "FlowerIcons";
        tempItems.add(DataManager.getFlower(item.getItemID()));
      } else if (item.getItemID() / 100 == 11) { // meal
        folder = "MealIcons";
        tempItems.add(DataManager.getMeal(item.getItemID()));
      } else if (item.getItemID() / 100 == 13) { // furniture
        folder = "FurnitureIcons";
        tempItems.add(DataManager.getFurniture(item.getItemID()));
      } else if (item.getItemID() / 100 == 14) { // office supply
        folder = "OfficeIcons";
        tempItems.add(DataManager.getOfficeSupply(item.getItemID()));
      } else if (item.getItemID() / 100 == 15) { // medical Supply
        folder = "MedicalIcons";
        tempItems.add(DataManager.getMedicalSupply(item.getItemID()));
      } else if (item.getItemID() / 100 == 12) { // pharmacuedical
        folder = "PharmaceuticalIcons";
        tempItems.add(DataManager.getPharmaceutical(item.getItemID()));
      }
      try {
        tempItems.get(i).getItemID();
        totalPrice += (orderedItems.get(i).getQuantity() * tempItems.get(i).getPrice());
        cartBox.getChildren().add(new ReqMenuItems(tempItems.get(i), folder, item.getQuantity()));
      } catch (NullPointerException e) { // no items in request
        System.out.println(e);
        totalPrice = 0.0;
      }
    }

    String details = request.getDetails();
    int d = details.indexOf("Deliver");
    detailsLabel.setText(details.substring(0, d)); // cut string at Deliver by
    detailsLabel1.setText(details.substring(d) + "   Status: " + request.getStatus());
    DecimalFormat format = new DecimalFormat("###0.00");
    totalLabel.setText("Order Total: $" + format.format(totalPrice));
  }
}
