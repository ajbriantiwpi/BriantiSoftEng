package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.database.Login;
import edu.wpi.teamname.servicerequest.requestitem.Meal;

import java.util.ArrayList;

public interface MealDAO extends DAO<Meal> {
    void sync();

    ArrayList<Meal> getAll();

    void add(Meal meal);

    void delete(Meal meal);
}
