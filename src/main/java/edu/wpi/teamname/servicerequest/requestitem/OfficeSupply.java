package edu.wpi.teamname.servicerequest.requestitem;

import edu.wpi.teamname.database.DataManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.Getter;
import lombok.Setter;

public class OfficeSupply extends RequestItem {
  @Setter @Getter private float price;
  @Setter @Getter private String category;
  @Setter @Getter private boolean isElectric;

  public OfficeSupply(int itemID, String name, float price, String category, boolean isElectric) {
    super(itemID, name);
    this.price = price;
    this.category = category;
    this.isElectric = isElectric;
  }

  public OfficeSupply(int id) throws SQLException {
    super(id);
    Connection connection = DataManager.DbConnection();
    String query = "SELECT * FROM \"OfficeSupply\" WHERE \"supplyID\" = ?;";

    String name = null;
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setInt(1, id);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        super.setName(rs.getString("name"));
        setPrice(rs.getFloat("price"));
        setCategory(rs.getString("category"));
        setElectric(rs.getBoolean("isElectric"));
      }
    } catch (SQLException e) {
      System.out.println("Error retrieving office supply data: " + e.getMessage());
    }
  }

  public String toString() {
    return "["
        + this.getItemID()
        + ", "
        + this.getName()
        + ", "
        + price
        + ", "
        + category
        + ", "
        + isElectric
        + "]";
  }
}
