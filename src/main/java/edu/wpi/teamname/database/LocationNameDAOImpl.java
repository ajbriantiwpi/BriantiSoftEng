package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.LocationNameDAO;
import edu.wpi.teamname.navigation.LocationName;
import edu.wpi.teamname.navigation.Move;
import lombok.Getter;

import java.sql.*;
import java.util.ArrayList;

public class LocationNameDAOImpl implements LocationNameDAO {
  /** */
  @Override
  public void sync(LocationName locationName) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query = "UPDATE \"LocationName\" SET \"shortName\" = ?, \"nodeType\" = ?" +
              " WHERE \"longName\" = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, locationName.getShortName());
      statement.setString(2, locationName.getNodeType());
      statement.setString(3, locationName.getLongName());

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    connection.close();
  }

  /** @return */
  @Override
  public ArrayList<LocationName> getAll() throws SQLException {
    Connection connection = DataManager.DbConnection();
    ArrayList<LocationName> list = new ArrayList<LocationName>();
    try (connection) {
      String query = "SELECT * FROM \"LocationName\"";
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery(query);

      while (rs.next()) {
        String longn = rs.getString("longName");
        String shortn = rs.getString("shortName");
        String ntype = rs.getString("nodeType");
        list.add(new LocationName(longn, shortn, ntype));
      }
    } catch (SQLException e) {
      System.out.println("Get all Location Names error.");
    }
    return list;
  }

  /** @param locationName */
  @Override
  public void add(LocationName locationName) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query =
            "INSERT INTO \"LocationName\" (\"longName\", \"shortName\", \"nodeType\") " + "VALUES (?, ?, ?)";

    try (connection) {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.executeUpdate(query);
      statement = connection.prepareStatement(query);
      statement.setString(1, locationName.getLongName());
      statement.setString(2, locationName.getShortName());
      statement.setString(3, locationName.getNodeType());
      statement.executeUpdate();
      System.out.println("Location Name information has been successfully added to the database.");
    } catch (SQLException e) {
      System.err.println("Error adding Location Name information to database: " + e.getMessage());
    }
  }

  /** @param locationName */
  @Override
  public void delete(LocationName locationName) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query = "Delete from \"LocationName\" " + "where \"longName\" = " + locationName.getLongName();

    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Delete in Location Name table error. " + e);
    }

    try (Statement statement = connection.createStatement()) {
      ResultSet rs2 = statement.executeQuery(query);
      int count = 0;
      while (rs2.next()) count++;
      if (count == 0)
        System.out.println("LocationName information deleted successfully.");
      else System.out.println("LocationName information did not delete.");
    } catch (SQLException e2) {
      System.out.println("Error checking delete. " + e2);
    }
  }
}
