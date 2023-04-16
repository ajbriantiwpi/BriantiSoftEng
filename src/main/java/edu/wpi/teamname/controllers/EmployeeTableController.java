package edu.wpi.teamname.controllers;

import edu.wpi.teamname.controllers.JFXitems.DatePickerEditingCell;
import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.employees.Employee;
import edu.wpi.teamname.employees.EmployeeType;
import edu.wpi.teamname.navigation.Move;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Timestamp;

public class EmployeeTableController {
    @FXML private TableView<Employee> employeeTable;
    @FXML private TextField employeeIDField;
    @FXML private TextField employeeFirstNameTextField;
    @FXML private TextField employeeLastNameTextField;
    @FXML private TextField employeeTypeTextField;
    @FXML private TextField employeeUserTextField;
    @FXML private Button exportButton;
    @FXML private Button importButton;
    @FXML private TextField searchEmployee;

    public void initialize() {
        ParentController.titleString.set("Employee Edit Table");
        TableColumn<Employee, Integer> employeeIDColumn = new TableColumn<>("Employee ID");
        employeeIDColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));

        TableColumn<Employee, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("First Name"));

        TableColumn<Employee, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("Last Name"));

        TableColumn<EmployeeType, String> employeeTypeColumn = new TableColumn<>("Employee Type");
        employeeTypeColumn.setCellValueFactory(new PropertyValueFactory<>("Employee Type"));

        TableColumn<Employee, String> userColumn = new TableColumn<>("Username");
        userColumn.setCellValueFactory(new PropertyValueFactory<>("Username"));



        employeeTable.getColumns().addAll(employeeIDColumn, firstNameColumn, lastNameColumn, employeeIDColumn, userColumn);
        employeeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        DataManager employeeDAO = new DataManager();
    }
}
