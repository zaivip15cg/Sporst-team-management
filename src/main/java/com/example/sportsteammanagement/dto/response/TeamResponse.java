package com.example.sportsteammanagement.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamResponse {
    Integer id;
    String teamName;
    Integer memberCount;
    String managerName;
    LocalDateTime createdAt;
    
}
