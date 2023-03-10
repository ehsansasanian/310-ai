package com.ai.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

@Table(name = "active_audience")
@Entity
@Data
public class ActiveAudience {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private Long id;
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @ManyToOne
    @JsonIgnore
    private Account account;
    @Column(name = "audience_username", unique = true)
    private String audienceUserName;
    @Column(name = "reply_count")
    private Integer replyCount = 0;

    @Column(name = "lastModifiedTime")
    @JsonIgnore
    private LocalDateTime lastModifiedTime;

    public void incrementActivityCounter() {
        replyCount++;
        lastModifiedTime = LocalDateTime.now();
    }
}
