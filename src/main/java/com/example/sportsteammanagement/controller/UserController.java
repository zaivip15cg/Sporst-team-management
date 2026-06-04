package com.example.sportsteammanagement.controller;

import com.example.sportsteammanagement.dto.APIResponse;
import com.example.sportsteammanagement.dto.request.ChangePasswordRequest;
import com.example.sportsteammanagement.dto.request.UserCreationRequest;
import com.example.sportsteammanagement.dto.request.UserResetPassword;
import com.example.sportsteammanagement.dto.request.UserUpdateRequest;
import com.example.sportsteammanagement.dto.response.UserResponse;
import com.example.sportsteammanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {

    UserService userService;


    @PostMapping
    APIResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        APIResponse<UserResponse> apiResponse = new APIResponse<>();

        apiResponse.setResult(userService.createUser(request));
        return apiResponse;

    }

    @GetMapping
    APIResponse<List<UserResponse>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username:{}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority ->
                log.info(grantedAuthority.getAuthority()));
        return APIResponse.<List<UserResponse>>builder().result(userService.getUsers()).build();

    }

    @GetMapping("/{userId}")
    UserResponse getUser(@PathVariable("userId") String userId) {
        return userService.getUser(userId);
    }

    @GetMapping("/search")
    public APIResponse<List<UserResponse>> searchUsers(@RequestParam String keyword) {
        return APIResponse.<List<UserResponse>>builder()
                .result(userService.findUserByName(keyword))
                .build();
    }

    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {

        return userService.updateUser(userId, request);
    }
    @PutMapping("/change-password")
    APIResponse<String> changePassword(@RequestBody ChangePasswordRequest request){
        userService.changePassword(request);
        return APIResponse.<String>builder().result("Đổi mật khẩu thành công").build();
    }

    @PostMapping("/forgot-password")
    APIResponse<String> forgotPassword(@RequestParam String email) {
        userService.sendResetPasswordMail(email);
        return APIResponse.<String>builder().result("Email hướng dẫn đã được gửi").build();
    }

    @PostMapping("/reset-password")
    APIResponse<UserResponse> resetPassword(@RequestBody UserResetPassword request){

        return APIResponse.<UserResponse>builder().result(userService.resetPassword(request)).build();
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return "User has been deleted";
    }

    @GetMapping("/myInfo")
    APIResponse<UserResponse> getMyInfo() {
        return APIResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }



}
