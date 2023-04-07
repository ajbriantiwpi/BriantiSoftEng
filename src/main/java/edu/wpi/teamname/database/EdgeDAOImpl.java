package edu.wpi.teamname.database;

import edu.wpi.teamname.database.interfaces.EdgeDAO;
import edu.wpi.teamname.navigation.Edge;
import lombok.Getter;

import java.util.ArrayList;

public class EdgeDAOImpl implements EdgeDAO {
    @Getter private ArrayList<Edge> edges;

    /**
     *
     */
    @Override
    public void sync() {

    }

    /**
     * @return
     */
    @Override
    public ArrayList<Edge> getAll() {
        return null;
    }

    /**
     * @param type
     */
    @Override
    public void add(Edge type) {

    }

    /**
     * @param type
     */
    @Override
    public void delete(Edge type) {

    }
}
