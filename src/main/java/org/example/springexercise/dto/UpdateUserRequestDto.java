package org.example.springexercise.dto;

import lombok.Data;

@Data
public class UpdateUserRequestDto {
    private String name;
    private String email;
    private int age;
}