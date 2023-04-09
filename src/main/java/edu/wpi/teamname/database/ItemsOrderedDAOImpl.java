package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.ItemsOrderedDAO;
import edu.wpi.teamname.servicerequest.ItemsOrdered;
import java.sql.*;
import java.util.ArrayList;

public class ItemsOrderedDAOImpl implements ItemsOrderedDAO {
  /** */
  @Override
  public void sync(ItemsOrdered itemsOrdered) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query =
          "UPDATE \"ItemsOrdered\" SET \"requestID\" = ?, \"itemID\" = ?, \"quantity\" = ?"
              + " WHERE \"requestID\" = ? AND \"itemID\" = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, itemsOrdered.getRequestID());
      statement.setInt(2, itemsOrdered.getItemID());
      statement.setInt(3, itemsOrdered.getQuantity());
      statement.setInt(4, itemsOrdered.getRequestID());
      statement.setInt(5, itemsOrdered.getItemID());
      ;
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    connection.close();
  }

  /** @return */
  @Override
  public ArrayList<ItemsOrdered> getAll() throws SQLException {
    Connection connection = DataManager.DbConnection();
    ArrayList<ItemsOrdered> list = new ArrayList<ItemsOrdered>();

    try (connection) {
      String query = "SELECT * FROM \"ItemsOrdered\"";
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        int requestID = rs.getInt("requestID");
        int itemID = rs.getInt("itemID");
        int quantity = rs.getInt("quantity");
        list.add(new ItemsOrdered(requestID, itemID, quantity));
      }
    }
    connection.close();
    return list;
  }

  /** @param itemsOrdered */
  @Override
  public void add(ItemsOrdered itemsOrdered) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query =
          "INSERT INTO \"ItemsOrdered\" (\"requestID\", \"itemID\", \"quantity\") "
              + "VALUES (?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, itemsOrdered.getRequestID());
      statement.setInt(2, itemsOrdered.getItemID());
      statement.setInt(3, itemsOrdered.getQuantity());

      statement.executeUpdate();

    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    connection.close();
  }

  /** @param itemsOrdered */
  @Override
  public void delete(ItemsOrdered itemsOrdered) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query = "DELETE FROM \"ItemsOrdered\" WHERE \"requestID\" = ? AND \"itemID\" = ?";
    try (connection) {

      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, itemsOrdered.getRequestID());
      statement.setInt(2, itemsOrdered.getItemID());

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    try (Statement statement = connection.createStatement()) {
      ResultSet rs2 = statement.executeQuery(query);
      int count = 0;
      while (rs2.next()) count++;
      if (count == 0) System.out.println("ItemOrdered information deleted successfully.");
      else System.out.println("ItemOrdered information did not delete.");
    } catch (SQLException e2) {
      System.out.println("Error checking delete. " + e2);
    }
    connection.close();
  }
}
