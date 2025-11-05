package com.security.multifactor.Service;

import com.security.multifactor.DTO.Mapping.UserMapping;
import com.security.multifactor.DTO.Request.Login;
import com.security.multifactor.DTO.Request.UserRequest;
import com.security.multifactor.Model.User;
import com.security.multifactor.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapping userMapping;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OtpService otpService;
    @Autowired
    private MailService mailService;

    public void registerUser(UserRequest userRequest) {
        Optional<User> userPresent = userRepository.findByEmail(userRequest.getEmail());

        if(userPresent.isPresent()) {
            throw new RuntimeException("User with email " + userRequest.getEmail() + " already exists.");
        } else {
            User user = userMapping.mapToEntity(userRequest);
            userRepository.save(user);
        }
    }

    public User loginUser(Login login) {
        Optional<User> user = userRepository.findByEmail(login.getEmail());

        if(user.isPresent()) {
            boolean coincide = passwordEncoder.matches(login.getPassword(), user.get().getPassword());
            if(coincide) {

                var otp = otpService.generateOtp(user.get());
                mailService.sendOtpEmail(user.get().getEmail(), otp.getCode());
                return user.get();
            } else {
                throw new RuntimeException("Invalid password for email " + login.getEmail() + ".");
            }
        } else {
            throw new RuntimeException("User with email " + login.getEmail() + " not found.");
        }
    }
}
