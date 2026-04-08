package com.example.identityservice.mapper;

import com.example.identityservice.dto.UserCreationRequest;
import com.example.identityservice.dto.UserUpdateRequest;
import com.example.identityservice.dto.response.UserResponse;
import com.example.identityservice.entity.User;
import org.mapstruct.Mapper; 
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser (UserCreationRequest request);
    UserResponse toUserResponse (User user );
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
