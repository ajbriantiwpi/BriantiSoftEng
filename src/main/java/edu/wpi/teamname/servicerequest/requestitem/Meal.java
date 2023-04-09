package edu.wpi.teamname.servicerequest.requestitem;

import lombok.Getter;
import lombok.Setter;

public class Meal extends RequestItem {
  @Setter @Getter private float price;
  @Setter @Getter private String meal;
  @Setter @Getter private String cuisine;

  public Meal(int itemID, String name, float price, String meal, String cuisine) {
    super(itemID, name);
    this.price = price;
    this.meal = meal;
    this.cuisine = cuisine;
  }

  public String toString() {
    return "["
        + this.getItemID()
        + ", "
        + this.getName()
        + ", "
        + price
        + ", "
        + meal
        + ", "
        + cuisine
        + "]";
  }
}
