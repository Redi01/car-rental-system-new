package carrentalsystem.service.impl;

import carrentalsystem.dto.VehicleDTO;
import carrentalsystem.entities.Vehicle;
import carrentalsystem.repository.VehicleRepository;
import carrentalsystem.service.VehicleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleSeviceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleSeviceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void createVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle();
        vehicle.setMake(vehicleDTO.getMake());
        vehicle.setModel(vehicleDTO.getModel());
        vehicle.setYear(vehicleDTO.getYear());
        vehicle.setFuelType(vehicleDTO.getFuelType());
        vehicle.setSeatingCapacity(vehicleDTO.getSeatingCapacity());
        vehicle.setRentalPricePerDay(vehicleDTO.getRentalPricePerDay());

        vehicleRepository.save(vehicle);

    }

    @Override
    public VehicleDTO getVehicleById(Integer id) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();
            VehicleDTO vehicleDTO = new VehicleDTO();
            vehicleDTO.setMake(vehicle.getMake());
            vehicleDTO.setModel(vehicle.getModel());
            vehicleDTO.setYear(vehicle.getYear());
            vehicleDTO.setSeatingCapacity(vehicle.getSeatingCapacity());
            vehicleDTO.setFuelType(vehicle.getFuelType());
            vehicleDTO.setRentalPricePerDay(vehicle.getRentalPricePerDay());

            return vehicleDTO;
        } else {
            return null;
        }
    }

    @Override
    public boolean updateVehicle(Integer id, VehicleDTO vehicleDTO) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();
            vehicle.setRentalPricePerDay(vehicleDTO.getRentalPricePerDay());
            vehicle.setSeatingCapacity(vehicleDTO.getSeatingCapacity());
            vehicle.setYear(vehicleDTO.getYear());

            vehicleRepository.save(vehicle);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteVehicle(Integer id) {
        if (vehicleRepository.existsById(id)) {
            vehicleRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
