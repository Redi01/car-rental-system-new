package carrentalsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "vehicles")
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
