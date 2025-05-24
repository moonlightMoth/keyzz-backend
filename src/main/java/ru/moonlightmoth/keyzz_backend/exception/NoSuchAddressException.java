package ru.moonlightmoth.keyzz_backend.exception;

public class NoSuchAddressException extends RuntimeException{
    public NoSuchAddressException(String message)
    {
        super(message);
    }
}
