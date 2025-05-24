package ru.moonlightmoth.keyzz_backend.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to delete record by id")
public class DeleteRecordRequest {

    @Min(1)
    @JsonProperty("id")
    @Schema(description = "Id of record to delete")
    private Long id;
}
