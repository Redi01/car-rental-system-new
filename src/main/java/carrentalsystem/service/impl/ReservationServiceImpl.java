package carrentalsystem.service.impl;

import carrentalsystem.dto.ReservationRequest;
import carrentalsystem.entities.Reservation;
import carrentalsystem.entities.User;
import carrentalsystem.entities.Vehicle;
import carrentalsystem.repository.ReservationRepository;
import carrentalsystem.repository.UserRepository;
import carrentalsystem.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;

    private final VehicleServiceImpl vehicleService;

    private final UserRepository userRepository;


    @Transactional
    @Override
    public Boolean createReservation(ReservationRequest reservationRequest) {
        LocalDateTime startDate = reservationRequest.getStartDate();
        LocalDateTime endDate = reservationRequest.getEndDate();

        // Todo:  Get user from Token
        Integer userId = reservationRequest.getUserId();
        User user = userRepository.findUserByUserId(userId).orElse(null);

        Integer vehicleId = reservationRequest.getVehicleId();
        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);

        if (vehicle == null || startDate == null || endDate == null) {
            return false;
        }

        if (!isVehicleAvailable(vehicle, startDate, endDate)) {
            return false;
        }

        Reservation reservation = new Reservation(startDate, endDate, vehicle, user);
        reservationRepository.save(reservation);
        return true;
    }

    public Boolean isVehicleAvailable(Vehicle vehicle, LocalDateTime startDate, LocalDateTime endDate) {
        List<Reservation> conflictingStartDates = reservationRepository.findByStartDateGreaterThanAndStartDateLessThan(startDate, endDate);
        if (!conflictingStartDates.isEmpty()) {
            return false;
        }

        List<Reservation> conflictingEndDates = reservationRepository.findByEndDateGreaterThanAndEndDateLessThan(startDate, endDate);
        return conflictingEndDates.isEmpty();
    }

}
