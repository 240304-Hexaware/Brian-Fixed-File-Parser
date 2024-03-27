package com.github.budget.domain;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "spec_files")
public class SpecFile {
    public @MongoId ObjectId id;
    public String filename;
    public String filetype;
    public String path;
    public String username;
    public org.bson.Document schema;

    public String getSpecfilename() {
        return filename;
    }

    public void setfilename(String filename) {
        this.filename = filename;
    }

    public String getfiletype() {
        return filetype;
    }

    public void setfiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String filepath) {
        this.path = filepath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public org.bson.Document getSchema() {
        return schema;
    }

    public void setSchema(org.bson.Document schema) {
        this.schema = schema;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

}
