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

@SQLDelete(sql = "UPDATE tokens SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")

public class Tokens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(length = 512)
    String token;

    Integer tokenType;

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

    LocalDateTime expiresAt;
}


