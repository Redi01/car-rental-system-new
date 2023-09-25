package carrentalsystem.controller;

import carrentalsystem.dto.ReservationRequest;
import carrentalsystem.entities.Reservation;
import carrentalsystem.mapper.ApiResponse;
import carrentalsystem.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Integer id) {
        try {
            Reservation reservation = reservationService.findReservationById(id);
            if (reservation == null) {
                return ApiResponse.map(HttpStatus.NOT_FOUND, null, "Reservation not found!");
            }
            return ApiResponse.map(HttpStatus.OK, reservation, "Reservation retrieved successfully.");
        } catch (Exception e) {
            return ApiResponse.map(HttpStatus.INTERNAL_SERVER_ERROR, null, "Server Failed!");

        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> makeReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationService.createReservation(reservationRequest);
        if (reservation == null) {
            return ApiResponse.map(HttpStatus.BAD_REQUEST, null, "Reservation failed");
        }
        return ApiResponse.map(HttpStatus.OK, reservation, "Reservation created successfully");
    }

}
