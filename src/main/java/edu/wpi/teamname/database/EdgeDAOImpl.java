package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.EdgeDAO;
import edu.wpi.teamname.navigation.Edge;

import java.sql.*;
import java.util.ArrayList;
import lombok.Getter;
import oracle.ucp.proxy.annotation.Pre;

public class EdgeDAOImpl implements EdgeDAO {
  /** Sync an ORM with its row in the database
   * WARNING: do not create a new node just change the parameters on the old one
   *
   * */
  @Override
  public void sync(Edge edge) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query = "UPDATE \"Edge\" SET \"startNode\" = ?, \"endNode\" = ?" +
              " WHERE \"startNode\" = ? AND \"endNode\" = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, edge.getStartNodeID());
      statement.setInt(2, edge.getEndNodeID());
      statement.setInt(3, edge.getOriginalStartNodeID());
      statement.setInt(4, edge.getOriginalEndNodeID());

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
  }
    connection.close();
  }

  /** @return */
  @Override
  public ArrayList<Edge> getAll() throws SQLException {
    Connection connection = DataManager.DbConnection();
    ArrayList<Edge> list = new ArrayList<Edge>();

    try (connection) {
      String query = "SELECT * FROM \"Edge\"";
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        int startNode = rs.getInt("startNode");
        int endNode = rs.getInt("endNode");
        list.add(new Edge(startNode, endNode));
      }
    }
    connection.close();
    return list;
  }

  /** @param edge */
  @Override
  public void add(Edge edge) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query = "INSERT INTO \"Edge\" (\"startNode\", \"endNode\") VALUES (?, ?)";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, edge.getStartNodeID()); // startNode is a int column
      statement.setInt(2, edge.getEndNodeID()); // endNode is a int column

      statement.executeUpdate();

    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    connection.close();
  }

  /** @param edge */
  @Override
  public void delete(Edge edge) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query = "DELETE FROM \"Edge\" WHERE \"startNode\" = ? AND \"endNode\" = ?";
    try (connection) {

      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, edge.getStartNodeID());
      statement.setInt(2, edge.getEndNodeID());

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    try (Statement statement = connection.createStatement()) {
      ResultSet rs2 = statement.executeQuery(query);
      int count = 0;
      while (rs2.next()) count++;
      if (count == 0)
        System.out.println("Edge information deleted successfully.");
      else System.out.println("Edge information did not delete.");
    } catch (SQLException e2) {
      System.out.println("Error checking delete. " + e2);
    }
    connection.close();
  }
}
