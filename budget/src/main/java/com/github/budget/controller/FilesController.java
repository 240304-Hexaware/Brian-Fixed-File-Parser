package com.github.budget.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.budget.dto.RecordsDto;
import com.github.budget.entity.SpecFile;
import com.github.budget.repository.FlatFileRepository;
import com.github.budget.repository.SpecFileRepository;
import com.github.budget.service.FileService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@AllArgsConstructor
public class FilesController {

  FlatFileRepository flatFileRepository;
  SpecFileRepository specFileRepository;
  FileService fileService;

  @PostMapping("/specfiles")
  public ResponseEntity<String> handleSpecFileUpload(@RequestParam("file") MultipartFile file) {

    try {
      String result = fileService.saveSpecFileToBlockStorage(file);

      return ResponseEntity.ok(result);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not save file");
    }
  }

  @GetMapping("/specfiles")
  public ResponseEntity<List<SpecFile>> getSpecFiles() {
    return fileService.getSpecFiles();
  }

  @PostMapping("/flatfiles")
  public ResponseEntity<RecordsDto> handleFlatFileUpload(@RequestParam("flatFile") MultipartFile flatFile,
      @RequestParam("specFileName") String specFileName) throws IllegalStateException, IOException {

    RecordsDto recordsDto = fileService.saveFlatFileToBlockStorage(flatFile, specFileName);

    return ResponseEntity.status(HttpStatus.OK).body(recordsDto);
  }

}
