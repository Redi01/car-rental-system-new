package carrentalsystem.service.impl;

import carrentalsystem.auth.service.JwtService;
import carrentalsystem.dto.ReservationRequest;
import carrentalsystem.entities.Reservation;
import carrentalsystem.entities.User;
import carrentalsystem.entities.Vehicle;
import carrentalsystem.repository.ReservationRepository;
import carrentalsystem.repository.UserRepository;
import carrentalsystem.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private static final Duration MIN_RESERVATION_DURATION = Duration.ofHours(48); // Minimum duration: 48 hour
    private static final Duration MAX_RESERVATION_DURATION = Duration.ofDays(30); // Maximum duration: 30 days
    private final ReservationRepository reservationRepository;
    private final VehicleServiceImpl vehicleService;
    private final UserRepository userRepository;
    private final HttpServletRequest request;
    private final JwtService jwtService;

    @Override
    public Reservation findReservationById(Integer id) {
        return reservationRepository.findByReservationId(id);
    }

    @Transactional
    @Override
    public Reservation createReservation(ReservationRequest reservationRequest) {
        LocalDateTime startDate = reservationRequest.getStartDate();
        LocalDateTime endDate = reservationRequest.getEndDate();

        Duration reservationDuration = Duration.between(startDate, endDate);
        if (reservationDuration.isNegative() || reservationDuration.compareTo(MIN_RESERVATION_DURATION) < 0
                || reservationDuration.compareTo(MAX_RESERVATION_DURATION) > 0) {
            return null;
        }

        String authHeader = request.getHeader("Authorization");
        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwt);
        User user = userRepository.findByEmail(userEmail).orElse(null);

        Vehicle vehicle = vehicleService.getVehicleById(reservationRequest.getVehicleId());

        if (vehicle == null || startDate == null || endDate == null || user == null) {
            return null;
        }

        List<Reservation> conflictingReservations = reservationRepository.findConflictingReservations(
                vehicle.getVehicleId(),
                startDate,
                endDate
        );

        if (!conflictingReservations.isEmpty()) {
            return null;
        }

        Reservation reservation = new Reservation(startDate, endDate, vehicle, user);
        reservation.setUser(user);
        reservation.setVehicle(vehicle);
        reservationRepository.save(reservation);
        return reservation;
    }

}
