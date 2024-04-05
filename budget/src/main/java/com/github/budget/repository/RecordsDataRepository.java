package com.github.budget.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.github.budget.entity.RecordsData;

@Repository
public interface RecordsDataRepository extends MongoRepository<RecordsData, String> {
    Optional<List<RecordsData>> findByCreatedBy(String username);

    // @Query(value = "{ 'records.?0': { $exists: true } }", update = "{ '$unset': {
    // 'records.?0': '' } }")
    // void deleteSpecificRecordByRecordId(String recordId);

    Optional<RecordsData> findBySpecFileId(String specFileId);
}
