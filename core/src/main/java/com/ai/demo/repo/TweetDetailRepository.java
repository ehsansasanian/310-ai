package com.ai.demo.repo;

import com.ai.demo.domain.TweetDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TweetDetailRepository extends JpaRepository<TweetDetail, Long> {
    @Query("from TweetDetail where id > ?1 and processStatus = 'NOT_PROCESSED' order by id asc limit 1")
    TweetDetail findNotProcessedTweetById(Long id);
}
