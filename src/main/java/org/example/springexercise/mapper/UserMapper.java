package org.example.springexercise.mapper;

import org.example.springexercise.dto.CreateUserRequestDto;
import org.example.springexercise.dto.UserResponseDto;
import org.example.springexercise.entity.User;

public interface UserMapper {
    UserResponseDto toUserResponseDto(User user);
    User toUser(CreateUserRequestDto requestDto);
}