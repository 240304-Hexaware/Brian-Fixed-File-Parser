package com.github.budget.mapper;

import java.util.List;

import com.github.budget.dto.response.SpecFileResponseDto;
import com.github.budget.entity.SpecFile;

public class SpecFileMapper {

    public static SpecFileResponseDto mapToSpecFileDto(SpecFile specFile) {
        SpecFileResponseDto responseDto = new SpecFileResponseDto();
        responseDto.setFileName(specFile.getFilename());

        return responseDto;
    }

}
