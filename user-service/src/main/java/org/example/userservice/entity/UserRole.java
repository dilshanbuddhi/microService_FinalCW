package org.example.userservice.entity;

public enum UserRole {
    DRIVER("Driver"),
    OWNER("Parking Owner"),
    ADMIN("System Administrator");
    
    private final String displayName;
    
    UserRole(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}