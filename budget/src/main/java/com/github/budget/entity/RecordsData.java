package com.github.budget.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "records_data")
@Data
public class RecordsData {

    @Id
    private String id;
    private org.bson.Document records;
    private String specFileId;
    private String flatFileId;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private String createdAt;

    @LastModifiedDate
    private String updatedAt;

    @LastModifiedBy
    private String updatedBy;

}
