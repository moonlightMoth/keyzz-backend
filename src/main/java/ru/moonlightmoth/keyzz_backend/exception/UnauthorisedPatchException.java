package ru.moonlightmoth.keyzz_backend.exception;

public class UnauthorisedPatchException extends RuntimeException{
    public UnauthorisedPatchException(String message)
    {
        super(message);
    }
}
