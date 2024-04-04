package com.github.budget.controller;

import org.springframework.web.bind.annotation.RestController;

import com.github.budget.dto.response.RecordsResponseDto;
import com.github.budget.entity.RecordsData;
import com.github.budget.service.RecordsDataService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@AllArgsConstructor
@RequestMapping("/records")
public class RecordsDataController {

    RecordsDataService recordsDataService;

    @GetMapping()
    public ResponseEntity<List<RecordsData>> getAllRecords() {
        return ResponseEntity.ok().body(
                recordsDataService.getAllRecords());
    }

    @GetMapping("/{username}")
    public ResponseEntity<RecordsResponseDto> getRecordsById(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(
                recordsDataService.getRecordsByUsername(username));
    }
}
