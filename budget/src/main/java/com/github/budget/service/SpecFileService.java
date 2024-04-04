package com.github.budget.service;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.budget.constant.Constant;
import com.github.budget.dto.request.SpecFileRequestDto;
import com.github.budget.dto.response.SpecFileResponseDto;
import com.github.budget.entity.SpecFile;
import com.github.budget.mapper.SpecFileMapper;
import com.github.budget.repository.SpecFileRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SpecFileService {
    SpecFileRepository specFileRepository;

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
        // upload file to disk
        file.transferTo(destFile);
        // read file and convert to json
        specFile.setSchema(fileJsonToSpec(destinationPath));
        specFileRepository.save(specFile);

    }

    public List<SpecFileResponseDto> getSpecFilesByUsername(String username) {

        Optional<List<SpecFile>> specFiles = specFileRepository.findByCreatedBy(username);
        if (specFiles.isEmpty()) {
            return Collections.emptyList();
        }
        return specFiles.get().stream()
                .map(SpecFileMapper::mapToSpecFileDto)
                .collect(Collectors.toList());
    }

    public boolean deleteSpecFile(String filename) {
        Optional<SpecFile> specFile = specFileRepository.findByFilename(filename);
        if (specFile.isEmpty()) {
            return false;
        }

        SpecFile spec = specFile.get();
        File file = new File(spec.getPath());

        // deletes file from disk
        if (!file.delete())
            return false;

        specFileRepository.deleteByFilename(filename);

        return true;
    }

    public Document getSpecFileSchemaByFilename(String filename) {
        Optional<SpecFile> specFile = specFileRepository.findByFilename(filename);

        if (specFile.isEmpty()) {
            return new Document();
        }
        return specFile.get().getSchema();
    }

    public String getSpecFileIdByFilename(String filename) {
        Optional<SpecFile> specFile = specFileRepository.findByFilename(filename);

        if (specFile.isEmpty()) {
            return null;
        }
        return specFile.get().getId();
    }

    public Document fileJsonToSpec(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(new File(filePath), Document.class);
    }

}
