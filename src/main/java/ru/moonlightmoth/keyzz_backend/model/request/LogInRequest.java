package ru.moonlightmoth.keyzz_backend.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Log in request")
public class LogInRequest {

    @NotNull
    @JsonProperty("login")
    @Schema(description = "Login")
    private String login;

    @NotNull
    @JsonProperty("password")
    @Schema(description = "Password")
    private String password;
}
