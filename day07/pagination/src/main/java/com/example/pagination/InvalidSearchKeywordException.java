package com.example.pagination;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidSearchKeywordException extends RuntimeException {
    public InvalidSearchKeywordException(String msg) {
        super(msg);
    }
}