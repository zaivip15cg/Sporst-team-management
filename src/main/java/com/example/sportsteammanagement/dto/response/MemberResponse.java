package com.example.sportsteammanagement.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberResponse {
    String userId;
    Integer teamId;
    Integer jerseyNumber;
    String positionName;
    LocalDateTime createdAt;
}
