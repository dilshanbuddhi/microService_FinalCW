package org.example.parkingservice.service;

import org.example.parkingservice.Entity.ParkingSpace;
import org.example.parkingservice.repo.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingService {
    
    @Autowired
    private ParkingRepository parkingRepository;
    
    public List<ParkingSpace> getAllSpaces() {
        return parkingRepository.findAll();
    }
    
    public Optional<ParkingSpace> getSpaceById(Long id) {
        return parkingRepository.findById(id);
    }
    
    public List<ParkingSpace> getAvailableSpaces() {
        return parkingRepository.findByStatus(ParkingSpace.SpaceStatus.AVAILABLE);
    }
    
    public List<ParkingSpace> getSpacesByCity(String city) {
        return parkingRepository.findByCity(city);
    }
    
    public List<ParkingSpace> getAvailableSpacesByCity(String city) {
        return parkingRepository.findByCityAndStatus(city, ParkingSpace.SpaceStatus.AVAILABLE);
    }
    
    public List<ParkingSpace> getSpacesByOwner(String ownerId) {
        return parkingRepository.findByOwnerId(ownerId);
    }
    
    public ParkingSpace createSpace(ParkingSpace space) {
        return parkingRepository.save(space);
    }
    
    public ParkingSpace updateSpace(Long id, ParkingSpace spaceDetails) {
        Optional<ParkingSpace> optionalSpace = parkingRepository.findById(id);
        if (optionalSpace.isPresent()) {
            ParkingSpace space = optionalSpace.get();
            space.setSpaceNumber(spaceDetails.getSpaceNumber());
            space.setLocation(spaceDetails.getLocation());
            space.setCity(spaceDetails.getCity());
            space.setZone(spaceDetails.getZone());
            space.setHourlyRate(spaceDetails.getHourlyRate());
            return parkingRepository.save(space);
        }
        return null;
    }
    
    public ParkingSpace reserveSpace(Long id, String vehicleId) {
        Optional<ParkingSpace> optionalSpace = parkingRepository.findById(id);
        if (optionalSpace.isPresent()) {
            ParkingSpace space = optionalSpace.get();
            if (space.getStatus() == ParkingSpace.SpaceStatus.AVAILABLE) {
                space.setStatus(ParkingSpace.SpaceStatus.RESERVED);
                space.setVehicleId(vehicleId);
                space.setReservedAt(LocalDateTime.now());
                return parkingRepository.save(space);
            }
        }
        return null;
    }
    
    public ParkingSpace occupySpace(Long id) {
        Optional<ParkingSpace> optionalSpace = parkingRepository.findById(id);
        if (optionalSpace.isPresent()) {
            ParkingSpace space = optionalSpace.get();
            if (space.getStatus() == ParkingSpace.SpaceStatus.RESERVED) {
                space.setStatus(ParkingSpace.SpaceStatus.OCCUPIED);
                space.setOccupiedAt(LocalDateTime.now());
                return parkingRepository.save(space);
            }
        }
        return null;
    }
    
    public ParkingSpace releaseSpace(Long id) {
        Optional<ParkingSpace> optionalSpace = parkingRepository.findById(id);
        if (optionalSpace.isPresent()) {
            ParkingSpace space = optionalSpace.get();
            space.setStatus(ParkingSpace.SpaceStatus.AVAILABLE);
            space.setVehicleId(null);
            space.setReservedAt(null);
            space.setOccupiedAt(null);
            return parkingRepository.save(space);
        }
        return null;
    }
    
    public void deleteSpace(Long id) {
        parkingRepository.deleteById(id);
    }
}