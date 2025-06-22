package org.example.parkingservice.repo;

import org.example.parkingservice.Entity.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingRepository extends JpaRepository<ParkingSpace, Long> {
    List<ParkingSpace> findByStatus(ParkingSpace.SpaceStatus status);
    List<ParkingSpace> findByCity(String city);
    List<ParkingSpace> findByZone(String zone);
    List<ParkingSpace> findByOwnerId(String ownerId);
    List<ParkingSpace> findByCityAndStatus(String city, ParkingSpace.SpaceStatus status);
    
    @Query("SELECT p FROM ParkingSpace p WHERE p.location LIKE %:location%")
    List<ParkingSpace> findByLocationContaining(@Param("location") String location);
}