package ru.moonlightmoth.keyzz_backend.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moonlightmoth.keyzz_backend.model.entity.Address;
import ru.moonlightmoth.keyzz_backend.model.entity.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "post record request")
public class PostRecordRequest {

    @JsonProperty("address")
    @Schema(description = "Address to post a note")
    @NotNull
    private Address address;

    @JsonProperty("user")
    @Schema(description = "User posted record")
    @NotNull
    private User user;

    @JsonProperty("note")
    @Size(min = 1, max = 4095, message = "Invalid note length, must be from 1 to 4095")
    @Schema(description = "Actual note")
    @NotNull
    private String note;
}
