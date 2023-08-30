package carrentalsystem.service;

import carrentalsystem.dto.VehicleDTO;
import carrentalsystem.mapper.ApiResponse;

public interface VehicleService {
    ApiResponse<Void> createVehicle(VehicleDTO vehicleDTO);

    ApiResponse<VehicleDTO> getVehicleById(Integer id);

    ApiResponse<VehicleDTO> updateVehicle(Integer id, VehicleDTO vehicleDTO);

    ApiResponse<Boolean> deleteVehicle(Integer id);
}
