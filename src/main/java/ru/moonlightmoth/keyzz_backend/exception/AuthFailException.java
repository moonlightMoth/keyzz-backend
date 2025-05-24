package ru.moonlightmoth.keyzz_backend.exception;

public class AuthFailException extends RuntimeException{

    public AuthFailException(String e)
    {
        super(e);
    }
}
