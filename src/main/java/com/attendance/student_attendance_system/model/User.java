package com.attendance.student_attendance_system.model; // Adjust package if needed

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "app_users")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String password; // Plain text password

    // Default constructor
    public User() {
    }

    // Constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // --- GETTERS ---
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() { // <-- ** ADD THIS METHOD **
        return password;
    }

    // --- SETTERS ---
    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) { // <-- ** ADD THIS METHOD **
        this.password = password;
    }
}
