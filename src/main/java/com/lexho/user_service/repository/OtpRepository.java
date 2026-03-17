package com.lexho.user_service.repository;

import com.lexho.user_service.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findTopByPhoneOrderByExpiryTimeDesc(String phone);
}