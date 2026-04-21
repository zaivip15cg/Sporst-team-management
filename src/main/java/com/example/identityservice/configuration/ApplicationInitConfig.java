package com.example.identityservice.configuration;

import com.example.identityservice.entity.User;
import com.example.identityservice.enums.Role;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;
    @Bean

    ApplicationRunner applicationRunner(com.example.identityservice.repository.UserRepository userRepository) {
        return args -> {

            String adminname = "admin06072026";
            String adminEmail = "admin@identity.local";


            try {
                if (userRepository.findByEmail(adminEmail).isEmpty()) {
                    User user = User.builder()
                            .name(adminname)
                            .email(adminEmail)
                            .password(passwordEncoder.encode("admin"))
                            .role(Role.ADMIN)
                            .build();

                    userRepository.save(user);
                    log.warn("admin user has been created with default password:admin, please change it");
                }
            } catch (Exception ex) {
                log.warn("Skip admin init because current schema/data is incompatible: {}", ex.getMessage());
            }














        };
    }
}
