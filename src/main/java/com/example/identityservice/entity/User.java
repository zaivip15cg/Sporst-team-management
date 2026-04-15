package com.example.identityservice.entity;

import com.example.identityservice.enums.Role;
import com.example.identityservice.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

// soft delete
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(length = 100)
    String name;

    @Column(unique = true, length = 255)
    String email;

    @Column(unique = true, length = 20)
    String phone;

    @Column(length = 255)
    String password;

    LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    Role role;

    @Enumerated(EnumType.STRING)
    Status status = Status.INACTIVE;

    Boolean isFirstLogin = true;

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

    public void setRole(HashSet<String> roles) {
    }
}