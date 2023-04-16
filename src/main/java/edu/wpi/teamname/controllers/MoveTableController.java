package edu.wpi.teamname.controllers;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.database.MoveDAOImpl;
import edu.wpi.teamname.navigation.Move;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import jdk.jfr.Timestamp;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

public class MoveTableController {
    @FXML
    private TableView<Move> moveTable;
    @FXML
    private Button importButton;

    @FXML
    private Button exportButton;

    public void initialize() {
        TableColumn<Move, Integer> nodeIDColumn = new TableColumn<>("Node ID");
        nodeIDColumn.setCellValueFactory(new PropertyValueFactory<>("nodeID"));

        TableColumn<Move, String> longNameColumn = new TableColumn<>("Long Name");
        longNameColumn.setCellValueFactory(new PropertyValueFactory<>("longName"));

        TableColumn<Move, Timestamp> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        moveTable.getColumns().addAll(nodeIDColumn, longNameColumn, dateColumn);

        DataManager moveDAO = new DataManager();

        try {
            ArrayList<Move> moves = moveDAO.getAllMoves();
            moveTable.setItems(FXCollections.observableArrayList(moves));
        } catch (SQLException e) {
            System.err.println("Error getting moves from database: " + e.getMessage());
        }
        importButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select CSV File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV Files", "*.csv")
            );
            File file = fileChooser.showOpenDialog(importButton.getScene().getWindow());
            if (file != null) {
                try {
                    MoveDAOImpl.uploadMoveToPostgreSQL(file.getAbsolutePath());
                    // refresh table view
                    moveTable.getItems().clear();
                    moveTable.getItems().addAll(moveDAO.getAllMoves());
                } catch (SQLException e) {
                    System.err.println("Error uploading data to database: " + e.getMessage());
                }
            }
        });

        // event handler for export button
        exportButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save CSV File");
            fileChooser.setInitialFileName("moves.csv");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV Files", "*.csv")
            );
            File file = fileChooser.showSaveDialog(exportButton.getScene().getWindow());
            if (file != null) {
                try {
                    MoveDAOImpl.exportMoveToCSV(file.getAbsolutePath());
                } catch (SQLException e) {
                    System.err.println("Error exporting data to CSV file: " + e.getMessage());
                }
            }
        });
    }
}
