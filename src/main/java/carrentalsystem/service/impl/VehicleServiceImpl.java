package carrentalsystem.service.impl;

import carrentalsystem.dto.VehicleDTO;
import carrentalsystem.entities.Vehicle;
import carrentalsystem.repository.VehicleRepository;
import carrentalsystem.service.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final ModelMapper modelMapper = new ModelMapper();


    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public void createVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle = modelMapper.map(vehicleDTO, Vehicle.class);
        vehicleRepository.save(vehicle);
    }

    public VehicleDTO getVehicleById(Integer id) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();
            return modelMapper.map(vehicle, VehicleDTO.class);
        } else
            return null;
    }

    public VehicleDTO updateVehicle(Integer id, VehicleDTO vehicleDTO) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);

        if (optionalVehicle.isEmpty()) {
            throw new EntityNotFoundException("Vehicle with ID " + id + " not found");
        }

        Vehicle vehicle = optionalVehicle.get();

        validateAndUpdate(vehicleDTO, vehicle);
        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        VehicleDTO updatedVehicleDTO = modelMapper.map(updatedVehicle, VehicleDTO.class);

        return updatedVehicleDTO;
    }

    private void validateAndUpdate(VehicleDTO vehicleDTO, Vehicle vehicle) {
        if (vehicleDTO.getSeatingCapacity() <= 0) {
            throw new IllegalArgumentException("Seating capacity must be positive");
        }
        if (vehicleDTO.getYear() < 1886) {
            throw new IllegalArgumentException("Invalid year");
        }
        vehicle.setRentalPricePerDay(vehicleDTO.getRentalPricePerDay());
        vehicle.setSeatingCapacity(vehicleDTO.getSeatingCapacity());
        vehicle.setYear(vehicleDTO.getYear());
    }


    public Boolean deleteVehicle(Integer id) {
        if (vehicleRepository.existsById(id)) {
            vehicleRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


    public String uploadVehiclePhoto(Integer id, MultipartFile photo) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);

        if (optionalVehicle.isEmpty()) {
            return "Vehicle not found";
        }

        Vehicle vehicle = optionalVehicle.get();
        String photoFileName = "vehicle" + id + "_" + System.currentTimeMillis() + ".jpg";
        String basePhotoPath = "C:\\Users\\Lenovo\\IdeaProjects\\car-rental-system\\src\\main\\resources\\photos\\";
        String photoPath = basePhotoPath + photoFileName;

        try {
            File file = new File(photoPath);
            photo.transferTo(file);
            vehicle.setPhotoPath(photoPath);
            vehicleRepository.save(vehicle);

            return "Photo uploaded successfully";
        } catch (IOException e) {
            return "Failed to upload vehicle photo";
        }
    }
}
