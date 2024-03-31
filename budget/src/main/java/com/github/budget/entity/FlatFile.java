package com.github.budget.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "flat_files")
public class FlatFile {
    public @MongoId ObjectId id;
    public String filename;
    public String filetype;
    public String path;
    public String username;
    public String specFileId;
    public org.bson.Document data;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSpecFileId() {
        return specFileId;
    }

    public void setSpecFileId(String specFileId) {
        this.specFileId = specFileId;
    }

    public org.bson.Document getData() {
        return data;
    }

    public void setData(org.bson.Document data) {
        this.data = data;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

}
