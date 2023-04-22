package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.servicerequest.requestitem.ConfRoom;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public interface ConfRomDAO extends DAO<ConfRoom>{
    int getConfRoomTimes();
    ArrayList<String> getConfRooms(Timestamp datB) throws SQLException;
    void sync(ConfRoom confRoom) throws SQLException;

    ArrayList<ConfRoom> getAll() throws SQLException;

    void add(ConfRoom confRoom) throws SQLException;

    void delete(ConfRoom confRoom) throws SQLException;
}
