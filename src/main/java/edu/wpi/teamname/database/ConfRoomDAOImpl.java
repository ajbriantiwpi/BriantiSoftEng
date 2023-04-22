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
                    "UPDATE \"ConferenceRooms\" SET \"room\" = ?, \"starttime\" = ?, \"endtime\" = ?, \"datebook\" = ?, \"name\" = ?  WHERE \"room\" = ? AND \"startime\" = ? AND \"endtime\" = ? AND \"datebook\" = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, ConfRoom.getRoom());
            statement.setString(2, ConfRoom.getStartTime());
            statement.setString(3, ConfRoom.getEndTime());
            statement.setTimestamp(4, ConfRoom.getDateBooked());
            statement.setString(5, ConfRoom.getName());

            statement.setString(6, ConfRoom.getOrigRoom());
            statement.setString(7, ConfRoom.getOrigRoom());
            statement.setString(8, ConfRoom.getOrigEndTime());
            statement.setTimestamp(9, ConfRoom.getOrigDateBooked());

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
            String query = "SELECT * FROM \"ConferenceRooms\"";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String room = rs.getString("room");
                String startTime = rs.getString("starttime");
                String endTime= rs.getString("endtime");
                Timestamp dateBook = rs.getTimestamp("datebook");
                String name = rs.getString("name");
                list.add(new ConfRoom(room, startTime, endTime, dateBook, name));
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
                    "INSERT INTO \"ConferenceRooms\" (\"room\", \"starttime\", \"endtime\", \"datbook\", \"name\") "
                            + "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, ConfRoom.getRoom());
            statement.setString(2, ConfRoom.getStartTime());
            statement.setString(3, ConfRoom.getEndTime());
            statement.setTimestamp(4, ConfRoom.getDateBooked());
            statement.setString(5, ConfRoom.getName());

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
        String query = "DELETE FROM \"ConferenceRooms\" WHERE \"room\" = ? AND \"startime\" = ? AND \"endtime\" = ? AND \"datebook\" = ?";
        try (connection) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, ConfRoom.getRoom());
            statement.setString(2, ConfRoom.getStartTime());
            statement.setString(3, ConfRoom.getEndTime());
            statement.setTimestamp(4, ConfRoom.getDateBooked());

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
     * @param room the ID of the ConfRoom object to retrieve from the "ConfRooms" table
     * @return the ConfRoom object with the specified ID, or null if not found
     * @throws SQLException if there is a problem accessing the database
     */
    public static ConfRoom getConfRoom(String room, String startTime, String endTime, Timestamp dateBook) throws SQLException {
        Connection connection = DataManager.DbConnection();
        String query = "SELECT * FROM \"ConferenceRooms\" WHERE \"room\" = ? AND \"startime\" = ? AND \"endtime\" = ? AND \"datebook\" = ?";
        ConfRoom ConfRoom = null;
        try (connection) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, ConfRoom.getRoom());
            statement.setString(2, ConfRoom.getStartTime());
            statement.setString(3, ConfRoom.getEndTime());
            statement.setTimestamp(4, ConfRoom.getDateBooked());

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String room2 = rs.getString("room");
                String startTime2 = rs.getString("starttime");
                String endTime2 = rs.getString("endtime");
                Timestamp dateBook2 = rs.getTimestamp("datebook");
                String name2 = rs.getString("name");
                ConfRoom = (new ConfRoom(room2, startTime2, endTime2, dateBook2, name2));
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
        String query = "SELECT * FROM \"ConferenceRooms\"";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
            writer.write("room,starttime,endtime,datebook,name\n");
            while (resultSet.next()) {
                String room2 = resultSet.getString("room");
                String startTime2 = resultSet.getString("starttime");
                String endTime2 = resultSet.getString("endtime");
                Timestamp dateBook2 = resultSet.getTimestamp("datebook");
                String name2 = resultSet.getString("name");

                String row = room2 + "," + startTime2 + "," + endTime2 + "," + dateBook2 + "," + name2 + "\n";
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
                    "INSERT INTO \"ConferenceRooms\" (\"room\", \"starttime\", \"endtime\", \"datebook\", \"name\") VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE \"ConferenceRooms\";");
            statement.executeUpdate();
            statement = connection.prepareStatement(query);

            for (int i = 1; i < csvData.size(); i++) {
                String[] row = csvData.get(i);
                statement.setString(1, row[0]);
                statement.setString(2, row[1]);
                statement.setString(3, row[2]);
                statement.setTimestamp(4, Timestamp.valueOf(row[3]));
                statement.setString(5, row[4]);

                statement.executeUpdate();
            }
            System.out.println("CSV data uploaded to PostgreSQL database");
        } catch (SQLException e) {
            System.err.println("Error uploading CSV data to PostgreSQL database: " + e.getMessage());
        }
    }
}
