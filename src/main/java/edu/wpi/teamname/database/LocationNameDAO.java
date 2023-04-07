package edu.wpi.teamname.database;

import edu.wpi.teamname.navigation.LocationName;

import java.util.ArrayList;

public interface LocationNameDAO extends DAO<LocationName>{
    void sync();

    ArrayList<LocationName> getAll();

    void add(LocationName locationName);

    void delete(LocationName locationName);
}
