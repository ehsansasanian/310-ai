package com.ai.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tweet_details")
public class TweetDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JoinColumn(name = "tweet_id", referencedColumnName = "id")
    @ManyToOne
    private Tweet tweet;

    @Column(name = "process_counter")
    private Integer processCounter = 0;

    private LocalDateTime lastProcessTime = LocalDateTime.now();

    public void incrementProcess() {
        processCounter++;
        lastProcessTime = LocalDateTime.now();
    }
}
