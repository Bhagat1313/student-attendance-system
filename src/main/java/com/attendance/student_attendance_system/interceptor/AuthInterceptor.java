package com.attendance.student_attendance_system.interceptor; // Adjust package if needed

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.Collections; // ** ADD THIS IMPORT **

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("<<<<<<<<<< AuthInterceptor: preHandle triggered for URI: " + request.getRequestURI() + " >>>>>>>>>>");

        HttpSession session = request.getSession(false); // Get session, don't create

        if (session == null) {
            // --- Log if session is null ---
            System.out.println("<<<<<<<<<< AuthInterceptor: HttpSession is NULL. Redirecting to login. >>>>>>>>>>");
            response.sendRedirect(request.getContextPath() + "/login");
            return false; // Block request
        } else {
            // --- Log if session exists ---
            System.out.println("<<<<<<<<<< AuthInterceptor: HttpSession FOUND. ID = [" + session.getId() + "] >>>>>>>>>>");
            Object userAttribute = session.getAttribute("loggedInUser"); // Get the attribute

            if (userAttribute == null) {
                // --- Log if attribute is null ---
                System.out.println("<<<<<<<<<< AuthInterceptor: Attribute 'loggedInUser' is NULL in session. Redirecting to login. >>>>>>>>>>");
                // Log all attributes found in session for debugging
                System.out.print("<<<<<<<<<< AuthInterceptor: Attributes found in session: [");
                Collections.list(session.getAttributeNames()).forEach(name -> System.out.print(name + ", "));
                System.out.println("] >>>>>>>>>>");

                response.sendRedirect(request.getContextPath() + "/login");
                return false; // Block request
            } else {
                // --- Log if attribute exists ---
                System.out.println("<<<<<<<<<< AuthInterceptor: User attribute ('loggedInUser' = " + userAttribute + ") FOUND. Allowing request. >>>>>>>>>>");
                return true; // Allow request
            }
        }
    }
}
