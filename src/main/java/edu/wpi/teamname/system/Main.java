package edu.wpi.teamname.system;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.database.MealDAOImpl;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
  public static void main(String[] args) throws SQLException, IOException {
    // Singleton is in DataManager, function has it so it will only return one connection when
    // called
    DataManager.configConnection(
        "jdbc:postgresql://database.cs.wpi.edu:5432/teamddb?currentSchema=\"teamD\"",
        "teamd",
        "teamd40");
    MealDAOImpl me = new MealDAOImpl();
    me.uploadMealToPostgreSQL("C:\\Users\\boxtu\\OneDrive\\Desktop\\meal.csv");

    // App.launch(App.class, args);
  }

  // shortcut: psvm
}
