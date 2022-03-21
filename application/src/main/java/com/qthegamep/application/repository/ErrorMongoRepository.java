package com.qthegamep.application.repository;

import com.qthegamep.application.entity.Error;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorMongoRepository extends ReactiveMongoRepository<Error, String> {
}
