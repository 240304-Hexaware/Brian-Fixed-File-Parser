package com.github.budget.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.Document;
import org.springframework.stereotype.Service;

import com.github.budget.dto.response.RecordsResponseDto;
import com.github.budget.entity.FlatFile;
import com.github.budget.entity.RecordsData;
import com.github.budget.mapper.RecordsDataMapper;
import com.github.budget.repository.RecordsDataRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RecordsDataService {
    FlatFileService flatFileService;
    SpecFileService specFileService;
    RecordsDataRepository recordsDataRepository;

    public RecordsResponseDto createRecords(FlatFile flatFile, String specFileName) throws IOException {

        Document schema = specFileService.getSpecFileSchemaByFilename(specFileName);

        Document parsedRecords = parseFixedLengthFile(flatFile, schema);

        // Create RecordsData object
        RecordsData recordsData = new RecordsData();
        recordsData.setRecords(parsedRecords);
        recordsData.setSpecFileId(specFileService.getSpecFileIdByFilename(specFileName));
        recordsData.setFlatFileId(flatFile.getId());
        recordsDataRepository.save(recordsData);

        RecordsResponseDto recordsDto = RecordsDataMapper.mapToRecordsDTO(recordsData, new RecordsResponseDto());

        return recordsDto;
    }

    public List<RecordsData> getAllRecords() {
        return recordsDataRepository.findAll();
    }

    public RecordsResponseDto getRecordsByUsername(String username) {
        Optional<RecordsData> recordsData = recordsDataRepository.findByCreatedBy(username);

        return RecordsDataMapper.mapToRecordsDTO(recordsData.get(), new RecordsResponseDto());
    }

    public Document parseFixedLengthFile(FlatFile flatFile, Document schema)
            throws IOException {
        Document records = new Document();
        int index = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(flatFile.getPath()))) {
            String record;
            while ((record = reader.readLine()) != null) {
                Map<String, String> parsedRecord = parseLine(record, schema);

                records.put(String.valueOf(index++), new Document(parsedRecord));
            }
        }
        return records;
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
