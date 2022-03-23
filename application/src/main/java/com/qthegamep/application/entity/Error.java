package com.qthegamep.application.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.relational.core.mapping.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "errors")
public class Error {

    @Id
    private String id;

    @Field(name = "requestId")
    @Column(value = "requestId")
    @Indexed(direction = IndexDirection.ASCENDING)
    private String requestId;

    @Field(name = "errorCode")
    @Column(value = "errorCode")
    @Indexed(direction = IndexDirection.ASCENDING)
    private Integer errorCode;
}
