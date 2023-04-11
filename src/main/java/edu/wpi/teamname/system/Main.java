package edu.wpi.teamname.system;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.navigation.Move;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

public class Main {
  public static void main(String[] args) throws SQLException, IOException, ParseException {
    // Singleton is in DataManager, function has it so it will only return one connection when
    // called
    DataManager.configConnection(
        "jdbc:postgresql://database.cs.wpi.edu:5432/teamddb?currentSchema=\"teamD\"",
        "teamd",
        "teamd40");
    // App.launch(App.class, args);
    /*Move move = new Move(2, "Test Long Name", new Timestamp(2023, 3, 2, 0, 0, 0, 0));
    DataManager.addMoves(move);
    System.out.println(new Timestamp(2023, 3, 2, 0, 0, 0, 0));
    move.setLongName("New Test Long Name");
    System.out.println(move.getOriginalLongName());
    System.out.println(move.getDate());
    DataManager.syncMove(move);
    System.out.println(move.getDate());*/
    Date date = new Date();
    Move move = new Move(100, "Test Long Name", new Timestamp(date.getTime()));
    DataManager.addMoves(move);

  }

  // shortcut: psvm
}
