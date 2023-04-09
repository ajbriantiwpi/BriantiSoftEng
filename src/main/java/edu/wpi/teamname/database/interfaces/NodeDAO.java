package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.database.Login;
import edu.wpi.teamname.navigation.Node;

import java.sql.SQLException;
import java.util.ArrayList;

public interface NodeDAO extends DAO<Node> {
    void sync();

    ArrayList<Node> getAll() throws SQLException;

    void add(Node node) throws SQLException;

    void delete(Node node) throws SQLException;
}
