package carrentalsystem.controller;

import carrentalsystem.dto.VehicleDTO;
import carrentalsystem.mapper.ApiResponse;
import carrentalsystem.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Void>> createVehicle(@RequestBody VehicleDTO vehicleDTO) {
        ApiResponse<Void> response = vehicleService.createVehicle(vehicleDTO);
        HttpStatus status = response.isSuccess() ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VehicleDTO>> getVehicle(@PathVariable Integer id) {
        ApiResponse<VehicleDTO> response = vehicleService.getVehicleById(id);
        HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<String>> updateVehicle(@PathVariable Integer id, @RequestBody VehicleDTO vehicleDTO) {
        ApiResponse<VehicleDTO> updatedVehicleResponse = vehicleService.updateVehicle(id, vehicleDTO);

        if (updatedVehicleResponse.isSuccess()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Vehicle successfully updated", "Vehicle successfully updated"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Vehicle not found or failed to update", null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteVehicle(@PathVariable Integer id) {
        ApiResponse<Boolean> deleteResponse = vehicleService.deleteVehicle(id);

        if (deleteResponse.getData()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Vehicle deleted", "Vehicle deleted"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Vehicle not found", null));
        }
    }
}
