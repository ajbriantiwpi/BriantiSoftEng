package edu.wpi.teamname.controllers;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.servicerequest.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import java.sql.Timestamp;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.SearchableComboBox;

public class ConfrenceViewController {

  @FXML TableView<ConfReservation> table;
  @FXML TableColumn resIDCol;
  @FXML TableColumn dateCol;
  @FXML TableColumn startCol;
  @FXML TableColumn endCol;
  @FXML TableColumn nameCol;
  @FXML TableColumn usernameCol;
  @FXML TableColumn assignedStaffCol;
  @FXML TableColumn madeCol;
  @FXML TableColumn roomCol;
  @FXML SearchableComboBox<Integer> reservationIDText;
  @FXML SearchableComboBox<String> assignStaffText;
  @FXML MFXButton submitButton;

  @FXML MFXButton refreshButton;

  private double totalPrice = 0.0;

  @FXML DatePicker dateBox;
  @FXML SearchableComboBox<String> requestStaffCombo;
  //  ObservableList<String> serviceType =
  //      FXCollections.observableArrayList(
  //          "", "Meal Request", "Flower Request", "Furniture Request", "Office Supply Request");
  ObservableList<String> statusValue =
      FXCollections.observableArrayList("", "PROCESSING", "BLANK", "DONE");

  /**
   * filters the list of service requests to add it to the table
   *
   * @param date date to be filtered to
   * @param username username of staff to filter to
   * @return the list of filtered items
   * @throws SQLException if there is an error when connecting to the database
   */
  public static ObservableList<ConfReservation> tableFilter(Timestamp date, String username)
      throws SQLException {
    ObservableList<ConfReservation> reservationList =
        FXCollections.observableList(DataManager.getAllConfReservation());
    if (!(date == (null))) {
      reservationList =
          FXCollections.observableList(
              reservationList.stream()
                  .filter((reservation) -> reservation.getDateBook().getDate() == date.getDate())
                  .toList());
    }
    if (!(username == (null)) && !(username.toString().equals(""))) {
      reservationList =
          FXCollections.observableList(
              reservationList.stream()
                  .filter((reservation) -> reservation.getStaffAssigned().equals(username))
                  .toList());
    }
    return reservationList;
  }

  /**
   * assigns a staff and status to a request
   *
   * @param id id we want to assign to
   * @param assignStaff staff we want to assign the request to
   * @throws SQLException if there is an error connecting to the database
   */
  public void assignStuff(String id, String assignStaff) throws SQLException {
    DataManager.uploadStaffNameToConferenceRequest(Integer.parseInt(id), assignStaff);
    reservationIDText.setValue(null);
    assignStaffText.setValue(null);
    refreshTable();
  }

  public void refreshTable() throws SQLException {
    ObservableList<ConfReservation> reservations =
        FXCollections.observableList(DataManager.getAllConfReservation());
    FilteredList<ConfReservation> serviceRequests1 = new FilteredList<>(reservations);
    //    serviceRequests1.predicateProperty().bind(table.predicateProperty());
    SortedList<ConfReservation> sortedServiceReq = new SortedList<>(serviceRequests1);
    table.setItems(sortedServiceReq);
  }

