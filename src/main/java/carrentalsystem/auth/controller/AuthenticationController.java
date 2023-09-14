package carrentalsystem.auth.controller;

import carrentalsystem.auth.dto.AuthenticationRequest;
import carrentalsystem.auth.dto.AuthenticationResponse;
import carrentalsystem.auth.dto.RegisterRequest;
import carrentalsystem.auth.service.AuthenticationService;
import carrentalsystem.entities.User;
import carrentalsystem.mapper.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
        try {
            User user = service.register(request);
            return ApiResponse.map(HttpStatus.OK, null, "Registration successful");
        } catch (Exception e) {
            return ApiResponse.map(HttpStatus.BAD_REQUEST, null, "Registration failed, email is taken");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationRequest request) {
        try {
            String authenticatedUser = service.authenticate(request);
            return ApiResponse.map(HttpStatus.OK, authenticatedUser, "Login successful");
        } catch (Exception e) {
            return ApiResponse.map(HttpStatus.BAD_REQUEST, null, "Login failed");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshAccessToken(@NonNull HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        try {
            AuthenticationResponse authResponse = service.refreshToken(token);
            if (authResponse == null) {
                return ApiResponse.map(HttpStatus.BAD_REQUEST, null, "Refresh token failed!");
            }
            return ApiResponse.map(HttpStatus.OK, authResponse, "Refresh token created by success.");
        } catch (Exception e) {
            return ApiResponse.map(HttpStatus.INTERNAL_SERVER_ERROR, null, "Server failed!");
        }
    }
}
