package carrentalsystem.controller;

import carrentalsystem.entities.User;
import carrentalsystem.mapper.ApiResponse;
import carrentalsystem.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Integer id) {
        try {
            User user = userService.getUserById(id);
            if (user == null) {
                return ApiResponse.map(HttpStatus.NOT_FOUND, null, "User not found");
            }
            return ApiResponse.map(HttpStatus.OK, user, "User retrieved successfully");
        } catch (Exception e) {
            return ApiResponse.map(HttpStatus.INTERNAL_SERVER_ERROR, null, "Server Failed");

        }
    }
}
