package com.github.budget.service;

import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.budget.constant.Constant;
import com.github.budget.entity.FlatFile;
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

        // save file to disk
        file.transferTo(destFile);

        FlatFile flatFile = new FlatFile();
        flatFile.setFilename(file.getOriginalFilename());
        flatFile.setFiletype(file.getContentType());
        flatFile.setPath(destinationPath);
        flatFile.setSpecFileId(specFileService.getSpecFileIdByFilename(specFileName));
        flatFileRepository.save(flatFile);

        return flatFile;
    }

}