package edu.wpi.teamname.controllers;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.servicerequest.RequestType;
import edu.wpi.teamname.servicerequest.ServiceRequest;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
  @FXML TextField requestIDText;
  @FXML TextField assignStaffText;
  @FXML TextField requestStatusText;

  @FXML MFXButton submitButton;

  @FXML ComboBox<RequestType> requestTypeCombo;

  ObservableList<String> serviceType =
      FXCollections.observableArrayList(
          "", "Meal Request", "Flower Request", "Furniture Request", "Office Supply Request");
  ObservableList<String> statusValue =
      FXCollections.observableArrayList("", "PROCESSING", "BLANK", "DONE");
  @FXML ComboBox requestStatusCombo;

  public static ObservableList<ServiceRequest> tableFilter(
      RequestType requestType, String statusString) throws SQLException {
    List<ServiceRequest> requests = DataManager.getAllServiceRequests();

    if (requestType != null && !requestType.toString().isEmpty()) {
      requests =
          requests.stream()
              .filter(request -> request.getRequestType().toString().equals(requestType.toString()))
              .toList();
    }

    if (statusString != null && !statusString.isEmpty()) {
      requests =
          requests.stream()
              .filter(request -> request.getStatus().getStatusString().equals(statusString))
              .toList();
    }

    return FXCollections.observableArrayList(requests);
  }

  //  public static ObservableList<ServiceRequest> tableFilter(RequestType one, String two)
  //      throws SQLException {
  //    if (one.equals(null) || one.toString().equals("")) {
  //      return filterByStatus(two);
  //    } else {
  //      if (two.equals(null) || two.equals("")) {
  //        return filterByRequestType(one);
  //      } else {
  //        return (FXCollections.observableArrayList(
  //            DataManager.getAllServiceRequests().stream()
  //                .filter((request) -> request.getStatus().toString().equals(two.toString()))
  //                .filter((request) -> request.getRequestType().toString().equals(one.toString()))
  //                .toList()));
  //      }
  //    }
  //  }
  //
  //  public static ObservableList<ServiceRequest> filterByStatus(String s) throws SQLException {
  //    if (s.equals(null) || s.equals("")) {
  //      return FXCollections.observableList(DataManager.getAllServiceRequests());
  //    } else {
  //      return (FXCollections.observableArrayList(
  //          DataManager.getAllServiceRequests().stream()
  //              .filter((request) -> request.getStatus().getStatusString().equals(s))
  //              .toList()));
  //    }
  //  }
  //
  //  public static ObservableList<ServiceRequest> filterByRequestType(RequestType r)
  //      throws SQLException {
  //    if (r.equals(null) || r.toString().equals("")) {
  //      return FXCollections.observableList(DataManager.getAllServiceRequests());
  //    }
  //    return (FXCollections.observableArrayList(
  //        DataManager.getAllServiceRequests().stream()
  //            .filter((request) -> request.getRequestType().getString().equals(r.getString()))
  //            .toList()));
  //  }

  public void assignStuff(String id, String assignStaff, String requestStatus) throws SQLException {
    DataManager.uploadStatusToServiceRequest(Integer.parseInt(id), requestStatus);
    DataManager.uploadStaffNameToServiceRequest(Integer.parseInt(id), assignStaff);
  }

  @FXML
  public void initialize() throws SQLException {
    ParentController.titleString.set("Service Request View");
    submitButton.disableProperty().bind(Bindings.isEmpty(requestIDText.textProperty()));
    submitButton.disableProperty().bind(Bindings.isEmpty(assignStaffText.textProperty()));
    submitButton.disableProperty().bind(Bindings.isEmpty(requestStatusText.textProperty()));

    submitButton.setOnMouseClicked(
        event -> {
          try {
            assignStuff(
                requestIDText.getText(), assignStaffText.getText(), requestStatusText.getText());
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });

    requestTypeCombo.setItems(FXCollections.observableArrayList(RequestType.values()));
    requestStatusCombo.setItems(statusValue);

    // add an event listener to the status combo box
    requestStatusCombo.setOnAction(
        event -> {
          try {
            table.setItems(
                tableFilter(requestTypeCombo.getValue(), (String) requestStatusCombo.getValue()));
          } catch (SQLException e) {
            e.printStackTrace();
          }
        });

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
  }
  //    ParentController.titleString.set("Service Request View");
  //
  //    requestTypeCombo.setItems(FXCollections.observableArrayList(RequestType.values()));
  //    requestStatusCombo.setItems(statusValue);
  //
  //    ObservableList<ServiceRequest> serviceRequests =
  //        FXCollections.observableList(DataManager.getAllServiceRequests());
  //    FilteredList<ServiceRequest> serviceRequests1 = new FilteredList<>(serviceRequests);
  //    serviceRequests1.predicateProperty().bind(table.predicateProperty());
  //    SortedList<ServiceRequest> sortedServiceReq = new SortedList<>(serviceRequests1);
  //    table.setItems(sortedServiceReq);
  //    requestIDCol.setCellValueFactory(new PropertyValueFactory<ServiceRequest,
  // String>("requestID"));
  //    roomNumCol.setCellValueFactory(new PropertyValueFactory<ServiceRequest,
  // String>("roomNumber"));
  //    assignedStaffCol.setCellValueFactory(
  //        new PropertyValueFactory<ServiceRequest, String>("staffName"));
  //    patientNameCol.setCellValueFactory(
  //        new PropertyValueFactory<ServiceRequest, String>("patientName"));
  //    requestedAtCol.setCellValueFactory(
  //        new PropertyValueFactory<ServiceRequest, String>("requestedAt"));
  //    requestedForCol.setCellValueFactory(
  //        new PropertyValueFactory<ServiceRequest, String>("deliverBy"));
  //    statusCol.setCellValueFactory(new PropertyValueFactory<ServiceRequest, String>("status"));
  //    requesterIDCol.setCellValueFactory(
  //        new PropertyValueFactory<ServiceRequest, String>("requestMadeBy"));
  //
  //    // create the ComboBoxes
  //    ComboBox<RequestType> requestTypeComboBox =
  //        new ComboBox<>(FXCollections.observableArrayList(RequestType.values()));
  //    ComboBox<String> statusComboBox =
  //        new ComboBox<>(FXCollections.observableArrayList("Open", "In Progress", "Closed"));
  //
  //    // create the properties for the ComboBoxes
  //    ObjectProperty<RequestType> requestTypeProperty = requestTypeComboBox.valueProperty();
  //    ObjectProperty<String> statusProperty = statusComboBox.valueProperty();
  //
  //    // add listeners to the properties
  //    requestTypeProperty.addListener(
  //        (observable, oldValue, newValue) -> {
  //          ObservableList<ServiceRequest> filteredList = null;
  //          try {
  //            filteredList = tableFilter(newValue, statusProperty.get());
  //          } catch (SQLException e) {
  //            throw new RuntimeException(e);
  //          }
  //          table.setItems(filteredList);
  //        });
  //
  //    statusProperty.addListener(
  //        (observable, oldValue, newValue) -> {
  //          ObservableList<ServiceRequest> filteredList;
  //          try {
  //            filteredList = tableFilter(requestTypeProperty.get(), newValue);
  //          } catch (SQLException e) {
  //            throw new RuntimeException(e);
  //          }
  //          table.setItems(filteredList);
  //        });
  //
  //    requestStatusCombo
  //        .valueProperty()
  //        .addListener(
  //            ((observable, oldValue, newValue) -> {
  //              requestTypeCombo
  //                  .valueProperty()
  //                  .addListener(
  //                      ((observable1, oldValue1, newValue1) -> {
  //                        try {
  //                          table.setItems(tableFilter(newValue1, newValue.toString()));
  //                        } catch (SQLException e) {
  //                          throw new RuntimeException(e);
  //                        }
  //                      }));
  //            }));
  //    requestTypeCombo.valueProperty().addListener(
  //            (observable1, oldValue1, newValue1) -> {
  //              requestStatusCombo
  //                  .valueProperty()
  //                  .addListener(
  //                      (observable2, oldValue2, newValue2) -> {
  //                        if (!newValue1.equals(null)) {
  //                          try {
  //                            if (newValue1.equals("Meal Request")) {
  //                              table.setItems(
  //                                  FXCollections.observableList(
  //                                      DataManager.getAllServiceRequests().stream()
  //                                          .filter(
  //                                              (request) ->
  //                                                  request
  //                                                      .getStatus()
  //                                                      .getStatusString()
  //                                                      .equals(newValue2))
  //                                          .filter(
  //                                              (request) ->
  //
  // request.getRequestType().toString().equals(newValue1))
  //                                          .toList()));
  //                            } else if (newValue1.equals("Flower Request")) {
  //                              table.setItems(
  //                                  FXCollections.observableList(
  //                                      DataManager.getAllServiceRequests().stream()
  //                                          .filter(
  //                                              (request) ->
  //                                                  request
  //                                                      .getStatus()
  //                                                      .getStatusString()
  //                                                      .equals(newValue2))
  //                                          .filter(
  //                                              (request) ->
  //                                                  request
  //                                                      .getRequestType()
  //                                                      .equals(RequestType.FLOWER))
  //                                          .toList()));
  //                            } else if (newValue1.equals("Furniture Request")) {
  //                              table.setItems(
  //                                  FXCollections.observableList(
  //                                      DataManager.getAllServiceRequests().stream()
  //                                          .filter(
  //                                              (request) ->
  //                                                  request
  //                                                      .getStatus()
  //                                                      .getStatusString()
  //                                                      .equals(newValue2))
  //                                          .filter(
  //                                              (request) ->
  //                                                  request
  //                                                      .getRequestType()
  //                                                      .equals(RequestType.FURNITURE))
  //                                          .toList()));
  //                            } else if (newValue1.equals("Office Supply Request")) {
  //                              table.setItems(
  //                                  FXCollections.observableList(
  //                                      DataManager.getAllServiceRequests().stream()
  //                                          .filter(
  //                                              (request) ->
  //                                                  request
  //                                                      .getStatus()
  //                                                      .getStatusString()
  //                                                      .equals(newValue2))
  //                                          .filter(
  //                                              (request) ->
  //                                                  request
  //                                                      .getRequestType()
  //                                                      .equals(RequestType.OFFICESUPPLY))
  //                                          .toList()));
  //                            } else if (newValue1.equals("")) {
  //                              table.setItems(
  //                                  FXCollections.observableList(
  //                                      DataManager.getAllServiceRequests()));
  //                            }
  //                          } catch (SQLException e) {
  //                            throw new RuntimeException(e);
  //                          }
  //                        }
  //                      });
  //            });

  //    requestStatusCombo
  //        .valueProperty()
  //        .addListener(
  //            (observable, oldValue, newValue) -> {
  //              if (!newValue.equals(null)) {
  //                try {
  //                  if (newValue.equals("")) {
  //                    table.setItems(sortedServiceReq);
  //                  } else {
  //                    table.setItems(
  //                        FXCollections.observableList(
  //                            DataManager.getAllServiceRequests().stream()
  //                                .filter(
  //                                    (request) ->
  //
  // request.getStatus().getStatusString().equals(newValue))
  //                                .toList()));
  //                  }
  //                } catch (SQLException e) {
  //                  throw new RuntimeException(e);
  //                }
  //              }
  //
  //            });
}
