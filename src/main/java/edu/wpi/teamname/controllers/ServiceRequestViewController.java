package edu.wpi.teamname.controllers;

import edu.wpi.teamname.controllers.JFXitems.ReqMenuItems;
import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.servicerequest.ItemsOrdered;
import edu.wpi.teamname.servicerequest.RequestType;
import edu.wpi.teamname.servicerequest.ServiceRequest;
import edu.wpi.teamname.servicerequest.requestitem.RequestItem;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
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

  @FXML MFXButton switchButton;
  @FXML Label detailsLabel;
  @FXML Label totalLabel;
  @FXML MFXButton backButton;
  @FXML VBox cartBox;
  @FXML AnchorPane summaryPane;
  @Setter @Getter private ServiceRequest request;

  @FXML ComboBox<RequestType> requestTypeCombo;

  //  ObservableList<String> serviceType =
  //      FXCollections.observableArrayList(
  //          "", "Meal Request", "Flower Request", "Furniture Request", "Office Supply Request");
  ObservableList<String> statusValue =
      FXCollections.observableArrayList("", "PROCESSING", "BLANK", "DONE");
  @FXML ComboBox requestStatusCombo;

  //  public static ObservableList<ServiceRequest> tableFilter(
  //      RequestType requestType, String statusString) throws SQLException {
  //    List<ServiceRequest> requests = DataManager.getAllServiceRequests();
  //
  //    if (requestType != null && !requestType.toString().isEmpty()) {
  //      requests =
  //          requests.stream()
  //              .filter(request ->
  // request.getRequestType().toString().equals(requestType.toString()))
  //              .toList();
  //    }
  //
  //    if (statusString != null && !statusString.isEmpty()) {
  //      requests =
  //          requests.stream()
  //              .filter(request -> request.getStatus().getStatusString().equals(statusString))
  //              .toList();
  //    }
  //
  //    return FXCollections.observableArrayList(requests);
  //  }

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
  public void assignStuff(String id, String assignStaff, String requestStatus) throws SQLException {
    DataManager.uploadStatusToServiceRequest(Integer.parseInt(id), requestStatus);
    DataManager.uploadStaffNameToServiceRequest(Integer.parseInt(id), assignStaff);
  }

  /**
   * initializes the serviceRequestView page
   *
   * @throws SQLException if there is an error connecting to the database
   */
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

    backButton.setOnMouseClicked( // show service reqs
        event -> {
          table.setVisible(true);
          table.setDisable(false);
          summaryPane.setVisible(false);
          summaryPane.setDisable(true);
          cartBox.getChildren().clear();
        });
    switchButton.setOnMouseClicked( // show orders
        event -> {
          table.setVisible(false);
          table.setDisable(true);
          summaryPane.setVisible(true);
          summaryPane.setDisable(false);
          try {
            fillPane();
          } catch (SQLException e) {
            System.out.println(e);
          }
        });
  }

  private void fillPane() throws SQLException {
    String folder = "FlowerIcons";
    int reqID = 489118;
    ArrayList<ItemsOrdered> orderedItems = new ArrayList<>();
    ArrayList<RequestItem> tempItems = new ArrayList<>();
    orderedItems = DataManager.getItemsFromReq(reqID);
    for (int i = 0; i < orderedItems.size(); i++) {
      ItemsOrdered item = orderedItems.get(i);
      if (item.getItemID() / 100 >= 10 && item.getItemID() / 100 < 11) { // flower
        folder = "FlowerIcons";
        tempItems.add(DataManager.getFlower(item.getItemID()));
      } else if (item.getItemID() / 100 >= 11 && item.getItemID() / 100 < 12) { // meal
        folder = "MealIcons";
        tempItems.add(DataManager.getMeal(item.getItemID()));
      } else if (item.getItemID() / 100 >= 13 && item.getItemID() / 100 < 14) { // furniture
        folder = "FurnitureIcons";
        tempItems.add(DataManager.getFurniture(item.getItemID()));
      } else if (item.getItemID() / 100 >= 14 && item.getItemID() / 100 < 15) { // office supply
        folder = "OfficeIcons";
        tempItems.add(DataManager.getOfficeSupply(item.getItemID()));
      } else if (item.getItemID() / 100 >= 15 && item.getItemID() / 100 < 16) { // medical Supply
        folder = "MedicalIcons";
        tempItems.add(DataManager.getMedicalSupply(item.getItemID()));
      }
      cartBox.getChildren().add(new ReqMenuItems(tempItems.get(i), folder, item.getQuantity()));
    }
    //fill in detailsLabel
//        request.setPatientName();
//        request.setRequestMadeBy();
//        request.setStatus();
//        request.getRequestID();

        detailsLabel.setText(request.toString());
    //fill in totalLabel
  }
}
