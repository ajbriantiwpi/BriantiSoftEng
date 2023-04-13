package edu.wpi.teamname.servicerequest.requestitem;

import java.text.DecimalFormat;
import lombok.Getter;
import lombok.Setter;

public abstract class RequestItem {
  @Getter @Setter private int itemID;
  @Getter @Setter private String name;
  @Getter private final int originalID;
  @Getter @Setter private float price;

  /*public RequestItem(int itemID, String name) {
    this.itemID = itemID;
    this.name = name;
    this.originalID = itemID;
    this.price = 0;
  }*/

  /**
   * Creates a request item
   *
   * @param itemID id of the item
   * @param name name of the item
   * @param price price of the item
   */
  public RequestItem(int itemID, String name, float price) {
    DecimalFormat df = new DecimalFormat("0.00");
    this.itemID = itemID;
    this.name = name;
    this.originalID = itemID;
    this.price = Float.parseFloat(df.format(price));
  }

  /**
   * changes the id of the item
   *
   * @param itemID id needed to change to
   */
  public RequestItem(int itemID) {
    this.originalID = itemID;
  }

  /**
   * checks if this is eaual to another request item object
   *
   * @param other the item we are checking if it is the same as
   * @return true if equal, false if not
   */
  public boolean isEqual(RequestItem other) {
    return itemID == other.getItemID();
  }
}
