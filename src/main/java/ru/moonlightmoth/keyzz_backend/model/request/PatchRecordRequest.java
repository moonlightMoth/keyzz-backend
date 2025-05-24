package ru.moonlightmoth.keyzz_backend.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
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
@Schema(description = "Patch record request")
public class PatchRecordRequest {

    @Min(1)
    //@JsonProperty("id")
    @Schema(description = "Id of newly posted record")
    private Long id;

    //@JsonProperty("note")
    @Size(min = 1, max = 4095, message = "Invalid note length, must be from 1 to 4095")
    @Schema(description = "Actual note to replace one in database")
    @NotNull
    private String note;
}
