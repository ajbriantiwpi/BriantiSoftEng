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

  @Override
  public String toString() {
    if (this.getString() == "MEAL") {
      return "Meal Request";
    } else if (this.getString() == "FLOWER") {
      return "Flower Request";
    } else if (this.getString() == "OFFICESUPPLY") {
      return "Office Supply Request";
    }
    if (this.getString() == "FURNITURE") {
      return "Furniture Request";
    } else {
      return "";
    }
  }
}
