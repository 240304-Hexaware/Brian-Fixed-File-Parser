package com.github.budget.dto.request;

import lombok.Data;

@Data
public class UserRequestDto {
    private String username;
    private String password;
    private String role;

}
