package edu.wpi.teamname.controllers;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.servicerequest.RequestType;
import edu.wpi.teamname.servicerequest.ServiceRequest;
import edu.wpi.teamname.servicerequest.Status;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.control.tableview2.FilteredTableColumn;
import org.controlsfx.control.tableview2.FilteredTableView;

public class ServiceRequestViewController {

  @FXML FilteredTableView<ServiceRequest> table;
  @FXML FilteredTableColumn requestIDCol;
  @FXML FilteredTableColumn patientNameCol;
  @FXML FilteredTableColumn roomNumCol;
  @FXML FilteredTableColumn requesterIDCol;
  @FXML FilteredTableColumn requestedAtCol;
  @FXML FilteredTableColumn requestedForCol;
  @FXML FilteredTableColumn assignedStaffCol;
  @FXML FilteredTableColumn statusCol;
  @FXML SearchableComboBox<String> requestIDText;
  @FXML SearchableComboBox<String> assignStaffText;
  @FXML ComboBox<Status> requestStatusText;

  @FXML MFXButton submitButton;

  @FXML ComboBox<RequestType> requestTypeCombo;

  //  ObservableList<String> serviceType =
  //      FXCollections.observableArrayList(
  //          "", "Meal Request", "Flower Request", "Furniture Request", "Office Supply Request");
  ObservableList<String> statusValue =
      FXCollections.observableArrayList("", "PROCESSING", "BLANK", "DONE");
  @FXML ComboBox requestStatusCombo;

  /**
   * filters the list of service requests to add it to the table
   *
   * @param one the request type that we want to see
   * @param two the status that we want to see
   * @return the list of filtered items
   * @throws SQLException if there is an error when connecting to the database
   */
  public static ObservableList<ServiceRequest> tableFilter(RequestType one, String two)
      throws SQLException {
    ObservableList<ServiceRequest> requestList =
        FXCollections.observableList(DataManager.getAllServiceRequests());
    if (!one.equals(null) && !(one.toString().equals(""))) {
      requestList =
          FXCollections.observableList(
              requestList.stream()
                  .filter((request) -> request.getRequestType().toString().equals(one.toString()))
                  .toList());
    }
    if (!(two.equals(null)) && !(two.equals(""))) {
      requestList =
          FXCollections.observableList(
              requestList.stream()
                  .filter((request) -> request.getStatus().getStatusString().equals(two))
                  .toList());
    }
    return requestList;
  }

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

  public void refreshTable() throws SQLException {
    ObservableList<ServiceRequest> serviceRequests =
        FXCollections.observableList(DataManager.getAllServiceRequests());
    FilteredList<ServiceRequest> serviceRequests1 = new FilteredList<>(serviceRequests);
    serviceRequests1.predicateProperty().bind(table.predicateProperty());
    SortedList<ServiceRequest> sortedServiceReq = new SortedList<>(serviceRequests1);
    table.setItems(sortedServiceReq);
  }

  /**
   * initializes the serviceRequestView page
   *
   * @throws SQLException if there is an error connecting to the database
   */
  @FXML
  public void initialize() throws SQLException {
    ParentController.titleString.set("Service Request View");
    submitButton.disableProperty().bind(Bindings.isNull(requestIDText.valueProperty()));
    submitButton.disableProperty().bind(Bindings.isNull(assignStaffText.valueProperty()));
    submitButton.disableProperty().bind(Bindings.isNull(requestStatusText.valueProperty()));

    submitButton.setOnMouseClicked(
        event -> {
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
    serviceRequests1.predicateProperty().bind(table.predicateProperty());
    SortedList<ServiceRequest> sortedServiceReq = new SortedList<>(serviceRequests1);
    table.setItems(sortedServiceReq);
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
            // update the table when the status combo box is changed
            table.setItems(
                tableFilter(requestTypeCombo.getValue(), requestStatusCombo.getValue().toString()));
          } catch (SQLException e) {
            e.printStackTrace();
          }
        });

    requestTypeCombo.setOnAction(
        event -> {
          try {
            // update the table when the status combo box is changed
            table.setItems(
                tableFilter(requestTypeCombo.getValue(), requestStatusCombo.getValue().toString()));
          } catch (SQLException e) {
            e.printStackTrace();
          }
        });

    requestStatusCombo
        .valueProperty()
        .addListener(
            ((observable, oldValue, one) -> {
              if (!one.equals(null) && !(one.toString().equals(""))) {
                try {
                  table.setItems(
                      FXCollections.observableList(
                          DataManager.getAllServiceRequests().stream()
                              .filter(
                                  (request) ->
                                      request.getStatus().getStatusString().equals(one.toString()))
                              .toList()));
                } catch (SQLException e) {
                  throw new RuntimeException(e);
                }
              }
            }));
    requestTypeCombo
        .valueProperty()
        .addListener(
            ((observable, oldValue, one) -> {
              if (!one.equals(null) && !(one.toString().equals(""))) {
                try {
                  table.setItems(
                      FXCollections.observableList(
                          DataManager.getAllServiceRequests().stream()
                              .filter(
                                  (request) ->
                                      request.getRequestType().toString().equals(one.toString()))
                              .toList()));
                } catch (SQLException e) {
                  throw new RuntimeException(e);
                }
              }
            }));
  }
}
