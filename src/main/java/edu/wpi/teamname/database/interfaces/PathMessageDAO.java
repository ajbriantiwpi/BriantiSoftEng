package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.navigation.PathMessage;
import java.sql.SQLException;
import java.util.ArrayList;

public interface PathMessageDAO extends DAO<PathMessage> {
    void sync(PathMessage pm) throws SQLException;

    ArrayList<PathMessage> getAll() throws SQLException;

    void add(PathMessage pm) throws SQLException;

    void delete(PathMessage pm) throws SQLException;
}
