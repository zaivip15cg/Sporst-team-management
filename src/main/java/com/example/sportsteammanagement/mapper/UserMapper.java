package com.example.sportsteammanagement.mapper;

import com.example.sportsteammanagement.dto.request.UserCreationRequest;
import com.example.sportsteammanagement.dto.request.UserResetPassword;
import com.example.sportsteammanagement.dto.request.UserUpdateRequest;
import com.example.sportsteammanagement.dto.response.UserResponse;
import com.example.sportsteammanagement.entity.User;
import org.mapstruct.Mapper; 
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser (UserCreationRequest request);
    UserResponse toUserResponse (User user );
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
    void resetPassword(@MappingTarget User user, UserResetPassword request);
}
