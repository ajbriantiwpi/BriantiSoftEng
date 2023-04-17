package edu.wpi.teamname.controllers;

import edu.wpi.teamname.controllers.JFXitems.ReqMenuItems;
import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.servicerequest.ItemsOrdered;
import edu.wpi.teamname.servicerequest.RequestType;
import edu.wpi.teamname.servicerequest.ServiceRequest;
import edu.wpi.teamname.servicerequest.Status;
import edu.wpi.teamname.servicerequest.requestitem.RequestItem;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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

  @FXML Label detailsLabel;
  @FXML Label detailsLabel1;
  @FXML Label totalLabel;
  @FXML MFXButton backButton;
  @FXML VBox cartBox;
  @FXML AnchorPane summaryPane;
  @FXML MFXButton switchButton;

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

    backButton.setOnMouseClicked( // show service reqs
        event -> {
          table.setVisible(true);
          table.setDisable(false);
          summaryPane.setVisible(false);
          summaryPane.setDisable(true);
          cartBox.getChildren().clear();
        });

    // when table row is clicked do this
    table.setRowFactory(
        tv -> {
          TableRow<ServiceRequest> row = new TableRow<>();
          row.setOnMouseClicked(
              event -> {
                if (!row.isEmpty()
                    && event.getButton() == MouseButton.PRIMARY
                    && event.getClickCount() == 2) {
                  ServiceRequest clickedRow = row.getItem();
                  totalLabel.setText(totalLabel.getText());
                  System.out.println(totalLabel);
                  table.setVisible(false);
                  table.setDisable(true);
                  summaryPane.setVisible(true);
                  summaryPane.setDisable(false);
                  try {
                    String f = "";
                    int req = clickedRow.getRequestID();
                    fillPane(req, f);
                  } catch (SQLException e) {
                    System.out.println(e);
                  }
                }
              });
          return row;
        });

    // stub
    switchButton.setOnMouseClicked(
        event -> {
          totalLabel.setText(totalLabel.getText());
          table.setVisible(false);
          table.setDisable(true);
          summaryPane.setVisible(true);
          summaryPane.setDisable(false);
          try {

            String f = "FlowerIcons";
            int req = 489118;
            fillPane(req, f);
          } catch (SQLException e) {
            System.out.println(e);
          }
        });
  }

  private void fillPane(int reqID, String folder) throws SQLException {
    // int reqID = req.getRequestID();
    ServiceRequest request = DataManager.getServiceRequest(reqID);
    ArrayList<ItemsOrdered> orderedItems = new ArrayList<>();
    ArrayList<RequestItem> tempItems = new ArrayList<>();
    orderedItems = DataManager.getItemsFromReq(reqID);
    double totalPrice = 0.0;
    for (int i = 0; i < orderedItems.size(); i++) {
      ItemsOrdered item = orderedItems.get(i);
      if (item.getItemID() / 100 >= 10 && item.getItemID() / 100 < 11) { // flower
        folder = "FlowerIcons";
        tempItems.add(DataManager.getFlower(item.getItemID()));
        totalPrice += tempItems.get(i).getPrice();
      } else if (item.getItemID() / 100 >= 11 && item.getItemID() / 100 < 12) { // meal
        folder = "MealIcons";
        tempItems.add(DataManager.getMeal(item.getItemID()));
        totalPrice += tempItems.get(i).getPrice();
      } else if (item.getItemID() / 100 >= 13 && item.getItemID() / 100 < 14) { // furniture
        folder = "FurnitureIcons";
        tempItems.add(DataManager.getFurniture(item.getItemID()));
        totalPrice += tempItems.get(i).getPrice();
      } else if (item.getItemID() / 100 >= 14 && item.getItemID() / 100 < 15) { // office supply
        folder = "OfficeIcons";
        tempItems.add(DataManager.getOfficeSupply(item.getItemID()));
        totalPrice += tempItems.get(i).getPrice();
      } else if (item.getItemID() / 100 >= 15 && item.getItemID() / 100 < 16) { // medical Supply
        folder = "MedicalIcons";
        tempItems.add(DataManager.getMedicalSupply(item.getItemID()));
        totalPrice += tempItems.get(i).getPrice();
      }
      cartBox.getChildren().add(new ReqMenuItems(tempItems.get(i), folder, item.getQuantity()));
    }

    String details = request.getDetails();
    int d = details.indexOf("Deliver");
    detailsLabel.setText(details.substring(0, d)); // cut string at Deliver by
    detailsLabel1.setText(details.substring(d) + "   Status: " + request.getStatus());
    DecimalFormat format = new DecimalFormat("###.00");
    totalLabel.setText(totalLabel.getText() + format.format(totalPrice));
  }
}
