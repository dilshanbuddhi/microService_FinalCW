package org.example.usersservice.repo;

import org.example.usersservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email); // optional: to check if email is already used
}
