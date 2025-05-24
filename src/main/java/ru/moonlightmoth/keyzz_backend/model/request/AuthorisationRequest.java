package ru.moonlightmoth.keyzz_backend.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moonlightmoth.keyzz_backend.model.JwtToken;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Auth request with token provided")
public class AuthorisationRequest {
    @NotNull
    @JsonProperty("token")
    @Schema(description = "JWT token", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cC.I6MTYyMjUwNj")
    private JwtToken token;

    @JsonProperty("message")
    @Schema(description = "Message for clarity")
    private String message;
}
