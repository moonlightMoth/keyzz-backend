package ru.moonlightmoth.keyzz_backend.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "records")
@Schema(description = "Records from database")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id")
    Long id;

    @Schema(description = "Address entity")
    @NotNull
    @ManyToOne
    @JoinColumn(name = "address_id")
    Address address;

    @Size(min = 1, max = 4095, message = "Invalid note length, must be from 1 to 4095")
    @Schema(description = "Note containing client needed info")
    @NotNull
    String note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Schema(description = "User entity posted record")
    User user;

    @Schema(description = "Id of user posted record")
    @NotNull
    Timestamp timestamp;
}
