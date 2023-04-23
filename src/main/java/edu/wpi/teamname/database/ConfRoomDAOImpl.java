package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.ConfRomDAO;
import edu.wpi.teamname.servicerequest.requestitem.ConfRoom;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
public class ConfRoomDAOImpl implements ConfRomDAO {
//    public void makeDayBookings(Timestamp date) throws SQLException {
//        Connection connection = DataManager.DbConnection();
//        try (connection) {
//            String query =
//                    "Create Table \"teamD\".\"DayBookings\" (room VARCHAR, )";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.executeUpdate();
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//    public ArrayList<String> getConfRooms(Timestamp dateB) throws SQLException {
//        ArrayList<String> tempRooms = new ArrayList<>();
//        Connection connection = DataManager.DbConnection();>
//        try (connection) {
//            String query =
//                    "SELECT room FROM \"ConferenceRooms\" WHERE \"datebook\" = ?";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setTimestamp(1, dateB);
//
//            ResultSet rs = statement.executeQuery();
//            while (rs.next()) {
//                tempRooms.add(rs.getString("room"));
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        connection.close();
//        return tempRooms;
//    }
//    public int getConfRoomTimes(){
//        //8AM to 7PM
//        return 11 * 2;//times 2 bc it is 30 minute intervals on table
//    }


    /**
     * This method updates an existing ConfRoom object in the "ConferenceRooms" table in the database with the
     * new ConfRoom object.
     *
     * @param ConfRoom the new ConfRoom object to be updated in the "ConfRoom" table
     * @throws SQLException if there is a problem accessing the database
     */
    @Override
    public void sync(ConfRoom ConfRoom) throws SQLException {
        Connection connection = DataManager.DbConnection();
        try (connection) {
            String query =
                    "UPDATE \"ConfRooms\" SET \"roomID\" = ?, \"locationName\" = ?, \"seats\" = ?  WHERE \"roomID\" = ?, \"locationName\" = ?, \"seats\" = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ConfRoom.getRoomID());
            statement.setString(2, ConfRoom.getLocationName());
            statement.setInt(3, ConfRoom.getRoomID());

            statement.setInt(4, ConfRoom.getOrigRoomID());
            statement.setString(5, ConfRoom.getOrigLocationName());
            statement.setInt(6, ConfRoom.getOrigRoomID());


            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        connection.close();
    }

    /**
     * The method retrieves all the ConfRoom objects from the "ConferenceRooms" table in the database.
     *
     * @return an ArrayList of the ConfRoom objects in the database
     * @throws SQLException if there is a problem accessing the database
     */
    @Override
    public ArrayList<ConfRoom> getAll() throws SQLException {
        Connection connection = DataManager.DbConnection();
        ArrayList<ConfRoom> list = new ArrayList<ConfRoom>();

        try (connection) {
            String query = "SELECT * FROM \"ConfRooms\"";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int roomID = rs.getInt("roomID");
                String locName = rs.getString("locationName");
                int seats = rs.getInt("seats");
                list.add(new ConfRoom(roomID, locName, seats));
            }
        }
        connection.close();
        return list;
    }

    /**
     * This method adds a new ConfRoom object to the "ConferenceRooms" table in the database.
     *
     * @param ConfRoom the ConfRoom object to be added to the "ConfRoom" table
     * @throws SQLException if there is a problem accessing the database
     */
    @Override
    public void add(ConfRoom ConfRoom) throws SQLException {//WILL NOT USE UNLESS A CONFERENCE ROOM IS BEING ADDED
        Connection connection = DataManager.DbConnection();
        try (connection) {
            String query =
                    "INSERT INTO \"ConfRooms\" (\"roomID\", \"locationName\", \"seats\") "
                            + "VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ConfRoom.getRoomID());
            statement.setString(2, ConfRoom.getLocationName());
            statement.setInt(3, ConfRoom.getRoomID());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        connection.close();
    }

    /**
     * This method deletes the given ConfRoom object from the database
     *
     * @param ConfRoom the ConfRoom object that will be deleted in the database
     * @throws SQLException if there is a problem accessing the database
     */
    @Override
    public void delete(ConfRoom ConfRoom) throws SQLException {
        Connection connection = DataManager.DbConnection();
        String query = "DELETE FROM \"ConfRooms\" WHERE \"roomID\" = ?";
        try (connection) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ConfRoom.getRoomID());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try (Statement statement = connection.createStatement()) {
            ResultSet rs2 = statement.executeQuery(query);
            int count = 0;
            while (rs2.next()) count++;
            if (count == 0) System.out.println("ConfRoom information deleted successfully.");
            else System.out.println("ConfRoom information did not delete.");
        } catch (SQLException e2) {
            System.out.println("Error checking delete. " + e2);
        }
        connection.close();
    }

