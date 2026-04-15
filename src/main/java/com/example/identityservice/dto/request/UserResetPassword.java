package com.example.identityservice.dto.request;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResetPassword {
    String password;
    String newPassword;
    String confirmPassword;

}
