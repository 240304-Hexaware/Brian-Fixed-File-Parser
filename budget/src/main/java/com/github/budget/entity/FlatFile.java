package com.github.budget.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "flat_files")
@Data
public class FlatFile {
    @Id
    private String id;
    private String filename;
    private String filetype;
    private String path;
    private String specFileId;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private String createdAt;

    @LastModifiedDate
    private String updatedAt;

    @LastModifiedBy
    private String updatedBy;

}
