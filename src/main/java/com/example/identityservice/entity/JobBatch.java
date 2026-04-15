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
public class JobBatch {

    @Id
    String id;

    String name;

    Integer totalJobs;
    Integer pendingJobs;
    Integer failedJobs;

    @Column(columnDefinition = "LONGTEXT")
    String failedJobIds;

    @Column(columnDefinition = "MEDIUMTEXT")
    String options;

    Integer cancelledAt;
    Integer createdAt;
    Integer finishedAt;
}