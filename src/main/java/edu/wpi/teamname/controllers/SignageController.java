package edu.wpi.teamname.controllers;

import edu.wpi.teamname.database.DataManager;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SignageController {
  @FXML VBox labelsVbox;
  @FXML HBox labelHBox;
  @FXML Label labelLine;
  @FXML ComboBox<Integer> Kiosks;
  @FXML ObservableList<Integer> kioskList;
  @FXML DatePicker dateChos;
  @FXML MFXButton submit;
  private static Timestamp date;

  @FXML
  public void initialize() throws SQLException {
    submit.setDisable(true);
    submit.setVisible(false);
    Kiosks.setDisable(true);
    Kiosks.setVisible(false);

    dateChos.setOnAction(
        event -> {
          date = Timestamp.valueOf(dateChos.getValue().toString());
          try {
            kioskList = FXCollections.observableArrayList(DataManager.getKiosks(date));
            Kiosks.setItems(kioskList);
          } catch (SQLException e) {
            System.out.println(e);
          }
          Kiosks.setDisable(false);
          Kiosks.setVisible(true);
        });

    Kiosks.setOnAction(
        event -> {
          try {
            DataManager.getSignage(Kiosks.getValue(), date);
          } catch (SQLException e) {
            System.out.println(e);
          }
          submit.setDisable(true);
          submit.setVisible(false);
        });

    submit.setOnMouseClicked(event -> {});

    // set default date to 2023-05-02
  }

  private void getSignage() {}
}
