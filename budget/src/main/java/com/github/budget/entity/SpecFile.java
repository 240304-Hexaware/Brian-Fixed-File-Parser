package com.github.budget.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "spec_files")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SpecFile {
    private @MongoId ObjectId id;
    private String filename;
    private String filetype;
    private String path;
    private String username;
    private org.bson.Document schema;
    private RecordsData recordsData;
}
