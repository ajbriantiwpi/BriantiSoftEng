package edu.wpi.teamname.controllers;

import edu.wpi.teamname.ThemeSwitch;
import edu.wpi.teamname.database.ServiceRequestDAOImpl;
import edu.wpi.teamname.servicerequest.RequestType;
import edu.wpi.teamname.servicerequest.ServiceRequest;
import edu.wpi.teamname.servicerequest.Status;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

/**
 * The ServiceRequestAnalyticsController class is responsible for populating and managing the
 * Service Request Analytics view.
 */
public class ServiceRequestAnalyticsController {
  @FXML AnchorPane root;
  @FXML private BarChart<String, Number> barChart;
  @FXML private PieChart pieChart;

  @FXML private ComboBox piechartCombo;
  @FXML private ComboBox barChartCombo;
  // Declare count variables at the class level
  private Map<RequestType, Integer> requestTypeCounts;
  private Map<Status, Integer> statusCounts;
  private Map<String, Integer> requesterCounts;
  private Map<LocalDate, Integer> dailyRequestCounts;
  private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  // bar chart list: Service Requests Per Day, Service Requests By Type
  // pie chart list:Service Requests By Status
  @FXML
  private void initialize() {
    ThemeSwitch.switchTheme(root);

    barChartCombo.getItems().addAll("Service Requests Per Day", "Service Requests By Type");
    piechartCombo.getItems().addAll("Service Requests By Status");

    barChartCombo
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateBarChart(newValue);
              }
            });

    piechartCombo
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updatePieChart(newValue);
              }
            });

    populateCharts();
  }
  /** Updates the BarChart based on the selected option in the ComboBox. */
  private void updateBarChart(String selectedOption) {
    if (selectedOption == null) return;

    barChart.getData().clear();

    XYChart.Series<String, Number> barChartData = new XYChart.Series<>();

    switch (selectedOption) {
      case "Service Requests Per Day":
        for (Map.Entry<LocalDate, Integer> entry : dailyRequestCounts.entrySet()) {
          barChartData
              .getData()
              .add(new XYChart.Data<>(entry.getKey().format(dateFormatter), entry.getValue()));
        }
        break;
      case "Service Requests By Type":
        for (Map.Entry<RequestType, Integer> entry : requestTypeCounts.entrySet()) {
          barChartData
              .getData()
              .add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
        }
        break;
    }

    barChart.getData().add(barChartData);
  }

  /** Updates the PieChart based on the selected option in the ComboBox. */
  private void updatePieChart(String selectedOption) {
    if (selectedOption == null) return;

    pieChart.getData().clear();

    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

    switch (selectedOption) {
      case "Service Requests By Status":
        for (Map.Entry<Status, Integer> entry : statusCounts.entrySet()) {
          pieChartData.add(new PieChart.Data(entry.getKey().toString(), entry.getValue()));
        }
        break;
    }

    pieChart.setData(pieChartData);
  }

  /** Populates the charts with data obtained from the database. */
  public void populateCharts() {
    ServiceRequestDAOImpl serviceRequestDAO = new ServiceRequestDAOImpl();
    List<ServiceRequest> serviceRequests = new ArrayList<>();

    try {
      serviceRequests = serviceRequestDAO.getAll();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    ParentController.titleString.set("Service Request Analytics");

    requestTypeCounts = new HashMap<>();
    statusCounts = new HashMap<>();
    requesterCounts = new HashMap<>();
    dailyRequestCounts = new HashMap<>(); // Add this line

    for (ServiceRequest request : serviceRequests) {
      requestTypeCounts.merge(request.getRequestType(), 1, Integer::sum);
      statusCounts.merge(request.getStatus(), 1, Integer::sum);
      requesterCounts.merge(request.getRequestMadeBy(), 1, Integer::sum);
      LocalDate date = request.getRequestedAt().toLocalDateTime().toLocalDate();
      dailyRequestCounts.put(date, dailyRequestCounts.getOrDefault(date, 0) + 1);
    }

    // Call update methods to populate the charts
    updateBarChart((String) barChartCombo.getSelectionModel().getSelectedItem());
    updatePieChart((String) piechartCombo.getSelectionModel().getSelectedItem());
  }
}
