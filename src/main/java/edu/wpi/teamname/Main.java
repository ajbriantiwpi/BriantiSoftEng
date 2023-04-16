package edu.wpi.teamname;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.employees.Employee;
import edu.wpi.teamname.employees.EmployeeType;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class Main {
  public static void main(String[] args) throws SQLException, IOException, ParseException {
    // Singleton is in DataManager, function has it so it will only return one connection when
    // called
    DataManager.configConnection(
        "jdbc:postgresql://database.cs.wpi.edu:5432/teamddb?currentSchema=\"teamD\"",
        "teamd",
        "teamd40");
    GlobalVariables.setCurrentUser(DataManager.getEmployee("admin"));
    App.launch(App.class, args);
  }

  // shortcut: psvm
}
