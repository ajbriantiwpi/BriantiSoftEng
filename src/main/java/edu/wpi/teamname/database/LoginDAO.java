package edu.wpi.teamname.database;

import java.util.ArrayList;

public interface LoginDAO extends DAO<Login>{
    void sync();

    ArrayList<Login> getAll();

    void add(Login login);

    void delete(Login login);
}
