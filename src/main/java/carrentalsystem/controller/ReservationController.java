package carrentalsystem.controller;

import carrentalsystem.dto.ReservationRequest;
import carrentalsystem.mapper.ApiResponse;
import carrentalsystem.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;


    @PostMapping("/create")
    public ResponseEntity<Object> makeReservation(@RequestBody ReservationRequest reservationRequest) {
        boolean reservationSuccessful = reservationService.createReservation(reservationRequest);
        if (!reservationSuccessful) {
            return ApiResponse.map(HttpStatus.NO_CONTENT, false, "Reservation failed");
        }
        return ApiResponse.map(HttpStatus.OK, true, "Reservation created successfully");
    }

}
