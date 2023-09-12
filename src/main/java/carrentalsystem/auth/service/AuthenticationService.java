package carrentalsystem.auth.service;

import carrentalsystem.auth.dto.LoginRequest;
import carrentalsystem.auth.dto.RegisterRequest;
import carrentalsystem.entities.JWToken;
import carrentalsystem.entities.Role;
import carrentalsystem.entities.User;
import carrentalsystem.repository.JWTokenRepository;
import carrentalsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final JWTokenRepository tokenRepo;

    ModelMapper modelMapper = new ModelMapper();

    public User register(RegisterRequest request) {
        User user = modelMapper.map(request, User.class);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepo.save(user);
        return user;
    }

    public String authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepo.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);
        JWToken jwToken = new JWToken();
        jwToken.setToken(token);
        tokenRepo.save(jwToken);
        return token;
    }
}
