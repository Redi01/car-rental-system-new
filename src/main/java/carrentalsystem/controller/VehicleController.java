package carrentalsystem.controller;

import carrentalsystem.dto.VehicleDTO;
import carrentalsystem.mapper.ApiResponse;
import carrentalsystem.service.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createVehicle(@RequestBody VehicleDTO vehicleDTO) {
        try {
            vehicleService.createVehicle(vehicleDTO);
            return ApiResponse.map(HttpStatus.CREATED, null, "New vehicle created by success!");
        } catch (Exception e) {
            return ApiResponse.map(HttpStatus.INTERNAL_SERVER_ERROR, null, "Could not create!");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getVehicle(@PathVariable Integer id) {
        try {
            VehicleDTO vehicleDTO = vehicleService.getVehicleById(id);
            return ApiResponse.map(HttpStatus.OK, vehicleDTO, "Vehicle retrieved successfully");
        } catch (Exception e) {
            return ApiResponse.map(HttpStatus.NOT_FOUND, null, "Vehicle not found");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateVehicle(@PathVariable Integer id, @RequestBody VehicleDTO vehicleDTO) {
        try {
            VehicleDTO updatedVehicleDTO = vehicleService.updateVehicle(id, vehicleDTO);
            return ApiResponse.map(HttpStatus.OK, updatedVehicleDTO, "Vehicle updated successfully");
        } catch (Exception e) {
            return ApiResponse.map(HttpStatus.NOT_FOUND, null, "Vehicle not found");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteVehicle(@PathVariable Integer id) {
        if (vehicleService.deleteVehicle(id)) {
            return ApiResponse.map(HttpStatus.OK, null, "Vehicle deleted successfully");
        } else {
            return ApiResponse.map(HttpStatus.NOT_FOUND, null, "Vehicle not found");
        }
    }

    @PostMapping("/{id}/upload-photo")
    public ResponseEntity<Object> uploadVehiclePhoto(@PathVariable Integer id, @RequestParam MultipartFile photo) {
        try {
            String uploadStatus = vehicleService.uploadVehiclePhoto(id, photo);
            return ApiResponse.map(HttpStatus.OK, null, uploadStatus);
        } catch (EntityNotFoundException e) {
            return ApiResponse.map(HttpStatus.NOT_FOUND, null, "Vehicle not found");
        } catch (Exception e) {
            return ApiResponse.map(HttpStatus.INTERNAL_SERVER_ERROR, null, "Failed to upload vehicle photo");
        }
    }
}
