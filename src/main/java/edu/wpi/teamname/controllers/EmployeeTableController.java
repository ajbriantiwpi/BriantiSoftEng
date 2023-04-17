package edu.wpi.teamname.controllers;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.employees.Employee;
import edu.wpi.teamname.employees.EmployeeType;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class EmployeeTableController {
  // TODO
  // populate UI data
  // make table fields editable
  // make columns fill space
  // make submit and add work
  // implement employee search
  // create CSV method for splitting the data between CSV
  // Fix password encryption
  @FXML private TableView<Employee> employeeTable;
  @FXML private TextField employeeIDField;
  @FXML private TextField employeeFirstNameTextField;
  @FXML private TextField employeeLastNameTextField;
  @FXML private TextField employeeTypeTextField;
  @FXML private TextField employeeUserTextField;
  @FXML private Button exportButton;
  @FXML private Button importButton;
  @FXML private TextField searchEmployee;
  @FXML private TextField employeePasswordTextField;

  public void initialize() {
    ParentController.titleString.set("Employee Edit Table");
    TableColumn<Employee, Integer> employeeIDColumn = new TableColumn<>("Employee ID");
    employeeIDColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));

    TableColumn<Employee, String> firstNameColumn = new TableColumn<>("First Name");
    firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

    TableColumn<Employee, String> lastNameColumn = new TableColumn<>("Last Name");
    lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

    TableColumn<Employee, EmployeeType> employeeTypeColumn = new TableColumn<>("Employee Type");
    employeeTypeColumn.setCellValueFactory(new PropertyValueFactory<>("employeeType"));

    TableColumn<Employee, String> userColumn = new TableColumn<>("Username");
    userColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

    TableColumn<Employee, String> passColumn = new TableColumn<>("Password");
    passColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

    // set the cell factory for employeeTypeColumn to display the EmployeeType's type field
    employeeTypeColumn.setCellFactory(
        column ->
            new TableCell<Employee, EmployeeType>() {
              @Override
              protected void updateItem(EmployeeType item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                  setText(null);
                } else {
                  setText(item.getString());
                }
              }
            });

    employeeTable
        .getColumns()
        .addAll(
            employeeIDColumn,
            firstNameColumn,
            lastNameColumn,
            employeeTypeColumn,
            userColumn,
            passColumn);
    employeeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    DataManager employeeDAO = new DataManager();
    try {
      ArrayList<Employee> employees = employeeDAO.getAllEmployees();
      employeeTable.setItems(FXCollections.observableArrayList(employees));
    } catch (SQLException e) {
      System.err.println("Error getting employees from database: " + e.getMessage());
    }
  }
}
