package edu.wpi.teamname.servicerequest.requestitem;

import edu.wpi.teamname.database.DataManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.Getter;
import lombok.Setter;

public class Furniture extends RequestItem {
  @Setter @Getter private String category;
  @Setter @Getter private String size;
  @Setter @Getter private String color;

  /**
   * Creates a furniture object
   * @param itemID id of the item
   * @param name name of the item
   * @param price price of the item
   * @param category type of item
   * @param size size of the furniture item
   * @param color color of the furniture
   */
  public Furniture(
      int itemID, String name, float price, String category, String size, String color) {
    super(itemID, name, price);
    this.category = category;
    this.size = size;
    this.color = color;
  }

  /**
   * creates a furniture object in the database
   * @param id id of the object
   * @throws SQLException thrown when there is an error connecting to the database or an error with the sql query syntax
   */
  public Furniture(int id) throws SQLException {
    super(id);
    Connection connection = DataManager.DbConnection();
    String query = "SELECT * FROM \"Furniture\" WHERE \"furnitureID\" = ?;";

    String name = null;
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setInt(1, id);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        super.setItemID(id);
        super.setName(rs.getString("name"));
        setPrice(rs.getFloat("price"));
        setCategory(rs.getString("category"));
        setColor(rs.getString("color"));
        setSize(rs.getString("size"));
      }
    } catch (SQLException e) {
      System.out.println("Error retrieving furniture data: " + e.getMessage());
    }
  }

  /**
   * A string of the item
   * @return String in format [ <itemID>, <name>, <price>, <category>, <size>, <color>]
   */
  public String toString() {
    return "["
        + this.getItemID()
        + ", "
        + this.getName()
        + ", "
        + this.getPrice()
        + ", "
        + category
        + ", "
        + size
        + ", "
        + color
        + "]";
  }
}
