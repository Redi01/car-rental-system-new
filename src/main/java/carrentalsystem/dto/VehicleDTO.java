package carrentalsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleDTO {
    private Integer vehicleId;
    private String make;
    private String model;
    private Integer year;
    private String fuelType;
    private Integer seatingCapacity;
    private BigDecimal rentalPricePerDay;
}
