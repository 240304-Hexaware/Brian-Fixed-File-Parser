package com.github.budget.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.budget.constant.Constant;
import com.github.budget.dto.response.FlatFileResponseDto;
import com.github.budget.dto.response.RecordsResponseDto;
import com.github.budget.dto.response.ResponseDto;
import com.github.budget.entity.FlatFile;
import com.github.budget.service.FlatFileService;
import com.github.budget.service.RecordsDataService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/flatfiles")
public class FlatFilesController {

    FlatFileService fileService;
    RecordsDataService recordsDataService;

    @PostMapping()
    public ResponseEntity<RecordsResponseDto> handleFlatFileUpload(@RequestParam("flatFile") MultipartFile flatFile,
            @RequestParam("specFileName") String specFileName) throws IllegalStateException, IOException {
        FlatFile storedFlatFile = fileService.createFlatFile(flatFile, specFileName);
        RecordsResponseDto recordsDto = recordsDataService.createRecords(storedFlatFile, specFileName);

        return ResponseEntity.status(HttpStatus.CREATED).body(recordsDto);
    }

    @DeleteMapping()
    public ResponseEntity<ResponseDto> deleteFlatFile(@RequestParam("filename") String filename) {
        fileService.deleteFlatFile(filename);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(Constant.STATUS_200, Constant.MESSAGE_200));
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FlatFileResponseDto>> getFlatFiles() {
        List<FlatFileResponseDto> result = fileService.getFlatFiles();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<FlatFileResponseDto>> getFlatFilesByUsername(@PathVariable("username") String username) {
        List<FlatFileResponseDto> result = fileService.getFlatFilesByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
