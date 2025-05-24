package ru.moonlightmoth.keyzz_backend.exception;

public class NoSuchRecordException extends RuntimeException{
    public NoSuchRecordException(String message)
    {
        super(message);
    }
}
