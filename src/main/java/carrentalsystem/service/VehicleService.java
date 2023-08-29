package carrentalsystem.service;

import carrentalsystem.dto.VehicleDTO;

public interface VehicleService {
    void createVehicle(VehicleDTO vehicleDTO);

    VehicleDTO getVehicleById(Integer id);

    VehicleDTO updateVehicle(Integer id, VehicleDTO vehicleDTO);

    boolean deleteVehicle(Integer id);
}
