package edu.wpi.teamname.servicerequest;

public enum RequestType {
  MEAL("MEAL"),
  FLOWER("FLOWER"),
  OFFICESUPPLY("OFFICESUPPLY"),
  FURNITURE("FURNITURE");

  private final String type;

  RequestType(String type) {
    this.type = type;
  }

  public String getString() {
    return type;
  }
}
