package com.github.budget.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.budget.dto.RecordsDto;
import com.github.budget.entity.FlatFile;
import com.github.budget.service.FlatFileService;
import com.github.budget.service.RecordsDataService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class FlatFilesController {

    FlatFileService fileService;
    RecordsDataService recordsDataService;

    @PostMapping("/flatfiles")
    public ResponseEntity<RecordsDto> handleFlatFileUpload(@RequestParam("flatFile") MultipartFile flatFile,
            @RequestParam("specFileName") String specFileName) throws IllegalStateException, IOException {
        FlatFile storedFlatFile = fileService.createFlatFile(flatFile, specFileName);
        RecordsDto recordsDto = recordsDataService.createRecords(storedFlatFile, specFileName);

        return ResponseEntity.status(HttpStatus.OK).body(recordsDto);
    }

}
