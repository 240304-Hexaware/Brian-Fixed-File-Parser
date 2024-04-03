package com.github.budget.mapper;

import com.github.budget.dto.RecordsDto;
import com.github.budget.entity.RecordsData;

public class RecordsDataMapper {

    public static RecordsDto mapToRecordsDTO(RecordsData recordsData, RecordsDto recordsDto) {
        recordsDto.setRecords(recordsData.getRecords());

        return recordsDto;
    }

}