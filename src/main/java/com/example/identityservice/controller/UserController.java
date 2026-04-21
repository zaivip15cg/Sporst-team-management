package com.example.identityservice.controller;

import com.example.identityservice.dto.APIResponse;
import com.example.identityservice.dto.request.UserCreationRequest;
import com.example.identityservice.dto.request.UserResetPassword;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.dto.response.UserResponse;
import com.example.identityservice.service.UserService;
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

    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {

        return userService.updateUser(userId, request);
    }

    @PostMapping("/forgot-password")
    APIResponse<String> forgotPassword(@RequestParam String email) {
        userService.sendResetPasswordMail(email);
        return APIResponse.<String>builder().result("Email hướng dẫn đã được gửi").build();
    }

    @PostMapping("/reset-password")
    APIResponse<UserResponse> resetPassword(@RequestBody UserResetPassword request){
        // Không dùng userId nữa, đổi sang POST và kiểm tra dựa vào token truyền trong body
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
