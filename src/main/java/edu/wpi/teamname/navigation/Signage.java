package edu.wpi.teamname.navigation;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

public class Signage {

    @Getter
    private String longName;
    @Getter private String shortName;
    @Getter @Setter private Timestamp date;
    @Getter @Setter private String arrowDirection;
    public Signage(String longName, String shortName, Timestamp date, String arrowDirection) {
        this.longName=longName;
        this.shortName=shortName;
        this.date=date;
        this.arrowDirection=arrowDirection;
    }
}
