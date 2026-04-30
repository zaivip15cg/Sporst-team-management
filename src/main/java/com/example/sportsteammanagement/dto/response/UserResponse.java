package com.example.sportsteammanagement.dto.response;

import com.example.sportsteammanagement.enums.Role;
import com.example.sportsteammanagement.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserResponse {
     String id;

     @Column(length = 100)
     String name;

     @Column(unique = true, length = 255)
     String email;

     @Column(unique = true, length = 20)
     String phone;

     @Size(min = 8, message = "Password must be at least 8 characters" )
     @Column(length = 255)
     String password;

     LocalDate dateOfBirth;

     @Enumerated(EnumType.STRING)
     Role role;

     @Enumerated(EnumType.STRING)
     @Builder.Default
     Status status = Status.INACTIVE;


}
