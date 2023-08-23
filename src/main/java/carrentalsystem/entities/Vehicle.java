package carrentalsystem.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    private Integer vehicleId;

    @Column(name = "make")
    private String make;

    private String model;
    private Integer year;
    private String fuelType;
    private Integer seatingCapacity;
    private Integer rentalPricePerDay;
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
