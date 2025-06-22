package org.example.parkingservice.controller;


import org.example.parkingservice.Entity.ParkingSpace;
import org.example.parkingservice.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {
    
    @Autowired
    private ParkingService parkingService;
    
    @GetMapping
    public List<ParkingSpace> getAllSpaces() {
        return parkingService.getAllSpaces();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpace> getSpaceById(@PathVariable Long id) {
        Optional<ParkingSpace> space = parkingService.getSpaceById(id);
        return space.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/available")
    public List<ParkingSpace> getAvailableSpaces() {
        return parkingService.getAvailableSpaces();
    }
    
    @GetMapping("/city/{city}")
    public List<ParkingSpace> getSpacesByCity(@PathVariable String city) {
        return parkingService.getSpacesByCity(city);
    }
    
    @GetMapping("/city/{city}/available")
    public List<ParkingSpace> getAvailableSpacesByCity(@PathVariable String city) {
        return parkingService.getAvailableSpacesByCity(city);
    }
    
    @GetMapping("/owner/{ownerId}")
    public List<ParkingSpace> getSpacesByOwner(@PathVariable String ownerId) {
        return parkingService.getSpacesByOwner(ownerId);
    }
    
    @PostMapping
    public ParkingSpace createSpace(@RequestBody ParkingSpace space) {
        return parkingService.createSpace(space);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ParkingSpace> updateSpace(@PathVariable Long id, @RequestBody ParkingSpace spaceDetails) {
        ParkingSpace updatedSpace = parkingService.updateSpace(id, spaceDetails);
        return updatedSpace != null ? ResponseEntity.ok(updatedSpace) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/reserve")
    public ResponseEntity<ParkingSpace> reserveSpace(@PathVariable Long id, @RequestParam String vehicleId) {
        ParkingSpace reservedSpace = parkingService.reserveSpace(id, vehicleId);
        return reservedSpace != null ? ResponseEntity.ok(reservedSpace) : ResponseEntity.badRequest().build();
    }
    
    @PostMapping("/{id}/occupy")
    public ResponseEntity<ParkingSpace> occupySpace(@PathVariable Long id) {
        ParkingSpace occupiedSpace = parkingService.occupySpace(id);
        return occupiedSpace != null ? ResponseEntity.ok(occupiedSpace) : ResponseEntity.badRequest().build();
    }
    
    @PostMapping("/{id}/release")
    public ResponseEntity<ParkingSpace> releaseSpace(@PathVariable Long id) {
        ParkingSpace releasedSpace = parkingService.releaseSpace(id);
        return releasedSpace != null ? ResponseEntity.ok(releasedSpace) : ResponseEntity.badRequest().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpace(@PathVariable Long id) {
        parkingService.deleteSpace(id);
        return ResponseEntity.noContent().build();
    }
}