package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.database.Login;
import edu.wpi.teamname.navigation.Node;

import java.util.ArrayList;

public interface NodeDAO extends DAO<Node> {
    void sync();

    ArrayList<Node> getAll();

    void add(Node node);

    void delete(Node node);
}
