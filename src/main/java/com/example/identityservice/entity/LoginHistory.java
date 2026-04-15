package com.example.identityservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

// soft delete
@SQLDelete(sql = "UPDATE login_histories SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    // FK user (cascade delete)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Enumerated(EnumType.STRING)
    Status status;

    LocalDateTime lockedUntil;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    User updatedBy;

    LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "deleted_by")
    User deletedBy;

    public enum Status {
        SUCCESS,
        FAILURE
    }
}