package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.navigation.Signage;
import edu.wpi.teamname.servicerequest.ServiceRequest;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SignageDAO {

    void sync(Signage signage) throws SQLException;

    ArrayList<Signage> getAll() throws SQLException;

    void add(Signage signage) throws SQLException;

    void delete(Signage signage) throws SQLException;
}
