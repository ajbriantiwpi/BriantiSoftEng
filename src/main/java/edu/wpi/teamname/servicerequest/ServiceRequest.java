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


}
