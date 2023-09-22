package carrentalsystem.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reservations")
@RequiredArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue
    private Integer reservationId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String reservationStatus;
    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "vehicleId")
    private Vehicle vehicle;

    public Reservation(LocalDateTime startDate, LocalDateTime endDate, Vehicle vehicle) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.vehicle = vehicle;
    }

}
