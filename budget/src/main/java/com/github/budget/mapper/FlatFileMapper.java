package com.github.budget.mapper;

import com.github.budget.dto.response.FlatFileResponseDto;
import com.github.budget.entity.FlatFile;

public class FlatFileMapper {
    public static FlatFileResponseDto mapToFlatFileDTO(FlatFile flatFile) {
        FlatFileResponseDto flatFileDto = new FlatFileResponseDto();
        flatFileDto.setFilename(flatFile.getFilename());
        flatFileDto.setSpecFileId(flatFile.getSpecFileId());
        flatFileDto.setCreatedBy(flatFile.getCreatedBy());

        return flatFileDto;
    }

}
