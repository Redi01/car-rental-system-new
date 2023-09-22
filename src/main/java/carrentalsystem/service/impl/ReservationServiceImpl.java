package carrentalsystem.service.impl;

import carrentalsystem.dto.ReservationRequest;
import carrentalsystem.entities.Reservation;
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

        reservationRepository.getAllByEndDateGreaterThanOrderByStartDateDesc(reservationRequest.getStartDate());

        LocalDateTime startDate = reservationRequest.getStartDate();
        LocalDateTime endDate = reservationRequest.getEndDate();

        Integer userId = reservationRequest.getUserId();
        userRepository.findUserByUserId(userId).orElse(null);

        Integer vehicleId = reservationRequest.getVehicleId();
        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);

        if (vehicle == null || startDate == null || endDate == null) {
            return false;
        }

        if (!isVehicleAvailable(vehicle, startDate, endDate)) {
            return false;
        }

        Reservation reservation = new Reservation(startDate, endDate, vehicle);
        reservationRepository.save(reservation);
        return true;
    }

    public Boolean isVehicleAvailable(Vehicle vehicle, LocalDateTime startDate, LocalDateTime endDate) {
        List<Reservation> reservations = reservationRepository.getAllByEndDateGreaterThanOrderByStartDateDesc(startDate);

        if(reservations.isEmpty()){
            return true;
        }

        Boolean isAvailable = Boolean.FALSE;

        for (Reservation reservation : reservations) {
            LocalDateTime existingStartDate = reservation.getStartDate();
            LocalDateTime existingEndDate = reservation.getEndDate();

            // First case: The new reservation is before an existing reservation
            // E.g. StartDate: 13 October, existingStartDate: 15 October
            // && TO DO: startDate is 24 hours before the existingStartDate - check if this one is needed
            if (startDate.isBefore(existingStartDate) && endDate.isBefore(existingStartDate)) {
                isAvailable = Boolean.TRUE;
                break;
            }

            if (startDate.isAfter(existingEndDate)) {
                return true;
            }






        }
        return isAvailable;
    }


}
