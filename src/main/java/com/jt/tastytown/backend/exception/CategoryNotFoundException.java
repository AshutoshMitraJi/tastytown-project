package com.jt.tastytown.backend.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(){
        super();
    }
    public CategoryNotFoundException(String message){
        super(message);
    }
}
