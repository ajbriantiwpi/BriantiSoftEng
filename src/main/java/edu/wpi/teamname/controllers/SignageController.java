package edu.wpi.teamname.controllers;

import edu.wpi.teamname.database.DataManager;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
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
  private static ArrayList<String> kiosksForDate = new ArrayList<>();

  @FXML
  public void initialize() throws SQLException {
    // Initialize kioskList before adding elements to it
    kioskList = FXCollections.observableArrayList();

    // Now you can safely add elements to kioskList
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
          }
        });

    KskBox.setOnMouseClicked(
        event -> {
          try {
            kiosksForDate = DataManager.getSignage(KskBox.getValue(), date);
          } catch (SQLException e) {
            System.out.println(e);
          }
        });

    submit.setOnMouseClicked(
        event -> {
          // loop thru kiosksForDate and put them below each other in VBox
        });

    // set default date to 2023-05-02
  }

  private void getSignage() {}
}
