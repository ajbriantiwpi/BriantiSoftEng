package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.database.Login;
import edu.wpi.teamname.servicerequest.requestitem.Meal;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MealDAO extends DAO<Meal> {
    void sync(Meal meal);

    ArrayList<Meal> getAll() throws SQLException;

    void add(Meal meal) throws SQLException;

    void delete(Meal meal) throws SQLException;
}
