package edu.wpi.teamname.system;


import java.io.IOException;
import java.sql.SQLException;

import edu.wpi.teamname.database.DataManager;


public class Main {
  public static void main(String[] args) {
    // Singleton is in DataManager, function has it so it will only return one connection when
    // called
    DataManager.configConnection(
        "jdbc:postgresql://database.cs.wpi.edu:5432/teamddb?currentSchema=\"teamD\"",
        "teamd40",
        "teamd");
    App.launch(App.class, args);
  }

  // shortcut: psvm
}
