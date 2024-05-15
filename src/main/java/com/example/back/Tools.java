package com.example.back;

import com.example.back.model.Response;
import org.springframework.http.ResponseEntity;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.OK;

public class Tools {
    public static ResponseEntity<Response> getResponseModel(Object result) {
        return ResponseEntity.ok(Response.builder()
                .timestamp(now())
                .message(OK.series().name())
                .status(OK.getReasonPhrase())
                .statusCode(OK.value())
                .result(result)
                .build()
        );
    }
}
