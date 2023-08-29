package carrentalsystem.controller;

import carrentalsystem.dto.VehicleDTO;
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
    public ResponseEntity<Object> createVehicle(@RequestBody VehicleDTO vehicleDTO) {
        vehicleService.createVehicle(vehicleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getVehicle(@PathVariable Integer id) {
        VehicleDTO vehicleDTO = vehicleService.getVehicleById(id);
        if (vehicleDTO != null) {
            return ResponseEntity.ok(vehicleDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateVehicle(@PathVariable Integer id, @RequestBody VehicleDTO vehicleDTO) {
        VehicleDTO updatedVehicle = vehicleService.updateVehicle(id, vehicleDTO);
        if (updatedVehicle != null) {
            return ResponseEntity.ok("Vehicle successfully updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found or failed to update");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable Integer id) {
        boolean deleted = vehicleService.deleteVehicle(id);
        if (deleted) {
            return ResponseEntity.ok("Vehicle deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
