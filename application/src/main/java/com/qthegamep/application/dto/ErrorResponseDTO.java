package com.qthegamep.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {

    @JsonProperty(
            value = "requestId",
            required = true)
    private String requestId;

    @JsonProperty(
            value = "errorCode",
            required = true)
    private Integer errorCode;
}
