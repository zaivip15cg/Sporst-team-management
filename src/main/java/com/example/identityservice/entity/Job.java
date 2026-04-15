package com.example.identityservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String queue;

    @Column(columnDefinition = "LONGTEXT")
    String payload;

    Integer attempts;

    Long reservedAt;

    Long availableAt;

    Long createdAt;
}