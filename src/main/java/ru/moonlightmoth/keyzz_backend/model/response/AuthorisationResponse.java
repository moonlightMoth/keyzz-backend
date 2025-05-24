package ru.moonlightmoth.keyzz_backend.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moonlightmoth.keyzz_backend.model.entity.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Authorisation response")
public class AuthorisationResponse {

    @NotNull
    @Schema(description = "Must be 'OK' or 'FAIL'")
    private Status status;

    @Schema(description = "Login of user")
    private String login;

    @Size(min = 1, max = 30, message = "Invalid name length, must be from 1 to 30")
    @Schema(description = "Name")
    private String name;

    @Size(min = 1, max = 30, message = "Invalid surname length, must be from 1 to 30")
    @Schema(description = "Surname")
    private String surname;

    @Schema(description = "Role of user")
    private Role role;

    @Schema(description = "Message for clarity")
    private String message;
}
