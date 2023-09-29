package carrentalsystem.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

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
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "userId")
    private User user;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "vehicle_id")
    @JsonBackReference(value = "vehicleId")
    @ToString.Exclude
    private Vehicle vehicle;

    public Reservation(LocalDateTime startDate, LocalDateTime endDate, Vehicle vehicle, User user) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.vehicle = vehicle;
        this.user = user;
    }


}
