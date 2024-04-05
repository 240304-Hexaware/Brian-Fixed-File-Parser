package com.github.budget.dto.request;

import java.util.Map;

import lombok.Data;

@Data
public class RecordsRequestDto {
    private String id;
    private Map<String, String> data;

}
