package edu.wpi.teamname.controllers;

import edu.wpi.teamname.database.ServiceRequestDAOImpl;
import edu.wpi.teamname.servicerequest.RequestType;
import edu.wpi.teamname.servicerequest.ServiceRequest;
import edu.wpi.teamname.servicerequest.Status;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class ServiceRequestAnalyticsController {
  @FXML private BarChart<String, Number> barChart;
  @FXML private PieChart pieChart;
  @FXML private LineChart<String, Number> lineChart;

  @FXML
  private void initialize() {
    populateCharts();
  }

  public void populateCharts() {
    ServiceRequestDAOImpl serviceRequestDAO = new ServiceRequestDAOImpl();
    List<ServiceRequest> serviceRequests = new ArrayList<>();

    try {
      serviceRequests = serviceRequestDAO.getAll();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    Map<RequestType, Integer> requestTypeCounts = new HashMap<>();
    Map<Status, Integer> statusCounts = new HashMap<>();
    Map<String, Integer> requesterCounts = new HashMap<>();

    for (ServiceRequest request : serviceRequests) {
      requestTypeCounts.merge(request.getRequestType(), 1, Integer::sum);
      statusCounts.merge(request.getStatus(), 1, Integer::sum);
      requesterCounts.merge(request.getRequestMadeBy(), 1, Integer::sum);
    }

    // Populate BarChart
    XYChart.Series<String, Number> barChartData = new XYChart.Series<>();
    for (Map.Entry<RequestType, Integer> entry : requestTypeCounts.entrySet()) {
      barChartData.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
    }
    barChart.getData().add(barChartData);

    // Populate PieChart
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    for (Map.Entry<Status, Integer> entry : statusCounts.entrySet()) {
      pieChartData.add(new PieChart.Data(entry.getKey().toString(), entry.getValue()));
    }
    pieChart.setData(pieChartData);

    // Populate LineChart
    XYChart.Series<String, Number> lineChartData = new XYChart.Series<>();
    for (Map.Entry<String, Integer> entry : requesterCounts.entrySet()) {
      lineChartData.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
    }
    lineChart.getData().add(lineChartData);
  }
}
