package com.github.budget.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.budget.entity.SpecFile;
import com.github.budget.service.SpecFileService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class SpecFilesController {
    SpecFileService specFileService;

    @PostMapping("/specfiles")
    public ResponseEntity<String> handleSpecFileUpload(@RequestParam("file") MultipartFile file) {

        try {
            String result = specFileService.createSpecFile(file);

            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not save file");
        }
    }

    @GetMapping("/specfiles")
    public ResponseEntity<List<SpecFile>> getSpecFiles() {
        return specFileService.getSpecFiles();
    }

    @DeleteMapping("/specfiles")
    public ResponseEntity<String> deleteSpecFile(@RequestParam("filename") String filename) {
        specFileService.deleteSpecFile(filename);
        return ResponseEntity.ok("Spec file deleted");
    }

}
