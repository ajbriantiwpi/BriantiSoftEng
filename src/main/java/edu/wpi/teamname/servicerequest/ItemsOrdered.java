package edu.wpi.teamname.servicerequest;

import lombok.Getter;
import lombok.Setter;

public class ItemsOrdered {
  @Setter @Getter private int requestID;
  @Setter @Getter private int itemID;
  @Setter @Getter private int quantity;
  @Getter private final int originalRequestID;
  @Getter private final int originalItemID;

  /**
   * creates an ordered items list for a given request ID
   *
   * @param requestID the request id that we are adding items to
   * @param itemID the items id
   * @param quantity number of items to assign
   */
  public ItemsOrdered(int requestID, int itemID, int quantity) {
    this.requestID = requestID;
    this.itemID = itemID;
    this.quantity = quantity;
    this.originalRequestID = requestID;
    this.originalItemID = itemID;
  }

  /**
   * a to string method
   *
   * @return String in format [<requestID>, <itemID>, <quantity>]
   */
  public String toString() {
    return "[" + requestID + " " + itemID + " " + quantity + "]";
  }
}
