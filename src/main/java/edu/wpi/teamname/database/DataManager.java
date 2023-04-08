package edu.wpi.teamname.database;

import edu.wpi.teamname.navigation.Edge;
import edu.wpi.teamname.navigation.Move;
import edu.wpi.teamname.navigation.Node;
import edu.wpi.teamname.servicerequest.ItemsOrdered;
import edu.wpi.teamname.servicerequest.ServiceRequest;
import edu.wpi.teamname.servicerequest.requestitem.Flower;
import edu.wpi.teamname.servicerequest.requestitem.Meal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataManager {
  private static Connection connection;

  /**
   * Main function to connect to the database
   *
   * @return a Connection to a database
   */
  public static Connection DbConnection() throws SQLException {
    String DB_URL = "jdbc:postgresql://database.cs.wpi.edu:5432/teamddb?currentSchema=\"teamD\"";
    String DB_PASSWORD = "teamd40";
    String DB_USER = "teamd";

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

  // ------------------------DAO Methods------------------------

  /** */
  public void syncAll() {
    syncMoves();
    syncNodes();
    syncEdges();
    syncItemsOrdered();
    syncItemsOrdered();
    syncServiceRequests();
    syncLogin();
    syncFlowers();
    syncMeals();
  }

  /** */
  public void syncMoves() {}

  /** */
  public void syncNodes() {}

  /** */
  public void syncEdges() {}

  /** */
  public void syncItemsOrdered() {}

  /** */
  public void syncServiceRequests() {}

  /** */
  public void syncLogin() {}

  /** */
  public void syncFlowers() {}

  /** */
  public void syncMeals() {}

  /** */
  public void addAll() {
    addMoves();
    addNodes();
    addEdges();
    addItemsOrdered();
    addServiceRequests();
    addLogin();
    addFlowers();
    addMeals();
  }

  /** */
  public void addMoves() {}

  /** */
  public void addNodes() {}

  /** */
  public void addEdges() {}

  /** */
  public void addItemsOrdered() {}

  /** */

  /** */
  public void addServiceRequests() {}

  /** */
  public void addLogin() {}

  /** */
  public void addFlowers() {}

  /** */
  public void addMeals() {}

  /** */
  public void deleteMoves() {}

  /** */
  public void deleteNodes() {}

  /** */
  public void deleteEdges() {}

  /** */
  public void deleteItemsOrdered() {}

  /** */
  public void deleteServiceRequests() {}

  /** */
  public void deleteLogin() {}

  /** */
  public void deleteFlowers() {}

  /** */
  public void deleteMeals() {}

  /** @return ArrayList<Move> */
  public ArrayList<Move> getMoves() {
    return null;
  }

  /** @return ArrayList<Node> */
  public ArrayList<Node> getNodes() {
    return null;
  }

  /** @return ArrayList<Edge> */
  public ArrayList<Edge> getEdges() {
    return null;
  }

  /** @return ArrayList<ItemsOrdered> */
  public ArrayList<ItemsOrdered> getItemsOrdered() {
    return null;
  }

  /** @return ArrayList<ServiceRequest> */
  public ArrayList<ServiceRequest> getServiceRequests() {
    return null;
  }

  /** @return ArrayList<Login> */
  public ArrayList<Login> getLogin() {
    return null;
  }

  /** @return ArrayList<Flower> */
  public ArrayList<Flower> getFlowers() {
    return null;
  }

  /** @return ArrayList<Meal> */
  public ArrayList<Meal> getMeals() {
    return null;
  }
}
