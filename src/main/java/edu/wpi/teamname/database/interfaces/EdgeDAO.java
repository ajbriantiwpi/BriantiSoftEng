package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.navigation.Edge;

import java.util.ArrayList;

public interface EdgeDAO extends DAO<Edge>{
    void sync();

    ArrayList<Edge> getAll();

    void add(Edge type);

    void delete(Edge type);
}
