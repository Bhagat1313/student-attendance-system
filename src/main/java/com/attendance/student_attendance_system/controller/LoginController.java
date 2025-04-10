package com.attendance.student_attendance_system.controller;

import com.attendance.student_attendance_system.model.User;
import com.attendance.student_attendance_system.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest; // ** ADD THIS IMPORT **
import jakarta.servlet.http.HttpSession;       // ** ADD THIS IMPORT **
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    // showLoginPage remains mostly the same, ensures flash attributes are checked
    @GetMapping("/login")
    public String showLoginPage(Model model,
                                @RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout) {

        // Check for flash attributes first (set during redirect)
        if (model.containsAttribute("loginError")) {
            model.addAttribute("loginError", model.getAttribute("loginError"));
        } else if (error != null) { // Fallback to URL param if needed
            model.addAttribute("loginError", "Invalid username or password.");
        }

        if (model.containsAttribute("logoutMessage")) {
            model.addAttribute("logoutMessage", model.getAttribute("logoutMessage"));
        } else if (logout != null) { // Fallback to URL param if needed
            model.addAttribute("logoutMessage", "You have been logged out successfully.");
        }

        if (model.containsAttribute("registrationSuccess")) {
            model.addAttribute("registrationSuccess", model.getAttribute("registrationSuccess"));
        }

        return "login";
    }

    // processLogin UPDATED to use HttpSession
    @PostMapping("/login")
    public String processLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletRequest request, // ** Inject HttpServletRequest **
            RedirectAttributes redirectAttributes) {

        System.out.println("Login attempt: username=" + username);

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // ** INSECURE: Comparing plain text passwords **
            if (password.equals(user.getPassword())) {
                // Passwords match - Create/Get session and store attribute
                HttpSession session = request.getSession(true); // true = create if needed
                session.setAttribute("loggedInUser", username); // Store username in session
                session.setMaxInactiveInterval(1800); // Optional: 30 min timeout
                System.out.println("Login successful for user: " + username + ", Session ID CREATED/USED: [" + session.getId() + "]");
                // ** Redirect WITHOUT URL parameters **
                return "redirect:/";
            } else {
                System.out.println("Login failed: Incorrect password for user: " + username);
            }
        } else {
            System.out.println("Login failed: User not found: " + username);
        }

        // Login failed - Use Flash Attribute for redirect message
        redirectAttributes.addFlashAttribute("loginError", "Invalid username or password.");
        return "redirect:/login";
    }

    // processLogout UPDATED to use HttpSession
    @GetMapping("/logout") // Changed path back to standard /logout
    public String processLogout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false); // Get existing session, don't create
        if (session != null) {
            String username = (String) session.getAttribute("loggedInUser");
            System.out.println("Logging out user: " + (username != null ? username : "UNKNOWN") + ", Session ID: " + session.getId());
            session.invalidate(); // Destroy the session
        } else {
            System.out.println("Logout attempt with no active session.");
        }
        // Use Flash Attribute for redirect message
        redirectAttributes.addFlashAttribute("logoutMessage", "You have been logged out successfully.");
        return "redirect:/login"; // Redirect to login page
    }

    // Remove the /logout-redirect endpoint as it's replaced by /logout
    // @GetMapping("/logout-redirect") ... REMOVE THIS METHOD ...

}
