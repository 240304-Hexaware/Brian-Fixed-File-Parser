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
import com.github.budget.dto.response.ResponseDto;
import com.github.budget.dto.response.SpecFileResponseDto;
import com.github.budget.service.SpecFileService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/specfiles")
@AllArgsConstructor
public class SpecFilesController {
    SpecFileService specFileService;

    @PostMapping
    public ResponseEntity<ResponseDto> handleSpecFileUpload(
            @RequestParam("file") MultipartFile request) throws IllegalStateException, IOException {

        specFileService.createSpecFile(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(Constant.STATUS_201, Constant.MESSAGE_201));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SpecFileResponseDto>> getSpecFiles() {
        List<SpecFileResponseDto> result = specFileService.getSpecFiles();
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @GetMapping("/{username}")
    public ResponseEntity<List<SpecFileResponseDto>> getSpecFilesByUsername(@PathVariable("username") String username) {

        List<SpecFileResponseDto> result = specFileService.getSpecFilesByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{filename}")
    public ResponseEntity<ResponseDto> deleteSpecFile(@PathVariable("filename") String filename) {
        specFileService.deleteSpecFile(filename);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(Constant.STATUS_200, Constant.MESSAGE_200));
    }

}
