package carrentalsystem.auth.service;

import carrentalsystem.auth.dto.AuthenticationRequest;
import carrentalsystem.auth.dto.AuthenticationResponse;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    //    private static final long REFRESH_TOKEN_EXPIRATION_MS = 1000 * 60 * 60 * 24 * 30; // 30 days;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final JWTokenRepository tokenRepo;

    private final UserDetailsService userDetailsService;

    ModelMapper modelMapper = new ModelMapper();

    public User register(RegisterRequest request) {
        User user = modelMapper.map(request, User.class);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepo.save(user);
        return user;
    }

    public String authenticate(AuthenticationRequest request) {
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

    public AuthenticationResponse refreshToken(String oldToken) throws IllegalAccessException {
        AuthenticationResponse response = new AuthenticationResponse();
        if (oldToken == null) {
            return null;
        }

        oldToken = oldToken.substring(7);
        String userEmail = jwtService.extractUsername(oldToken);
        if (userEmail == null) {
            return null;
        }

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
        if (!jwtService.isTokenValid(oldToken, userDetails)) {
            throw new IllegalAccessException("Invalid refresh token");
        }

        response.setToken(jwtService.generateToken(userDetails));
        return response;
    }
}
