package com.github.budget.mapper;

import com.github.budget.dto.response.RecordsResponseDto;
import com.github.budget.entity.RecordsData;

public class RecordsDataMapper {

    public static RecordsResponseDto mapToRecordsDTO(RecordsData recordsData, RecordsResponseDto recordsDto) {
        recordsDto.setRecords(recordsData.getRecords());

        return recordsDto;
    }

}