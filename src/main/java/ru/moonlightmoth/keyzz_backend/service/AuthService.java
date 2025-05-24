package ru.moonlightmoth.keyzz_backend.service;

import org.springframework.stereotype.Service;
import ru.moonlightmoth.keyzz_backend.model.JwtToken;
import ru.moonlightmoth.keyzz_backend.model.entity.User;

@Service
public interface AuthService {

    void validateUserJwt(JwtToken jwtToken);
    User validateUserJwtForUser(JwtToken jwtToken);
    void validateAdminJwt(JwtToken jwtToken);
    void validateAdminJwtForUser(JwtToken jwtToken);
    boolean isTokenAdmins(JwtToken jwtToken);
}
