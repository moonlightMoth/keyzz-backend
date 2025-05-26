package ru.moonlightmoth.keyzz_backend.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.moonlightmoth.keyzz_backend.model.response.*;
import ru.moonlightmoth.keyzz_backend.service.AuthService;
import ru.moonlightmoth.keyzz_backend.model.JwtToken;
import ru.moonlightmoth.keyzz_backend.model.request.*;
import ru.moonlightmoth.keyzz_backend.service.RecordService;

import java.time.LocalDateTime;

@Validated
@RestController
public class KeyzzBackendController {

    @Autowired
    private RecordService recordService;
    @Autowired
    private AuthService authService;

    @Operation(summary = "Get records endpoint", description = "Returns list of records posted after timestamp that must be provided in request params.")
    @ApiResponse(description = "Response status OK", responseCode = "200",
            content = @Content(schema = @Schema(implementation = GetRecordsUpdateResponse.class)))
    @ApiResponse(description = "Response status FAIL", responseCode = "403",
            content = @Content(schema = @Schema(implementation = GetRecordsUpdateResponse.class)))
    @GetMapping("/get-new-records")
    public ResponseEntity<GetRecordsUpdateResponse> getNewRecords(@Schema(description = "Required JWT token minted by keyzz-auth")
                                                                      @RequestHeader("Authorization")
                                                                      String authHeader,
                                                                  @Valid
                                                                  @RequestParam
                                                                  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-ddTHH:mm:ss")
                                                                  @Schema(description = "UTC timestamp in format yyyy-MM-ddTHH:mm:ss representing moment in time to retrieve records from")
                                                                  LocalDateTime timestamp)
    {
        JwtToken jwtToken = JwtToken.builder().token(authHeader).stripBearer().build();
        GetRecordsUpdateRequest request = GetRecordsUpdateRequest.builder().lastUpdatedTimestamp(timestamp).build();
        GetRecordsUpdateResponse response = recordService.getRecords(request, jwtToken);

        if (response.getStatus() == Status.FAIL)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Post record endpoint", description = "Posting is allowed for any user provided with JWT")
    @ApiResponse(description = "Response status OK", responseCode = "200",
            content = @Content(schema = @Schema(implementation = PostRecordResponse.class)))
    @ApiResponse(description = "Response status FAIL", responseCode = "403",
            content = @Content(schema = @Schema(implementation = PostRecordResponse.class)))
    @PostMapping("/post-record")
    public ResponseEntity<?> postRecord(@Schema(description = "Required JWT token minted by keyzz-auth")
                                            @RequestHeader("Authorization")
                                            String authHeader,
                                        @Valid
                                        @RequestBody
                                        PostRecordRequest postRecordRequest)
    {
        JwtToken jwtToken = JwtToken.builder().token(authHeader).stripBearer().build();

        PostRecordResponse response = recordService.postRecord(postRecordRequest, jwtToken);

        if (response.getStatus() == Status.FAIL)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Patch record endpoint", description = "Patching allowed for user only if record is posted by them and only within 3 hours from posting. " +
            "Admin may delete any record.")
    @ApiResponse(description = "Response status OK", responseCode = "200",
            content = @Content(schema = @Schema(implementation = PatchRecordResponse.class)))
    @ApiResponse(description = "Response status FAIL", responseCode = "403",
            content = @Content(schema = @Schema(implementation = PatchRecordResponse.class)))
    @PatchMapping("/patch-record")
    public ResponseEntity<?> patchRecord(@Schema(description = "Required JWT token minted by keyzz-auth")
                                             @RequestHeader("Authorization")
                                             String authHeader,
                                         @Valid
                                         @RequestBody
                                         PatchRecordRequest patchRecordRequest)
    {
        JwtToken jwtToken = JwtToken.builder().token(authHeader).stripBearer().build();

        PatchRecordResponse response = recordService.patchRecord(patchRecordRequest, jwtToken);

        if (response.getStatus() == Status.FAIL)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Deletion of record endpoint", description = "May be used by admin only, so admin jwt required")
    @ApiResponse(description = "Response status OK", responseCode = "200",
            content = @Content(schema = @Schema(implementation = DeleteRecordResponse.class)))
    @ApiResponse(description = "Response status FAIL if user is not admin", responseCode = "403",
            content = @Content(schema = @Schema(implementation = DeleteRecordResponse.class)))
    @DeleteMapping("/delete-record")
    public ResponseEntity<?> deleteRecord(@Schema(description = "Required ADMIN JWT token minted by keyzz-auth")
                                              @RequestHeader("Authorization")
                                              String authHeader,
                                          @Valid
                                          @RequestBody
                                          DeleteRecordRequest deleteRecordRequest)
    {
        JwtToken jwtToken = JwtToken.builder().token(authHeader).stripBearer().build();
        DeleteRecordResponse response = recordService.deleteRecord(deleteRecordRequest, jwtToken);

        if (response.getStatus() == Status.FAIL)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);

        return ResponseEntity.ok(response);
    }

}
