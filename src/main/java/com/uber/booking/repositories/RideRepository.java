package com.uber.booking.repositories;

import com.uber.booking.entities.RideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface RideRepository extends JpaRepository<RideEntity, Integer> {

    List<RideEntity> findAllByPinCodeAndVehicleTypeAndStatusAndDeleted(Integer pinCode, String vehicleType, String status, Boolean deleted);

    Optional<RideEntity> findFirstByIdAndStatusAndDeleted(Integer pinCode, String status, Boolean deleted);

    Optional<RideEntity> findFirstByIdAndStatusAndDeletedAndAndDriverId(Integer pinCode, String status, Boolean deleted, Integer driverId);
}
