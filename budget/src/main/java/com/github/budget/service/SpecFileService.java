package com.github.budget.service;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties.Server.Spec;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.budget.constant.Constant;
import com.github.budget.dto.response.SpecFileResponseDto;
import com.github.budget.entity.FlatFile;
import com.github.budget.entity.SpecFile;
import com.github.budget.exception.ResourceNotFoundException;
import com.github.budget.mapper.SpecFileMapper;
import com.github.budget.repository.SpecFileRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SpecFileService {
    SpecFileRepository specFileRepository;
    MongoTemplate mongoTemplate;

    public List<SpecFileResponseDto> getSpecFiles() {
        List<SpecFile> specFiles = specFileRepository.findAll();
        return specFiles.stream()
                .map(SpecFileMapper::mapToSpecFileDto)
                .collect(Collectors.toList());
    }

    public void createSpecFile(MultipartFile file)
            throws IllegalStateException, IOException {

        String destinationPath = Constant.FILE_DIR + file.getOriginalFilename();
        File destFile = new File(destinationPath);

        SpecFile specFile = new SpecFile();
        specFile.setFilename(file.getOriginalFilename());
        specFile.setFiletype(file.getContentType());
        specFile.setPath(destinationPath);
        // // upload file to disk
        file.transferTo(destFile);
        // // read file and convert to json
        specFile.setSchema(fileJsonToSpec(destinationPath));
        mongoTemplate.insert(specFile);

    }

    public List<SpecFileResponseDto> getSpecFilesByUsername(String username) {

        Query query = new Query();
        query.addCriteria(Criteria.where("createdBy").is(username));
        List<SpecFile> list = mongoTemplate.find(query, SpecFile.class);
        return list.stream().map(SpecFileMapper::mapToSpecFileDto).collect(Collectors.toList());
    }

    public void deleteSpecFile(String filename) {

        Optional<SpecFile> specFile = specFileRepository.findByFilename(filename);

        SpecFile spec = specFile.get();
        File file = new File(spec.getPath());

        // deletes file from disk
        file.delete();

        specFileRepository.deleteByFilename(filename);

    }

    public Document getSpecFileSchemaByFilename(String filename) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(filename));
        SpecFile specFile = mongoTemplate.findOne(query, SpecFile.class);
        if (specFile == null) {
            throw new ResourceNotFoundException("SpecFile", "filename", filename);
        }
        return specFile.getSchema();
    }

    public String getSpecFileIdByFilename(String filename) {
        Query query = new Query();
        query.addCriteria(Criteria.where("filename").is(filename));
        SpecFile specFile = mongoTemplate.findOne(query, SpecFile.class);
        if (specFile == null) {
            throw new ResourceNotFoundException("SpecFile", "filename", filename);
        }
        return specFile.getId();
    }

    public SpecFile getSpecFileByFilename(String filename) {
        Optional<SpecFile> specFile = specFileRepository.findByFilename(filename);

        if (specFile.isEmpty()) {
            throw new ResourceNotFoundException("SpecFile", "filename", filename);
        }
        return specFile.get();
    }

    public Document fileJsonToSpec(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(new File(filePath), Document.class);
    }

}
