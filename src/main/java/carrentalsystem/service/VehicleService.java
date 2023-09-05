package carrentalsystem.service;

import carrentalsystem.dto.VehicleDTO;
import org.springframework.web.multipart.MultipartFile;

public interface VehicleService {
    void createVehicle(VehicleDTO vehicleDTO);

    VehicleDTO getVehicleById(Integer id);

    VehicleDTO updateVehicle(Integer id, VehicleDTO vehicleDTO);

    Boolean deleteVehicle(Integer id);

    String uploadVehiclePhoto(Integer id, MultipartFile photo);

}
