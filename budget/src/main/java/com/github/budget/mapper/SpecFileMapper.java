package com.github.budget.mapper;

import com.github.budget.dto.response.SpecFileResponseDto;
import com.github.budget.entity.SpecFile;

public class SpecFileMapper {

    public static SpecFileResponseDto mapToSpecFileDto(SpecFile specFile) {
        SpecFileResponseDto responseDto = new SpecFileResponseDto();
        responseDto.setFileName(specFile.getFilename());
        responseDto.setCreatedBy(specFile.getCreatedBy());
        responseDto.setSpecFileId(specFile.getId());

        return responseDto;
    }

}
