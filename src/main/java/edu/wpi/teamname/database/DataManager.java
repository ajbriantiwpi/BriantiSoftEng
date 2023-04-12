package edu.wpi.teamname.database;

import edu.wpi.teamname.navigation.*;
import edu.wpi.teamname.servicerequest.ItemsOrdered;
import edu.wpi.teamname.servicerequest.ServiceRequest;
import edu.wpi.teamname.servicerequest.requestitem.Flower;
import edu.wpi.teamname.servicerequest.requestitem.Furniture;
import edu.wpi.teamname.servicerequest.requestitem.Meal;
import edu.wpi.teamname.servicerequest.requestitem.OfficeSupply;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
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
   * Main function to create all Database tables if they don't already exist
   *
   * @param createTableQuery a String that reps the query to create a table
   * @param tableName a String that reps the name of the table being checked
   */
  public static void createTableIfNotExists(String tableName, String createTableQuery)
      throws SQLException {
    connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    DatabaseMetaData dbm = connection.getMetaData();
    ResultSet rs = dbm.getTables(null, null, tableName, null);
    if (!rs.next()) { // table does not exist
      Statement statement = connection.createStatement();
      statement.executeUpdate(createTableQuery);
      statement.close();
    }
    rs.close();
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

  /**
   * This method updates an existing Edge object in the "Edge" table in the database with the new
   * Edge object.
   *
   * @param edge the new Edge object to be updated in the "Edge" table
   * @throws SQLException if there is a problem accessing the database
   */
  public static void syncEdge(Edge edge) throws SQLException {
    EdgeDAOImpl edgeDAO = new EdgeDAOImpl();
    edgeDAO.sync(edge);
  }

  /**
   * This method updates an existing ItemsOrdered object in the "ItemsOrdered" table in the database
   * with the new ItemsOrdered object.
   *
   * @param itemsOrdered the new ItemsOrdered object to be updated in the "ItemsOrdered" table
   * @throws SQLException if there is a problem accessing the database
   */
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

  /**
   * This method updates an existing Flower object in the "Flower" table in the database with the
   * new Flower object.
   *
   * @param flower the new Flower object to be updated in the "Flower" table
   * @throws SQLException if there is a problem accessing the database
   */
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

  /**
   * This method updates an existing Furniture object in the "Furniture" table in the database with
   * the new Furniture object.
   *
   * @param furniture the new Flower object to be updated in the "Furniture" table
   * @throws SQLException if there is a problem accessing the database
   */
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

  /**
   * This method adds a new Move object to the "Move" table in the database.
   *
   * @param move the Move object to be added to the "Move" table
   * @throws SQLException if there is a problem accessing the database
   */
  public static void addMoves(Move move) throws SQLException {
    MoveDAOImpl moveDAO = new MoveDAOImpl();
    moveDAO.add(move);
  }

  /**
   * This method adds a new Node object to the "Node" table in the database.
   *
   * @param node the Node object to be added to the "Node" table
   * @throws SQLException if there is a problem accessing the database
   */
  public static void addNode(Node node) throws SQLException {
    NodeDAOImpl nodeDAO = new NodeDAOImpl();
    nodeDAO.add(node);
  }

  /**
   * This method adds a new Edge object to the "Edge" table in the database.
   *
   * @param edge the Edge object to be added to the "Edge" table
   * @throws SQLException if there is a problem accessing the database
   */
  public static void addEdge(Edge edge) throws SQLException {
    EdgeDAOImpl edgeDAO = new EdgeDAOImpl();
    edgeDAO.add(edge);
  }

  /**
   * This method adds a new ItemsOrdered object to the "ItemsOrdered" table in the database.
   *
   * @param itemsOrdered the ItemsOrdered object to be added to the "ItemsOrdered" table
   * @throws SQLException if there is a problem accessing the database
   */
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

  /**
   * This method adds a new Login object to the "Login" table in the database.
   *
   * @param login the Login object to be added to the "Login" table
   * @throws SQLException if there is a problem accessing the database
   */
  public static void addLogin(Login login) throws SQLException {
    LoginDAOImpl loginDAO = new LoginDAOImpl();
    loginDAO.add(login);
  }

  /**
   * This method adds a new Flower object to the "Flowers" table in the database.
   *
   * @param flower the Flower object to be added to the "Flower" table
   * @throws SQLException if there is a problem accessing the database
   */
  public static void addFlower(Flower flower) throws SQLException {
    FlowerDAOImpl flowerDAO = new FlowerDAOImpl();
    flowerDAO.add(flower);
  }

  /**
   * This method adds a new Meal object to the "Meal" table in the database.
   *
   * @param meal the Meal object to be added to the "Meal" table
   * @throws SQLException if there is a problem accessing the database
   */
  public static void addMeal(Meal meal) throws SQLException {
    MealDAOImpl mealDAO = new MealDAOImpl();
    mealDAO.add(meal);
  }

  /**
   * This method adds a new LocationName object to the "LocationName" table in the database.
   *
   * @param locationName the LocationName object to be added to the "LocationName" table
   * @throws SQLException if there is a problem accessing the database
   */
  public static void addLocationName(LocationName locationName) throws SQLException {
    LocationNameDAOImpl locationNameDAO = new LocationNameDAOImpl();
    locationNameDAO.add(locationName);
  }

  /**
   * This method adds a new Furniture object to the "Furniture" table in the database.
   *
   * @param furniture the Furniture object to be added to the "Furniture" table
   * @throws SQLException if there is a problem accessing the database
   */
  public static void addFurniture(Furniture furniture) throws SQLException {
    FurnitureDAOImpl furnitureDAO = new FurnitureDAOImpl();
    furnitureDAO.add(furniture);
  }

  /**
   * This method adds a new OfficeSupply object to the "OfficeSupply" table in the database.
   *
   * @param officeSupply the OfficeSupply object to be added to the "OfficeSupply" table
   * @throws SQLException if there is a problem accessing the database
   */
  public static void addOfficeSupply(OfficeSupply officeSupply) throws SQLException {
    OfficeSupplyDAOImpl officeSupplyDAO = new OfficeSupplyDAOImpl();
    officeSupplyDAO.add(officeSupply);
  }

  /**
   * This method deletes the given Move object from the database
   *
   * @param move the Move object that will be deleted in the database
   * @throws SQLException if there is a problem accessing the database
   */
  public static void deleteMove(Move move) throws SQLException {
    MoveDAOImpl moveDAO = new MoveDAOImpl();
    moveDAO.delete(move);
  }

  /**
   * This method deletes the given Node object from the database
   *
   * @param node the Node object that will be deleted in the database
   * @throws SQLException if there is a problem accessing the database
   */
  public static void deleteNode(Node node) throws SQLException {
    NodeDAOImpl nodeDAO = new NodeDAOImpl();
    nodeDAO.delete(node);
  }

  /**
   * This method deletes the given Edge object from the database
   *
   * @param edge the Edge object that will be deleted in the database
   * @throws SQLException if there is a problem accessing the database
   */
  public static void deleteEdge(Edge edge) throws SQLException {
    EdgeDAOImpl edgeDAO = new EdgeDAOImpl();
    edgeDAO.delete(edge);
  }

  /**
   * This method deletes the given ItemsOrdered object from the database
   *
   * @param itemsOrdered the ItemsOrdered object that will be deleted in the database
   * @throws SQLException if there is a problem accessing the database
   */
  public static void deleteItemsOrdered(ItemsOrdered itemsOrdered) throws SQLException {
    ItemsOrderedDAOImpl itemsOrderedDAO = new ItemsOrderedDAOImpl();
    itemsOrderedDAO.delete(itemsOrdered);
  }

  /**
   * This method deletes the given ServiceRequest object from the database
   *
   * @param serviceRequest the ServiceRequest object that will be deleted in the database
   * @throws SQLException if there is a problem accessing the database
   */
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

  /**
   * This method deletes the given Login object from the database
   *
   * @param login the Login object that will be deleted in the database
   * @throws SQLException if there is a problem accessing the database
   */
  public static void deleteLogin(Login login) throws SQLException {
    LoginDAOImpl loginDAO = new LoginDAOImpl();
    loginDAO.delete(login);
  }

  /**
   * This method deletes the given Flower object from the database
   *
   * @param flower the Flower object that will be deleted in the database
   * @throws SQLException if there is a problem accessing the database
   */
  public static void deleteFlower(Flower flower) throws SQLException {
    FlowerDAOImpl flowerDAO = new FlowerDAOImpl();
    flowerDAO.delete(flower);
  }

  /**
   * This method deletes the given Meal object from the database
   *
   * @param meal the Meal object that will be deleted in the database
   * @throws SQLException if there is a problem accessing the database
   */
  public static void deleteMeals(Meal meal) throws SQLException {
    MealDAOImpl mealDAO = new MealDAOImpl();
    mealDAO.delete(meal);
  }

  /**
   * This method deletes the given LocationName object from the database
   *
   * @param locationName the LocationName object that will be deleted in the database
   * @throws SQLException if there is a problem accessing the database
   */
  public static void deleteLocationName(LocationName locationName) throws SQLException {
    LocationNameDAOImpl locationNameDAO = new LocationNameDAOImpl();
    locationNameDAO.delete(locationName);
  }

  /**
   * This method deletes the given Furniture object from the database
   *
   * @param furniture the Flower object that will be deleted in the database
   * @throws SQLException if there is a problem accessing the database
   */
  public static void deleteFurniture(Furniture furniture) throws SQLException {
    FurnitureDAOImpl furnitureDAO = new FurnitureDAOImpl();
    furnitureDAO.delete(furniture);
  }

  /**
   * This method deletes the given OfficeSupply object from the database
   *
   * @param officeSupply the OfficeSupply object that will be deleted in the database
   * @throws SQLException if there is a problem accessing the database
   */
  public static void deleteOfficeSupply(OfficeSupply officeSupply) throws SQLException {
    OfficeSupplyDAOImpl officeSupplyDAO = new OfficeSupplyDAOImpl();
    officeSupplyDAO.delete(officeSupply);
  }

  /**
   * The method retrieves all the Move objects from the "Move" table in the database.
   *
   * @return an ArrayList of the Move objects in the database
   * @throws SQLException if there is a problem accessing the database
   */
  public static ArrayList<Move> getAllMoves() throws SQLException {
    MoveDAOImpl moveDAO = new MoveDAOImpl();
    return moveDAO.getAll();
  }

  /**
   * The method retrieves all the Move objects from the "Move" table in the database.
   *
   * @return an ArrayList of the Move objects in the database
   * @throws SQLException if there is a problem accessing the database
   */
  public static ArrayList<Node> getAllNodes() throws SQLException {
    NodeDAOImpl nodeDAO = new NodeDAOImpl();
    return nodeDAO.getAll();
  }

  /**
   * The method retrieves all the Edge objects from the "Edge" table in the database.
   *
   * @return an ArrayList of the Edge objects in the database
   * @throws SQLException if there is a problem accessing the database
   */
  public static ArrayList<Edge> getAllEdges() throws SQLException {
    EdgeDAOImpl edgeDAO = new EdgeDAOImpl();
    return edgeDAO.getAll();
  }

  /**
   * The method retrieves all the ItemsOrdered objects from the "ItemsOrdered" table in the
   * database.
   *
   * @return an ArrayList of the ItemsOrdered objects in the database
   * @throws SQLException if there is a problem accessing the database
   */
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

  /**
   * The method retrieves all the Login objects from the "Login" table in the database.
   *
   * @return an ArrayList of the Login objects in the database
   * @throws SQLException if there is a problem accessing the database
   */
  public static ArrayList<Login> getAllLogins() throws SQLException {
    return (new LoginDAOImpl()).getAll();
  }

  /**
   * The method retrieves all the Flower objects from the "Flowers" table in the database.
   *
   * @return an ArrayList of the Flower objects in the database
   * @throws SQLException if there is a problem accessing the database
   */
  public static ArrayList<Flower> getAllFlowers() throws SQLException {
    return (new FlowerDAOImpl()).getAll();
  }

  /**
   * The method retrieves all the Meal objects from the "Meal" table in the database.
   *
   * @return an ArrayList of the Meal objects in the database
   * @throws SQLException if there is a problem accessing the database
   */
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

  /**
   * The method retrieves all the Furniture objects from the "Furniture" table in the database.
   *
   * @return an ArrayList of the Furniture objects in the database
   * @throws SQLException if there is a problem accessing the database
   */
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

  /**
   * This method retrieves a Flower object with the specified ID from the "Flowers" table in the
   * database.
   *
   * @param id the ID of the Flower object to retrieve from the "Flowers" table
   * @return the Flower object with the specified ID, or null if not found
   * @throws SQLException if there is a problem accessing the database
   */
  public static Flower getFlower(int id) throws SQLException {
    FlowerDAOImpl flowerDAO = new FlowerDAOImpl();
    return flowerDAO.getFlower(id);
  }

  /**
   * This method retrieves an ItemsOrdered object with the specified ID from the "ItemsOrdered"
   * table in the database.
   *
   * @param requestID the requestID of the ItemsOrdered object to retrieve from the "ItemsOrdered"
   *     table
   * @param itemID the itemID of the ItemsOrdered object to retrieve from the "ItemsOrdered" table
   * @return the ItemsOrdered object that matches the specified ID's, or null if not found
   * @throws SQLException if there is a problem accessing the database
   */
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

  /**
   * This method retrieves a Login object with the specified username from the "Login" table in the
   * database.
   *
   * @param username the username of the Login object to retrieve from the "Login" table
   * @return the Login object with the specified username, or null if not found
   * @throws SQLException if there is a problem accessing the database
   */
  public static Login getLogin(String username) throws SQLException {
    LoginDAOImpl loginDAO = new LoginDAOImpl();
    return loginDAO.getLogin(username);
  }

  /**
   * This method retrieves a Meal object with the specified ID from the "Meal" table in the
   * database.
   *
   * @param id the ID of the Meal object to retrieve from the "Meal" table
   * @return the Meal object with the specified ID, or null if not found
   * @throws SQLException if there is a problem accessing the database
   */
  public static Meal getMeal(int id) throws SQLException {
    MealDAOImpl mealDAO = new MealDAOImpl();
    return mealDAO.getMeal(id);
  }

  /**
   * This method retrieves a Node object with the specified ID from the "Node" table in the
   * database.
   *
   * @param id the ID of the Meal object to retrieve from the "Node" table
   * @return the Node object with the specified ID, or null if not found
   * @throws SQLException if there is a problem accessing the database
   */
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

  /**
   * This method retrieves a Furniture object with the specified ID from the "Furniture" table in
   * the database.
   *
   * @param id the ID of the Furniture object to retrieve from the "Furniture" table
   * @return the Furniture object with the specified ID, or null if not found
   * @throws SQLException if there is a problem accessing the database
   */
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

  /**
   * Updates the status for a service request with the given request ID in the database.
   *
   * @param requestID the ID of the service request to update.
   * @param status the new staff name to set.
   * @throws SQLException if a database error occurs.
   */
  public static void uploadStatusToServiceRequest(int requestID, String status) throws SQLException {
    ServiceRequestDAOImpl.uploadStatus(requestID, status);
  }

  /*/**
   * Uploads CSV data to a PostgreSQL database table "LocationName" also creates table if one does
   * not exist
   *
   * @param csvFilePath a String representing the csv data (must use "//" not "/")
   * @throws SQLException if an error occurs while uploading the data to the database
   *
  public static void uploadLocationNameToPostgreSQL(String csvFilePath) throws SQLException {
    LocationNameDAOImpl locationNameDAO = new LocationNameDAOImpl();
    locationNameDAO.uploadLocationNameToPostgreSQL(csvFilePath);
  }*/

  /**
   * Uploads CSV data to a PostgreSQL database table "Edge"-also creates one if one does not exist
   *
   * @param path a string that represents a file path (/ is illegal so you must use double//)
   * @throws SQLException if an error occurs while uploading the data to the database
   */
  public static void uploadEdge(String path) throws SQLException {
    EdgeDAOImpl.uploadEdgeToPostgreSQL(path);
  }

  /**
   * Uploads CSV data to a PostgreSQL database table "Flower"-also creates one if one does not exist
   *
   * @param path a string that represents a file path (/ is illegal so you must use double//)
   * @throws SQLException if an error occurs while uploading the data to the database
   */
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

  /**
   * Uploads CSV data to a PostgreSQL database table "LocationName" also creates table if one does
   * not exist
   *
   * @param path a String representing the csv data (must use "//" not "/")
   * @throws SQLException if an error occurs while uploading the data to the database
   */
  public static void uploadLocationName(String path) throws SQLException, ParseException {
    LocationNameDAOImpl.uploadLocationNameToPostgreSQL(path);
  }

  /**
   * Uploads CSV data to a PostgreSQL database table "Login"-also creates one if one does not exist
   *
   * @param path a string that represents a file path (/ is illegal so you must use double//)
   * @throws SQLException if an error occurs while uploading the data to the database
   */
  public static void uploadLogin(String path) throws SQLException, ParseException {
    LoginDAOImpl.uploadLoginToPostgreSQL(path);
  }

  /**
   * Uploads CSV data to a PostgreSQL database table "Meal"-also creates one if one does not exist
   *
   * @param path a string that represents a file path (/ is illegal so you must use double//)
   * @throws SQLException if an error occurs while uploading the data to the database
   */
  public static void uploadMeal(String path) throws SQLException, ParseException {
    MealDAOImpl.uploadMealToPostgreSQL(path);
  }

  /**
   * Uploads CSV data to a PostgreSQL database table "Move"-also creates one if one does not exist
   *
   * @param path a string that represents a file path (/ is illegal so you must use double//)
   * @throws SQLException if an error occurs while uploading the data to the database
   */
  public static void uploadMove(String path) throws SQLException, ParseException {
    MoveDAOImpl.uploadMoveToPostgreSQL(path);
  }

  /**
   * Uploads CSV data to a PostgreSQL database table "Node"-also creates one if one does not exist
   *
   * @param path a string that represents a file path (/ is illegal so you must use double//)
   * @throws SQLException if an error occurs while uploading the data to the database
   */
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

  /**
   * This method exports all the Edge objects from the "Edge" table in the database to a CSV file at
   * the specified file path.
   *
   * @param path the file path of the CSV file to export the Edge objects to
   * @throws SQLException if there is a problem accessing the database
   * @throws IOException if there is a problem writing the CSV file
   */
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

  /**
   * This method exports all the Meal objects from the "Meal" table in the database to a CSV file at
   * the specified file path.
   *
   * @param path the file path of the CSV file to export the Meal objects to
   * @throws SQLException if there is a problem accessing the database
   * @throws IOException if there is a problem writing the CSV file
   */
  public static void exportMealToCSV(String path) throws SQLException, IOException {
    MealDAOImpl.exportMealToCSV(path);
  }

  /**
   * This method exports all the Move objects from the "Move" table in the database to a CSV file at
   * the specified file path.
   *
   * @param path the file path of the CSV file to export the Move objects to
   * @throws SQLException if there is a problem accessing the database
   * @throws IOException if there is a problem writing the CSV file
   */
  public static void exportMoveToCSV(String path) throws SQLException, IOException {
    MoveDAOImpl.exportMoveToCSV(path);
  }

  /**
   * This method exports all the Node objects from the "Node" table in the database to a CSV file at
   * the specified file path.
   *
   * @param path the file path of the CSV file to export the Node objects to
   * @throws SQLException if there is a problem accessing the database
   * @throws IOException if there is a problem writing the CSV file
   */
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
