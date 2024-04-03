package com.github.budget.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.github.budget.entity.RecordsData;

@Repository
public interface RecordsDataRepository extends MongoRepository<RecordsData, Object> {
    Optional<RecordsData> findByCreatedBy(String username);

    Optional<RecordsData> findBySpecFileId(ObjectId specFileId);
}