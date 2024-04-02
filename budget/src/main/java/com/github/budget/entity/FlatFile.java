package com.github.budget.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "flat_files")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FlatFile {
    public @MongoId ObjectId id;
    public String filename;
    public String filetype;
    public String path;
    public String username;
    public String specFileId;
    public org.bson.Document data;

}
