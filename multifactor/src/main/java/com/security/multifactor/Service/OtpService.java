package com.security.multifactor.Service;

import com.security.multifactor.Model.CodeOTP;
import com.security.multifactor.Model.User;
import com.security.multifactor.Repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    public CodeOTP generateOtp(User user) {
        int otpCode = new Random().nextInt(900000) + 100000;

        CodeOTP otp = new CodeOTP();
        otp.setCode(otpCode);
        otp.setUser(user);
        otp.setCreatedAt(LocalDateTime.now());
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        otp.setUsed(false);
        otp.setDevice("web");

        otpRepository.save(otp);

        return otp;
    }

    public boolean verifyOtp(String email, int otpCode) {
        Optional<CodeOTP> otpOpt = otpRepository.findLatestOtpByEmail(email);

        if (otpOpt.isEmpty()) {
            return false;
        }

        CodeOTP otp = otpOpt.get();

        boolean match = otp.getCode().equals(otpCode);
        boolean notExpired = otp.getExpiresAt().isAfter(LocalDateTime.now());
        boolean notUsed = !otp.getUsed();

        if (match && notExpired && notUsed) {
            otp.setUsed(true);
            otpRepository.save(otp);
            return true;
        }
        return false;
    }

}
