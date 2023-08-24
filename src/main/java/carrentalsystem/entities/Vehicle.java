package carrentalsystem.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
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
    private Integer seatingCapacity;
    private BigDecimal rentalPricePerDay;
    @OneToMany(mappedBy = "vehicle")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "vehicle")
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "rented_by_id")
    private User rentedBy;
    /*@Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;
*/

}
