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

@SQLDelete(sql = "UPDATE members SET deleted_at = NOW() WHERE team_id = ? AND user_id = ?")
@Where(clause = "deleted_at IS NULL")
public class Member {

    @EmbeddedId
    MemberId id;

    // mapping tới team
    @ManyToOne
    @MapsId("teamId")
    @JoinColumn(name = "team_id")
    Team team;

    // mapping tới user
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @Column(unique = true)
    Integer jerseyNumber;

    @ManyToOne
    @JoinColumn(name = "position_id")
    Position position;

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
}