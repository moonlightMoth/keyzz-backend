package ru.moonlightmoth.keyzz_backend.model.response;

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
@Schema(description = "Authorisation response")
public class PatchRecordResponse {

    @NotNull
    @Schema(description = "Status field. Must be OK or FAIL")
    private Status status;

    @Schema(description = "Message for clarity")
    private String message;
}
