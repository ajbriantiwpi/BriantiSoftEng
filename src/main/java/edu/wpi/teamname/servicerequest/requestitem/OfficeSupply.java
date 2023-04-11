package edu.wpi.teamname.servicerequest.requestitem;

import lombok.Getter;
import lombok.Setter;

public class OfficeSupply extends RequestItem {
  @Setter @Getter private float price;
  @Setter @Getter private String category;
  @Setter @Getter private boolean isElectric;

  public OfficeSupply(int itemID, String name, float price, String category, boolean isElectric) {
    super(itemID, name);
    this.price = price;
    this.category = category;
    this.isElectric = isElectric;
  }

  public String toString() {
    return "["
        + this.getItemID()
        + ", "
        + this.getName()
        + ", "
        + price
        + ", "
        + category
        + ", "
        + isElectric
        + "]";
  }
}
