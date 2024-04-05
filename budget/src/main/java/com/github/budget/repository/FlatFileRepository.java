package com.github.budget.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.github.budget.entity.FlatFile;

@Repository
public interface FlatFileRepository extends MongoRepository<FlatFile, String> {
  Optional<FlatFile> findByFilename(String filename);

  Optional<List<FlatFile>> findBySpecFileId(String specFileId);

  Optional<List<FlatFile>> findByCreatedBy(String username);

  void deleteByFilename(String filename);
}
