package org.example.userservice.service;

import org.example.userservice.dto.LoginDto;
import org.example.userservice.dto.UserRegistrationDto;
import org.example.userservice.dto.UserResponseDto;
import org.example.userservice.entity.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponseDto registerUser(UserRegistrationDto registrationDto);
    Optional<UserResponseDto> authenticateUser(LoginDto loginDto);
    Optional<UserResponseDto> getUserById(Long id);
    Optional<UserResponseDto> getUserByEmail(String email);
    List<UserResponseDto> getAllUsers();
    List<UserResponseDto> getUsersByRole(UserRole role);
    UserResponseDto updateUser(Long id, UserRegistrationDto updateDto);
    void deleteUser(Long id);
    void deactivateUser(Long id);
    void activateUser(Long id);
    List<UserResponseDto> searchUsersByName(String name);
    Long getUserCountByRole(UserRole role);
    boolean emailExists(String email);
}
