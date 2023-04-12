package edu.wpi.teamname.controllers;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.servicerequest.RequestType;
import edu.wpi.teamname.servicerequest.ServiceRequest;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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
  @FXML ComboBox<RequestType> requestTypeCombo;

  ObservableList<String> serviceType =
      FXCollections.observableArrayList(
          "", "Meal Request", "Flower Request", "Furniture Request", "Office Supply Request");
  ObservableList<String> statusValue =
      FXCollections.observableArrayList("", "PROCESSING", "BLANK", "DONE");
  @FXML ComboBox requestStatusCombo;

  public static ObservableList<ServiceRequest> tableFilter(RequestType one, String two)
      throws SQLException {
    if (one.equals(null) || one.toString().equals("")) {
      return (FXCollections.observableArrayList(
          DataManager.getAllServiceRequests().stream()
              .filter((request) -> request.getStatus().getStatusString().equals(two))
              .toList()));
    } else {
      if (two.equals(null) || two.equals("")) {
        return (FXCollections.observableArrayList(
            DataManager.getAllServiceRequests().stream()
                .filter((request) -> request.getRequestType().toString().equals(one.toString()))
                .toList()));
      } else {
        return (FXCollections.observableArrayList(
            DataManager.getAllServiceRequests().stream()
                .filter((request) -> request.getStatus().toString().equals(two.toString()))
                .filter((request) -> request.getRequestType().toString().equals(one.toString()))
                .toList()));
      }
    }
  }

  @FXML
  public void initialize() throws SQLException {
    ParentController.titleString.set("Service Request View");

    requestTypeCombo.setItems(FXCollections.observableArrayList(RequestType.values()));
    requestStatusCombo.setItems(statusValue);

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

    requestStatusCombo
        .valueProperty()
        .addListener(
            ((observable, oldValue, newValue) -> {
              requestTypeCombo
                  .valueProperty()
                  .addListener(
                      ((observable1, oldValue1, newValue1) -> {
                        try {
                          table.setItems(tableFilter(newValue1, newValue.toString()));
                        } catch (SQLException e) {
                          throw new RuntimeException(e);
                        }
                      }));
            }));
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
}
