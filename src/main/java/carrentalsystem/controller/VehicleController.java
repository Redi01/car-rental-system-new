package carrentalsystem.controller;


import carrentalsystem.auth.IsAdmin;
import carrentalsystem.dto.VehicleDTO;
import carrentalsystem.mapper.ApiResponse;
import carrentalsystem.service.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    private final String basePath = "C:\\Users\\Lenovo\\IdeaProjects\\car-rental-system\\src\\main\\resources\\photos\\";

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/create")
    @IsAdmin(message = "This option can be accessed only by Administrator!")
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

    @PostMapping("/{id}/upload-photo-as-string")
    public ResponseEntity<Object> uploadPhotoAsString(@PathVariable Integer id, @RequestParam("base64Image") String base64Image) {
        try {
            byte[] decodedImage = Base64.getDecoder().decode(base64Image);

            String fileName = "uploaded_image_" + id + ".jpg";
            Path filePath = Paths.get(basePath, fileName);
            Files.write(filePath, decodedImage);

            return ApiResponse.map(HttpStatus.OK, null, "Image uploaded successfully");
        } catch (IOException e) {
            return ApiResponse.map(HttpStatus.INTERNAL_SERVER_ERROR, null, "Failed to upload image");
        }
    }

    @GetMapping("/displayImage/{imageName}")
    public ResponseEntity<Object> displayImage(@PathVariable String imageName) {
        String imagePath = basePath + imageName;
        File imageFile = new File(imagePath);

        if (imageFile.exists() && imageFile.isFile()) {
            try {
                byte[] imageBytes = FileUtils.readFileToByteArray(imageFile);
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
            } catch (IOException e) {
                return ApiResponse.map(HttpStatus.OK, true, "Image found");
            }
        } else {
            return ApiResponse.map(HttpStatus.NOT_FOUND, null, "Image not found");
        }
    }


}
