package com.security.multifactor.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.security.multifactor.Model.CodeOTP;
import com.security.multifactor.Model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<CodeOTP, Long> {
    Optional<CodeOTP> findByUserAndCodeAndUsedFalse(User user, int code);
    @Query("SELECT o FROM CodeOTP o WHERE o.user.email = :email ORDER BY o.createdAt DESC LIMIT 1")
    Optional<CodeOTP> findLatestOtpByEmail(@Param("email") String email);
}
