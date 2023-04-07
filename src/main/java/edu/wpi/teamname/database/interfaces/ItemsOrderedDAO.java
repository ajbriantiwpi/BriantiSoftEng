package edu.wpi.teamname.database.interfaces;

import edu.wpi.teamname.servicerequest.ItemsOrdered;

import java.util.ArrayList;

public interface ItemsOrderedDAO extends DAO<ItemsOrdered>{
    void sync();

    ArrayList<ItemsOrdered> getAll();

    void add(ItemsOrdered itemsOrdered);

    void delete(ItemsOrdered itemsOrdered);
}
