package org.example.parkingservice.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking_spaces")
public class ParkingSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String spaceNumber;
    
    @Column(nullable = false)
    private String location;
    
    @Column(nullable = false)
    private String city;
    
    @Column(nullable = false)
    private String zone;
    
    @Column(nullable = false)
    private String ownerId;
    
    @Enumerated(EnumType.STRING)
    private SpaceStatus status = SpaceStatus.AVAILABLE;
    
    private String vehicleId;
    private LocalDateTime reservedAt;
    private LocalDateTime occupiedAt;
    private double hourlyRate;
    
    // Constructors
    public ParkingSpace() {}
    
    public ParkingSpace(String spaceNumber, String location, String city, String zone, String ownerId, double hourlyRate) {
        this.spaceNumber = spaceNumber;
        this.location = location;
        this.city = city;
        this.zone = zone;
        this.ownerId = ownerId;
        this.hourlyRate = hourlyRate;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getSpaceNumber() { return spaceNumber; }
    public void setSpaceNumber(String spaceNumber) { this.spaceNumber = spaceNumber; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }
    
    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }
    
    public SpaceStatus getStatus() { return status; }
    public void setStatus(SpaceStatus status) { this.status = status; }
    
    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }
    
    public LocalDateTime getReservedAt() { return reservedAt; }
    public void setReservedAt(LocalDateTime reservedAt) { this.reservedAt = reservedAt; }
    
    public LocalDateTime getOccupiedAt() { return occupiedAt; }
    public void setOccupiedAt(LocalDateTime occupiedAt) { this.occupiedAt = occupiedAt; }
    
    public double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }
    
    public enum SpaceStatus {
        AVAILABLE, RESERVED, OCCUPIED
    }
}