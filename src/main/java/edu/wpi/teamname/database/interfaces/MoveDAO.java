package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.navigation.Move;

import java.util.ArrayList;

public interface MoveDAO extends DAO<Move> {
    void sync();

    ArrayList<Move> getAll();

    void add(Move type);

    void delete(Move type);
}
