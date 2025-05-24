package ru.moonlightmoth.keyzz_backend.exception;

public class NoSuchUserRecordedException extends RuntimeException{
    public NoSuchUserRecordedException(String message)
    {
        super(message);
    }
}
