package edu.wpi.teamname.controllers;

import edu.wpi.teamname.ThemeSwitch;
import edu.wpi.teamname.controllers.JFXitems.DirectionArrow;
import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.extras.Sound;
import edu.wpi.teamname.navigation.Direction;
import edu.wpi.teamname.navigation.Signage;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class SignageController {
  @FXML AnchorPane root;
  @FXML ComboBox<Integer> KskBox;
  @FXML ObservableList<Integer> kioskList;
  @FXML DatePicker dateChos;
  @FXML MFXButton submit;
  @FXML VBox textVbox;

  private static Timestamp dateChosen;
  private static ArrayList<Signage> signsForDate = new ArrayList<>();

  @FXML
  public void initialize() throws SQLException {
    ThemeSwitch.switchTheme(root);
    ParentController.titleString.set("Signage");
    kioskList = FXCollections.observableArrayList();
    kioskList.add(null);
    KskBox.setItems(kioskList);
    dateChos
        .valueProperty()
        .addListener(
            (t, o, n) -> {
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
              signsForDate = DataManager.getSignages(KskBox.getValue(), dateChosen);
            } catch (SQLException e) {
              System.out.println(e);
            }
          }
        });

    submit.setOnMouseClicked(
        event -> {
          Sound.playOnButtonClick();
          textVbox.getChildren().clear();
          String finalSign = "";
          System.out.println(signsForDate);
          for (int i = 0; i < signsForDate.size(); i++) {
            Direction dir = signsForDate.get(i).getArrowDirection();
            String text = signsForDate.get(i).getLongName();
            DirectionArrow da = new DirectionArrow(dir, text, 100);
            da.setMaxHeight(100);
            textVbox.getChildren().add(da);

            // arrowVbox.getChildren().add();

          }
          // labelLine.setText(finalSign);
        });
  }
}
