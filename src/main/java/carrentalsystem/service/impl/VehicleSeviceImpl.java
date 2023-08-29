package carrentalsystem.service.impl;

import carrentalsystem.dto.VehicleDTO;
import carrentalsystem.entities.Vehicle;
import carrentalsystem.repository.VehicleRepository;
import carrentalsystem.service.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleSeviceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final ModelMapper modelMapper = new ModelMapper();


    public VehicleSeviceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void createVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle = modelMapper.map(vehicleDTO, Vehicle.class);
        vehicleRepository.save(vehicle);
    }

    @Override
    public VehicleDTO getVehicleById(Integer id) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();
            return modelMapper.map(vehicle, VehicleDTO.class);
        } else {
            return null;
        }
    }

    @Override
    public VehicleDTO updateVehicle(Integer id, VehicleDTO vehicleDTO) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();

            vehicle.setRentalPricePerDay(vehicleDTO.getRentalPricePerDay());
            vehicle.setSeatingCapacity(vehicleDTO.getSeatingCapacity());
            vehicle.setYear(vehicleDTO.getYear());


            vehicleRepository.save(vehicle);

            return modelMapper.map(vehicle, VehicleDTO.class);
        }
        return null;
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
