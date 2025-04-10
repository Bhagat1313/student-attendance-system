package com.attendance.student_attendance_system.controller; // Adjust package if needed

import com.attendance.student_attendance_system.model.User;
import com.attendance.student_attendance_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Show registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // Pass an empty User object if needed by the form, otherwise just return view name
        // model.addAttribute("user", new User()); // Optional
        return "register"; // register.html
    }

    // Process registration form submission
    @PostMapping("/register")
    public String processRegistration(
            @RequestParam String username,
            @RequestParam String password,
            RedirectAttributes redirectAttributes,
            Model model) { // Add Model to pass back errors directly if needed

        System.out.println("Registration attempt: username=" + username);

        // Basic validation
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            model.addAttribute("registrationError", "Username and password cannot be empty.");
            return "register"; // Show form again with error
        }

        // Check if username already exists
        if (userRepository.existsByUsername(username)) {
            System.out.println("Registration failed: Username already exists - " + username);
            model.addAttribute("registrationError", "Username '" + username + "' is already taken.");
            return "register"; // Show form again with error
        }

        // Create and save the new user (with plain text password!)
        User newUser = new User(username, password); // Store plain text password
        userRepository.save(newUser);
        System.out.println("Registration successful for user: " + username);

        // Add success message for the redirect to login page
        redirectAttributes.addFlashAttribute("registrationSuccess", "Registration successful! Please log in.");
        return "redirect:/login";
    }
}

