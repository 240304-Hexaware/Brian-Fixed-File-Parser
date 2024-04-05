package com.github.budget.mapper;

import com.github.budget.dto.request.RecordsRequestDto;
import com.github.budget.dto.response.RecordsResponseDto;
import com.github.budget.entity.RecordsData;

public class RecordsDataMapper {

    public static RecordsResponseDto mapToRecordsDTO(RecordsData recordsData) {
        RecordsResponseDto recordsDto = new RecordsResponseDto();
        recordsDto.setData(recordsData.getData());
        recordsDto.setId(recordsData.getId());

        return recordsDto;
    }

    public static RecordsData mapToRecordsData(RecordsRequestDto recordsDto) {
        RecordsData recordsData = new RecordsData();
        recordsData.setId(recordsDto.getId());
        recordsData.setData(recordsDto.getData());

        return recordsData;
    }

}