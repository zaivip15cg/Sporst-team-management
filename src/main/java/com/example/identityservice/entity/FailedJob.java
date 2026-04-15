package com.example.identityservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FailedJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String uuid;

    @Column(columnDefinition = "TEXT")
    String connection;

    @Column(columnDefinition = "TEXT")
    String queue;

    @Column(columnDefinition = "LONGTEXT")
    String payload;

    @Column(columnDefinition = "LONGTEXT")
    String exception;

    @CreationTimestamp
    LocalDateTime failedAt;
}