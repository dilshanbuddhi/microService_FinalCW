package org.example.vehicalservice.repo;

import org.example.vehicalservice.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByLicenseNumber(String licenseNumber);
    List<Vehicle> findByUserId(String userId);
    List<Vehicle> findByType(Vehicle.VehicleType type);
    List<Vehicle> findByCurrentParkingSpaceIdIsNotNull();
    List<Vehicle> findByCurrentParkingSpaceIdIsNull();
}
