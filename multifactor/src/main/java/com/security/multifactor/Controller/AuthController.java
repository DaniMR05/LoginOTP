package com.security.multifactor.Controller;

import com.security.multifactor.DTO.Request.Login;
import com.security.multifactor.DTO.Request.UserRequest;
import com.security.multifactor.Model.CodeOTP;
import com.security.multifactor.Model.User;
import com.security.multifactor.Repository.OtpRepository;
import com.security.multifactor.Repository.UserRepository;
import com.security.multifactor.Service.MailService;
import com.security.multifactor.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private UserService userService;

    // ➜ Usaremos directamente lo que YA tienes
    @Autowired private MailService mailService;
    @Autowired private OtpRepository otpRepository;
    @Autowired private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody UserRequest user) {
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody Login login) {
        userService.loginUser(login);


        return ResponseEntity.ok("OTP sent");
    }
}
