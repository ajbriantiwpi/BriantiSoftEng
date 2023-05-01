package edu.wpi.teamname.controllers;

import edu.wpi.teamname.GlobalVariables;
import edu.wpi.teamname.controllers.JFXitems.DirectionArrow;
import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.extras.Language;
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
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SignageController {
  @FXML Label signageSearchLabel;
  @FXML Label dateLabel;
  @FXML Label kioskIDLabel;

  @FXML ComboBox<Integer> KskBox;
  @FXML ObservableList<Integer> kioskList;
  @FXML DatePicker dateChos;
  @FXML MFXButton submit;
  @FXML VBox textVbox;

  private static Timestamp dateChosen;
  private static ArrayList<Signage> signsForDate = new ArrayList<>();

  public void setLanguage(Language lang) {
    switch (lang) {
      case ENGLISH:
        ParentController.titleString.set("Signage");
        signageSearchLabel.setText("Signage Search");
        dateLabel.setText("Choose Date");
        kioskIDLabel.setText("Choose Kiosk ID");
        submit.setText("Submit");
        break;
      case SPANISH:
        ParentController.titleString.set("Señalización");
        signageSearchLabel.setText("Búsqueda de señalización");
        dateLabel.setText("Seleccione fecha");
        kioskIDLabel.setText("Seleccione ID de quiosco");
        submit.setText("Enviar");
        break;
      case FRENCH:
        ParentController.titleString.set("Signalisation");
        signageSearchLabel.setText("Recherche de signalisation");
        dateLabel.setText("Choisir la date");
        kioskIDLabel.setText("Choisir l'ID du kiosque");
        submit.setText("Soumettre");
        break;
      case ITALIAN:
        ParentController.titleString.set("Segnaletica");
        signageSearchLabel.setText("Ricerca segnaletica");
        dateLabel.setText("Scegli data");
        kioskIDLabel.setText("Scegli ID chiosco");
        submit.setText("Invia");
        break;
    }
  }

  @FXML
  public void initialize() throws SQLException {
    ParentController.titleString.set("Signage");
    setLanguage(GlobalVariables.getB().getValue());
    GlobalVariables.b.addListener(
        (options, oldValue, newValue) -> {
          setLanguage(newValue);
        });

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
