package ru.moonlightmoth.keyzz_backend.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response to delete record by id")
public class DeleteRecordResponse {

    @NotNull
    @Schema(description = "Status field. Must be OK or FAIL")
    private Status status;

    @Schema(description = "Message for clarity")
    private String message;
}
