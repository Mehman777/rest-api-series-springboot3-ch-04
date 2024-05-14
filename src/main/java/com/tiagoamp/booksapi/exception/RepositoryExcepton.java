package com.tiagoamp.booksapi.exception;

public class RepositoryExcepton extends Exception  {
    
    private static final long serialVersionUID = 1L;

    public RepositoryExcepton() {
    }
    
    public RepositoryExcepton(Throwable cause) {
        super(cause);
    }

    public RepositoryExcepton(String message) {
        super(message);
    }

    public RepositoryExcepton(String message, Throwable cause) {
        super(message, cause);
    }

}