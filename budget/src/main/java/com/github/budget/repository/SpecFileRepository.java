package com.github.budget.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.github.budget.entity.SpecFile;

@Repository
public interface SpecFileRepository extends MongoRepository<SpecFile, Object> {
    Optional<SpecFile> findByFilename(String filename);

}
