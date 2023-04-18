package edu.wpi.teamname.employees;

public enum EmployeeType {
  ADMIN("ADMIN"),
  STAFF("STAFF");
  // CHEF("CHEF"),
  // NURSE("NURSE"),
  // RECEPTIONIST("RECEPTIONIST"),
  // DOCTOR("DOCTOR"),
  // JANITOR("JANITOR"),
  // MAINTENANCE("MAINTENANCE"),
  // DELIVERY("DELIVERY"),
  // SECURITY("SECURITY");

  private final String type;

  EmployeeType(String type) {
    this.type = type;
  }

  public String getString() {
    return type;
  }
}
