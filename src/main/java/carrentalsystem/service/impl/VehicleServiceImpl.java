package carrentalsystem.service.impl;

import carrentalsystem.dto.VehicleDTO;
import carrentalsystem.entities.Vehicle;
import carrentalsystem.mapper.ApiResponse;
import carrentalsystem.repository.VehicleRepository;
import carrentalsystem.service.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final ModelMapper modelMapper = new ModelMapper();


    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public ApiResponse<Void> createVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle = modelMapper.map(vehicleDTO, Vehicle.class);
        vehicleRepository.save(vehicle);
        return new ApiResponse<>(true, "Vehicle created successfully", null);
    }

    public ApiResponse<VehicleDTO> getVehicleById(Integer id) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();
            VehicleDTO vehicleDTO = modelMapper.map(vehicle, VehicleDTO.class);
            return new ApiResponse<>(true, "Vehicle retrieved successfully", vehicleDTO);
        } else {
            return new ApiResponse<>(false, "Vehicle not found", null);
        }
    }

    public ApiResponse<VehicleDTO> updateVehicle(Integer id, VehicleDTO vehicleDTO) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();

            vehicle.setRentalPricePerDay(vehicleDTO.getRentalPricePerDay());
            vehicle.setSeatingCapacity(vehicleDTO.getSeatingCapacity());
            vehicle.setYear(vehicleDTO.getYear());

            vehicleRepository.save(vehicle);

            VehicleDTO updatedVehicleDTO = modelMapper.map(vehicle, VehicleDTO.class);
            return new ApiResponse<>(true, "Vehicle updated successfully", updatedVehicleDTO);
        } else {
            return new ApiResponse<>(false, "Vehicle not found", null);
        }
    }

    public ApiResponse<Boolean> deleteVehicle(Integer id) {
        if (vehicleRepository.existsById(id)) {
            vehicleRepository.deleteById(id);
            return new ApiResponse<>(true, "Vehicle deleted successfully", true);
        } else {
            return new ApiResponse<>(false, "Vehicle not found", false);
        }
    }
}
