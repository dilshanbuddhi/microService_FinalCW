package org.example.vehicalservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String licenseNumber;
    
    @Column(nullable = false)
    private String make;
    
    @Column(nullable = false)
    private String model;
    
    @Column(nullable = false)
    private String color;
    
    @Column(nullable = false)
    private String userId;
    
    @Enumerated(EnumType.STRING)
    private VehicleType type;
    
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Long currentParkingSpaceId;
    
    // Constructors
    public Vehicle() {}
    
    public Vehicle(String licenseNumber, String make, String model, String color, String userId, VehicleType type) {
        this.licenseNumber = licenseNumber;
        this.make = make;
        this.model = model;
        this.color = color;
        this.userId = userId;
        this.type = type;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public VehicleType getType() { return type; }
    public void setType(VehicleType type) { this.type = type; }
    
    public LocalDateTime getEntryTime() { return entryTime; }
    public void setEntryTime(LocalDateTime entryTime) { this.entryTime = entryTime; }
    
    public LocalDateTime getExitTime() { return exitTime; }
    public void setExitTime(LocalDateTime exitTime) { this.exitTime = exitTime; }
    
    public Long getCurrentParkingSpaceId() { return currentParkingSpaceId; }
    public void setCurrentParkingSpaceId(Long currentParkingSpaceId) { this.currentParkingSpaceId = currentParkingSpaceId; }
    
    public enum VehicleType {
        CAR, MOTORCYCLE, TRUCK, SUV, VAN
    }
}