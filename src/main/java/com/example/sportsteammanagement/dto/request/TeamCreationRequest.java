package com.example.sportsteammanagement.dto.request;

import com.example.sportsteammanagement.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamCreationRequest {
    String teamName;
    String managerId;
}
