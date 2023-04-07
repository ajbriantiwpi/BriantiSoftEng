package edu.wpi.teamname.servicerequest;

import edu.wpi.teamname.navigation.Node;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class ServiceRequest {
    @Getter
    @Setter
    private int requestID;
    @Setter @Getter private String staffName;
    @Setter @Getter private String patientName;

    @Setter @Getter private String roomNumber;

    @Setter @Getter private LocalDateTime deliverBy;
    @Setter @Getter private LocalDateTime requestedAt;

    @Setter @Getter private Status status;

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
    }

    public ServiceRequest(
            int requestID,
            String staffName,
            String patientName,
            String roomNumber,
            LocalDateTime deliverBy) {
        this.requestID = requestID;
        this.staffName = staffName;
        this.patientName = patientName;
        this.roomNumber = roomNumber;
        this.deliverBy = deliverBy;
        requestedAt = LocalDateTime.now();
        this.status = Status.BLANK;
    }

    public ServiceRequest(
            int requestID,
            String staffName,
            String patientName,
            String roomNumber,
            LocalDateTime deliverBy,
            LocalDateTime requestedAt,
            Status status) {
        this.requestID = requestID;
        this.staffName = staffName;
        this.patientName = patientName;
        this.roomNumber = roomNumber;
        this.deliverBy = deliverBy;
        this.requestedAt = requestedAt;
        this.status = status;
    }
}
