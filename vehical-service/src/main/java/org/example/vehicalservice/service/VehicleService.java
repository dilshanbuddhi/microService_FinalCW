package org.example.vehicalservice.service;

import org.example.vehicalservice.entity.Vehicle;
import org.example.vehicalservice.repo.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    
    @Autowired
    private VehicleRepository vehicleRepository;
    
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
    
    public Optional<Vehicle> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }
    
    public Optional<Vehicle> getVehicleByLicenseNumber(String licenseNumber) {
        return vehicleRepository.findByLicenseNumber(licenseNumber);
    }
    
    public List<Vehicle> getVehiclesByUserId(String userId) {
        return vehicleRepository.findByUserId(userId);
    }
    
    public List<Vehicle> getVehiclesByType(Vehicle.VehicleType type) {
        return vehicleRepository.findByType(type);
    }
    
    public List<Vehicle> getParkedVehicles() {
        return vehicleRepository.findByCurrentParkingSpaceIdIsNotNull();
    }
    
    public List<Vehicle> getUnparkedVehicles() {
        return vehicleRepository.findByCurrentParkingSpaceIdIsNull();
    }
    
    public Vehicle registerVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }
    
    public Vehicle updateVehicle(Long id, Vehicle vehicleDetails) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();
            vehicle.setLicenseNumber(vehicleDetails.getLicenseNumber());
            vehicle.setMake(vehicleDetails.getMake());
            vehicle.setModel(vehicleDetails.getModel());
            vehicle.setColor(vehicleDetails.getColor());
            vehicle.setType(vehicleDetails.getType());
            return vehicleRepository.save(vehicle);
        }
        return null;
    }
    
    public Vehicle simulateEntry(Long id, Long parkingSpaceId) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();
            vehicle.setCurrentParkingSpaceId(parkingSpaceId);
            vehicle.setEntryTime(LocalDateTime.now());
            vehicle.setExitTime(null);
            return vehicleRepository.save(vehicle);
        }
        return null;
    }
    
    public Vehicle simulateExit(Long id) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();
            vehicle.setCurrentParkingSpaceId(null);
            vehicle.setExitTime(LocalDateTime.now());
            return vehicleRepository.save(vehicle);
        }
        return null;
    }
    
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }
}
