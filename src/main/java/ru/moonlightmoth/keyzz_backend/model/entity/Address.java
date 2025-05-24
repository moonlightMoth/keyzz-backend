package ru.moonlightmoth.keyzz_backend.model.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
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
@Table(name = "addresses")
@Schema(description = "Addresses from database")
@JsonAutoDetect
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id")
    Long id;

    @Size(min = 1, max = 127, message = "Invalid address length, must be from 1 to 127")
    @Schema(description = "Actual address of building")
    @NotNull
    String address;
}
