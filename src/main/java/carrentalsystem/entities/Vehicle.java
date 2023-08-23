package carrentalsystem.entities;

import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

//@Entity
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    private Integer vehicleId;

    @Column(name = "make")
    private String make;

   /* private String model;
    private Integer year;
    private String fuelType;
    private Integer seatingCapacity;
    private Integer rentalPricePerDay;*/
//    @Enumerated(EnumType.STRING)
//    private AvailabilityStatus availabilityStatus;
//
//


}
