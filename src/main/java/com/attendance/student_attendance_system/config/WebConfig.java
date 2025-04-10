package com.attendance.student_attendance_system.config; // Adjust package if needed

import com.attendance.student_attendance_system.interceptor.AuthInterceptor;
import jakarta.annotation.PostConstruct; // ** ADD THIS IMPORT **
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    // ** ADD THIS METHOD TO LOG WHEN THE BEAN IS CREATED **
    @PostConstruct
    public void init() {
        System.out.println("<<<<<<<<<< WebConfig Bean Initialized >>>>>>>>>>");
    }
    // ****************************************************

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // ** ADD LOGGING HERE **
        System.out.println("<<<<<<<<<< WebConfig: Attempting to register AuthInterceptor >>>>>>>>>>");
        if (authInterceptor == null) {
            System.out.println("<<<<<<<<<< WebConfig: ERROR - AuthInterceptor is NULL! >>>>>>>>>>");
        } else {
            System.out.println("<<<<<<<<<< WebConfig: AuthInterceptor instance found, registering... >>>>>>>>>>");
        }
        // **********************

        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/students/**", "/attendance/**")
                .excludePathPatterns("/login", "/logout", "/", "/css/**", "/js/**", "/images/**", "/error", "/register"); // Added /register
        System.out.println("<<<<<<<<<< WebConfig: Finished registering interceptors. >>>>>>>>>>");
    }
}
