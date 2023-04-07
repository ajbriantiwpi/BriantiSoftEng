package edu.wpi.teamname.servicerequest;

import edu.wpi.teamname.navigation.Node;
import edu.wpi.teamname.servicerequest.requestitem.RequestItem;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ServiceRequest {
    @Getter @Setter private int requestID;
    @Setter @Getter private String staffName;
    @Setter @Getter private String patientName;

    @Setter @Getter private String roomNumber;

    @Setter @Getter private LocalDateTime deliverBy;
    @Setter @Getter private LocalDateTime requestedAt;

    @Setter @Getter private Status status;
    @Getter private ArrayList<RequestItem> items;

    public ServiceRequest(
            int requestID,
            String staffName,
            String patientName,
            String roomNumber,
            LocalDateTime deliverBy,
            Status status) {
        this.requestID = requestID;
        this.staffName = staffName;
        this.patientName = patientName;
        this.roomNumber = roomNumber;
        this.deliverBy = deliverBy;
        requestedAt = LocalDateTime.now();
        this.status = status;
        items = new ArrayList<RequestItem>();
    }

    public void addItem(int requestID) {
        return;
    }

    public void removeItem(int requestID) {
        return;
    }
}
