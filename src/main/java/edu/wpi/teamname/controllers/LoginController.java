package edu.wpi.teamname.controllers;

import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.Navigation;
import edu.wpi.teamname.alerts.Alert;
import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.employees.Employee;
import edu.wpi.teamname.employees.EmployeeType;
import edu.wpi.teamname.extras.Sound;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoField;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class LoginController {
  @FXML MFXButton exit;
  @FXML AnchorPane rootPane;
  @FXML StackPane paneOfStuff;
  //  String tempPassword;
  @FXML Label newPassword;
  @FXML Label success;
  @FXML MFXButton loginButton;
  @FXML MFXButton forgotPassword;
  @FXML MFXTextField loginText;
  @FXML PasswordField passwordText;
  @FXML MFXButton cancel;
  // @FXML MFXButton help;

  /**
   * handles when the login button is pressed
   *
   * @param username username
   * @param password password
   * @return a boolean if the login was successful or not
   * @throws SQLException if there is an error connecting to the database
   * @throws ExceptionInInitializerError for testing, when we change pages without initializing the
   *     screen
   */
  public static boolean loginPressed(String username, String password)
      throws SQLException, ExceptionInInitializerError {
    Employee user = DataManager.checkLogin(username, password);
    if (user != null) {
      GlobalVariables.setCurrentUser(user);
      HomeController.setLoggedIn(new SimpleBooleanProperty(true));
      Navigation.navigate(GlobalVariables.getPreviousScreen());
      return true;
    } else {
      return false;
    }
  }

  /** initializes the view for the login page */
  @FXML
  public void initialize() {
    // help.setVisible(false);
    newPassword.setVisible(false);
    success.setText("Username or password\nis incorrect");
    success.setVisible(false);
    exit.setOnMouseClicked(event -> System.exit(0));
    forgotPassword.disableProperty().bind(Bindings.isEmpty(loginText.textProperty()));
    loginButton.disableProperty().bind(Bindings.isEmpty(loginText.textProperty()));
    loginButton.disableProperty().bind((Bindings.isEmpty(passwordText.textProperty())));
    forgotPassword.setOnMouseClicked(
        event -> {
          try {
            String tempPassword = forgotPasswordPressed(loginText.getText());
            newPassword.setText(tempPassword);
            newPassword.setVisible(true);
            paneOfStuff.setDisable(true);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
    rootPane.setOnMouseClicked(
        event -> {
          paneOfStuff.setDisable(false);
          newPassword.setVisible(false);
          success.setVisible(false);
        });

    loginButton.setOnMouseClicked(
        event -> {
          try {
            boolean temp = loginPressed(loginText.getText(), passwordText.getText());
            if (!temp) {
              paneOfStuff.setDisable(true);
              success.setVisible(true);
              passwordText.clear();
            }
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
    cancel.setOnMouseClicked(event -> Navigation.navigate(GlobalVariables.getPreviousScreen()));

    passwordText.setOnKeyPressed(
        event -> {
          if (event.getCode() == KeyCode.ENTER
              && !passwordText.textProperty().equals("")
              && !loginText.textProperty().equals("")) {
            try {
              boolean temp = loginPressed(loginText.getText(), passwordText.getText());
              if (!temp) {
                paneOfStuff.setDisable(true);
                success.setVisible(true);
                passwordText.clear();
              }
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }
        });
  }

  /**
   * Handles when the forgot password button is pressed Sends an alert to ADMINISTRATORs that the
   * user's password needs to be reset
   *
   * @param username the username from the text field that we want to reset the password of
   * @return the new password string
   * @throws SQLException if there is an error connecting to the database
   */
  public static String forgotPasswordPressed(String username) throws SQLException {
    Sound.playOnButtonClick();
    //    return DataManager.forgotPassword(username);
    Employee employee = DataManager.getEmployee(username);
    if (employee != null) {
      edu.wpi.teamname.alerts.Alert newAlert =
          new Alert(
              Instant.now().get(ChronoField.MICRO_OF_SECOND),
              new Timestamp(System.currentTimeMillis()),
              (new Timestamp((long) (System.currentTimeMillis() + 6.048e+8))),
              "AUTO",
              "Reset password request for " + username,
              "Reset Password Request",
              EmployeeType.ADMINISTRATOR,
              Alert.Urgency.MILD);
      DataManager.addAlert(newAlert);
      return "Reset Password Request Submitted";
    }
    return "Please Enter a Correct Username";
  }
}
