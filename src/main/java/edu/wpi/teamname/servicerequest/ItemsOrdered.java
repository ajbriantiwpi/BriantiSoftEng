package edu.wpi.teamname.servicerequest;

import lombok.Getter;
import lombok.Setter;

public class ItemsOrdered {
  @Setter @Getter private int requestID;
  @Setter @Getter private int itemID;
  @Setter @Getter private int quantity;
  @Getter private final int originalRequestID;
  @Getter private final int originalItemID;

  public ItemsOrdered(int requestID, int itemID, int quantity) {
    this.requestID = requestID;
    this.itemID = itemID;
    this.quantity = quantity;
    this.originalRequestID = requestID;
    this.originalItemID = itemID;
  }

  public String toString() {
    return "[" + requestID + " " + itemID + " " + quantity + "]";
  }
}
