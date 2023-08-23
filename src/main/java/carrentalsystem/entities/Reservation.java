package carrentalsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue
    private Integer reservationId;
//    private Integer userId;
//    private Integer vehicleId;
    private LocalDateTime pickupDate;
    private LocalDateTime returnDate;
    private String reservationStatus;
    @ManyToOne
    @JoinColumn(insertable = false, updatable = false , name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false ,name = "vehicleId")
    private Vehicle vehicle;
}
