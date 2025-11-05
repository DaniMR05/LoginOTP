package com.security.multifactor.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;


    public void sendOtpEmail(String toEmail, int otpCode) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Tu código de verificación (OTP)");
            message.setText(
                    "Hola,\n\n" +
                            "Tu código de verificación es: " + otpCode + "\n\n" +
                            "Este código expirará en 5 minutos.\n\n" +
                            "Por motivos de seguridad, no compartas este código con nadie.\n\n"
            );

            mailSender.send(message);
        } catch (Exception e) {
        }
    }
}
