package ru.moonlightmoth.keyzz_backend.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moonlightmoth.keyzz_backend.model.entity.Record;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response to request for new records")
public class GetRecordsUpdateResponse {

    @NotNull
    @Schema(description = "Status field. Must be OK or FAIL")
    private Status status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Min(1)
    @NotNull
    @Schema(description = "Timestamp representing moment in time which client wants get records from")
    private LocalDateTime lastUpdatedTimestamp;

    @NotNull
    @Schema(description = "Actual records")
    private List<Record> records;

    @Schema(description = "Number of records")
    private int numOfRecords;

    @Schema(description = "Message for clarity")
    private String message;
}