  /**
   * initializes the serviceRequestView page
   *
   * @throws SQLException if there is an error connecting to the database
   */
  @FXML
  public void initialize() throws SQLException {
    ParentController.titleString.set("Conference Room Reservations View");
    submitButton.disableProperty().bind(Bindings.isNull(reservationIDText.valueProperty()));
    submitButton.disableProperty().bind(Bindings.isNull(assignStaffText.valueProperty()));

    submitButton.setOnMouseClicked(
        event -> {
          try {
            assignStuff(
                String.valueOf(reservationIDText.valueProperty().getValue()),
                assignStaffText.valueProperty().getValue());
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });

    ObservableList<String> staffNames =
        FXCollections.observableArrayList(DataManager.getAllUsernames());
    staffNames.add(null);
    requestStaffCombo.setItems(staffNames);

    reservationIDText.setItems(
        FXCollections.observableList(DataManager.getAllConferenceRequestIDs()));
    assignStaffText.setItems(FXCollections.observableList(DataManager.getAllUsernames()));

    ObservableList<ConfReservation> reservations =
        FXCollections.observableList(DataManager.getAllConfReservation());
    FilteredList<ConfReservation> reservation1 = new FilteredList<>(reservations);
    //    serviceRequests1.predicateProperty().bind(table.predicateProperty());
    SortedList<ConfReservation> sortedRes = new SortedList<>(reservation1);
    resIDCol.setCellValueFactory(new PropertyValueFactory<ConfReservation, String>("resID"));
    startCol.setCellValueFactory(new PropertyValueFactory<ConfReservation, String>("startTime"));
    endCol.setCellValueFactory(new PropertyValueFactory<ConfReservation, String>("endTime"));
    dateCol.setCellValueFactory(new PropertyValueFactory<ConfReservation, String>("dateBook"));
    nameCol.setCellValueFactory(new PropertyValueFactory<ConfReservation, String>("name"));
    usernameCol.setCellValueFactory(new PropertyValueFactory<ConfReservation, String>("username"));
    assignedStaffCol.setCellValueFactory(
        new PropertyValueFactory<ConfReservation, String>("staffAssigned"));
    madeCol.setCellValueFactory(new PropertyValueFactory<ConfReservation, String>("dateMade"));
    roomCol.setCellValueFactory(new PropertyValueFactory<ConfReservation, String>("roomID"));

    dateBox.setOnAction(
        event -> {
          try {
            // update the table when the status combo box is changed
            table.setItems(
                tableFilter(
                    Timestamp.valueOf(dateBox.getValue().atStartOfDay()),
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
            table.setItems(tableFilter(null, requestStaffCombo.getValue()));
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });

    requestStaffCombo.setOnAction(
        event -> {
          try {
            // update the table when the status combo box is changed
            table.setItems(
                tableFilter(
                    Timestamp.valueOf(dateBox.getValue().atStartOfDay()),
                    requestStaffCombo.getValue()));
          } catch (SQLException e) {
            e.printStackTrace();
          }
        });

    //    table
    //        .getSelectionModel()
    //        .selectedItemProperty()
    //        .addListener(
    //            new ChangeListener<ServiceRequest>() {
    //              @Override
    //              public void changed(
    //                  ObservableValue<? extends ServiceRequest> observable,
    //                  ServiceRequest oldValue,
    //                  ServiceRequest newValue) {
    //
    //                table.setOnMouseClicked(
    //                    event -> {
    //                      ViewButton.setVisible(true);
    //                      ViewButton.setDisable(false);
    //                      ViewButton.setText("View Order #" + newValue.getRequestID());
    //                    });
    //
    //                if (newValue != null) {
    //                  ViewButton.setOnMouseClicked(
    //                      event -> {
    //                        table.setVisible(false);
    //                        table.setDisable(true);
    //                        ViewButton.setVisible(false);
    //                        ViewButton.setDisable(true);
    //                        summaryPane.setVisible(true);
    //                        summaryPane.setDisable(false);
    //                        backButton.setVisible(true);
    //                        backButton.setDisable(false);
    //                        try {
    //                          System.out.println("Display");
    //                          String f = "";
    //                          int req = newValue.getRequestID();
    //                          totalPrice = 0.0;
    //                          fillPane(req, f);
    //                        } catch (SQLException e) {
    //                          System.out.println(e);
    //                        }
    //                      });
    //                }
    //              }
    //            });

    table.setItems(sortedRes);

    //    backButton.setOnMouseClicked(
    //        event -> {
    //          totalPrice = 0.0;
    //          System.out.println("Back " + totalPrice);
    //          table.setVisible(true);
    //          table.setDisable(false);
    //          summaryPane.setVisible(false);
    //          summaryPane.setDisable(true);
    //          backButton.setVisible(false);
    //          backButton.setDisable(true);
    //          ViewButton.setVisible(true);
    //          ViewButton.setDisable(false);
    //          cartBox.getChildren().clear();
    //        });
    //    if (GlobalVariables.isDoneRequestsPressed()) {
    //      GlobalVariables.setDoneRequestsPressed(false);
    //      requestStatusCombo.setValue(Status.DONE);
    //      requestStaffCombo.setValue(GlobalVariables.getCurrentUser().getUsername());
    //      table.setItems(
    //          tableFilter(
    //              requestTypeCombo.getValue(),
    //              requestStatusCombo.getValue(),
    //              requestStaffCombo.getValue()));
    //    } else if (GlobalVariables.isActiveRequestsPressed()) {
    //      requestStatusCombo.setValue(Status.PROCESSING);
    //      requestStaffCombo.setValue(GlobalVariables.getCurrentUser().getUsername());
    //      GlobalVariables.setActiveRequestsPressed(false);
    //      table.setItems(
    //          tableFilter(
    //              requestTypeCombo.getValue(),
    //              requestStatusCombo.getValue(),
    //              requestStaffCombo.getValue()));
    //    }
    //  }

    //  private void fillPane(int reqID, String folder) throws SQLException {
    //    ServiceRequest request = DataManager.getServiceRequest(reqID);
    //    ArrayList<ItemsOrdered> orderedItems = new ArrayList<>();
    //    ArrayList<RequestItem> tempItems = new ArrayList<>();
    //    orderedItems = DataManager.getItemsFromReq(reqID);
    //    for (int i = 0; i < orderedItems.size(); i++) {
    //      ItemsOrdered item = orderedItems.get(i);
    //      if (item.getItemID() / 100 >= 10 && item.getItemID() / 100 < 11) { // flower
    //        folder = "FlowerIcons";
    //        tempItems.add(DataManager.getFlower(item.getItemID()));
    //      } else if (item.getItemID() / 100 >= 11 && item.getItemID() / 100 < 12) { // meal
    //        folder = "MealIcons";
    //        tempItems.add(DataManager.getMeal(item.getItemID()));
    //      } else if (item.getItemID() / 100 >= 13 && item.getItemID() / 100 < 14) { // furniture
    //        folder = "FurnitureIcons";
    //        tempItems.add(DataManager.getFurniture(item.getItemID()));
    //      } else if (item.getItemID() / 100 >= 14 && item.getItemID() / 100 < 15) { // office
    // supply
    //        folder = "OfficeIcons";
    //        tempItems.add(DataManager.getOfficeSupply(item.getItemID()));
    //      } else if (item.getItemID() / 100 >= 15 && item.getItemID() / 100 < 16) { // medical
    // Supply
    //        folder = "MedicalIcons";
    //        tempItems.add(DataManager.getMedicalSupply(item.getItemID()));
    //      }
    //      try {
    //        tempItems.get(i).getItemID();
    //        totalPrice += (orderedItems.get(i).getQuantity() * tempItems.get(i).getPrice());
    //        cartBox.getChildren().add(new ReqMenuItems(tempItems.get(i), folder,
    // item.getQuantity()));
    //      } catch (NullPointerException e) { // no items in request
    //        System.out.println(e);
    //        totalPrice = 0.0;
    //      }
    //    }
    //
    //    String details = request.getDetails();
    //    int d = details.indexOf("Deliver");
    //    detailsLabel.setText(details.substring(0, d)); // cut string at Deliver by
    //    detailsLabel1.setText(details.substring(d) + "   Status: " + request.getStatus());
    //    DecimalFormat format = new DecimalFormat("###0.00");
    //    totalLabel.setText("Order Total: $" + format.format(totalPrice));
  }
}
