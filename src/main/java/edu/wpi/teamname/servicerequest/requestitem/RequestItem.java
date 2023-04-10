package edu.wpi.teamname.servicerequest.requestitem;

import lombok.Getter;
import lombok.Setter;

public abstract class RequestItem {
  @Getter @Setter private int itemID;
  @Getter @Setter private String name;
  @Getter private final int originalID;

  public RequestItem(int itemID, String name) {
    this.itemID = itemID;
    this.name = name;
    this.originalID = itemID;
  }

  public boolean isEqual(RequestItem other) {
    return itemID == other.getItemID();
  }
}
