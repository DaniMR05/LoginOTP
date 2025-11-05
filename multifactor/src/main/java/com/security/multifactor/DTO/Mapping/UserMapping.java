package com.security.multifactor.DTO.Mapping;

import com.security.multifactor.Configuration.SecurityConfig;
import com.security.multifactor.DTO.Request.UserRequest;
import com.security.multifactor.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapping {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User mapToEntity(UserRequest userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        return user;
    }
}
