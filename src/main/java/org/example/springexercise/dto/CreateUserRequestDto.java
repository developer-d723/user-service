package org.example.springexercise.dto;

import lombok.Data;

@Data
public class CreateUserRequestDto {
    private String name;
    private String email;
    private int age;
}