package com.ai.demo.repo;

import com.ai.demo.domain.TweetDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetDetailRepository extends JpaRepository<TweetDetail, Long> {
}
