package edu.wpi.teamname.employees;

public enum ClearanceLevel {
    ADMIN("ADMIN"),
    STAFF("STAFF");

    private final String level;

    ClearanceLevel(String level) {
        this.level = level;
    }

    public String getString() {
        return level;
    }
}
