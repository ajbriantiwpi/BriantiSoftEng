package edu.wpi.teamname.navigation;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

public class Move {
        @Getter @Setter private int nodeID;
        @Getter @Setter private String longName;
        @Getter @Setter private LocalDate date;

        public Move(int nodeID, String longName, LocalDate date) {
            this.nodeID = nodeID;
            this.longName = longName;
            this.date = date;
        }
}
