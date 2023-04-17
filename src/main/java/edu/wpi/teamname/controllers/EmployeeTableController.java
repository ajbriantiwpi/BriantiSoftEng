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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

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
    employeeTable.setEditable(true);
    employeeIDColumn.setCellFactory(
        TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    // For EmployeeType TODO
    userColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    passColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    employeeIDColumn.setOnEditCommit(
        event -> {
          Employee employee = event.getRowValue();
          employee.setEmployeeID(event.getNewValue());
          try {
            employeeDAO.syncEmployee(employee);
          } catch (SQLException e) {
            System.err.println("Error updating employee in the database: " + e.getMessage());
          }
        });
    firstNameColumn.setOnEditCommit(
        event -> {
          Employee employee = event.getRowValue();
          employee.setFirstName(event.getNewValue());
          try {
            employeeDAO.syncEmployee(employee);
          } catch (SQLException e) {
            System.err.println("Error updating employee in the database: " + e.getMessage());
          }
        });

    lastNameColumn.setOnEditCommit(
        event -> {
          Employee employee = event.getRowValue();
          employee.setLastName(event.getNewValue());
          try {
            employeeDAO.syncEmployee(employee);
          } catch (SQLException e) {
            System.err.println("Error updating employee in the database: " + e.getMessage());
          }
        });
    // TODO Employee Type
    userColumn.setOnEditCommit(
        event -> {
          Employee employee = event.getRowValue();
          employee.setUsername(event.getNewValue());
          try {
            employeeDAO.syncEmployee(employee);
          } catch (SQLException e) {
            System.err.println("Error updating employee in the database: " + e.getMessage());
          }
        });

    passColumn.setOnEditCommit(
        event -> {
          Employee employee = event.getRowValue();
          employee.setPassword(event.getNewValue());
          try {
            employeeDAO.syncEmployee(employee);
          } catch (SQLException e) {
            System.err.println("Error updating employee in the database: " + e.getMessage());
          }
        });
  }

  @FXML
  private void handleSubmitButton() throws SQLException {
    DataManager employeeDAO = new DataManager();
    int employeeIDInput = Integer.parseInt(employeeIDField.getText());
    String firstName = employeeFirstNameTextField.getText();
    String lastName = employeeLastNameTextField.getText();
    String username = employeeUserTextField.getText();
    String password = employeePasswordTextField.getText();
    EmployeeType employeeType = EmployeeType.valueOf(employeeTypeTextField.getText());

    Employee employee =
        new Employee(username, password, employeeIDInput, firstName, lastName, true);

    employee.addType(employeeType);
    employeeDAO.addEmployeeType(username, employeeType);
    employeeDAO.addEmployee(employee);
    employeeTable.getItems().add(employee);

    // Clear the input fields
    employeeFirstNameTextField.clear();
    employeeIDField.clear();
    employeeLastNameTextField.clear();
    employeeUserTextField.clear();
    employeePasswordTextField.clear();
    employeeTypeTextField.clear();
  }
}
