package edu.wpi.teamname.servicerequest.requestitem;

import lombok.Getter;
import lombok.Setter;

public class Furniture extends RequestItem {
  @Setter @Getter private float price;
  @Setter @Getter private String category;
  @Setter @Getter private String size;
  @Setter @Getter private String color;

  public Furniture(
      int itemID, String name, float price, String category, String size, String color) {
    super(itemID, name);
    this.price = price;
    this.category = category;
    this.size = size;
    this.color = color;
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
        + size
        + ", "
        + color
        + "]";
  }
}
