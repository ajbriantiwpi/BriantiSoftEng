package edu.wpi.teamname.servicerequest.requestitem;

import edu.wpi.teamname.database.DataManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.Getter;
import lombok.Setter;

public class Flower extends RequestItem {
  @Getter @Setter private String category;
  @Getter @Setter private String color;

  public Flower(int flowerID, String name, float price, String category, String color) {
    super(flowerID, name, price);
    this.category = category;
    this.color = color;
  }

  public Flower(int id) throws SQLException {
    super(id);
    Connection connection = DataManager.DbConnection();
    String query = "SELECT * FROM \"Flower\" WHERE \"flowerID\" = ?;";

    String name = null;
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setInt(1, id);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        super.setName(rs.getString("Name"));
        setPrice(rs.getFloat("Price"));
        setCategory(rs.getString("Category"));
        setColor(rs.getString("Color"));
      }
    } catch (SQLException e) {
      System.out.println("Error retrieving meal data: " + e.getMessage());
    }
  }

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
        + color
        + "]";
  }
}
