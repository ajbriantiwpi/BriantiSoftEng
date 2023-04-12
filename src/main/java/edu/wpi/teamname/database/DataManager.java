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

  /**
   * This method updates an existing Move object in the "Move" table in the database with the new
   * Move object.
   *
   * @param move the new Move object to be updated in the "Move" table
   * @throws SQLException if there is a problem accessing the database
   */
  public static void syncMove(Move move) throws SQLException {
    MoveDAOImpl moveDAO = new MoveDAOImpl();
    moveDAO.sync(move);
  }

  /**
   * This method updates an existing Node object in the "Node" table in the database with the new
   * Node object.
   *
   * @param node the new Node object to be updated in the "Node" table
   * @throws SQLException if there is a problem accessing the database
   */
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

  /**
   * This method updates an existing ServiceRequest object in the "ServiceRequest" table in the
   * database with the new ServiceRequest object.
   *
   * @param serviceRequest the new ServiceRequest object to be updated in the "ServiceRequest" table
   * @throws SQLException if there is a problem accessing the database
   */
  public static void syncServiceRequest(ServiceRequest serviceRequest) throws SQLException {
    ServiceRequestDAOImpl serviceRequestDAO = new ServiceRequestDAOImpl();
    serviceRequestDAO.sync(serviceRequest);
  }

  /**
   * This method updates an existing Login object in the "Login" table in the database with the new
   * Login object.
   *
   * @param login the new Login object to be updated in the "Login" table
   * @throws SQLException if there is a problem accessing the database
   */
  public static void syncLogin(Login login) throws SQLException {
    LoginDAOImpl loginDAO = new LoginDAOImpl();
    loginDAO.sync(login);
  }

  /** */
  public static void syncFlower(Flower flower) throws SQLException {
    FlowerDAOImpl flowerDAO = new FlowerDAOImpl();
    flowerDAO.sync(flower);
  }

  /**
   * This method updates an existing Meal object in the "Meal" table in the database with the new
   * Meal object.
   *
   * @param meal the new Meal object to be updated in the "Meal" table
   * @throws SQLException if there is a problem accessing the database
   */
  public static void syncMeal(Meal meal) throws SQLException {
    MealDAOImpl mealDAO = new MealDAOImpl();
    mealDAO.sync(meal);
  }

  /**
   * This method updates an existing LocationName object in the "LocationName" table in the database
   * with the new LocationName object.
   *
   * @param locationName the new LocationName object to be updated in the "LocationName" table
   * @throws SQLException if there is a problem accessing the database
   */
  public static void syncLocationName(LocationName locationName) throws SQLException {
    LocationNameDAOImpl locationNameDAO = new LocationNameDAOImpl();
    locationNameDAO.sync(locationName);
  }

  /** */
  public static void syncFurniture(Furniture furniture) throws SQLException {
    FurnitureDAOImpl furnitureDAO = new FurnitureDAOImpl();
    furnitureDAO.sync(furniture);
  }

  /**
   * This method updates an existing OfficeSupply object in the "OfficeSupply" table in the database
   * with the new OfficeSupply object.
   *
   * @param officeSupply the new OfficeSupply object to be updated in the "OfficeSupply" table
   * @throws SQLException if there is a problem accessing the database
   */
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

  /**
   * Adds a service request to the database, along with the corresponding order of items.
   *
   * @param serviceRequest The service request to be added to the database
   * @throws SQLException If an error occurs while accessing the database
   */
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

  /**
   * Deletes the ServiceRequest and all associated items from the database.
   *
   * @param serviceRequest The ServiceRequest to delete.
   * @throws SQLException if there is an error executing the SQL query.
   */
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

  /**
   * The method retrieves all the ServiceRequest objects from the "ServiceRequest" table in the
   * database.
   *
   * @return an ArrayList of the ServiceRequest objects in the database
   * @throws SQLException if there is a problem accessing the database
   */
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

  /**
   * The method retrieves all the LocationName objects from the "ItemsOrdered" table in the
   * database.
   *
   * @return an ArrayList of the ItemsOrdered objects in the database
   * @throws SQLException if there is a problem accessing the database
   */
  public static ArrayList<LocationName> getAllLocationNames() throws SQLException {
    LocationNameDAOImpl locationNameDAO = new LocationNameDAOImpl();
    return locationNameDAO.getAll();
  }

  /** @return ArrayList<LocationName> */
  public static ArrayList<Furniture> getAllFurniture() throws SQLException {
    FurnitureDAOImpl furnitureDAO = new FurnitureDAOImpl();
    return furnitureDAO.getAll();
  }

  /**
   * The method retrieves all the OfficeSupply objects from the "OfficeSupply" table in the
   * database.
   *
   * @return an ArrayList of the OfficeSupply objects in the database
   * @throws SQLException if there is a problem accessing the database
   */
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

  /**
   * This method retrieves a LocationName object with the specified name from the "LocationName"
   * table in the database.
   *
   * @param name the long name of the LocationName object to retrieve from the "LocationName" table
   * @return the LocationName object with the specified name, or null if not found
   * @throws SQLException if there is a problem accessing the database
   */
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

  /**
   * This method retrieves an ServiceRequest object with the specified ID from the "ServiceRequest"
   * table in the database.
   *
   * @param id the ID of the ServiceRequest object to retrieve from the "ServiceRequest" table
   * @return the ServiceRequest object with the specified ID, or null if not found
   * @throws SQLException if there is a problem accessing the database
   */
  public static ServiceRequest getServiceRequest(int id) throws SQLException {
    ServiceRequestDAOImpl serviceRequestDAO = new ServiceRequestDAOImpl();
    return serviceRequestDAO.getServiceRequest(id);
  }

  public static Furniture getFurniture(int id) throws SQLException {
    return FurnitureDAOImpl.getFurniture(id);
  }

  /**
   * This method retrieves an OfficeSupply object with the specified ID from the "OfficeSupply"
   * table in the database.
   *
   * @param id the ID of the OfficeSupply object to retrieve from the "OfficeSupply" table
   * @return the Flower object with the specified ID, or null if not found
   * @throws SQLException if there is a problem accessing the database
   */
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
   * Updates the staff name for a service request with the given request ID in the database.
   *
   * @param requestID the ID of the service request to update.
   * @param staffName the new staff name to set.
   * @throws SQLException if a database error occurs.
   */
  public static void uploadStaffNameToServiceRequest(int requestID, String staffName)
      throws SQLException {
    ServiceRequestDAOImpl serviceRequestDAO = new ServiceRequestDAOImpl();
    serviceRequestDAO.uploadStaffName(requestID, staffName);
  }

  public static void uploadLocationNameToPostgreSQL(String csvFilePath) throws SQLException {
    LocationNameDAOImpl locationNameDAO = new LocationNameDAOImpl();
    locationNameDAO.uploadLocationNameToPostgreSQL(csvFilePath);
  }

  public static void uploadEdge(String path) throws SQLException {
    EdgeDAOImpl.uploadEdgeToPostgreSQL(path);
  }

  public static void uploadFlower(String path) throws SQLException, ParseException {
    FlowerDAOImpl.uploadFlowerToPostgreSQL(path);
  }

  /**
   * Uploads CSV data to a PostgreSQL database table "Furniture"-also creates one if one does not
   * exist
   *
   * @param path a string that represents a file path (/ is illegal so you must use double//)
   * @throws SQLException if an error occurs while uploading the data to the database
   */
  public static void uploadFurniture(String path) throws SQLException, ParseException {
    FurnitureDAOImpl.uploadFurnitureToPostgreSQL(path);
  }

  /**
   * Uploads CSV data to a PostgreSQL database table "ItemsOrdered"-also creates one if one does not
   * exist
   *
   * @param path a string that represents a file path (/ is illegal so you must use double//)
   * @throws SQLException if an error occurs while uploading the data to the database
   */
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

  /**
   * Uploads CSV data to a PostgreSQL database table "OfficeSupply"-also creates one if one does not
   * exist
   *
   * @param path a string that represents a file path (/ is illegal so you must use double//)
   * @throws SQLException if an error occurs while uploading the data to the database
   */
  public static void uploadOfficeSupply(String path) throws SQLException, ParseException {
    OfficeSupplyDAOImpl.uploadOfficeSupplyToPostgreSQL(path);
  }

  /**
   * Uploads CSV data to a PostgreSQL database table "ServiceRequest"-also creates one if one does
   * not exist
   *
   * @param path a string that represents a file path (/ is illegal so you must use double//)
   * @throws SQLException if an error occurs while uploading the data to the database
   */
  public static void uploadServiceRequest(String path) throws SQLException, ParseException {
    ServiceRequestDAOImpl.uploadServiceRequestToPostgreSQL(path);
  }

  public static void exportEdgeToCSV(String path) throws SQLException, IOException {
    EdgeDAOImpl.exportEdgeToCSV(path);
  }

  /**
   * This method exports all the Flower objects from the "Flowers" table in the database to a CSV
   * file at the specified file path.
   *
   * @param path the file path of the CSV file to export the Flower objects to
   * @throws SQLException if there is a problem accessing the database
   * @throws IOException if there is a problem writing the CSV file
   */
  public static void exportFlowersToCSV(String path) throws SQLException, IOException {
    FlowerDAOImpl.exportFlowersToCSV(path);
  }

  /**
   * This method exports all the Furniture objects from the "Furniture" table in the database to a
   * CSV file at the specified file path.
   *
   * @param path the file path of the CSV file to export the Furniture objects to
   * @throws SQLException if there is a problem accessing the database
   * @throws IOException if there is a problem writing the CSV file
   */
  public static void exportFurnitureToCSV(String path) throws SQLException, IOException {
    FurnitureDAOImpl.exportFurnitureToCSV(path);
  }

  /**
   * This method exports all the ItemsOrdered objects from the "ItemsOrdered" table in the database
   * to a CSV file at the specified file path.
   *
   * @param path the file path of the CSV file to export the ItemsOrdered objects to
   * @throws SQLException if there is a problem accessing the database
   * @throws IOException if there is a problem writing the CSV file
   */
  public static void exportItemsOrderedToCSV(String path) throws SQLException, IOException {
    ItemsOrderedDAOImpl.exportItemsOrderedToCSV(path);
  }

  /**
   * This method exports all the LocationName objects from the "LocationName" table in the database
   * to a CSV file at the specified file path.
   *
   * @param path the file path of the CSV file to export the LocationName objects to
   * @throws SQLException if there is a problem accessing the database
   * @throws IOException if there is a problem writing the CSV file
   */
  public static void exportLocationNameToCSV(String path) throws SQLException, IOException {
    LocationNameDAOImpl.exportLocationNameToCSV(path);
  }

  /**
   * This method exports all the Login objects from the "Login" table in the database to a CSV file
   * at the specified file path.
   *
   * @param path the file path of the CSV file to export the Login objects to
   * @throws SQLException if there is a problem accessing the database
   * @throws IOException if there is a problem writing the CSV file
   */
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

  /**
   * This method exports all the OfficeSupply objects from the "OfficeSupply" table in the database
   * to a CSV file at the specified file path.
   *
   * @param path the file path of the CSV file to export the OfficeSupply objects to
   * @throws SQLException if there is a problem accessing the database
   * @throws IOException if there is a problem writing the CSV file
   */
  public static void exportOfficeSupplyToCSV(String path) throws SQLException, IOException {
    OfficeSupplyDAOImpl.exportOfficeSupplyToCSV(path);
  }

  /**
   * This method exports all the ServiceRequest objects from the "ServiceRequest" table in the
   * database to a CSV file at the specified file path.
   *
   * @param path the file path of the CSV file to export the ServiceRequest objects to
   * @throws SQLException if there is a problem accessing the database
   * @throws IOException if there is a problem writing the CSV file
   */
  public static void exportServiceRequestToCSV(String path) throws SQLException, IOException {
    ServiceRequestDAOImpl.exportServiceRequestToCSV(path);
  }

  /**
   * This method retrieves a list of all the long names of locations from the "LocationName" table
   * in the database.
   *
   * @return an ArrayList of Strings representing the long names of all locations in the
   *     "LocationName" table, ordered alphabetically
   * @throws SQLException if there is a problem accessing the database
   */
  public static ArrayList<String> getNamesAlphabetically() throws SQLException {
    return LocationNameDAOImpl.getAllLongNames();
  }

  /**
   * Returns a Room object containing all information about the node with the given ID. The
   * information includes the node's long name, short name, coordinates, node type, building, floor,
   * and the most recent date when the node's location was updated. The function queries the
   * database and joins the "LocationName" and "Move" tables to retrieve the necessary information.
   * It also filters the results by selecting only the information for the node with the given ID
   * and the most recent date prior to the current time. If no information is found for the given
   * ID, null is returned.
   *
   * @param id the ID of the node to retrieve information for
   * @return a Room object containing all information about the node, or null if no information is
   *     found
   * @throws SQLException if there is an error accessing the database
   */
  public static Room getAllNodeInfo(int id) throws SQLException {
    return NodeDAOImpl.getAllInfoOfNode(id);
  }
}
