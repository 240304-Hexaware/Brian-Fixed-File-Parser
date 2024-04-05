package com.github.budget.dto.response;

import lombok.Data;

@Data
public class AdminUserResponseDto extends UserResponseDto {
    private String id;
    private String createdBy;
    private String createdAt;
    private String updatedAt;
    private String updatedBy;

}
