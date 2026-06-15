package com.life.ai.repository;

import com.life.ai.model.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpToken, Long> {

    Optional<OtpToken> findTopByMobileOrderByExpiryTimeDesc(String mobile);

    void deleteByMobile(String mobile);
}