    /**
     * This method retrieves a ConfRoom object with the specified ID from the "ConfRooms" table in the
     * database.
     *
     * @param rID the ID of the ConfRoom object to retrieve from the "ConfRooms" table
     * @return the ConfRoom object with the specified ID, or null if not found
     * @throws SQLException if there is a problem accessing the database
     */
    public static ConfRoom getConfRoom(int rID) throws SQLException {
        ArrayList<ConfRoom> bookings = new ArrayList<>();
        Connection connection = DataManager.DbConnection();
        String query = "SELECT * FROM \"ConfRooms\" WHERE \"roomID\" = ?";
        ConfRoom ConfRoom = null;
        try (connection) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, rID);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int roomID = rs.getInt("roomID");
                String locName = rs.getString("locationName");
                int seats = rs.getInt("seats");
                ConfRoom = (new ConfRoom(roomID, locName, seats));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ConfRoom;
    }

    /**
     * This method exports all the ConfRoom objects from the "ConfRoom" table in the database to a CSV
     * file at the specified file path.
     *
     * @param csvFilePath the file path of the CSV file to export the ConfRoom objects to
     * @throws SQLException if there is a problem accessing the database
     * @throws IOException if there is a problem writing the CSV file
     */
    public static void exportConfRoomsToCSV(String csvFilePath) throws SQLException, IOException {
        Connection connection = DataManager.DbConnection();
        String query = "SELECT * FROM \"ConfRooms\"";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
            writer.write("roomID,locationName,seats\n");
            while (resultSet.next()) {
                int roomID = resultSet.getInt("roomID");
                String locName = resultSet.getString("locationName");
                int seats = resultSet.getInt("seats");

                String row = roomID + "," + locName + "," + seats + "\n";
                writer.write(row);
            }
            System.out.println("CSV data downloaded from PostgreSQL database");
        } catch (IOException e) {
            System.err.println("Error downloading CSV data from PostgreSQL database: " + e.getMessage());
        }
    }

    /**
     * Uploads CSV data to a PostgreSQL database table "ConfRooms"
     *
     * @param csvFilePath is a String representing a file path
     * @throws SQLException if an error occurs while uploading the data to the database
     */
    public static void uploadConfRoomToPostgreSQL(String csvFilePath)
            throws SQLException, ParseException {
        List<String[]> csvData;
        Connection connection = DataManager.DbConnection();
        DataManager dataImport = new DataManager();
        csvData = dataImport.parseCSVAndUploadToPostgreSQL(csvFilePath);

        try (connection) {
            String query =
                    "INSERT INTO \"ConfRooms\" (\"roomID\", \"locationName\", \"seats\") VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE \"ConfRooms\";");
            statement.executeUpdate();
            statement = connection.prepareStatement(query);

            for (int i = 1; i < csvData.size(); i++) {
                String[] row = csvData.get(i);
                statement.setInt(1, Integer.parseInt(row[0]));
                statement.setString(2, row[1]);
                statement.setInt(3, Integer.parseInt(row[2]));

                statement.executeUpdate();
            }
            System.out.println("CSV data uploaded to PostgreSQL database");
        } catch (SQLException e) {
            System.err.println("Error uploading CSV data to PostgreSQL database: " + e.getMessage());
        }
    }
}
