package com.github.budget.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "records_data")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RecordsData {

    private @MongoId ObjectId id;
    private org.bson.Document records;
    private ObjectId specFileId;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private String createdAt;

    @LastModifiedDate
    private String updatedAt;

    @LastModifiedBy
    private String updatedBy;

}
