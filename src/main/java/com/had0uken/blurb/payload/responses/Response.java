package com.had0uken.blurb.payload.responses;

import org.springframework.http.HttpStatus;

public abstract class Response {
    public abstract HttpStatus getStatus();
}
