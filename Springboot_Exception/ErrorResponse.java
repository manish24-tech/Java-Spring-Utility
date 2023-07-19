package com.fourbench.schoolmanagementservice.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include. NON_NULL)
public class ErrorResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String status;

    private String code;

    private String message;

    private String path;

    private long timestamp;

    private List<String> errors;

    public ErrorResponse(String status, String code, String message, String path) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.path = path;
        this.timestamp = System.currentTimeMillis();
    }

    public ErrorResponse(String status, String code, List<String> errors, String path) {
        this.status = status;
        this.code = code;
        this.path = path;
        this.errors = errors;
        this.timestamp = System.currentTimeMillis();
    }
}
