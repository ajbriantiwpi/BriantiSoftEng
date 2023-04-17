package edu.wpi.teamname.controllers;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.employees.Employee;
import edu.wpi.teamname.employees.EmployeeType;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.util.converter.IntegerStringConverter;

public class EmployeeTableController {
  // TODO
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

    TableColumn<Employee, String> employeeTypeColumn = new TableColumn<>("Employee Type");
    employeeTypeColumn.setCellValueFactory(
        cellData -> {
          Employee employee = cellData.getValue();
          if (employee.getType() != null) {
            StringBuilder typeNames = new StringBuilder();
            for (EmployeeType type : employee.getType()) {
              if (typeNames.length() > 0) {
                typeNames.append(", ");
              }
              typeNames.append(type.toString());
            }
            return new SimpleStringProperty(typeNames.toString());
          } else {
            return new SimpleStringProperty("");
          }
        });

    TableColumn<Employee, String> userColumn = new TableColumn<>("Username");
    userColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

    TableColumn<Employee, String> passColumn = new TableColumn<>("Password");
    passColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
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
      ArrayList<Employee> employees = employeeDAO.getAllEmployeesWithType();
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
    exportButton.setOnAction(
        event -> {
          FileChooser fileChooser = new FileChooser();
          fileChooser.setTitle("Save CSV File");
          fileChooser.setInitialFileName("employees.csv");
          fileChooser
              .getExtensionFilters()
              .addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
          File file = fileChooser.showSaveDialog(exportButton.getScene().getWindow());
          if (file != null) {
            try {
              employeeDAO.exportEmployeeToCSV(file.getAbsolutePath());
            } catch (SQLException | IOException e) {
              System.err.println("Error exporting data to CSV file: " + e.getMessage());
            }
          }
        });
    importButton.setOnAction(
        event -> {
          FileChooser fileChooser = new FileChooser();
          fileChooser.setTitle("Select CSV File");
          fileChooser
              .getExtensionFilters()
              .addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
          File file = fileChooser.showOpenDialog(importButton.getScene().getWindow());
          if (file != null) {
            try {
              employeeDAO.uploadEmployee(file.getAbsolutePath());
              // refresh table view
              employeeTable.getItems().clear();
              employeeTable.getItems().addAll(employeeDAO.getAllEmployeesWithType());
            } catch (SQLException e) {
              System.err.println("Error uploading data to database: " + e.getMessage());
            } catch (ParseException e) {
              throw new RuntimeException(e);
            }
          }
        });
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
    employeeTable.addEventFilter(
        KeyEvent.KEY_PRESSED,
        event -> {
          if (event.getCode() == KeyCode.DELETE) {
            deleteSelectedEmployee();
          }
        });
    searchEmployee
        .textProperty()
        .addListener((observable, oldValue, newValue) -> filterTable(newValue));
  }

  private void deleteSelectedEmployee() {
    DataManager employeeDAO = new DataManager();
    Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
    if (selectedEmployee != null) {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Delete Employee");
      alert.setHeaderText("Are you sure you want to delete this employee?");
      alert.setContentText(
          "Employee ID: "
              + selectedEmployee.getEmployeeID()
              + "\nFirst Name: "
              + selectedEmployee.getFirstName()
              + "\nLast Name: "
              + selectedEmployee.getLastName()
              + "\nUsername: "
              + selectedEmployee.getUsername());

      Optional<ButtonType> result = alert.showAndWait();
      if (result.isPresent() && result.get() == ButtonType.OK) {
        try {
          employeeDAO.deleteEmployee(selectedEmployee);
          employeeDAO.deleteEmployeeType(selectedEmployee.getUsername());
          employeeTable.getItems().remove(selectedEmployee);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
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

  private void filterTable(String searchText) {
    DataManager employeeDAO = new DataManager();
    if (searchText == null || searchText.isEmpty()) {
      // Show all employees when search text is empty
      try {
        ArrayList<Employee> employees = employeeDAO.getAllEmployees();
        employeeTable.setItems(FXCollections.observableArrayList(employees));
      } catch (SQLException e) {
        System.err.println("Error getting employees from database: " + e.getMessage());
      }
    } else {
      ObservableList<Employee> allEmployees = employeeTable.getItems();
      ObservableList<Employee> filteredEmployees = FXCollections.observableArrayList();

      for (Employee employee : allEmployees) {
        if (String.valueOf(employee.getEmployeeID()).contains(searchText)
            || employee.getFirstName().toLowerCase().contains(searchText.toLowerCase())
            || employee.getLastName().toLowerCase().contains(searchText.toLowerCase())
            || employee.getUsername().toLowerCase().contains(searchText.toLowerCase())) {
          filteredEmployees.add(employee);
        }
      }

      employeeTable.setItems(filteredEmployees);
    }
  }
}
