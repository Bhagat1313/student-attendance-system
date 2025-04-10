package com.attendance.student_attendance_system.repository; // Adjust package if needed

import com.attendance.student_attendance_system.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    // Method to find a user by their username
    Optional<User> findByUsername(String username);

    // Method to check if a username already exists
    boolean existsByUsername(String username);
}
