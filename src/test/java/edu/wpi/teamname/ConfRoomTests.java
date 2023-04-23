package edu.wpi.teamname;

import edu.wpi.teamname.database.DataManager;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class ConfRoomTests {
  //  @BeforeEach
  //  void setUp() throws SQLException {
  //    // TODO: Put in docker info
  //    DataManager.configConnection("jdbc:postgresql://localhost:5432/postgres", "user", "pass");
  //    String query =
  //        "CREATE TABLE IF NOT EXISTS \"ConfRooms\" (\"roomID\" INT, \"locationName\" VARCHAR,
  // \"seats\" INT);";
  //    Connection connection = DataManager.DbConnection();
  //    PreparedStatement statement = connection.prepareStatement(query);
  //    statement.executeUpdate();
  //
  //    query = "TRUNCATE TABLE \"Move\"";
  //    statement = connection.prepareStatement(query);
  //    statement.executeUpdate();
  //  }

  @Test
  public void test() throws SQLException {
    DataManager dm = new DataManager();
    dm.refreshConfRooms();
  }
}
