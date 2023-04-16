package edu.wpi.teamname.controllers;

import edu.wpi.teamname.controllers.JFXitems.DatePickerEditingCell;
import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.database.MoveDAOImpl;
import edu.wpi.teamname.navigation.Move;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.File;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.converter.IntegerStringConverter;

public class MoveTableController {
  @FXML private TableView<Move> moveTable;
  @FXML private Button importButton;

  @FXML private Button exportButton;
  @FXML private TextField nodeIdTextField;
  @FXML private TextField longNameTextField;
  @FXML private DatePicker datePicker;
  @FXML private MFXButton submitButton;

  public void initialize() {
    TableColumn<Move, Integer> nodeIDColumn = new TableColumn<>("Node ID");
    nodeIDColumn.setCellValueFactory(new PropertyValueFactory<>("nodeID"));

    TableColumn<Move, String> longNameColumn = new TableColumn<>("Long Name");
    longNameColumn.setCellValueFactory(new PropertyValueFactory<>("longName"));

    TableColumn<Move, Timestamp> dateColumn = new TableColumn<>("Date");
    dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    dateColumn.setCellFactory(tc -> new DatePickerEditingCell());

    moveTable.getColumns().addAll(nodeIDColumn, longNameColumn, dateColumn);
    moveTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    dateColumn
        .prefWidthProperty()
        .bind(
            moveTable
                .widthProperty()
                .subtract(nodeIDColumn.widthProperty())
                .subtract(longNameColumn.widthProperty()));

    DataManager moveDAO = new DataManager();

    try {
      ArrayList<Move> moves = moveDAO.getAllMoves();
      moveTable.setItems(FXCollections.observableArrayList(moves));
    } catch (SQLException e) {
      System.err.println("Error getting moves from database: " + e.getMessage());
    }
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
    exportButton.setOnAction(
        event -> {
          FileChooser fileChooser = new FileChooser();
          fileChooser.setTitle("Save CSV File");
          fileChooser.setInitialFileName("moves.csv");
          fileChooser
              .getExtensionFilters()
              .addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
          File file = fileChooser.showSaveDialog(exportButton.getScene().getWindow());
          if (file != null) {
            try {
              MoveDAOImpl.exportMoveToCSV(file.getAbsolutePath());
            } catch (SQLException e) {
              System.err.println("Error exporting data to CSV file: " + e.getMessage());
            }
          }
        });
    submitButton.setOnAction(
        event -> {
          int nodeId = Integer.parseInt(nodeIdTextField.getText());
          String longName = longNameTextField.getText();
          LocalDate localDate = datePicker.getValue();
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
          LocalDateTime dateTime = localDate.atStartOfDay();
          Timestamp date = Timestamp.valueOf(dateTime);
          Move move = new Move(nodeId, longName, date);
          try {
            moveDAO.addMoves(move);
          } catch (SQLException e) {
            e.printStackTrace();
          }
        });
    moveTable.setEditable(true);

    nodeIDColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    nodeIDColumn.setOnEditCommit(
        (CellEditEvent<Move, Integer> t) -> {
          Move move = t.getTableView().getItems().get(t.getTablePosition().getRow());
          move.setNodeID(t.getNewValue());
          try {
            moveDAO.syncMove(move);
          } catch (SQLException e) {
            System.err.println("Error updating node ID: " + e.getMessage());
          }
        });

    longNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    longNameColumn.setOnEditCommit(
        (CellEditEvent<Move, String> t) -> {
          Move move = t.getTableView().getItems().get(t.getTablePosition().getRow());
          move.setLongName(t.getNewValue());
          try {
            moveDAO.syncMove(move);
          } catch (SQLException e) {
            System.err.println("Error updating long name: " + e.getMessage());
          }
        });
  }
}
