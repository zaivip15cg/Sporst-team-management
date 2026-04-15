package com.example.identityservice.dto.request;

import com.example.identityservice.enums.Role;
import com.example.identityservice.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {



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
    Status status = Status.INACTIVE;

    Boolean isFirstLogin = true;


}
