package carrentalsystem.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ReservationRequest {
    private Integer vehicleId;
    private Integer userId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
