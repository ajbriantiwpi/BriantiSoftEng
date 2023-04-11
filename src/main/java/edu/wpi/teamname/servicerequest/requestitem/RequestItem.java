package edu.wpi.teamname.servicerequest.requestitem;

import edu.wpi.teamname.database.DataManager;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class RequestItem {
  @Getter @Setter private int itemID;
  @Getter @Setter private String name;
  @Getter private final int originalID;

  public RequestItem(int itemID, String name) {
    this.itemID = itemID;
    this.name = name;
    this.originalID = itemID;
  }

  public RequestItem(int itemID) {
    this.originalID = itemID;
  }

  public boolean isEqual(RequestItem other) {
    return itemID == other.getItemID();
  }



}
