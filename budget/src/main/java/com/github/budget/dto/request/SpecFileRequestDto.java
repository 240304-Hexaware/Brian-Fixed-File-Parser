package com.github.budget.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class SpecFileRequestDto {
    private MultipartFile file;

}
