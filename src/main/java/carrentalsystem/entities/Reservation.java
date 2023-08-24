package carrentalsystem.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue
    private Integer reservationId;
    private LocalDateTime pickupDate;
    private LocalDateTime returnDate;
    private String reservationStatus;
    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "vehicleId")
    private Vehicle vehicle;
}
