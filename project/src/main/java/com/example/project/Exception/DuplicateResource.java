package com.example.project.Exception;

public class DuplicateResource extends RuntimeException {
    public DuplicateResource(String mssg) {
        super(mssg);
    }
}
