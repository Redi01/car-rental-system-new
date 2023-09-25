package carrentalsystem.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

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
    @NotNull
    @JsonBackReference
    @JoinColumn(insertable = false, updatable = false, name = "userId")
    private User user;

    @ManyToOne
    @NotNull
    @JoinColumn(insertable = false, updatable = false, name = "vehicleId")
    private Vehicle vehicle;

    public Reservation(LocalDateTime startDate, LocalDateTime endDate, Vehicle vehicle, User user) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.vehicle = vehicle;
        this.user = user;
    }

}
