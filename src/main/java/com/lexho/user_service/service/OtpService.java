package com.lexho.user_service.service;

import com.lexho.user_service.entity.Otp;
import com.lexho.user_service.repository.OtpRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {

    private final OtpRepository otpRepository;

    public OtpService(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    public String generateOtp(String phone) {
        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        Otp otpEntity = new Otp();
        otpEntity.setPhone(phone);
        otpEntity.setOtp(otp);
        otpEntity.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        otpRepository.save(otpEntity);

        System.out.println("OTP for " + phone + " is: " + otp); // 🔥 console me show

        return otp;
    }

        public boolean verifyOtp(String phone, String otp) {
            return otpRepository.findTopByPhoneOrderByExpiryTimeDesc(phone)
                    .filter(o -> o.getOtp().equals(otp)
                            && o.getExpiryTime().isAfter(LocalDateTime.now()))
                    .isPresent();
        }
    }
