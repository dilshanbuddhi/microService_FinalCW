package org.example.userservice.service.impl;

import org.example.userservice.dto.LoginDto;
import org.example.userservice.dto.UserRegistrationDto;
import org.example.userservice.dto.UserResponseDto;
import org.example.userservice.entity.User;
import org.example.userservice.entity.UserRole;
import org.example.userservice.exeption.EmailAlreadyExistsException;
import org.example.userservice.exeption.UserNotFoundException;
import org.example.userservice.repo.UserRepository;
import org.example.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Autowired
    private UserRepository userRepository;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Override
    public UserResponseDto registerUser(UserRegistrationDto registrationDto) {
        logger.info("Registering new user with email: {}", registrationDto.getEmail());
        
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists: " + registrationDto.getEmail());
        }
        
        User user = new User();
        user.setName(registrationDto.getName());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setRole(registrationDto.getRole() != null ? registrationDto.getRole() : UserRole.DRIVER);
        
        User savedUser = userRepository.save(user);
        logger.info("User registered successfully with ID: {}", savedUser.getId());
        
        return convertToResponseDto(savedUser);
    }


    @Override
    public Optional<UserResponseDto> authenticateUser(LoginDto loginDto) {
        logger.info("Authenticating user with email: {}", loginDto.getEmail());
        
        Optional<User> userOpt = userRepository.findByEmail(loginDto.getEmail());
        
        if (userOpt.isPresent() && userOpt.get().getIsActive()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                logger.info("User authenticated successfully: {}", user.getEmail());
                return Optional.of(convertToResponseDto(user));
            }
        }
        
        logger.warn("Authentication failed for email: {}", loginDto.getEmail());
        return Optional.empty();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponseDto> getUserById(Long id) {
        logger.debug("Fetching user by ID: {}", id);
        return userRepository.findById(id)
                .map(this::convertToResponseDto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponseDto> getUserByEmail(String email) {
        logger.debug("Fetching user by email: {}", email);
        return userRepository.findByEmail(email)
                .map(this::convertToResponseDto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        logger.debug("Fetching all users");
        return userRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }



    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getUsersByRole(UserRole role) {
        logger.debug("Fetching users by role: {}", role);
        return userRepository.findByRole(role).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public UserResponseDto updateUser(Long id, UserRegistrationDto updateDto) {
        logger.info("Updating user with ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        
        // Check if email is being changed and if new email already exists
        if (!user.getEmail().equals(updateDto.getEmail()) && 
            userRepository.existsByEmail(updateDto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists: " + updateDto.getEmail());
        }
        
        user.setName(updateDto.getName());
        user.setEmail(updateDto.getEmail());
        if (updateDto.getPassword() != null && !updateDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updateDto.getPassword()));
        }
        if (updateDto.getRole() != null) {
            user.setRole(updateDto.getRole());
        }
        
        User updatedUser = userRepository.save(user);
        logger.info("User updated successfully: {}", updatedUser.getId());
        
        return convertToResponseDto(updatedUser);
    }
    
    @Override
    public void deleteUser(Long id) {
        logger.info("Deleting user with ID: {}", id);
        
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        
        userRepository.deleteById(id);
        logger.info("User deleted successfully: {}", id);
    }
    
    @Override
    public void deactivateUser(Long id) {
        logger.info("Deactivating user with ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        
        user.setIsActive(false);
        userRepository.save(user);
        logger.info("User deactivated successfully: {}", id);
    }
    
    @Override
    public void activateUser(Long id) {
        logger.info("Activating user with ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        
        user.setIsActive(true);
        userRepository.save(user);
        logger.info("User activated successfully: {}", id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> searchUsersByName(String name) {
        logger.debug("Searching users by name containing: {}", name);
        return userRepository.findByNameContaining(name).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Long getUserCountByRole(UserRole role) {
        logger.debug("Getting user count by role: {}", role);
        return userRepository.countByRole(role);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
    
    private UserResponseDto convertToResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getIsActive()
        );
    }
}
