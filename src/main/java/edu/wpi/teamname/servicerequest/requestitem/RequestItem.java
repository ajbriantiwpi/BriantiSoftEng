package edu.wpi.teamname.servicerequest.requestitem;

import lombok.Getter;
import lombok.Setter;

public abstract class RequestItem {
    @Getter @Setter private int itemID;
    @Getter @Setter private String name;

    public RequestItem(int itemID, String name) {
        this.itemID = itemID;
        this.name = name;
    }

    public boolean isEqual(RequestItem other) {
        return itemID == other.getItemID();
    }
}
