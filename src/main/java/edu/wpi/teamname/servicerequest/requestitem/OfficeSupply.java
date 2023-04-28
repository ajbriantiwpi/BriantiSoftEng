package edu.wpi.teamname.servicerequest.requestitem;

import edu.wpi.teamname.database.DataManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.Getter;
import lombok.Setter;

public class OfficeSupply extends RequestItem {
  @Setter @Getter private String category;
  @Setter @Getter private boolean isElectric;

  /**
   * @param itemID id of the item
   * @param name name of the item
   * @param price price of the item
   * @param category type of item
   * @param isElectric if it needs electricity to operate
   */
  public OfficeSupply(int itemID, String name, float price, String category, boolean isElectric) {
    super(itemID, name, price);
    this.category = category;
    this.isElectric = isElectric;
  }

  /**
   * Creates a OfficeSupply object only using the ID and gets the rest of the data from the database
   *
   * @param id id of the item
   * @throws SQLException thrown when there is an error connecting to the database or an error with
   *     the sql query syntax
   */
  public OfficeSupply(int id) throws SQLException {
    super(id);
    Connection connection = DataManager.DbConnection();
    String query = "SELECT * FROM \"OfficeSupply\" WHERE \"supplyID\" = ?;";

    String name = null;
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setInt(1, id);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        super.setItemID(id);
        super.setName(rs.getString("name"));
        setPrice(rs.getFloat("price"));
        setCategory(rs.getString("category"));
        setElectric(rs.getBoolean("isElectric"));
      }
    } catch (SQLException e) {
      System.out.println("Error retrieving office supply data: " + e.getMessage());
    }
  }

  /**
   * a toString method for the meal items
   *
   * @return String in format [ <itemID>, <name>, <price>, <category>, <isElectric>]
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
        + isElectric
        + "]";
  }
}
