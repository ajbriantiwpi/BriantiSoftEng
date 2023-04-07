package edu.wpi.teamname.database.interfaces;

import java.util.ArrayList;

public interface DAO<T> {
    void sync();

    ArrayList<T> getAll();

    void add(T type);

    void delete(T type);
}
