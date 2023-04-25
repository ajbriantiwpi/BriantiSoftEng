package edu.wpi.teamname.controllers;

import edu.wpi.teamname.database.DataManager;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DataController implements Initializable {
  @FXML private ComboBox<String> importComboBox;
  @FXML private RadioButton wpiButton;
  @FXML private RadioButton awsButton;

  @FXML private ComboBox<String> exportComboBox;

  @FXML private Button importButton;

  @FXML private Button exportButton;

  private static final String[] FIELDS = {
    "Alert", "Conference Rooms", "Edge", "Employee", "Employee Type", "Flowers", "Furniture",
    "Items Ordered", "Location Name", "Meal", "Medical Supplies", "Move", "Node", "Office Supply",
    "Path Messages", "Service Request", "Signage"
  };

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ParentController.titleString.set("Data Manager");
    importComboBox.getItems().addAll(FIELDS);
    exportComboBox.getItems().addAll(FIELDS);

    importButton.setOnAction(e -> onImportButtonClicked());
    exportButton.setOnAction(e -> onExportButtonClicked());

    // Create a ToggleGroup to ensure only one button can be selected at a time
    ToggleGroup databaseToggleGroup = new ToggleGroup();
    wpiButton.setToggleGroup(databaseToggleGroup);
    awsButton.setToggleGroup(databaseToggleGroup);

    // Hook up the methods to the toggle buttons
    wpiButton.setOnAction(
        e -> {
          try {
            DataManager.connectToWPI();
          } catch (SQLException ex) {
            ex.printStackTrace();
          }
        });

    awsButton.setOnAction(
        e -> {
          try {
            DataManager.connectToAWS();
          } catch (SQLException ex) {
            ex.printStackTrace();
          }
        });
  }

  private void onImportButtonClicked() {
    FileChooser fileChooser = new FileChooser();
    String selectedItem = importComboBox.getSelectionModel().getSelectedItem();
    fileChooser.setTitle("Open CSV File");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

    Stage stage = (Stage) importButton.getScene().getWindow();
    File csvFile = fileChooser.showOpenDialog(stage);
    if (selectedItem != null && selectedItem.equals("Edge")) {
      if (csvFile != null) {
        try {
          DataManager.uploadEdge(csvFile.getPath());
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    } else if (selectedItem != null && selectedItem.equals("Node")) {
      if (csvFile != null) {
        try {
          DataManager.uploadNode(csvFile.getPath());
        } catch (SQLException | ParseException e) {
          e.printStackTrace();
        }
      }
    } else if (selectedItem != null && selectedItem.equals("Conference Room")) {
      if (csvFile != null) {
        //                try {
        //                    DataManager.upload(csvFile.getPath());
        //                } catch (SQLException | ParseException e) {
        //                    e.printStackTrace();
        //                }
      }
    } else if (selectedItem != null && selectedItem.equals("Alert")) {
      if (csvFile != null) {
        //                try {
        //                    DataManager.uploadNode(csvFile.getPath());
        //                } catch (SQLException | ParseException e) {
        //                    e.printStackTrace();
        //                }
      } else if (selectedItem != null && selectedItem.equals("Employee")) {
        if (csvFile != null) {
          try {
            DataManager.uploadEmployee(csvFile.getPath());
          } catch (SQLException | ParseException e) {
            e.printStackTrace();
          }
        }
      } else if (selectedItem != null && selectedItem.equals("Employee Type")) {
        if (csvFile != null) {
          //                    try {
          //                        DataManager.uploadEmployeeType(csvFile.getPath());
          //                    } catch (SQLException | ParseException e) {
          //                        e.printStackTrace();
          //                    }
        }
      } else if (selectedItem != null && selectedItem.equals("Flowers")) {
        if (csvFile != null) {
          try {
            DataManager.uploadFlower(csvFile.getPath());
          } catch (SQLException | ParseException e) {
            e.printStackTrace();
          }
        }
      }
    } else if (selectedItem != null && selectedItem.equals("Furniture")) {
      if (csvFile != null) {
        try {
          DataManager.uploadFurniture(csvFile.getPath());
        } catch (SQLException | ParseException e) {
          e.printStackTrace();
        }
      }
    } else if (selectedItem != null && selectedItem.equals("Items Ordered")) {
      if (csvFile != null) {
        try {
          DataManager.uploadItemsOrdered(csvFile.getPath());
        } catch (SQLException | ParseException e) {
          e.printStackTrace();
        }
      }
    } else if (selectedItem != null && selectedItem.equals("Location Name")) {
      if (csvFile != null) {
        try {
          DataManager.uploadLocationName(csvFile.getPath());
        } catch (SQLException | ParseException e) {
          e.printStackTrace();
        }
      }
    } else if (selectedItem != null && selectedItem.equals("Meal")) {
      if (csvFile != null) {
        try {
          DataManager.uploadMeal(csvFile.getPath());
        } catch (SQLException | ParseException e) {
          e.printStackTrace();
        }
      }
    } else if (selectedItem != null && selectedItem.equals("Medical Supplies")) {
      if (csvFile != null) {
        try {
          DataManager.uploadMedicalSupply(csvFile.getPath());
        } catch (SQLException | ParseException e) {
          e.printStackTrace();
        }
      }
    } else if (selectedItem != null && selectedItem.equals("Move")) {
      if (csvFile != null) {
        try {
          DataManager.uploadMove(csvFile.getPath());
        } catch (SQLException | ParseException e) {
          e.printStackTrace();
        }
      }
    } else if (selectedItem != null && selectedItem.equals("Office Supply")) {
      if (csvFile != null) {
        try {
          DataManager.uploadOfficeSupply(csvFile.getPath());
        } catch (SQLException | ParseException e) {
          e.printStackTrace();
        }
      }
    } else if (selectedItem != null && selectedItem.equals("Path Messages")) {
      if (csvFile != null) {
        //                try {
        //                    DataManager.uploadPathMessages(csvFile.getPath());
        //                } catch (SQLException | ParseException e) {
        //                    e.printStackTrace();
        //                }
      }
    } else if (selectedItem != null && selectedItem.equals("Service Requests")) {
      if (csvFile != null) {
        try {
          DataManager.uploadServiceRequest(csvFile.getPath());
        } catch (SQLException | ParseException e) {
          e.printStackTrace();
        }
      }
    } else if (selectedItem != null && selectedItem.equals("Signage")) {
      if (csvFile != null) {
        try {
          DataManager.uploadSignage(csvFile.getPath());
        } catch (SQLException | ParseException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void onExportButtonClicked() {
    String selectedItem = exportComboBox.getSelectionModel().getSelectedItem();
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save CSV File");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

    Stage stage = (Stage) exportButton.getScene().getWindow();
    File csvFile = fileChooser.showSaveDialog(stage);
    if (selectedItem != null && selectedItem.equals("Edge")) {

      if (csvFile != null) {
        try {
          DataManager.exportEdgeToCSV(csvFile.getPath());
        } catch (SQLException | IOException e) {
          e.printStackTrace();
        }
      }
    } else if (selectedItem != null && selectedItem.equals("Node")) {
      if (csvFile != null) {
        try {
          DataManager.exportNodeToCSV(csvFile.getPath());
        } catch (SQLException e) {
          e.printStackTrace();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    } else if (selectedItem != null && selectedItem.equals("Conference Room")) {
      if (csvFile != null) {
        //                try {
        //                    DataManager.exportConferenceRoomToCSV(csvFile.getPath());
        //                } catch (SQLException | ParseException e) {
        //                    e.printStackTrace();
        //                }
      }
    } else if (selectedItem != null && selectedItem.equals("Alert")) {
      if (csvFile != null) {
        //                try {
        //                    DataManager.exportAlertToCSV(csvFile.getPath());
        //                } catch (SQLException | ParseException e) {
        //                    e.printStackTrace();
        //                }
      } else if (selectedItem != null && selectedItem.equals("Employee")) {
        if (csvFile != null) {
          try {
            DataManager.exportEmployeeToCSV(csvFile.getPath());
          } catch (SQLException e) {
            e.printStackTrace();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      } else if (selectedItem != null && selectedItem.equals("Employee Type")) {
        if (csvFile != null) {
          //                    try {
          //                        DataManager.exportEmployeeTypeToCSV(csvFile.getPath());
          //                    } catch (SQLException | ParseException e) {
          //                        e.printStackTrace();
          //                    }
        }
      } else if (selectedItem != null && selectedItem.equals("Flowers")) {
        if (csvFile != null) {
          try {
            DataManager.exportFlowersToCSV(csvFile.getPath());
          } catch (SQLException e) {
            e.printStackTrace();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      }
    } else if (selectedItem != null && selectedItem.equals("Furniture")) {
      if (csvFile != null) {
        try {
          DataManager.exportFurnitureToCSV(csvFile.getPath());
        } catch (SQLException e) {
          e.printStackTrace();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    } else if (selectedItem != null && selectedItem.equals("Items Ordered")) {
      if (csvFile != null) {
        try {
          DataManager.exportItemsOrderedToCSV(csvFile.getPath());
        } catch (SQLException e) {
          e.printStackTrace();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    } else if (selectedItem != null && selectedItem.equals("Location Name")) {
      if (csvFile != null) {
        try {
          DataManager.exportLocationNameToCSV(csvFile.getPath());
        } catch (SQLException e) {
          e.printStackTrace();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    } else if (selectedItem != null && selectedItem.equals("Meal")) {
      if (csvFile != null) {
        try {
          DataManager.exportMealToCSV(csvFile.getPath());
        } catch (SQLException e) {
          e.printStackTrace();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    } else if (selectedItem != null && selectedItem.equals("Medical Supplies")) {
      if (csvFile != null) {
        try {
          DataManager.exportMedicalSupplyToCSV(csvFile.getPath());
        } catch (SQLException e) {
          e.printStackTrace();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    } else if (selectedItem != null && selectedItem.equals("Move")) {
      if (csvFile != null) {
        try {
          DataManager.exportMoveToCSV(csvFile.getPath());
        } catch (SQLException e) {
          e.printStackTrace();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    } else if (selectedItem != null && selectedItem.equals("Office Supply")) {
      if (csvFile != null) {
        try {
          DataManager.exportOfficeSupplyToCSV(csvFile.getPath());
        } catch (SQLException e) {
          e.printStackTrace();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    } else if (selectedItem != null && selectedItem.equals("Path Messages")) {
      if (csvFile != null) {
        //                try {
        //                    DataManager.exportPathMessagesToCSV(csvFile.getPath());
        //                } catch (SQLException | ParseException e) {
        //                    e.printStackTrace();
        //                }
      }
    } else if (selectedItem != null && selectedItem.equals("Service Requests")) {
      if (csvFile != null) {
        try {
          DataManager.exportServiceRequestToCSV(csvFile.getPath());
        } catch (SQLException e) {
          e.printStackTrace();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    } else if (selectedItem != null && selectedItem.equals("Signage")) {
      if (csvFile != null) {
        try {
          DataManager.exportSignageToCSV(csvFile.getPath());
        } catch (SQLException e) {
          e.printStackTrace();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }
}
