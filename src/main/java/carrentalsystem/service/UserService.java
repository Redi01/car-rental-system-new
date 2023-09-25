package carrentalsystem.service;

import carrentalsystem.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User getUserById(Integer userId);
}
