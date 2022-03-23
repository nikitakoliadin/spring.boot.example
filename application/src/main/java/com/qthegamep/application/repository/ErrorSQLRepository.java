package com.qthegamep.application.repository;

import com.qthegamep.application.entity.Error;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorSQLRepository extends R2dbcRepository<Error, String> {
}
