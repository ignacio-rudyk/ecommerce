package com.ignacio.rudyk.ecommerce.repository.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

public class MetadataDTO {

    @JsonProperty("path")
    private String path;

    @JsonProperty("method")
    private String method;

    @JsonProperty("code")
    private String code;

    public MetadataDTO(String path, String method, HttpStatus httpStatus) {
        this.path = path;
        this.method = method;
        this.code = Integer.toString(httpStatus.value());
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}