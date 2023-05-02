package edu.wpi.teamname.controllers;

import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.ThemeSwitch;
import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.employees.Feedback;
import edu.wpi.teamname.extras.Language;
import edu.wpi.teamname.servicerequest.Status;
import java.sql.SQLException;
import java.sql.Timestamp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class FeedbackController {
  @FXML Label bugReportLabel;
  @FXML AnchorPane root;
  @FXML TextArea descriptionField;

  private DataManager dataManager;

  public void setLanguage(Language lang) {
    switch (lang) {
      case ENGLISH:
        ParentController.titleString.set("Feedback Submission");
        bugReportLabel.setText("Bug Report");
        descriptionField.setPromptText("Give a description of what occurred...");
        break;
      case ITALIAN:
        ParentController.titleString.set("Invio Commenti");
        bugReportLabel.setText("Segnala un Problema");
        descriptionField.setPromptText("Fornisci una descrizione di ciò che è accaduto...");
        break;
      case FRENCH:
        ParentController.titleString.set("Soumission de Commentaires");
        bugReportLabel.setText("Signalement de Problème");
        descriptionField.setPromptText("Donnez une description de ce qui s'est produit...");
        break;
      case SPANISH:
        ParentController.titleString.set("Envío de Comentarios");
        bugReportLabel.setText("Informe de Error");
        descriptionField.setPromptText("Proporcione una descripción de lo que ocurrió...");
        break;
    }
  }

  public void initialize() {
    ThemeSwitch.switchTheme(root);
    setLanguage(GlobalVariables.getB().getValue());
    GlobalVariables.b.addListener(
        (options, oldValue, newValue) -> {
          setLanguage(newValue);
        });
    ParentController.titleString.set("Feedback Submission");
    dataManager = new DataManager();
  }

  @FXML
  private void handleSubmit() {
    String description = descriptionField.getText();

    Feedback feedback = new Feedback();
    feedback.setReporter(
        String.valueOf(GlobalVariables.getCurrentUser())); // Replace with the actual reporter info
    feedback.setDateReported(new Timestamp(System.currentTimeMillis()));
    feedback.setDescription(description);
    feedback.setAssignee("Unassigned");
    feedback.setStatus(Status.BLANK);

    try {
      dataManager.addFeedback(feedback);
      descriptionField.clear();

    } catch (SQLException e) {
      System.out.println(e.getMessage());
      showErrorAlert("Error", "Failed to submit feedback", e.getMessage());
    }
  }

  private void showErrorAlert(String title, String header, String content) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
  }
}
