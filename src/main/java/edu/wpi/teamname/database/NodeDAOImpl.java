package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.NodeDAO;
import edu.wpi.teamname.navigation.Node;
import java.sql.*;
import java.util.ArrayList;

public class NodeDAOImpl implements NodeDAO {
  /** */
  @Override
  public void sync(Node node) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query =
          "UPDATE \"Node\" SET \"xcoord\" = ?, \"ycoord\" = ?, \"floor\" = ?, \"building\" = ?"
              + " WHERE \"nodeID\" = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, node.getX());
      statement.setInt(2, node.getY());
      statement.setString(3, node.getFloor());
      statement.setString(4, node.getBuilding());

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    connection.close();
  }

  /** @return */
  @Override
  public ArrayList<Node> getAll() throws SQLException {
    Connection connection = DataManager.DbConnection();
    ArrayList<Node> list = new ArrayList<Node>();

    try (connection) {
      String query = "SELECT * FROM \"Node\"";
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery(query);

      while (rs.next()) {
        int id = rs.getInt("nodeID");
        int xcoord = rs.getInt("xcoord");
        int ycoord = rs.getInt("ycoord");
        String floor = rs.getString("floor");
        String building = rs.getString("building");
        list.add(new Node(id, xcoord, ycoord, floor, building));
      }
    } catch (SQLException e) {
      System.out.println("Get all nodes error.");
    }
    return list;
  }

  /** @param node */
  @Override
  public void add(Node node) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query =
        "INSERT INTO \"Node\" (\"nodeID\", xcoord, ycoord, floor, building) "
            + "VALUES (?, ?, ?, ?, ?)";

    try (connection) {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.executeUpdate(query);
      statement = connection.prepareStatement(query);
      statement.setInt(1, node.getId());
      statement.setInt(2, node.getX());
      statement.setInt(3, node.getY());
      statement.setString(4, node.getFloor());
      statement.setString(5, node.getBuilding());
      statement.executeUpdate();
      System.out.println("Node information has been successfully added to the database.");
    } catch (SQLException e) {
      System.err.println("Error adding Node information to database: " + e.getMessage());
    }
  }

  /** @param node */
  @Override
  public void delete(Node node) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query = "Delete from \"Node\" " + "where \"nodeID\" = " + node.getId();

    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Delete in Node table error. " + e);
    }

    try (Statement statement = connection.createStatement()) {
      ResultSet rs2 = statement.executeQuery(query);
      int count = 0;
      while (rs2.next()) count++;
      if (count == 0) System.out.println("Node information deleted successfully.");
      else System.out.println("Node information did not delete.");
    } catch (SQLException e2) {
      System.out.println("Error checking delete. " + e2);
    }
  }
}
