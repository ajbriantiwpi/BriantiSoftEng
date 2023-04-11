package edu.wpi.teamname.database;

import edu.wpi.teamname.navigation.Edge;
import edu.wpi.teamname.navigation.LocationName;
import edu.wpi.teamname.navigation.Move;
import edu.wpi.teamname.navigation.Node;
import edu.wpi.teamname.servicerequest.ItemsOrdered;
import edu.wpi.teamname.servicerequest.ServiceRequest;
import edu.wpi.teamname.servicerequest.requestitem.Flower;
import edu.wpi.teamname.servicerequest.requestitem.Furniture;
import edu.wpi.teamname.servicerequest.requestitem.Meal;
import edu.wpi.teamname.servicerequest.requestitem.OfficeSupply;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
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
  public static void syncFurniture(Furniture furniture) throws SQLException {
    FurnitureDAOImpl furnitureDAO = new FurnitureDAOImpl();
    furnitureDAO.sync(furniture);
  }

  /** */
  public static void syncOfficeSupply(OfficeSupply officeSupply) throws SQLException {
    OfficeSupplyDAOImpl officeSupplyDAO = new OfficeSupplyDAOImpl();
    officeSupplyDAO.sync(officeSupply);
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
  public static void addFurniture(Furniture furniture) throws SQLException {
    FurnitureDAOImpl furnitureDAO = new FurnitureDAOImpl();
    furnitureDAO.add(furniture);
  }

  /** */
  public static void addOfficeSupply(OfficeSupply officeSupply) throws SQLException {
    OfficeSupplyDAOImpl officeSupplyDAO = new OfficeSupplyDAOImpl();
    officeSupplyDAO.add(officeSupply);
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

  /** */
  public static void deleteFurniture(Furniture furniture) throws SQLException {
    FurnitureDAOImpl furnitureDAO = new FurnitureDAOImpl();
    furnitureDAO.delete(furniture);
  }

  /** */
  public static void deleteOfficeSupply(OfficeSupply officeSupply) throws SQLException {
    OfficeSupplyDAOImpl officeSupplyDAO = new OfficeSupplyDAOImpl();
    officeSupplyDAO.delete(officeSupply);
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

  /** @return ArrayList<LocationName> */
  public static ArrayList<Furniture> getAllFurniture() throws SQLException {
    FurnitureDAOImpl furnitureDAO = new FurnitureDAOImpl();
    return furnitureDAO.getAll();
  }

  /** @return ArrayList<LocationName> */
  public static ArrayList<OfficeSupply> getAllOfficeSupplies() throws SQLException {
    OfficeSupplyDAOImpl officeSupplyDAO = new OfficeSupplyDAOImpl();
    return officeSupplyDAO.getAll();
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

  public static Furniture getFurniture(int id) throws SQLException {
    return FurnitureDAOImpl.getFurniture(id);
  }

  public static OfficeSupply getOfficeSupply(int id) throws SQLException {
    return OfficeSupplyDAOImpl.getOfficeSupply(id);
  }

  /**
   * * Parses a CSV after being given a String path and then returns a list of Strings after it
   * parses
   *
   * @param csvFilePath a String that represents a file path (must use "\\" instead of "\")
   * @throws SQLException
   */
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
   * Given a serviceRequestID and a staffName Update the given staff name at the certain service
   * request
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

  public static void uploadEdge(String path) throws SQLException {
    EdgeDAOImpl.uploadEdgeToPostgreSQL(path);
  }

  public static void uploadFlower(String path) throws SQLException, ParseException {
    FlowerDAOImpl.uploadFlowerToPostgreSQL(path);
  }

  public static void uploadFurniture(String path) throws SQLException, ParseException {
    FurnitureDAOImpl.uploadFurnitureToPostgreSQL(path);
  }

  public static void uploadItemsOrdered(String path) throws SQLException, ParseException {
    ItemsOrderedDAOImpl.uploadItemsOrderedToPostgreSQL(path);
  }

  public static void uploadLocationName(String path) throws SQLException, ParseException {
    LocationNameDAOImpl.uploadLocationNameToPostgreSQL(path);
  }

  public static void uploadLogin(String path) throws SQLException, ParseException {
    LoginDAOImpl.uploadLoginToPostgreSQL(path);
  }

  public static void uploadMeal(String path) throws SQLException, ParseException {
    MealDAOImpl.uploadMealToPostgreSQL(path);
  }

  public static void uploadMove(String path) throws SQLException, ParseException {
    MoveDAOImpl.uploadMoveToPostgreSQL(path);
  }

  public static void uploadNode(String path) throws SQLException, ParseException {
    NodeDAOImpl.uploadNodeToPostgreSQL(path);
  }

  public static void uploadOfficeSupply(String path) throws SQLException, ParseException {
    OfficeSupplyDAOImpl.uploadOfficeSupplyToPostgreSQL(path);
  }

  public static void uploadServiceRequest(String path) throws SQLException, ParseException {
    ServiceRequestDAOImpl.uploadServiceRequestToPostgreSQL(path);
  }

  public static void exportEdgeToCSV(String path) throws SQLException, IOException {
    EdgeDAOImpl.exportEdgeToCSV(path);
  }

  public static void exportFlowersToCSV(String path) throws SQLException, IOException {
    FlowerDAOImpl.exportFlowersToCSV(path);
  }

  public static void exportFurnitureToCSV(String path) throws SQLException, IOException {
    FurnitureDAOImpl.exportFurnitureToCSV(path);
  }

  public static void exportItemsOrderedToCSV(String path) throws SQLException, IOException {
    ItemsOrderedDAOImpl.exportItemsOrderedToCSV(path);
  }

  public static void exportLocationNameToCSV(String path) throws SQLException, IOException {
    LocationNameDAOImpl.exportLocationNameToCSV(path);
  }

  public static void exportLoginToCSV(String path) throws SQLException, IOException {
    LoginDAOImpl.exportLoginToCSV(path);
  }

  public static void exportMealToCSV(String path) throws SQLException, IOException {
    MealDAOImpl.exportMealToCSV(path);
  }

  public static void exportMoveToCSV(String path) throws SQLException, IOException {
    MoveDAOImpl.exportMoveToCSV(path);
  }

  public static void exportNodeToCSV(String path) throws SQLException, IOException {
    NodeDAOImpl.exportNodeToCSV(path);
  }

  public static void exportOfficeSupplyToCSV(String path) throws SQLException, IOException {
    OfficeSupplyDAOImpl.exportOfficeSupplyToCSV(path);
  }

  public static void exportServiceRequestToCSV(String path) throws SQLException, IOException {
    ServiceRequestDAOImpl.exportServiceRequestToCSV(path);
  }

  public static ArrayList<String> getNamesAlphabetically() throws SQLException {
    return LocationNameDAOImpl.getAllLongNames();
  }
}
