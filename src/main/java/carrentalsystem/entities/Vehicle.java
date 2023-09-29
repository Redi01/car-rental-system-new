package carrentalsystem.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue
    private Integer vehicleId;
    private String make;
    private String model;
    private Integer year;
    private String fuelType;
    private String transmission;
    private Integer seatingCapacity;
    private BigDecimal rentalPricePerDay;
    private String photoPath;
    @OneToMany(mappedBy = "vehicle",cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "rented_by_id")
    private User rentedBy;

    /*
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;
    */

}
