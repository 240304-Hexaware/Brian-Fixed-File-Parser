package com.github.budget.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.budget.constants.Constant;
import com.github.budget.entity.FlatFile;
import com.github.budget.entity.SpecFile;
import com.github.budget.repository.FlatFileRepository;
import com.github.budget.repository.SpecFileRepository;
import com.github.budget.security.SecurityUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FileService {

    FlatFileRepository flatFileRepository;
    SpecFileRepository specFileRepository;

    public String saveFileToBlockStorage(MultipartFile file, String type) throws IllegalStateException, IOException {

        String destinationPath = Constant.FILE_DIR + file.getOriginalFilename();
        File destFile = new File(destinationPath);

        file.transferTo(destFile);

        if (type.equals("flat")) {
            FlatFile flatFile = new FlatFile();
            flatFile.setFilename(file.getOriginalFilename());
            flatFile.setFiletype(file.getContentType());
            flatFile.setPath(destinationPath);
            flatFile.setUsername(SecurityUtils.getCurrentUsername());
            flatFileRepository.save(flatFile);
        }
        if (type.equals("spec")) {
            SpecFile specFile = new SpecFile();
            specFile.setFilename(file.getOriginalFilename());
            specFile.setFiletype(file.getContentType());
            specFile.setPath(destinationPath);
            specFile.setUsername(SecurityUtils.getCurrentUsername());
            specFile.setSchema(JSONtoSpec(destinationPath));
            specFileRepository.save(specFile);
        }

        return type + " file uploaded successfully at " + destinationPath;

    }

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