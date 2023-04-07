package edu.wpi.teamname.servicerequest;

public enum Status {
    BLANK("BLANK"),
    PROCESSING("PROCESSING"),
    DONE("DONE");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatusString() {
        return status;
    }
}
