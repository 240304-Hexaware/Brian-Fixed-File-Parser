package com.github.budget.mapper;

import com.github.budget.dto.request.UserRequestDto;
import com.github.budget.dto.response.AdminUserResponseDto;
import com.github.budget.dto.response.UserResponseDto;
import com.github.budget.entity.User;

public class UserMapper {

    public static User mapToUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setUsername(userRequestDto.getUsername());
        user.setPassword(userRequestDto.getPassword());
        return user;

    }

    public static User mapToUserWithRole(UserRequestDto userRequestDto) {
        User user = new User();
        user.setUsername(userRequestDto.getUsername());
        user.setRole(userRequestDto.getRole());
        return user;

    }

    public static UserResponseDto mapToUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setRole(user.getRole());
        return userResponseDto;
    }

    public static AdminUserResponseDto mapToAdminUserResponseDto(User user) {
        AdminUserResponseDto adminUserResponseDto = new AdminUserResponseDto();
        adminUserResponseDto.setRole(user.getRole());
        adminUserResponseDto.setUsername(user.getUsername());
        adminUserResponseDto.setId(user.getId());
        adminUserResponseDto.setCreatedAt(user.getCreatedAt());
        adminUserResponseDto.setUpdatedAt(user.getUpdatedAt());
        adminUserResponseDto.setCreatedBy(user.getCreatedBy());
        adminUserResponseDto.setUpdatedBy(user.getUpdatedBy());
        return adminUserResponseDto;
    }

}
