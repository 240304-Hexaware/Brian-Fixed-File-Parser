package com.github.budget.controller;

import org.springframework.web.bind.annotation.RestController;

import com.github.budget.constant.Constant;
import com.github.budget.dto.request.RecordsRequestDto;
import com.github.budget.dto.response.RecordsResponseDto;
import com.github.budget.dto.response.ResponseDto;
import com.github.budget.entity.RecordsData;
import com.github.budget.service.RecordsDataService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@AllArgsConstructor
@RequestMapping("/records")
public class RecordsDataController {

    RecordsDataService recordsDataService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RecordsResponseDto>> getAllRecords() {
        List<RecordsResponseDto> records = recordsDataService.getAllRecords();
        return ResponseEntity.status(HttpStatus.OK).body(records);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<RecordsResponseDto>> getRecordsById(@PathVariable("username") String username) {
        return ResponseEntity.status(HttpStatus.OK).body(
                recordsDataService.getRecordsByUsername(username));
    }

    @GetMapping("/spec/{id}")
    public ResponseEntity<List<RecordsResponseDto>> getRecordsBySpecId(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                recordsDataService.getRecordsBySpecId(id));
    }

    @PutMapping()
    public ResponseEntity<ResponseDto> updateRecords(@RequestBody RecordsRequestDto recordsRequestDto) {
        recordsDataService.updateRecords(recordsRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(Constant.STATUS_201, Constant.MESSAGE_201));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDto> deleteRecords(@PathVariable("id") String id) {
        recordsDataService.deleteRecords(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(Constant.STATUS_200, Constant.MESSAGE_200));
    }

}
