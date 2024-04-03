package com.github.budget.controller;

import org.springframework.web.bind.annotation.RestController;

import com.github.budget.dto.RecordsDto;
import com.github.budget.entity.RecordsData;
import com.github.budget.service.RecordsDataService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@AllArgsConstructor
public class RecordsDataController {

    RecordsDataService recordsDataService;

    @GetMapping("records")
    public ResponseEntity<List<RecordsData>> getAllRecords() {
        return ResponseEntity.ok().body(
                recordsDataService.getAllRecords());
    }

    @GetMapping("records/{username}")
    public ResponseEntity<RecordsDto> getRecordsById(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(
                recordsDataService.getRecordsByUsername(username));
    }

}
