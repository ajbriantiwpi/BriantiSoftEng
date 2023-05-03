package edu.wpi.teamname.controllers;

import edu.wpi.teamname.App;
import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.ThemeSwitch;
import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.employees.ClearanceLevel;
import edu.wpi.teamname.employees.Employee;
import edu.wpi.teamname.employees.EmployeeType;
import edu.wpi.teamname.extras.Language;
import edu.wpi.teamname.extras.SFX;
import edu.wpi.teamname.extras.Sound;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.controlsfx.control.SearchableComboBox;

/**
 * The EmployeeTableController class controls the behavior of the employee table in the user
 * interface.
 */
public class EmployeeTableController {
  @FXML AnchorPane root;
  @FXML private TableView<Employee> employeeTable;
  @FXML private TextField employeeIDField;
  @FXML private TextField employeeFirstNameTextField;
  @FXML private TextField employeeLastNameTextField;
  @FXML private SearchableComboBox employeeTypeText;
  @FXML private ComboBox employeeLevelText;
  @FXML private TextField employeeUserTextField;
  @FXML private Button exportButton;
  @FXML private Button importButton;
  @FXML private TextField searchEmployee;
  @FXML private TextField employeePasswordTextField;
  @FXML MFXButton submitButton;
  @FXML Label addEmployeeLabel;
  @FXML Label idLabel;
  @FXML Label firstNameLabel;
  @FXML Label lastNameLabel;
  @FXML Label clearanceLabel;
  @FXML Label typeLabel;
  @FXML Label usernameLabel;
  @FXML Label passwordLabel;
  @FXML Label csvLabel;
//  @FXML Label employeesLabel;
  @FXML Label searchLabel;

  public void setLanguage(
      Language lang,
      TableColumn id,
      TableColumn first,
      TableColumn last,
      TableColumn clearance,
      TableColumn type,
      TableColumn username,
      TableColumn password) {
    switch (lang) {
      case ENGLISH:
        ParentController.titleString.set("Employees");
        addEmployeeLabel.setText("Add Employee");
        idLabel.setText("Employee ID");
        firstNameLabel.setText("First Name");
        lastNameLabel.setText("Last Name");
        clearanceLabel.setText("Clearance Level;");
        typeLabel.setText("Employee Type");
        usernameLabel.setText("Username");
        passwordLabel.setText("Password");
        submitButton.setText("Submit");
        csvLabel.setText("CSV Manager");
        importButton.setText("Import");
        exportButton.setText("Export");
//        employeesLabel.setText("Employees");
        searchLabel.setText("Search");
        id.setText("Employee ID");
        first.setText("First Name");
        last.setText("Last Name");
        clearance.setText("Clearance Level");
        type.setText("Employee Type");
        username.setText("Username");
        password.setText("Password");
        break;
      case ITALIAN:
        ParentController.titleString.set("Dipendenti");
        addEmployeeLabel.setText("Aggiungi Dipendente");
        idLabel.setText("ID Dipendente");
        firstNameLabel.setText("Nome");
        lastNameLabel.setText("Cognome");
        clearanceLabel.setText("Livello di Accesso");
        typeLabel.setText("Tipo di Dipendente");
        usernameLabel.setText("Nome Utente");
        passwordLabel.setText("Password");
        submitButton.setText("Invia");
        csvLabel.setText("Gestore CSV");
        importButton.setText("Importa");
        exportButton.setText("Esporta");
//        employeesLabel.setText("Dipendenti");
        searchLabel.setText("Cerca");
        id.setText("ID Dipendente");
        first.setText("Nome");
        last.setText("Cognome");
        clearance.setText("Livello di Accesso");
        type.setText("Tipo di Dipendente");
        username.setText("Nome Utente");
        password.setText("Password");
        break;
      case FRENCH:
        ParentController.titleString.set("Employ" + GlobalVariables.getEAcute() + "s");
        addEmployeeLabel.setText("Ajouter un employ" + GlobalVariables.getEAcute());
        idLabel.setText("Identifiant de l'employ" + GlobalVariables.getEAcute());
        firstNameLabel.setText("Pr" + GlobalVariables.getEAcute() + "nom");
        lastNameLabel.setText("Nom de famille");
        clearanceLabel.setText("Niveau d'habilitation :");
        typeLabel.setText("Type d'employ" + GlobalVariables.getEAcute());
        usernameLabel.setText("Nom d'utilisateur");
        passwordLabel.setText("Mot de passe");
        submitButton.setText("Soumettre");
        csvLabel.setText("Gestionnaire CSV");
        importButton.setText("Importer");
        exportButton.setText("Exporter");

//        employeesLabel.setText("Employ" + GlobalVariables.getEAcute() + "s");

        searchLabel.setText("Rechercher");
        id.setText("Identifiant de l'employ" + GlobalVariables.getEAcute());
        first.setText("Pr" + GlobalVariables.getEAcute() + "nom");
        last.setText("Nom de famille");
        clearance.setText("Niveau d'habilitation");
        type.setText("Type d'employ" + GlobalVariables.getEAcute());
        username.setText("Nom d'utilisateur");
        password.setText("Mot de passe");
        break;
      case SPANISH:
        ParentController.titleString.set("Empleados");
        addEmployeeLabel.setText("Agregar Empleado");
        idLabel.setText("ID del Empleado");
        firstNameLabel.setText("Nombre");
        lastNameLabel.setText("Apellido");
        clearanceLabel.setText("Nivel de Autorizaci" + GlobalVariables.getOAcute() + "n");
        typeLabel.setText("Tipo de Empleado");
        usernameLabel.setText("Nombre de Usuario");
        passwordLabel.setText("Contrase" + GlobalVariables.getNTilda() + "a");
        submitButton.setText("Enviar");
        csvLabel.setText("Administrador CSV");
        importButton.setText("Importar");
        exportButton.setText("Exportar");
//        employeesLabel.setText("Empleados");
        searchLabel.setText("Buscar");
        id.setText("ID del Empleado");
        first.setText("Nombre");
        last.setText("Apellido");
        clearance.setText("Nivel de Autorizaci" + GlobalVariables.getOAcute() + "n");
        type.setText("Tipo de Empleado");
        username.setText("Nombre de Usuario");
        password.setText("Contrase" + GlobalVariables.getNTilda() + "a");
        break;
    }
  }

