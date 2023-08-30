package carrentalsystem.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VehicleDTO {
    private Integer vehicleId;
    private String make;
    private String model;
    private Integer year;
    private String transmission;
    private String fuelType;
    private Integer seatingCapacity;
    private BigDecimal rentalPricePerDay;
}
