package com.example.tasklist.domain.exception;

public class ResourceMappingException extends RuntimeException{
    public ResourceMappingException() {
        super();
    }

    public ResourceMappingException(String message) {
        super(message);
    }
}
