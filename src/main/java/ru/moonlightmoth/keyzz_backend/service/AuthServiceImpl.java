package ru.moonlightmoth.keyzz_backend.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.moonlightmoth.keyzz_backend.exception.AuthFailException;
import ru.moonlightmoth.keyzz_backend.exception.NoSuchUserRecordedException;
import ru.moonlightmoth.keyzz_backend.model.JwtToken;
import ru.moonlightmoth.keyzz_backend.model.entity.Role;
import ru.moonlightmoth.keyzz_backend.model.entity.User;
import ru.moonlightmoth.keyzz_backend.model.request.AuthorisationRequest;
import ru.moonlightmoth.keyzz_backend.model.response.AuthorisationResponse;
import ru.moonlightmoth.keyzz_backend.model.response.Status;
import ru.moonlightmoth.keyzz_backend.repository.UserRepository;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{

    private WebClient webClient;
    @Value("${keyzz.secret.auth-server-url}")
    private String authServerUrl;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void init()
    {
        webClient = WebClient.builder()
                .baseUrl(authServerUrl)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Override
    public void validateUserJwt(JwtToken jwtToken)
    {
        validateJwtAmalgamation(jwtToken, false, false);
    }

    @Override
    public User validateUserJwtForUser(JwtToken jwtToken)
    {
        return validateJwtAmalgamation(jwtToken, true, false);
    }

    @Override
    public void validateAdminJwt(JwtToken jwtToken)
    {
        validateJwtAmalgamation(jwtToken, false, true);
    }

    @Override
    public void validateAdminJwtForUser(JwtToken jwtToken)
    {
        validateJwtAmalgamation(jwtToken, true, true);
    }

    @Override
    public boolean isTokenAdmins(JwtToken jwtToken)
    {
        Mono<AuthorisationResponse> responseMono = webClient.method(HttpMethod.GET)
                .uri("/authorise")
                .bodyValue(AuthorisationRequest.builder().token(jwtToken).build())
                .retrieve()
                .bodyToMono(AuthorisationResponse.class);

        AuthorisationResponse authorisationResponse = responseMono.block();

        if (authorisationResponse == null || authorisationResponse.getStatus() == Status.FAIL)
            throw new AuthFailException("Failed authorisation with provided JWT token");

        return authorisationResponse.getRole() == Role.ADMIN;
    }

    private User validateJwtAmalgamation(JwtToken jwtToken, boolean isUserDetailsNeeded, boolean hasToBeAdmin)
    {
        Mono<AuthorisationResponse> responseMono = webClient.method(HttpMethod.POST)
                .uri("/authorise")
                .bodyValue(AuthorisationRequest.builder().token(jwtToken).build())
                .retrieve()
                .bodyToMono(AuthorisationResponse.class);

        AuthorisationResponse authorisationResponse = responseMono.block();

        if (authorisationResponse == null || authorisationResponse.getStatus() == Status.FAIL)
            throw new AuthFailException("Failed authorisation with provided JWT token");

        saveUserIfUnsaved(authorisationResponse);

        if (hasToBeAdmin && authorisationResponse.getRole() != Role.ADMIN)
            throw new AuthFailException("User needs to be Admin but is not");

        if (!isUserDetailsNeeded)
            return null;

        saveUserIfUnsaved(authorisationResponse);

        Optional<User> optionalUser = userRepository.findByLogin(authorisationResponse.getLogin());

        if (optionalUser.isEmpty())
            throw new NoSuchUserRecordedException("No such user recorded in backend db");

        return optionalUser.get();
    }

    private void saveUserIfUnsaved(AuthorisationResponse authorisationResponse)
    {
        Optional<User> userOptional = userRepository.findByLogin(authorisationResponse.getLogin());

        if (userOptional.isEmpty())
            userRepository.save(User.builder()
                    .login(authorisationResponse.getLogin())
                    .name(authorisationResponse.getName())
                    .surname(authorisationResponse.getSurname())
                    .build());
    }
}