  /**
   * Initializes the employee table and sets up the event handlers for interacting with the table.
   */
  public void initialize() {
    ThemeSwitch.switchTheme(root);
    ObservableList<String> employeeTypes =
        FXCollections.observableArrayList(EmployeeType.formattedValues());
    employeeTypeText.setItems(employeeTypes);
    ObservableList<String> clearanceLevels =
        FXCollections.observableArrayList(ClearanceLevel.formattedValues());
    employeeLevelText.setItems(clearanceLevels);
    ParentController.titleString.set("Employees");
    TableColumn<Employee, Integer> employeeIDColumn = new TableColumn<>("Employee ID");
    employeeIDColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));

    TableColumn<Employee, String> firstNameColumn = new TableColumn<>("First Name");
    firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

    TableColumn<Employee, String> lastNameColumn = new TableColumn<>("Last Name");
    lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

    TableColumn<Employee, EmployeeType> employeeTypeColumn = new TableColumn<>("Employee Type");
    employeeTypeColumn.setCellValueFactory(
        cellData -> {
          Employee employee = cellData.getValue();
          if (employee.getType() != null) {
            return new SimpleObjectProperty<>(employee.getType());
          } else {
            return new SimpleObjectProperty<>(null);
          }
        });

    employeeTypeColumn.setCellFactory(
        ComboBoxTableCell.forTableColumn(
            new StringConverter<>() {
              @Override
              public String toString(EmployeeType employeeType) {
                if (employeeType == null) {
                  return "";
                } else {
                  return employeeType.toString();
                }
              }

              @Override
              public EmployeeType fromString(String string) {
                return EmployeeType.valueOf(string);
              }
            },
            EmployeeType.values()));

    TableColumn<Employee, ClearanceLevel> employeeLevelColumn =
        new TableColumn<>("Clearance Level");
    employeeLevelColumn.setCellValueFactory(
        cellData -> {
          Employee employee = cellData.getValue();
          if (employee.getType() != null) {
            return new SimpleObjectProperty<>(employee.getLevel());
          } else {
            return new SimpleObjectProperty<>(null);
          }
        });

    employeeLevelColumn.setCellFactory(
        ComboBoxTableCell.forTableColumn(
            new StringConverter<>() {
              @Override
              public String toString(ClearanceLevel clearanceLevel) {
                if (clearanceLevel == null) {
                  return "";
                } else {
                  return clearanceLevel.toString();
                }
              }

              @Override
              public ClearanceLevel fromString(String string) {
                return ClearanceLevel.valueOf(string);
              }
            },
            ClearanceLevel.values()));

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
            employeeLevelColumn,
            employeeTypeColumn,
            userColumn,
            passColumn);

    employeeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    DataManager employeeDAO = new DataManager();
    try {
      ArrayList<Employee> employees = DataManager.getAllEmployees();

      employeeTable.setItems(FXCollections.observableArrayList(employees));

    } catch (SQLException e) {
      System.err.println("Error getting employees from database: " + e.getMessage());
    }

    employeeTable.setEditable(true);

    employeeIDColumn.setCellFactory(
        TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    employeeLevelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(ClearanceLevel.values()));
    employeeTypeColumn.setCellFactory(ComboBoxTableCell.forTableColumn(EmployeeType.values()));
    userColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    passColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    ContextMenu contextMenu = new ContextMenu();
    MenuItem deleteMenuItem = new MenuItem("Delete");
    contextMenu.getItems().add(deleteMenuItem);

    employeeTable.setRowFactory(
        tableView -> {
          TableRow<Employee> row = new TableRow<>();
          row.contextMenuProperty()
              .bind(
                  Bindings.when(row.emptyProperty())
                      .then((ContextMenu) null)
                      .otherwise(contextMenu));
          return row;
        });

    deleteMenuItem.setOnAction(event -> deleteSelectedEmployee());

    exportButton.setOnAction(
        event -> {
          Sound.playSFX(SFX.BUTTONCLICK);
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
          Sound.playSFX(SFX.BUTTONCLICK);
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
              employeeTable.getItems().addAll(employeeDAO.getAllEmployees());
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
    employeeTypeColumn.setOnEditCommit(
        event -> {
          Employee employee = event.getRowValue();
          employee.setType(event.getNewValue());
          try {
            DataManager.syncEmployee(employee);
          } catch (SQLException e) {
            System.out.println(e.getMessage());
          }
        });
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
            DataManager.syncEmployee(employee);
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

    setLanguage(
        GlobalVariables.getB().getValue(),
        employeeIDColumn,
        firstNameColumn,
        lastNameColumn,
        employeeLevelColumn,
        employeeTypeColumn,
        userColumn,
        passColumn);
    GlobalVariables.b.addListener(
        (options, oldValue, newValue) -> {
          setLanguage(
              newValue,
              employeeIDColumn,
              firstNameColumn,
              lastNameColumn,
              employeeLevelColumn,
              employeeTypeColumn,
              userColumn,
              passColumn);
        });
  }
  /** Deletes the selected employee from the database and updates the employee table. */
  private void deleteSelectedEmployee() {
    DataManager employeeDAO = new DataManager();
    Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
    if (selectedEmployee != null) {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.initOwner(App.getPrimaryStage());
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
          // employeeDAO.deleteEmployeeType(selectedEmployee.getUsername());
          employeeTable.getItems().remove(selectedEmployee);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }
  /**
   * Adds a new employee to the database and updates the employee table.
   *
   * <p>Validates the password input against certain criteria.
   */
  @FXML
  private void handleSubmitButton() {

    try {
      DataManager employeeDAO = new DataManager();
      int employeeIDInput = Integer.parseInt(employeeIDField.getText());
      String firstName = employeeFirstNameTextField.getText();
      String lastName = employeeLastNameTextField.getText();
      String username = employeeUserTextField.getText();
      String password = employeePasswordTextField.getText();
      EmployeeType employeeType =
          EmployeeType.valueOf(
              employeeTypeText.valueProperty().getValue().toString().toUpperCase());
      ClearanceLevel clearanceLevel =
          ClearanceLevel.valueOf(
              employeeLevelText.valueProperty().getValue().toString().toUpperCase());

      Employee employee =
          new Employee(
              username,
              password,
              employeeIDInput,
              firstName,
              lastName,
              clearanceLevel,
              employeeType,
              true);

      // Validate the password using checkLegalLogin method
      if (employee.checkLegalLogin(password)) {

        // employee.addType(employeeType);
        // employeeDAO.addEmployeeType(username, employeeType);
        employeeDAO.addEmployee(employee);
        employeeTable.getItems().add(employee);
        Sound.playSFX(SFX.SUCCESS);
        // Clear the input fields
        employeeFirstNameTextField.clear();
        employeeIDField.clear();
        employeeLastNameTextField.clear();
        employeeUserTextField.clear();
        employeePasswordTextField.clear();
      } else {
        // Display an error message if password validation fails
        Sound.playSFX(SFX.ERROR);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Password");
        alert.setContentText(
            "The provided password does not meet the required criteria. \n "
                + "At least 8 Characters\n"
                + "One special character\n"
                + "One capital letter\n"
                + "One number");
        alert.showAndWait();
      }
    } catch (Exception e) {
      // Display an error message if an exception occurs
      Sound.playSFX(SFX.ERROR);
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.initOwner(App.getPrimaryStage());
      alert.setTitle("Error");
      alert.setHeaderText("An error occurred");
      alert.setContentText("An error occurred while processing your request:\n " + e.getMessage());
      alert.showAndWait();
    }
  }
  /**
   * Filters the employee table based on the search text. If search text is empty, shows all
   * employees in the database.
   */
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

  /*private void updateEmployeeType(Employee employee) {
    DataManager employeeDAO = new DataManager();
    try {
      employeeDAO.deleteEmployeeType(employee.getUsername());
      for (EmployeeType type : employee.getType()) {
        employeeDAO.addEmployeeType(employee.getUsername(), type);
      }
    } catch (SQLException e) {
      System.err.println("Error updating employee type in the database: " + e.getMessage());
    }
  }*/
}
