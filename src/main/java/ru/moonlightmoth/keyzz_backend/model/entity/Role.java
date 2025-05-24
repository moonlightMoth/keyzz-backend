package ru.moonlightmoth.keyzz_backend.model.entity;

public enum Role
{
    ADMIN, USER;

    public static Role fromString(String s)
    {
        try
        {
            return Role.valueOf(s.toUpperCase());
        }
        catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException("Unknown enum value: " + s, e);
        }
    }
}