package org.example.userservice.controller;

import org.example.userservice.dto.LoginDto;
import org.example.userservice.dto.UserRegistrationDto;
import org.example.userservice.dto.UserResponseDto;
import org.example.userservice.entity.UserRole;
import org.example.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser( @RequestBody UserRegistrationDto registrationDto) {
        logger.info("Received registration request for email: {}", registrationDto.getEmail());
        UserResponseDto user = userService.registerUser(registrationDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> loginUser( @RequestBody LoginDto loginDto) {
        logger.info("Received login request for email: {}", loginDto.getEmail());
        Optional<UserResponseDto> user = userService.authenticateUser(loginDto);
        return user.map(u -> ResponseEntity.ok(u))
                   .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        logger.debug("Fetching user by ID: {}", id);
        Optional<UserResponseDto> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        logger.debug("Fetching user by email: {}", email);
        Optional<UserResponseDto> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        logger.debug("Fetching all users");
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponseDto>> getUsersByRole(@PathVariable UserRole role) {
        logger.debug("Fetching users by role: {}", role);
        List<UserResponseDto> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, 
                                                      @RequestBody UserRegistrationDto updateDto) {
        logger.info("Updating user with ID: {}", id);
        UserResponseDto updatedUser = userService.updateUser(id, updateDto);
        return ResponseEntity.ok(updatedUser);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDto>> searchUsersByName(@RequestParam String name) {
        logger.debug("Searching users by name: {}", name);
        List<UserResponseDto> users = userService.searchUsersByName(name);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        logger.debug("Checking if email exists: {}", email);
        boolean exists = userService.emailExists(email);
        return ResponseEntity.ok(exists);
    }
    
   }