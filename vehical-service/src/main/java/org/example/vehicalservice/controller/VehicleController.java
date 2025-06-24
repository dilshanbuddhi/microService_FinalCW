package org.example.vehicalservice.controller;


import org.example.vehicalservice.entity.Vehicle;
import org.example.vehicalservice.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    
    @Autowired
    private VehicleService vehicleService;
    
    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        Optional<Vehicle> vehicle = vehicleService.getVehicleById(id);
        return vehicle.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    
    @GetMapping("/user/{userId}")
    public List<Vehicle> getVehiclesByUserId(@PathVariable String userId) {
        return vehicleService.getVehiclesByUserId(userId);
    }

    
    @GetMapping("/parked")
    public List<Vehicle> getParkedVehicles() {
        return vehicleService.getParkedVehicles();
    }
    
    @GetMapping("/unparked")
    public List<Vehicle> getUnparkedVehicles() {
        return vehicleService.getUnparkedVehicles();
    }
    
    @PostMapping
    public Vehicle registerVehicle(@RequestBody Vehicle vehicle) {
        return vehicleService.registerVehicle(vehicle);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @RequestBody Vehicle vehicleDetails) {
        Vehicle updatedVehicle = vehicleService.updateVehicle(id, vehicleDetails);
        return updatedVehicle != null ? ResponseEntity.ok(updatedVehicle) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/entry")
    public ResponseEntity<Vehicle> simulateEntry(@PathVariable Long id, @RequestParam Long parkingSpaceId) {
        Vehicle vehicle = vehicleService.simulateEntry(id, parkingSpaceId);
        return vehicle != null ? ResponseEntity.ok(vehicle) : ResponseEntity.badRequest().build();
    }
    
    @PostMapping("/{id}/exit")
    public ResponseEntity<Vehicle> simulateExit(@PathVariable Long id) {
        Vehicle vehicle = vehicleService.simulateExit(id);
        return vehicle != null ? ResponseEntity.ok(vehicle) : ResponseEntity.badRequest().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}