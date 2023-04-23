package edu.wpi.teamname.controllers;

import edu.wpi.teamname.database.SignageDAOImpl;
import edu.wpi.teamname.database.interfaces.SignageDAO;
import edu.wpi.teamname.navigation.Direction;
import edu.wpi.teamname.navigation.Signage;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import jdk.jfr.Timestamp;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import static edu.wpi.teamname.database.DataManager.syncSignage;

public class EditSignageController {
    @FXML
    private TableView<Signage> editSignageTable;
    @FXML
    private TextField signIDinput;
    @FXML
    private TextField longNameInput;
    @FXML
    private TextField shortNameInput;
    @FXML
    private TextField dateInput;
    @FXML
    private ComboBox<String> directionPicker;
    @FXML
    private MFXButton submitButton;

    public void initialize() {
        TableColumn<Signage, String> longNameColumn = new TableColumn<>("Long Name");
        longNameColumn.setCellValueFactory(new PropertyValueFactory<>("longName"));

        TableColumn<Signage, String> shortNameColumn = new TableColumn<>("Short Name");
        shortNameColumn.setCellValueFactory(new PropertyValueFactory<>("shortName"));

        TableColumn<Signage, Timestamp> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Signage, String> arrowDirectionColumn = new TableColumn<>("Direction");
        arrowDirectionColumn.setCellValueFactory(new PropertyValueFactory<>("arrowDirection"));

        TableColumn<Signage, Integer> signIDColumn = new TableColumn<>("Sign ID");
        signIDColumn.setCellValueFactory(new PropertyValueFactory<>("signId"));


        longNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        shortNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        arrowDirectionColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Make the TableView editable
        editSignageTable.setEditable(true);

        // Add the event handlers for committing the edits
        signIDColumn.setOnEditCommit(event -> {
            Signage editedSignage = event.getRowValue();
            editedSignage.setSignId(event.getNewValue());
            try {
                syncSignage(editedSignage);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        longNameColumn.setOnEditCommit(event -> {
            Signage editedSignage = event.getRowValue();
            editedSignage.setLongName(event.getNewValue());
            try {
                syncSignage(editedSignage);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        shortNameColumn.setOnEditCommit(event -> {
            Signage editedSignage = event.getRowValue();
            editedSignage.setShortName(event.getNewValue());
            try {
                syncSignage(editedSignage);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        arrowDirectionColumn.setOnEditCommit(event -> {
            Signage editedSignage = event.getRowValue();
            editedSignage.setArrowDirection(Direction.valueOf(event.getNewValue()));
            try {
                syncSignage(editedSignage);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        editSignageTable.getColumns().addAll(longNameColumn, shortNameColumn, dateColumn, arrowDirectionColumn, signIDColumn);
        loadData();
        directionPicker.getItems().addAll();

    }

    private void loadData() {
        SignageDAO signageDAO = new SignageDAOImpl();
        try {
            ObservableList<Signage> signageData = FXCollections.observableArrayList(signageDAO.getAll());
            editSignageTable.setItems(signageData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleSubmitButton(ActionEvent event) {
        int signId = Integer.parseInt(signIDinput.getText());
        String longName = longNameInput.getText();
        String shortName = shortNameInput.getText();
        java.sql.Timestamp date = java.sql.Timestamp.valueOf(dateInput.getText());
        Direction direction = Direction.valueOf(directionPicker.getValue());

        Signage newSignage = new Signage(longName, shortName, date, direction, signId);
        SignageDAO signageDAO = new SignageDAOImpl();

        try {
            signageDAO.add(newSignage);
            editSignageTable.getItems().add(newSignage);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Clear input fields
        signIDinput.clear();
        longNameInput.clear();
        shortNameInput.clear();
        dateInput.clear();
        directionPicker.setValue(null);
    }
}
