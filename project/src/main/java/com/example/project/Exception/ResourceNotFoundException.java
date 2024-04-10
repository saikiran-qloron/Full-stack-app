package com.example.project.Exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String mssg){
        super(mssg);
    }

    public ResourceNotFoundException(String mssg, Throwable throwable) {
        super(mssg, throwable);
    }
}
