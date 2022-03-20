package com.qthegamep.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponseDTO {

    @JsonProperty(
            value = "now",
            required = true)
    private Date now;

    @JsonProperty(
            value = "requestId",
            required = true)
    private String requestId;

    @JsonProperty(
            value = "applicationName",
            required = true)
    private String applicationName;

    @JsonProperty(
            value = "serverIp",
            required = true)
    private String serverIp;

    @JsonProperty(
            value = "dockerImageName",
            required = true)
    private String dockerImageName;
}
