package com.ai.demo.repo;

import com.ai.demo.domain.Account;
import com.ai.demo.domain.ActiveAudience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ActiveAudienceRepository extends JpaRepository<ActiveAudience, Long> {
    @Query("from ActiveAudience where audienceUserName = ?1 and account = ?2")
    Optional<ActiveAudience> findByUsernameAndAudience(String username, Account account);
}
