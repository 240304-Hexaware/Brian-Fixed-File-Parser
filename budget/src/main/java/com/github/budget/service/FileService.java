package com.github.budget.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.budget.entity.FlatFile;
import com.github.budget.entity.SpecFile;

@Service
public class FileService {

    public Document parseFixedLengthFile(FlatFile flatFile, SpecFile specFile) throws IOException {
        Document spec = specFile.getSchema();
        Document data = new Document();
        int index = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(flatFile.getPath()))) {
            String record;
            while ((record = reader.readLine()) != null) {
                Map<String, String> parsedRecord = parseLine(record, spec);

                data.put(String.valueOf(index++), new Document(parsedRecord));
            }
        }
        return data;
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

    public Document JSONtoSpec(String filePath) throws StreamReadException, DatabindException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Document spec = objectMapper.readValue(new File(filePath), Document.class);
        return spec;
    }

}