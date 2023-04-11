package edu.wpi.teamname.database;

import com.sun.javafx.geom.Point2D;
import edu.wpi.teamname.database.interfaces.NodeDAO;
import edu.wpi.teamname.navigation.Node;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NodeDAOImpl implements NodeDAO {
  /** */
  @Override
  public void sync(Node node) throws SQLException {
    Connection connection = DataManager.DbConnection();
    try (connection) {
      String query =
          "UPDATE \"Node\" SET \"xcoord\" = ?, \"ycoord\" = ?, \"floor\" = ?, \"building\" = ?, \"nodeID\" = ?"
              + " WHERE \"nodeID\" = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, node.getX());
      statement.setInt(2, node.getY());
      statement.setString(3, node.getFloor());
      statement.setString(4, node.getBuilding());
      statement.setInt(5, node.getId());
      statement.setInt(6, node.getOriginalID());

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
    String query = "Delete from \"Node\" WHERE \"nodeID\" = ?";

    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setInt(1, node.getId());
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
  /**
   * Uploads CSV data to a PostgreSQL database table "Node"
   *
   * @param csvFilePath is a String representing the filepath of the file we want to upload (use
   *     "\\" instead of "\")
   * @throws SQLException if an error occurs while uploading the data to the database
   */
  public static void uploadNodeToPostgreSQL(String csvFilePath) throws SQLException {
    List<String[]> csvData;
    Connection connection = DataManager.DbConnection();
    DataManager dataImport = new DataManager();
    csvData = dataImport.parseCSVAndUploadToPostgreSQL(csvFilePath);

    try (connection) {
      DatabaseMetaData dbm = connection.getMetaData();
      ResultSet tables = dbm.getTables(null, null, "Node", null);
      if (!tables.next()) {
        Statement createStatement = connection.createStatement();
        createStatement.executeUpdate(
            "CREATE TABLE \"Node\" (\n"
                + "  \"nodeID\" INT NOT NULL,\n"
                + "  xcoord INT NOT NULL,\n"
                + "  ycoord INT NOT NULL,\n"
                + "  floor VARCHAR(10) NOT NULL,\n"
                + "  building VARCHAR(255) NOT NULL,\n"
                + "  PRIMARY KEY (\"nodeID\")\n"
                + ");");
        System.out.println("Table \"Node\" created successfully.");
      }

      String query =
          "INSERT INTO \"Node\" (\"nodeID\", xcoord, ycoord, floor, building) "
              + "VALUES (?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE \"Node\";");
      statement.executeUpdate();
      statement = connection.prepareStatement(query);

      for (int i = 1; i < csvData.size(); i++) {
        String[] row = csvData.get(i);
        statement.setInt(1, Integer.parseInt(row[0])); // nodeID is a int column
        statement.setInt(2, Integer.parseInt(row[1])); // xcoord is an integer column
        statement.setInt(3, Integer.parseInt(row[2])); // ycoord is an integer column
        statement.setString(4, row[3]); // assuming floor is a string column
        statement.setString(5, row[4]); // assuming building is a string column

        statement.executeUpdate();
      }
      System.out.println("CSV data uploaded to PostgreSQL database");
    } catch (SQLException e) {
      System.err.println("Error uploading CSV data to PostgreSQL database: " + e.getMessage());
    }
  }

  public static void exportNodeToCSV(String csvFilePath) throws SQLException, IOException {
    Connection connection = DataManager.DbConnection();
    String query = "SELECT * FROM \"Node\"";
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(query);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
      writer.write("nodeID,xcoord,ycoord,floor,building\n");
      while (resultSet.next()) {
        int nodeID = resultSet.getInt("nodeID");
        int xcoord = resultSet.getInt("xcoord");
        int ycoord = resultSet.getInt("ycoord");
        String floor = resultSet.getString("floor");
        String building = resultSet.getString("building");

        String row = nodeID + "," + xcoord + "," + ycoord + "," + floor + "," + building + "\n";
        writer.write(row);
      }
      System.out.println("CSV data downloaded from PostgreSQL database");
    } catch (IOException e) {
      System.err.println("Error downloading CSV data from PostgreSQL database: " + e.getMessage());
    }
  }

  public static Node getNode(int id) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query = "SELECT * FROM \"Node\" WHERE \"nodeID\" = ?";
    Node node = null;
    try (connection) {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, id);
      ResultSet rs = statement.executeQuery();

      int nodeid = rs.getInt("nodeID");
      int xcoord = rs.getInt("xcoord");
      int ycoord = rs.getInt("ycoord");
      String floor = rs.getString("floor");
      String building = rs.getString("building");
      node = (new Node(nodeid, xcoord, ycoord, floor, building));
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return node;
  }
  /**
   * Display nodes located on every floor the parameter String is on within the "Node" table
   *
   * @param floor a String representing the floor the user wants to display nodes on
   * @throws SQLException if an error occurs while displaying the data
   */
  public static ArrayList<Point2D> displayNodesByFloor(String floor) throws SQLException {
    Connection connection = DataManager.DbConnection();
    String query = "SELECT * FROM \"Node\" WHERE floor = ?";
    ArrayList<Point2D> ret = new ArrayList<Point2D>();
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setString(1, floor);
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        ret.add(
            new Point2D(
                Integer.parseInt(rs.getString("xcoord")),
                Integer.parseInt(rs.getString("ycoord"))));
      }
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
    return ret;
  }
}
