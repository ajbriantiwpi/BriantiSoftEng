package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.EdgeDAO;
import edu.wpi.teamname.navigation.Edge;

import java.sql.*;
import java.util.ArrayList;
import lombok.Getter;
import oracle.ucp.proxy.annotation.Pre;

public class EdgeDAOImpl implements EdgeDAO {
  /** */
  @Override
  public void sync(Edge edge) {

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
    try (connection) {
      String query = "DELETE FROM \"Edge\" WHERE \"startNode\" = ? AND \"endNode\" = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, edge.getStartNodeID());
      statement.setInt(2, edge.getEndNodeID());

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    connection.close();
  }
}
