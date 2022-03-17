package com.qthegamep.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Objects;

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

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuccessResponseDTO that = (SuccessResponseDTO) o;
        return Objects.equals(now, that.now) &&
                Objects.equals(requestId, that.requestId) &&
                Objects.equals(applicationName, that.applicationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(now, requestId, applicationName);
    }

    @Override
    public String toString() {
        return "SuccessResponseDTO{" +
                "now=" + now +
                ", requestId='" + requestId + '\'' +
                ", applicationName='" + applicationName + '\'' +
                '}';
    }
}
