package edu.wpi.teamname.controllers;

import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.ThemeSwitch;
import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.employees.Feedback;
import edu.wpi.teamname.extras.Language;
import edu.wpi.teamname.servicerequest.Status;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

/**
 * The ViewFeedbackController class is responsible for managing the "View Feedback" screen of the
 * application. It displays all feedback records in a TableView and allows the user to edit the
 * reporter, description, assignee, and status fields of each record. Changes made to the records
 * are synced with the database.
 */
public class ViewFeedbackController {
  @FXML AnchorPane root;
  @FXML private TableView<Feedback> feedbackTable;
  @FXML private TableColumn<Feedback, String> reporterColumn;
  @FXML private TableColumn<Feedback, String> descriptionColumn;
  @FXML private TableColumn<Feedback, Timestamp> dateColumn;
  @FXML private TableColumn<Feedback, String> assigneeColumn;
  @FXML private TableColumn<Feedback, Integer> idColumn;
  @FXML private TableColumn<Feedback, Status> statusColumn;


    public void setLanguage(Language lang) {
        switch (lang) {
            case ENGLISH:
                ParentController.titleString.set("Feedback Requests");
                reporterColumn.setText("Reporter");
                descriptionColumn.setText("Description");
                dateColumn.setText("Date");
                assigneeColumn.setText("Assignee");
                statusColumn.setText("Status");
                idColumn.setText("ID");
                break;
            case ITALIAN:
                ParentController.titleString.set("Richieste di Feedback");
                reporterColumn.setText("Segnalatore");
                descriptionColumn.setText("Descrizione");
                dateColumn.setText("Data");
                assigneeColumn.setText("Assegnatario");
                statusColumn.setText("Stato");
                idColumn.setText("ID");
                break;
            case FRENCH:
                ParentController.titleString.set("Demandes de Commentaires");
                reporterColumn.setText("Déclarant");
                descriptionColumn.setText("Description");
                dateColumn.setText("Date");
                assigneeColumn.setText("Assigné");
                statusColumn.setText("Statut");
                idColumn.setText("ID");
                break;
            case SPANISH:
                ParentController.titleString.set("Solicitudes de Comentarios");
                reporterColumn.setText("Reportante");
                descriptionColumn.setText("Descripción");
                dateColumn.setText("Fecha");
                assigneeColumn.setText("Asignado");
                statusColumn.setText("Estado");
                idColumn.setText("ID");
                break;
        }
    }
  /** Initializes the ViewFeedbackController and sets up the UI elements and functionality. */
  @FXML
  private void initialize() {
    ThemeSwitch.switchTheme(root);
    DataManager dm = new DataManager();
    ParentController.titleString.set("Feedback Requests");
      setLanguage(GlobalVariables.getB().getValue());
      GlobalVariables.b.addListener(
              (options, oldValue, newValue) -> {
                  setLanguage(newValue);
              });
    // Fetch all feedback records from the database
    ArrayList<Feedback> feedbackList = new ArrayList<>();
    try {
      feedbackList = dm.getAllFeedback();
    } catch (SQLException e) {
      System.out.println("Error fetching data from the database: " + e.getMessage());
    }

    // Set the cell value factory for each column in the feedbackTable
    reporterColumn.setCellValueFactory(new PropertyValueFactory<>("reporter"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateReported"));
    assigneeColumn.setCellValueFactory(new PropertyValueFactory<>("assignee"));
    idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    // Make the cells editable using TextFieldTableCell.forTableColumn()
    reporterColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    assigneeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    statusColumn.setCellFactory(
        TextFieldTableCell.forTableColumn(
            new StringConverter<Status>() {
              @Override
              public String toString(Status status) {
                return status.getStatusString();
              }

              @Override
              public Status fromString(String statusString) {
                return Status.valueOf(statusString);
              }
            }));

    // Set the cell edit commit handler to update the database
    reporterColumn.setOnEditCommit(
        event -> {
          Feedback feedback = event.getRowValue();
          feedback.setReporter(event.getNewValue());
          try {
            dm.syncFeedback(feedback);
          } catch (SQLException e) {
            System.out.println("Error updating reporter in the database: " + e.getMessage());
          }
        });
    descriptionColumn.setOnEditCommit(
        event -> {
          Feedback feedback = event.getRowValue();
          feedback.setDescription(event.getNewValue());
          try {
            dm.syncFeedback(feedback);
          } catch (SQLException e) {
            System.out.println("Error updating description in the database: " + e.getMessage());
          }
        });
    assigneeColumn.setOnEditCommit(
        event -> {
          Feedback feedback = event.getRowValue();
          feedback.setAssignee(event.getNewValue());
          try {
            dm.syncFeedback(feedback);
          } catch (SQLException e) {
            System.out.println("Error updating assignee in the database: " + e.getMessage());
          }
        });
    statusColumn.setOnEditCommit(
        event -> {
          Feedback feedback = event.getRowValue();
          feedback.setStatus(event.getNewValue());
          try {
            dm.syncFeedback(feedback);
          } catch (SQLException e) {
            System.out.println("Error updating status in the database: " + e.getMessage());
          }
        });

    ObservableList<Feedback> feedbackObservableList =
        FXCollections.observableArrayList(feedbackList);
    feedbackTable.setItems(feedbackObservableList);
    feedbackTable.setEditable(true);
  }
}
