package ru.moonlightmoth.keyzz_backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "JWT token wrapper")
public class JwtToken {

    @Pattern(regexp = "^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_.+/=]*$",
            message = "Invalid JWT token format")
    @Schema(description = "JWT token", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cC.I6MTYyMjUwNj")
    String token;

    public static class JwtTokenBuilder
    {
        public JwtTokenBuilder stripBearer()
        {
            if (token.startsWith("Bearer "))
                token = token.substring(7);
            return this;
        }
    }
}
