package edu.wpi.teamname.database;

import java.util.ArrayList;

public interface ItemsOrderedDAO extends DAO<ItemsOrdered>{
    void sync();

    ArrayList<ItemsOrdered> getAll();

    void add(ItemsOrdered itemsOrdered);

    void delete(ItemsOrdered itemsOrdered);
}
