package com.example.sportsteammanagement.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

@SQLDelete(sql = "UPDATE teams SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "team_name", length = 100, unique = true)
    String teamName;

    @ManyToOne
    @JoinColumn(name="manager_id")
    User manager;

    // created_by (ON DELETE SET NULL)
    @ManyToOne
    @JoinColumn(name = "created_by")
    User createdBy;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    // updated_by (ON DELETE SET NULL)
    @ManyToOne
    @JoinColumn(name = "updated_by")
    User updatedBy;

    LocalDateTime deletedAt;

    // deleted_by (ON DELETE SET NULL)
    @ManyToOne
    @JoinColumn(name = "deleted_by")
    User deletedBy;
}