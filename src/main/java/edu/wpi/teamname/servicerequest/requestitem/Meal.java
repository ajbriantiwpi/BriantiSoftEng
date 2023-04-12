package edu.wpi.teamname.servicerequest.requestitem;

import edu.wpi.teamname.database.DataManager;
import java.sql.*;
import lombok.Getter;
import lombok.Setter;

public class Meal extends RequestItem {
  @Setter @Getter private String meal;
  @Setter @Getter private String cuisine;

  public Meal(int itemID, String name, float price, String meal, String cuisine) {
    super(itemID, name, price);
    this.meal = meal;
    this.cuisine = cuisine;
  }

  public Meal(int mealID) throws SQLException {
    super(mealID);
    Connection connection = DataManager.DbConnection();
    String query = "SELECT * FROM \"Meal\" WHERE \"mealID\" = ?;";

    String name = null;
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setInt(1, mealID);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        super.setName(rs.getString("Name"));
        setPrice(rs.getFloat("Price"));
        setMeal(rs.getString("Meal"));
        setCuisine(rs.getString("Cuisine"));
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
        + meal
        + ", "
        + cuisine
        + "]";
  }
}
