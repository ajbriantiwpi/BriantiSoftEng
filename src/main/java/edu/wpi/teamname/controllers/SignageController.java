package edu.wpi.teamname.controllers;

import edu.wpi.teamname.database.DataManager;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
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
  private static Timestamp dateChosen;
  private static ArrayList<String> kiosksForDate = new ArrayList<>();

  @FXML
  public void initialize() throws SQLException {
    //    submit.setDisable(true);
    //    submit.setVisible(false);
    //    KskBox.setDisable(true);
    //    KskBox.setVisible(false);
    kioskList = FXCollections.observableArrayList();
    kioskList.add(0);
    KskBox.setItems(kioskList);
    dateChos.setOnAction(
        event -> {
          if (dateChos.getValue() != null) {
            System.out.println(
                dateChos
                    .getValue()
                    .atTime(12, 0)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nnn")));
            dateChosen =
                Timestamp.valueOf(
                    dateChos
                        .getValue()
                        .atTime(12, 0)
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nnn")));
            try {
              kioskList = FXCollections.observableArrayList(DataManager.getKiosks(dateChosen));
              KskBox.setItems(kioskList);
            } catch (SQLException e) {
              System.out.println(e);
            }
          }
        });

    KskBox.setOnAction(
        event -> {
          if (KskBox.getValue() != null) {
            try {
              kiosksForDate = DataManager.getSignage(KskBox.getValue(), dateChosen);
            } catch (SQLException e) {
              System.out.println(e);
            }
          }
        });

    submit.setOnMouseClicked(
        event -> {
          System.out.println(kiosksForDate);
          // loop thru kiosksForDate and put them below eachother in VBox
        });

    // set default date to 2023-05-02
  }

  private void getSignage() {}
}
