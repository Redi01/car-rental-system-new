package carrentalsystem.service;

import carrentalsystem.dto.ReservationRequest;
import carrentalsystem.entities.Reservation;
import carrentalsystem.entities.Vehicle;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface ReservationService {

    Reservation findReservationById(Integer id);

    Reservation createReservation(ReservationRequest reservationRequest);

    Boolean isVehicleAvailable(Vehicle vehicle, LocalDateTime startDate, LocalDateTime endDate);
}
