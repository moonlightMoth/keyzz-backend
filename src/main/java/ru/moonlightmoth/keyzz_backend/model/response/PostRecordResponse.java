package ru.moonlightmoth.keyzz_backend.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response to attempt to post a record")
public class PostRecordResponse {

    @NotNull
    @Schema(description = "Status field. Must be OK or FAIL")
    private Status status;

    @Schema(description = "Message for clarity")
    private String message;

    @Min(1)
    @Schema(description = "Id of newly posted record")
    private Long id;
}
