package com.example.sportsteammanagement.service;

import com.example.sportsteammanagement.Exception.AppException;
import com.example.sportsteammanagement.Exception.ErrorCode;
import com.example.sportsteammanagement.Exception.UserNotFoundException;
import com.example.sportsteammanagement.dto.mail.DataMailDTO;
import com.example.sportsteammanagement.dto.request.ChangePasswordRequest;
import com.example.sportsteammanagement.dto.request.UserCreationRequest;
import com.example.sportsteammanagement.dto.request.UserResetPassword;
import com.example.sportsteammanagement.dto.request.UserUpdateRequest;
import com.example.sportsteammanagement.dto.response.UserResponse;
import com.example.sportsteammanagement.entity.PasswordResetToken;
import com.example.sportsteammanagement.entity.User;
import com.example.sportsteammanagement.enums.Role;
import com.example.sportsteammanagement.mapper.UserMapper;
import com.example.sportsteammanagement.repository.PasswordResetTokenRepository;
import com.example.sportsteammanagement.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
     UserRepository userRepository;
     PasswordResetTokenRepository passwordResetTokenRepository;
     MailService mailService;
     PasswordEncoder passwordEncoder;
     UserMapper userMapper;

    public UserResponse createUser(UserCreationRequest request){


       if (userRepository.existsByEmail(request.getEmail()))
           throw new AppException(ErrorCode.User_Exist);

       if (userRepository.existsByPhone(request.getPhone()))
           throw new AppException(ErrorCode.PHONE_EXISTED);

   User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        return  userMapper.toUserResponse(userRepository.save(user));
    }





    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByEmail(name)
                .orElseThrow(() -> new AppException(ErrorCode.User_NOT_Exist));
                return userMapper.toUserResponse(user);
    }


    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers(){
        log.info("In method get users");

        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }


   @PostAuthorize("returnObject.email == authentication.name")
    public UserResponse getUser(String id){
        log.info("In method get user by Id");
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("khong thay thằng nào như thế het tron")));
    }

    public List<UserResponse> findUserByName(String keyword){
        log.info("In method findUserByName");
        return userRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }



    public UserResponse updateUser(String userId, UserUpdateRequest request){
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("khong thay gi het tron"));
        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }
    public UserResponse resetPassword(UserResetPassword request){
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new AppException(ErrorCode.TOKEN_NOT_EXIST));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.TOKEN_OVERDUE);
        }

        User user = userRepository.findByEmail(resetToken.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.User_NOT_Exist));

        if(!request.getNewPassword().equals(request.getConfirmPassword())){
            throw new AppException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // Xóa token sau khi sử dụng xong
        passwordResetTokenRepository.delete(resetToken);

        return userMapper.toUserResponse(userRepository.save(user));
    }
    public void changePassword(ChangePasswordRequest request){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.User_NOT_Exist));
        if (!passwordEncoder.matches(request.getOldPassword(),user.getPassword()))throw new AppException(ErrorCode.PASSWORD_NOT_MATCH);

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

    }


    public void deleteUser(String userId){
        userRepository.deleteById(userId);


    }
    public void sendResetPasswordMail(String email) {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.User_NOT_Exist));
        
        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .email(email)
                .expiryDate(LocalDateTime.now().plusMinutes(15))
                .build();
        passwordResetTokenRepository.save(resetToken);

        String link = "http://localhost:3000/reset-password?token=" + token;

        DataMailDTO mailDTO = new DataMailDTO();
        mailDTO.setTo(email);
        mailDTO.setSubject("Yêu cầu đặt lại mật khẩu");
        mailDTO.setProps(Map.of("link", link));
        try {
            mailService.sendHtmlMail(mailDTO, "mail_resetpassword");
        } catch (MessagingException e) {
            log.error("Lỗi gửi mail", e);
            throw new AppException(ErrorCode.EMAIL_SEND_FAID);
        }
    }




}
