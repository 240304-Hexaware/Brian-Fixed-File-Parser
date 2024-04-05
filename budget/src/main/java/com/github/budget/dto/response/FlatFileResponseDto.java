package com.github.budget.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlatFileResponseDto {
    private String filename;
    private String specFileId;
    private String createdBy;

}
