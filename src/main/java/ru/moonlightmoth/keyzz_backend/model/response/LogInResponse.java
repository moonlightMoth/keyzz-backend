package ru.moonlightmoth.keyzz_backend.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moonlightmoth.keyzz_backend.model.JwtToken;
import ru.moonlightmoth.keyzz_backend.model.entity.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Authorisation response")
public class LogInResponse {

    @NotNull
    @Schema(description = "Status field. Must be OK or FAIL")
    private Status status;

    @Size(min = 1, max = 30, message = "Invalid name length, must be from 1 to 30")
    @Schema(description = "Name")
    private String name;

    @Size(min = 1, max = 30, message = "Invalid surname length, must be from 1 to 30")
    @Schema(description = "Surname")
    private String surname;

    @NotNull
    @Schema(description = "Role. Must be ADMIN or USER")
    private Role role;

    @NotNull
    @Schema(description = "JWT token to be saved on client side, may be null on status FAIL", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cC.I6MTYyMjUwNj")
    private JwtToken token;

    @Schema(description = "Message for clarity")
    private String message;

}
