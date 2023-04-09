package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.database.Login;
import edu.wpi.teamname.servicerequest.ServiceRequest;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ServiceRequestDAO extends DAO<ServiceRequest> {
    void sync();

    ArrayList<ServiceRequest> getAll() throws SQLException;

    void add(ServiceRequest serviceRequest) throws SQLException;

    void delete(ServiceRequest serviceRequest) throws SQLException;
}
