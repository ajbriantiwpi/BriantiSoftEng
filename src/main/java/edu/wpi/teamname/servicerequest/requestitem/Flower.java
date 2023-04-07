package edu.wpi.teamname.servicerequest.requestitem;

import lombok.Getter;
import lombok.Setter;

public class Flower extends RequestItem{
    @Getter @Setter private float price;
    @Getter @Setter private String category;
    @Getter @Setter private String color;

    public Flower(int flowerID, String name, float price, String category, String color) {
        super(flowerID, name);
        this.price = price;
        this.category = category;
        this.color = color;
    }
}
