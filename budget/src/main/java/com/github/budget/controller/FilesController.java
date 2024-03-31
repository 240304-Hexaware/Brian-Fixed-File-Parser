package com.github.budget.controller;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.budget.entity.FlatFile;
import com.github.budget.entity.SpecFile;
import com.github.budget.repository.FlatFileRepository;
import com.github.budget.repository.SpecFileRepository;
import com.github.budget.security.SecurityUtils;
import com.github.budget.service.FileService;

@RestController
public class FilesController {
  @Autowired
  FlatFileRepository flatFileRepository;

  @Autowired
  SpecFileRepository specFileRepository;

  @Autowired
  FileService fileService;

  @PostMapping("/flatfiles")
  public ResponseEntity<String> handleSpecFileUpload(@RequestParam("file") MultipartFile file) {
    // create folder for each user /files/{username}
    String destinationPath = "/Users/shadow/Workstation/Revature/Brian-Project/budget/src/main/resources/"
        + file.getOriginalFilename();
    File destFile = new File(destinationPath);

    try {
      file.transferTo(destFile);
      FlatFile flatFile = new FlatFile();
      flatFile.setFilename(file.getOriginalFilename());
      flatFile.setFiletype(file.getContentType());
      flatFile.setPath(destinationPath);
      flatFile.setUsername(SecurityUtils.getCurrentUsername());
      flatFileRepository.save(flatFile);

      return ResponseEntity.ok("File uploaded successfully");
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not save file");
    }
  }

  @PostMapping("/specfiles")
  public ResponseEntity<String> handleFixedFileUpload(@RequestParam("file") MultipartFile file) {
    // create folder for each user /files/{username}
    String destinationPath = "/Users/shadow/Workstation/Revature/Brian-Project/budget/src/main/resources/"
        + file.getOriginalFilename();
    File destFile = new File(destinationPath);

    try {
      file.transferTo(destFile);
      SpecFile specFile = new SpecFile();
      specFile.setFilename(file.getOriginalFilename());
      specFile.setFiletype(file.getContentType());
      specFile.setPath(destinationPath);
      specFile.setUsername(SecurityUtils.getCurrentUsername());
      specFile.setSchema(fileService.JSONtoSpec(destinationPath));
      specFileRepository.save(specFile);

      return ResponseEntity.ok("File uploaded successfully");
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not save file");
    }
  }

  @PostMapping("/mergefiles")
  public ResponseEntity<Document> mergeFiles(@RequestParam("flatFileName") String flatFileName,
      @RequestParam("specFileName") String specFileName) throws IOException {
    Optional<FlatFile> flatFile = flatFileRepository.findByFilename(flatFileName);
    Optional<SpecFile> specFile = specFileRepository.findByFilename(specFileName);

    if (flatFile.isEmpty() || specFile.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    Document res = fileService.parseFixedLengthFile(flatFile.get(), specFile.get());

    return ResponseEntity.ok(res);
  }

}
