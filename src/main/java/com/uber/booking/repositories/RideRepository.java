package com.uber.booking.repositories;

import com.uber.booking.entities.RideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface RideRepository extends JpaRepository<RideEntity, Integer> {

    List<RideEntity> findAllByPinCodeAndVehicleTypeAndStatusAndDeleted(Integer pinCode, String vehicleType, String status, Boolean deleted);

    Optional<RideEntity> findFirstByIdAndStatusAndDeleted(Long id, String status, Boolean deleted);

    Optional<RideEntity> findFirstByIdAndDeleted(Long id, Boolean deleted);

    Optional<RideEntity> findFirstByRiderIdAndStatusInAndDeleted(Integer riderId, List<String> status, Boolean deleted);

    Optional<RideEntity> findFirstByIdAndStatusAndDeletedAndDriverId(Long riderId, String status, Boolean deleted, Integer driverId);
}
