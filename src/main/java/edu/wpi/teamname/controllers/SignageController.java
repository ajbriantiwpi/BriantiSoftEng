package edu.wpi.teamname.controllers;

import edu.wpi.teamname.database.DataManager;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalTime;
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
  @FXML ComboBox<Integer> KskBox;
  @FXML ObservableList<Integer> kioskList;
  @FXML DatePicker dateChos;
  @FXML MFXButton submit;
  private static Timestamp date;

  @FXML
  public void initialize() throws SQLException {
    //    submit.setDisable(true);
    //    submit.setVisible(false);
    //    KskBox.setDisable(true);
    //    KskBox.setVisible(false);
    kioskList.add(0);
    KskBox.setItems(kioskList);
    dateChos.setOnAction(
        event -> {
          if (dateChos.getValue() != null) {
            date = Timestamp.valueOf(dateChos.getValue().atTime(LocalTime.of(0, 0)));
            try {
              kioskList = FXCollections.observableArrayList(DataManager.getKiosks(date));
              KskBox.setItems(kioskList);
            } catch (SQLException e) {
              System.out.println(e);
            }
            //            KskBox.setDisable(false);
            //            KskBox.setVisible(true);
          }
        });

    KskBox.setOnMouseClicked(
        event -> {
          try {
            DataManager.getSignage(KskBox.getValue(), date);
          } catch (SQLException e) {
            System.out.println(e);
          }
          //          submit.setDisable(true);
          //          submit.setVisible(false);
        });

    submit.setOnMouseClicked(event -> {});

    // set default date to 2023-05-02
  }

  private void getSignage() {}
}
