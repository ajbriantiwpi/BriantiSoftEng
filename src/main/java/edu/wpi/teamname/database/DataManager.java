package edu.wpi.teamname.database;

import edu.wpi.teamname.navigation.Edge;
import edu.wpi.teamname.navigation.LocationName;
import edu.wpi.teamname.navigation.Move;
import edu.wpi.teamname.navigation.Node;
import edu.wpi.teamname.servicerequest.ItemsOrdered;
import edu.wpi.teamname.servicerequest.ServiceRequest;
import edu.wpi.teamname.servicerequest.requestitem.Flower;
import edu.wpi.teamname.servicerequest.requestitem.Meal;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DataManager {
  private static Connection connection;
  private static String DB_URL =
      "jdbc:postgresql://database.cs.wpi.edu:5432/teamddb?currentSchema=\"teamD\"";
  private static String DB_PASSWORD = "teamd40";
  private static String DB_USER = "teamd";

  /**
   * Main function to connect to the database
   *
   * @return a Connection to a database
   */
  public static Connection DbConnection() throws SQLException {
    if (connection == null || connection.isClosed()) {
      System.out.print("--- Connecting To Database... ---");
      try {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        System.out.println(" Successfully connected to database!");
      } catch (SQLException e) {
        System.out.println(" Connection Failed! Check output console");
        e.printStackTrace();
        connection = null;
      } catch (ClassNotFoundException e) {
        System.out.println(e);
        throw new RuntimeException(e);
      }
    }
    return connection;
  }

  /**
   * * Updates the connection arguements
   *
   * @param url
   * @param username
   * @param password
   */
  public static void configConnection(String url, String username, String password) {
    DB_URL = url;
    DB_USER = username;
    DB_PASSWORD = password;
  }

  // ------------------------DAO Methods------------------------

  /** */
  public static void syncMove(Move move) throws SQLException {
    MoveDAOImpl moveDAO = new MoveDAOImpl();
    moveDAO.sync(move);
  }

  /** */
  public static void syncNode(Node node) throws SQLException {
    NodeDAOImpl nodeDAO = new NodeDAOImpl();
    nodeDAO.sync(node);
  }

  /** */
  public static void syncEdge(Edge edge) throws SQLException {
    EdgeDAOImpl edgeDAO = new EdgeDAOImpl();
    edgeDAO.sync(edge);
  }

  /** */
  public static void syncItemsOrdered(ItemsOrdered itemsOrdered) throws SQLException {
    ItemsOrderedDAOImpl itemsOrderedDao = new ItemsOrderedDAOImpl();
    itemsOrderedDao.sync(itemsOrdered);
  }

  /** */
  public static void syncServiceRequest(ServiceRequest serviceRequest) throws SQLException {
    ServiceRequestDAOImpl serviceRequestDAO = new ServiceRequestDAOImpl();
    serviceRequestDAO.sync(serviceRequest);
  }

  /** */
  public static void syncLogin(Login login) throws SQLException {
    LoginDAOImpl loginDAO = new LoginDAOImpl();
    loginDAO.sync(login);
  }

  /** */
  public static void syncFlower(Flower flower) throws SQLException {
    FlowerDAOImpl flowerDAO = new FlowerDAOImpl();
    flowerDAO.sync(flower);
  }

  /** */
  public static void syncMeal(Meal meal) throws SQLException {
    MealDAOImpl mealDAO = new MealDAOImpl();
    mealDAO.sync(meal);
  }

  /** */
  public static void syncLocationName(LocationName locationName) throws SQLException {
    LocationNameDAOImpl locationNameDAO = new LocationNameDAOImpl();
    locationNameDAO.sync(locationName);
  }

  /** */
  public static void addMoves(Move move) throws SQLException {
    MoveDAOImpl moveDAO = new MoveDAOImpl();
    moveDAO.add(move);
  }

  /** */
  public static void addNode(Node node) throws SQLException {
    NodeDAOImpl nodeDAO = new NodeDAOImpl();
    nodeDAO.add(node);
  }

  /** */
  public static void addEdge(Edge edge) throws SQLException {
    EdgeDAOImpl edgeDAO = new EdgeDAOImpl();
    edgeDAO.add(edge);
  }

  /** */
  public static void addItemsOrdered(ItemsOrdered itemsOrdered) throws SQLException {
    ItemsOrderedDAOImpl itemsOrderedDAO = new ItemsOrderedDAOImpl();
    itemsOrderedDAO.add(itemsOrdered);
  }

  /** */

  /** */
  public static void addServiceRequest(ServiceRequest serviceRequest) throws SQLException {
    ServiceRequestDAOImpl serviceRequestDAO = new ServiceRequestDAOImpl();
    serviceRequestDAO.add(serviceRequest);
  }

  /** */
  public static void addLogin(Login login) throws SQLException {
    LoginDAOImpl loginDAO = new LoginDAOImpl();
    loginDAO.add(login);
  }

  /** */
  public static void addFlower(Flower flower) throws SQLException {
    FlowerDAOImpl flowerDAO = new FlowerDAOImpl();
    flowerDAO.add(flower);
  }

  /** */
  public static void addMeal(Meal meal) throws SQLException {
    MealDAOImpl mealDAO = new MealDAOImpl();
    mealDAO.add(meal);
  }

  /** */
  public static void addLocationName(LocationName locationName) throws SQLException {
    LocationNameDAOImpl locationNameDAO = new LocationNameDAOImpl();
    locationNameDAO.add(locationName);
  }

  /** */
  public static void deleteMove(Move move) throws SQLException {
    MoveDAOImpl moveDAO = new MoveDAOImpl();
    moveDAO.delete(move);
  }

  /** */
  public static void deleteNode(Node node) throws SQLException {
    NodeDAOImpl nodeDAO = new NodeDAOImpl();
    nodeDAO.delete(node);
  }

  /** */
  public static void deleteEdge(Edge edge) throws SQLException {
    EdgeDAOImpl edgeDAO = new EdgeDAOImpl();
    edgeDAO.delete(edge);
  }

  /** */
  public static void deleteItemsOrdered(ItemsOrdered itemsOrdered) throws SQLException {
    ItemsOrderedDAOImpl itemsOrderedDAO = new ItemsOrderedDAOImpl();
    itemsOrderedDAO.delete(itemsOrdered);
  }

  /** */
  public static void deleteServiceRequest(ServiceRequest serviceRequest) throws SQLException {
    ServiceRequestDAOImpl serviceRequestDAO = new ServiceRequestDAOImpl();
    serviceRequestDAO.delete(serviceRequest);
  }

  /** */
  public static void deleteServiceRequestsWithItems(ServiceRequest serviceRequest)
      throws SQLException {
    ServiceRequestDAOImpl serviceRequestDAO = new ServiceRequestDAOImpl();
    serviceRequestDAO.deleteWithItems(serviceRequest);
  }

  /** */
  public static void deleteLogin(Login login) throws SQLException {
    LoginDAOImpl loginDAO = new LoginDAOImpl();
    loginDAO.delete(login);
  }

  /** */
  public static void deleteFlower(Flower flower) throws SQLException {
    FlowerDAOImpl flowerDAO = new FlowerDAOImpl();
    flowerDAO.delete(flower);
  }

  /** */
  public static void deleteMeals(Meal meal) throws SQLException {
    MealDAOImpl mealDAO = new MealDAOImpl();
    mealDAO.delete(meal);
  }

  /** */
  public static void deleteLocationName(LocationName locationName) throws SQLException {
    LocationNameDAOImpl locationNameDAO = new LocationNameDAOImpl();
    locationNameDAO.delete(locationName);
  }

  /** @return ArrayList<Move> */
  public static ArrayList<Move> getAllMoves() throws SQLException {
    MoveDAOImpl moveDAO = new MoveDAOImpl();
    return moveDAO.getAll();
  }

  /** @return ArrayList<Node> */
  public static ArrayList<Node> getAllNodes() throws SQLException {
    NodeDAOImpl nodeDAO = new NodeDAOImpl();
    return nodeDAO.getAll();
  }

  /** @return ArrayList<Edge> */
  public static ArrayList<Edge> getAllEdges() throws SQLException {
    EdgeDAOImpl edgeDAO = new EdgeDAOImpl();
    return edgeDAO.getAll();
  }

  /** @return ArrayList<ItemsOrdered> */
  public static ArrayList<ItemsOrdered> getAllItemsOrdered() throws SQLException {
    return (new ItemsOrderedDAOImpl()).getAll();
  }

  /** @return ArrayList<ServiceRequest> */
  public static ArrayList<ServiceRequest> getAllServiceRequests() throws SQLException {
    return (new ServiceRequestDAOImpl()).getAll();
  }

  /** @return ArrayList<Login> */
  public static ArrayList<Login> getAllLogins() throws SQLException {
    return (new LoginDAOImpl()).getAll();
  }

  /** @return ArrayList<Flower> */
  public static ArrayList<Flower> getAllFlowers() throws SQLException {
    return (new FlowerDAOImpl()).getAll();
  }

  /** @return ArrayList<Meal> */
  public static ArrayList<Meal> getAllMeals() throws SQLException {
    return (new MealDAOImpl()).getAll();
  }

  /** @return ArrayList<LocationName> */
  public static ArrayList<LocationName> getAllLocationNames() throws SQLException {
    LocationNameDAOImpl locationNameDAO = new LocationNameDAOImpl();
    return locationNameDAO.getAll();
  }

  public static Flower getFlower(int id) throws SQLException {
    FlowerDAOImpl flowerDAO = new FlowerDAOImpl();
    return flowerDAO.getFlower(id);
  }

  public static ItemsOrdered getItemOrdered(int requestID, int itemID) throws SQLException {
    ItemsOrderedDAOImpl itemsOrderedDAO = new ItemsOrderedDAOImpl();
    return itemsOrderedDAO.getItemOrdered(requestID, itemID);
  }

  public static LocationName getLocationName(String name) throws SQLException {
    LocationNameDAOImpl locationNameDAO = new LocationNameDAOImpl();
    return locationNameDAO.getLocationName(name);
  }

  public static Login getLogin(String username) throws SQLException {
    LoginDAOImpl loginDAO = new LoginDAOImpl();
    return loginDAO.getLogin(username);
  }

  public static Meal getMeal(int id) throws SQLException {
    MealDAOImpl mealDAO = new MealDAOImpl();
    return mealDAO.getMeal(id);
  }

  public static Node getNode(int id) throws SQLException {
    NodeDAOImpl nodeDAO = new NodeDAOImpl();
    return nodeDAO.getNode(id);
  }

  public static ServiceRequest getServiceRequest(int id) throws SQLException {
    ServiceRequestDAOImpl serviceRequestDAO = new ServiceRequestDAOImpl();
    return serviceRequestDAO.getServiceRequest(id);
  }

  public static List<String[]> parseCSVAndUploadToPostgreSQL(String csvFilePath)
      throws SQLException {
    List<String[]> csvData = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
      String line;

      while ((line = br.readLine()) != null) {
        StringTokenizer st = new StringTokenizer(line, ",");
        List<String> row = new ArrayList<>();

        while (st.hasMoreTokens()) {
          row.add(st.nextToken());
        }

        csvData.add(row.toArray(new String[0]));
      }
    } catch (IOException e) {
      System.err.println("Error reading CSV file: " + e.getMessage());
    }

    return csvData;
  }

  /**
   * *
   *
   * @param requestID
   * @param staffName
   * @throws SQLException
   */
  public static void uploadStaffNameToServiceRequest(int requestID, String staffName)
      throws SQLException {
    ServiceRequestDAOImpl serviceRequestDAO = new ServiceRequestDAOImpl();
    serviceRequestDAO.uploadStaffName(requestID, staffName);
  }
}
