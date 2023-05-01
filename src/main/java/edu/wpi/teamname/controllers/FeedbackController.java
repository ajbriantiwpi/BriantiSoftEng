package edu.wpi.teamname.controllers;

import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.ThemeSwitch;
import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.employees.Feedback;
import edu.wpi.teamname.servicerequest.Status;
import java.sql.SQLException;
import java.sql.Timestamp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class FeedbackController {
  @FXML
  AnchorPane root;
  @FXML TextArea descriptionField;

  private DataManager dataManager;

  public void initialize() {
    ThemeSwitch.switchTheme(root);
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
