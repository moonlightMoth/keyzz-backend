package ru.moonlightmoth.keyzz_backend.model.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
@Schema(description = "Users from database")
@JsonAutoDetect
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id")
    Long id;

    @Size(min = 1, max = 30, message = "Invalid login length, must be from 1 to 30")
    @Schema(description = "User's login")
    @NotNull
    String login;

    @Size(min = 1, max = 30, message = "Invalid name length, must be from 1 to 30")
    @Schema(description = "User's name")
    @NotNull
    String name;

    @Size(min = 1, max = 30, message = "Invalid surname length, must be from 1 to 30")
    @Schema(description = "User's surname")
    @NotNull
    String surname;
}
