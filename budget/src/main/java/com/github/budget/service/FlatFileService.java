package com.github.budget.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.budget.constant.Constant;
import com.github.budget.dto.response.FlatFileResponseDto;
import com.github.budget.entity.FlatFile;
import com.github.budget.mapper.FlatFileMapper;
import com.github.budget.mapper.RecordsDataMapper;
import com.github.budget.repository.FlatFileRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FlatFileService {

    FlatFileRepository flatFileRepository;
    SpecFileService specFileService;

    public FlatFile createFlatFile(MultipartFile file, String specFileName)
            throws IllegalStateException, IOException {

        String destinationPath = Constant.FILE_DIR + file.getOriginalFilename();
        File destFile = new File(destinationPath);

        FlatFile flatFile = new FlatFile();
        flatFile.setFilename(file.getOriginalFilename());
        flatFile.setFiletype(file.getContentType());
        flatFile.setPath(destinationPath);
        flatFile.setSpecFileId(specFileService.getSpecFileIdByFilename(specFileName));
        // save file to disk
        file.transferTo(destFile);

        flatFileRepository.save(flatFile);

        return flatFile;
    }

    public void deleteFlatFile(String filename) {
        Optional<FlatFile> flatFile = flatFileRepository.findByFilename(filename);

        FlatFile flat = flatFile.get();
        File file = new File(flat.getPath());

        // deletes file from disk
        file.delete();

        flatFileRepository.deleteByFilename(filename);

    }

    public List<FlatFileResponseDto> getFlatFiles() {
        List<FlatFile> flatFiles = flatFileRepository.findAll();
        return flatFiles.stream()
                .map(FlatFileMapper::mapToFlatFileDTO)
                .collect(Collectors.toList());

    }

    public List<FlatFileResponseDto> getFlatFilesByUsername(String username) {
        Optional<List<FlatFile>> flatFiles = flatFileRepository.findByCreatedBy(username);
        if (flatFiles.isEmpty()) {
            throw new UsernameNotFoundException(username + " not found");
        }
        return flatFiles.get().stream()
                .map(FlatFileMapper::mapToFlatFileDTO)
                .collect(Collectors.toList());
    }

}