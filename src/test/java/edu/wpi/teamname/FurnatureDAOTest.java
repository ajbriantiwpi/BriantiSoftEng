package edu.wpi.teamname;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teamname.database.DataManager;
import edu.wpi.teamname.servicerequest.requestitem.Furniture;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FurnatureDAOTest {

  @Getter @Setter int itemID;
  @Getter @Setter String name;
  @Getter @Setter float price;
  @Getter @Setter String category;
  @Getter @Setter String size;
  @Getter @Setter String color;

  @BeforeEach
  void setUp() throws SQLException {
    // TODO: Put in docker info
    DataManager.configConnection("jdbc:postgresql://localhost:5432/postgres", "user", "pass");
    String query = "Truncate Table \"Furniture\"";
    Connection connection = DataManager.DbConnection();
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Truncate Error. " + e);
    }
    connection.close();
  }

  @Test
  void testExportCSV() throws SQLException {
    try {
      Connection conn =
          DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "user", "pass");
      PreparedStatement ps = conn.prepareStatement("TRUNCATE TABLE \"Furniture\"");
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    // insert some test data
    List<Furniture> testData = new ArrayList<>();
    testData.add(new Furniture(1, "TestN1", 1f, "1Cat", "TestSize1", "1Col"));
    testData.add(new Furniture(2, "TestN2", 2f, "2Cat", "TestSize2", "2Col"));
    testData.add(new Furniture(3, "TestN3", 3f, "3Cat", "TestSize3", "3Col"));
    try {
      for (Furniture ln : testData) {
        DataManager.addFurniture(ln);
      }
    } catch (SQLException e) {
      fail("SQL Exception thrown while adding test location names");
    }

    // export the location names to a CSV file
    String csvFilePath = "test_furniture.csv";
    try {
      DataManager.exportFurnitureToCSV(csvFilePath);
    } catch (IOException e) {
      fail("IOException thrown while exporting location names to CSV");
    }

    // read the CSV file and verify its contents
    try {
      List<String> lines = Files.readAllLines(Paths.get(csvFilePath));
      assertEquals(lines.get(0), "furnitureID,name,price,category,size,color");
      assertEquals(lines.get(1), "1,TestN1,1,1Cat,TestSize1,1Col");
      assertEquals(lines.get(2), "2,TestN2,2,2Cat,TestSize2,2Col");
      assertEquals(lines.get(3), "3,TestN3,3,3Cat,TestSize3,3Col");
    } catch (IOException e) {
      fail("IOException thrown while reading CSV file");
    }
  }
}
