package com.github.budget.mapper;

import com.github.budget.dto.RecordsDto;
import com.github.budget.entity.SpecFile;

public class RecordsDataMapper {

    public static RecordsDto mapToRecordsDTO(SpecFile spec, RecordsDto recordsDto) {
        recordsDto.setRecords(spec.getRecordsData().getRecords());

        return recordsDto;
    }

}