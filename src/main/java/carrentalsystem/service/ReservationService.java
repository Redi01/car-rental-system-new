package carrentalsystem.service;

import carrentalsystem.dto.ReservationRequest;
import carrentalsystem.entities.Vehicle;

import java.time.LocalDateTime;

public interface ReservationService {
    Boolean createReservation(ReservationRequest reservationRequest);
    Boolean isVehicleAvailable(Vehicle vehicle, LocalDateTime startDate, LocalDateTime endDate);
}
