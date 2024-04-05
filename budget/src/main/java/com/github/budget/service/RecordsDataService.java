package com.github.budget.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.github.budget.dto.request.RecordsRequestDto;
import com.github.budget.dto.response.RecordsResponseDto;
import com.github.budget.entity.FlatFile;
import com.github.budget.entity.RecordsData;
import com.github.budget.entity.SpecFile;
import com.github.budget.exception.ResourceNotFoundException;
import com.github.budget.mapper.RecordsDataMapper;
import com.github.budget.repository.RecordsDataRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RecordsDataService {
    FlatFileService flatFileService;
    SpecFileService specFileService;
    RecordsDataRepository recordsDataRepository;

    private MongoTemplate mongoTemplate;

    public RecordsResponseDto createRecords(FlatFile flatFile, String specFileName) throws IOException {

        SpecFile specFile = specFileService.getSpecFileByFilename(specFileName);

        Document schema = specFile.getSchema();
        try (BufferedReader reader = new BufferedReader(new FileReader(flatFile.getPath()))) {
            String record;
            while ((record = reader.readLine()) != null) {
                Map<String, String> parsedRecord = parseLine(record, schema);

                RecordsData recordsData = new RecordsData();
                recordsData.setData(parsedRecord);
                recordsData.setFlatFileId(flatFile.getId());
                recordsData.setSpecFileId(specFile.getId());

                mongoTemplate.insert(recordsData);
            }
        }

        return null;
    }

    public List<RecordsResponseDto> getAllRecords() {
        Query query = new Query();
        List<RecordsData> recordsData = mongoTemplate.find(query, RecordsData.class);
        return recordsData.stream()
                .map(RecordsDataMapper::mapToRecordsDTO)
                .collect(Collectors.toList());
    }

    public List<RecordsResponseDto> getRecordsByUsername(String username) {

        Query query = new Query();
        query.addCriteria(Criteria.where("createdBy").is(username));
        List<RecordsData> recordsData = mongoTemplate.find(query, RecordsData.class);
        return recordsData.stream()
                .map(RecordsDataMapper::mapToRecordsDTO)
                .collect(Collectors.toList());

    }

    public void updateRecords(RecordsRequestDto recordsRequestDto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(recordsRequestDto.getId()));
        RecordsData recordsData = mongoTemplate.findOne(query, RecordsData.class);
        if (recordsData == null) {
            throw new ResourceNotFoundException("RecordsData", "recordsid", recordsRequestDto.getId());
        }
        recordsData.setData(recordsRequestDto.getData());
        mongoTemplate.save(recordsData);
    }

    public void deleteRecords(String recordsId) {

        Optional<RecordsData> recordsData = recordsDataRepository.findById(recordsId);
        if (recordsData.isEmpty()) {
            throw new ResourceNotFoundException("RecordsData", "recordsid", recordsId);
        }

        recordsDataRepository.delete(recordsData.get());
    }

    public List<RecordsResponseDto> getRecordsBySpecId(String filename) {
        String id = specFileService.getSpecFileIdByFilename(filename);
        Query query = new Query();
        query.addCriteria(Criteria.where("specFileId").is(id));
        List<RecordsData> recordsData = mongoTemplate.find(query, RecordsData.class);
        return recordsData.stream()
                .map(RecordsDataMapper::mapToRecordsDTO)
                .collect(Collectors.toList());
    }

    private Map<String, String> parseLine(String line, Document spec) {
        Map<String, String> record = new LinkedHashMap<>();
        int startIndex = 0;
        for (String key : spec.keySet()) {
            int fieldLength = spec.getInteger(key, 0);
            int endIndex = startIndex + fieldLength;
            String value = line.substring(startIndex, Math.min(endIndex, line.length())).trim();
            record.put(key, value);
            startIndex += fieldLength;
        }
        return record;
    }
}
