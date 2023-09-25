package carrentalsystem.service.impl;

import carrentalsystem.entities.User;
import carrentalsystem.repository.UserRepository;
import carrentalsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Integer userId) {
        return userRepository.findUserByUserId(userId);
    }
}
