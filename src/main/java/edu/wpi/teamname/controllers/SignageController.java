package edu.wpi.teamname.controllers;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.database.SignageDAOImpl;
import edu.wpi.teamname.navigation.Signage;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SignageController {
  @FXML VBox labelsVbox;
  @FXML VBox contentVBox;
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
    ParentController.titleString.set("View Signage");
    SignageDAOImpl signageDAO = new SignageDAOImpl();
    ArrayList<Signage> signageList = new ArrayList<>();

    try {
      signageList = signageDAO.getAll();
    } catch (SQLException e) {
      System.out.println("Error fetching signage data: " + e.getMessage());
    }

    populateContent(signageList);
    kioskList = FXCollections.observableArrayList();
    dateChos.setValue(LocalDate.of(2023, 5, 2));

    kioskList.add(0);
    KskBox.setItems(kioskList);
    dateChos.setOnAction(
        event -> {
          if (dateChos.getValue() != null) {
            date = java.sql.Timestamp.valueOf(dateChos.getValue().atStartOfDay());
            try {
              kioskList = FXCollections.observableArrayList(DataManager.getKiosks(date));
              KskBox.setItems(kioskList);
              populateContentForDate(
                  date); // Add this line to display signage for the selected date
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

    submit.setOnAction( // Change setOnMouseClicked to setOnAction
        event -> {
          try {
            kiosksForDate = DataManager.getSignage(KskBox.getValue(), date);
            populateContentForKiosk(kiosksForDate); // Add this line to display kiosk signage
          } catch (SQLException e) {
            System.out.println(e);
          }
        });

    // set default date to 2023-05-02
  }

  private void populateContentForDate(Timestamp selectedDate) {
    SignageDAOImpl signageDAO = new SignageDAOImpl();
    ArrayList<Signage> signageList = new ArrayList<>();
    ArrayList<Signage> filteredSignageList = new ArrayList<>();

    try {
      signageList = signageDAO.getAll();
    } catch (SQLException e) {
      System.out.println("Error fetching signage data: " + e.getMessage());
    }

    // Filter signageList based on the selected date
    for (Signage signage : signageList) {
      if (signage
          .getDate()
          .toLocalDateTime()
          .toLocalDate()
          .equals(selectedDate.toLocalDateTime().toLocalDate())) {
        filteredSignageList.add(signage);
      }
    }

    // Pass the filtered list to the populateContent method
    populateContent(filteredSignageList);
  }

  private void populateContent(ArrayList<Signage> signageList) {
    contentVBox.getChildren().clear();

    for (Signage data : signageList) {
      HBox row = new HBox();
      row.setSpacing(10); // Set spacing between elements
      row.setPadding(new Insets(5, 0, 5, 0)); // Set padding for the HBox

      // Create Labels for each data field
      Label directionArrow = new Label(data.getArrowDirection().toString());
      Label kioskId = new Label(String.valueOf(data.getKioskId()));
      Label longName = new Label(data.getLongName());
      Label date = new Label(data.getDate().toString());

      // Add Labels to HBox
      row.getChildren().addAll(directionArrow, kioskId, longName, date);

      // Add HBox to contentVBox
      contentVBox.getChildren().add(row);
    }
  }

  @FXML
  private void handleSubmitButtonAction(ActionEvent event) {
    SignageDAOImpl signageDAO = new SignageDAOImpl();
    ArrayList<Signage> signageList = new ArrayList<>();

    try {
      signageList = signageDAO.getAll();
    } catch (SQLException e) {
      System.out.println("Error fetching signage data: " + e.getMessage());
    }

    populateContent(signageList);
  }

  private void populateContentForKiosk(ArrayList<String> kiosksForDate) {
    contentVBox.getChildren().clear();

    for (String kioskData : kiosksForDate) {
      HBox row = new HBox();
      row.setSpacing(10); // Set spacing between elements
      row.setPadding(new Insets(5, 0, 5, 0)); // Set padding for the HBox

      // Replace placeholders with actual data
      Label directionArrow = new Label(/* Direction arrow data */ );
      Label kioskId = new Label(/* Kiosk ID data */ );
      Label longName = new Label(/* Long name data */ );
      Label date = new Label(/* Date data */ );

      // Add Labels to HBox
      row.getChildren().addAll(directionArrow, kioskId, longName, date);

      // Add HBox to contentVBox
      contentVBox.getChildren().add(row);
    }
  }

  private void getSignage() {}
}
