package com.example.identityservice.controller;

import com.example.identityservice.dto.APIResponse;
import com.example.identityservice.dto.UserCreationRequest;
import com.example.identityservice.dto.UserUpdateRequest;
import com.example.identityservice.dto.response.UserResponse;
import com.example.identityservice.entity.User;
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
    APIResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        APIResponse<UserResponse> apiResponse = new APIResponse<>();

        apiResponse.setResult(userService.createUser(request));
        return apiResponse;

    }
    @GetMapping
    APIResponse<List<UserResponse>>getUsers(){
       var authentication = SecurityContextHolder.getContext().getAuthentication();

       log.info("Username:{}",authentication.getName());
       authentication.getAuthorities().forEach(grantedAuthority ->
               log.info(grantedAuthority.getAuthority()));
       return APIResponse.<List<UserResponse>>builder().result(userService.getUsers()).build();

    }
    @GetMapping("/{userId}")
    UserResponse getUser(@PathVariable("userId") String userId){
        return userService.getUser(userId);
    }
    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request){

           return userService.updateUser(userId, request);
    }
    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return "User hass been deleted";
    }

    @GetMapping("/myInfo")
    APIResponse<UserResponse> getMyInfo(@PathVariable("userid") String userId ){
        return APIResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }


}

