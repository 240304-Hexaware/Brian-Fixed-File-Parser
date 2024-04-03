package com.github.budget.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.budget.constant.Constant;
import com.github.budget.entity.SpecFile;
import com.github.budget.repository.SpecFileRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SpecFileService {
    SpecFileRepository specFileRepository;

    public ResponseEntity<List<SpecFile>> getSpecFiles() {
        return ResponseEntity.ok(specFileRepository.findAll());
    }

    public String createSpecFile(MultipartFile file)
            throws IllegalStateException, IOException {

        String destinationPath = Constant.FILE_DIR + file.getOriginalFilename();
        File destFile = new File(destinationPath);

        // upload file to disk
        file.transferTo(destFile);

        SpecFile specFile = new SpecFile();
        specFile.setFilename(file.getOriginalFilename());
        specFile.setFiletype(file.getContentType());
        specFile.setPath(destinationPath);
        specFile.setSchema(JSONtoSpec(destinationPath));
        specFileRepository.save(specFile);
        return "Saved spec file to " + destinationPath + " successfully";

    }

    public Document getSpecFileSchemaByFilename(String filename) {
        Optional<SpecFile> specFile = specFileRepository.findByFilename(filename);

        if (specFile.isEmpty()) {
            return null;
        }
        return specFile.get().getSchema();
    }

    public ObjectId getSpecFileIdByFilename(String filename) {
        Optional<SpecFile> specFile = specFileRepository.findByFilename(filename);

        if (specFile.isEmpty()) {
            return null;
        }
        return specFile.get().getId();
    }

    public boolean deleteSpecFile(String filename) {
        Optional<SpecFile> specFile = specFileRepository.findByFilename(filename);

        SpecFile spec = specFile.get();
        File file = new File(spec.getPath());
        file.delete();
        specFileRepository.deleteById(spec.getId());

        return true;
    }

    public Document JSONtoSpec(String filePath) throws StreamReadException, DatabindException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Document spec = objectMapper.readValue(new File(filePath), Document.class);
        return spec;
    }

}
