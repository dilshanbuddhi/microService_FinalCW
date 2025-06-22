package org.example.userservice.dto;


import org.example.userservice.entity.UserRole;

public class UserRegistrationDto {
    
    private String name;
    
    private String email;
    
    private String password;
    
    private UserRole role;
    
    // Constructors
    public UserRegistrationDto() {}
    
    public UserRegistrationDto(String name, String email, String password, UserRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
}
