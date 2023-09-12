package carrentalsystem.auth.controller;

import carrentalsystem.auth.dto.LoginRequest;
import carrentalsystem.auth.dto.RegisterRequest;
import carrentalsystem.auth.service.AuthenticationService;
import carrentalsystem.entities.User;
import carrentalsystem.mapper.ApiResponse;
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
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        try{
            String authenticatedUser = service.authenticate(request);
            return ApiResponse.map(HttpStatus.OK, authenticatedUser, "Login successful");
        }
        catch(Exception e){
            return ApiResponse.map(HttpStatus.BAD_REQUEST, null, "Login failed");
        }
    }
}
