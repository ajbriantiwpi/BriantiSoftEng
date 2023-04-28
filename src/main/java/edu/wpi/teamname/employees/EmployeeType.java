package edu.wpi.teamname.employees;

public enum EmployeeType {
  CHEF("CHEF"),
  NURSE("NURSE"),
  RECEPTIONIST("RECEPTIONIST"),
  DOCTOR("DOCTOR"),
  JANITOR("JANITOR"),
  MAINTENANCE("MAINTENANCE"),
  DELIVERY("DELIVERY"),
  MANAGER("MANAGER"),
  ADMINISTRATOR("ADMINISTRATOR"),
  SECURITY("SECURITY"),
  PLUMBER("PLUMBER"),
  NONE("NONE");

  private final String type;

  EmployeeType(String type) {
    this.type = type;
  }

  public String getString() {
    return type;
  }

  /**
   * * Gets the string values of all of the EmployeeTypes and returns a string array of all of them
   * formatted It is formatted with first letter is capitalized and the rest is lowercase
   *
   * @return The string array with all formatted values
   */
  public static String[] formattedValues() {
    String[] output = new String[values().length];
    String string = "";
    for (int i = 0; i < values().length; i++) {
      string = values()[i].getString();
      output[i] = string.substring(0, 1) + string.substring(1).toLowerCase();
    }
    return output;
  }
}
