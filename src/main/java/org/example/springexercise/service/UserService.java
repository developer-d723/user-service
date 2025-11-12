package org.example.springexercise.service;

import lombok.RequiredArgsConstructor;
import org.example.springexercise.dto.CreateUserRequestDto;
import org.example.springexercise.dto.UpdateUserRequestDto;
import org.example.springexercise.dto.UserResponseDto;
import org.example.springexercise.entity.User;
import org.example.springexercise.exception.ResourceNotFoundException;
import org.example.springexercise.mapper.UserMapper;
import org.example.springexercise.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponseDto createUser(CreateUserRequestDto requestDto) {
        validate(requestDto.getName(), requestDto.getEmail(), requestDto.getAge());
        User user = userMapper.toUser(requestDto);
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponseDto(savedUser);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found."));
        return userMapper.toUserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UpdateUserRequestDto requestDto) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found."));

        validate(requestDto.getName(), requestDto.getEmail(), requestDto.getAge());

        userToUpdate.setName(requestDto.getName());
        userToUpdate.setEmail(requestDto.getEmail());
        userToUpdate.setAge(requestDto.getAge());

        User updatedUser = userRepository.save(userToUpdate);
        return userMapper.toUserResponseDto(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }
        userRepository.deleteById(id);
    }

    private void validate(String name, String email, int age) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (age <= 0) {
            throw new IllegalArgumentException("Age must be positive.");
        }
    }
}