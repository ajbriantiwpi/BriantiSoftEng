package edu.wpi.teamname.database;

import java.util.ArrayList;

public interface DAO<T> {
    void sync();

    ArrayList<T> getAll();

    void add(T type);

    void delete(T type);
}
