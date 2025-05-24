package ru.moonlightmoth.keyzz_backend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.moonlightmoth.keyzz_backend.service.AuthService;
import ru.moonlightmoth.keyzz_backend.model.JwtToken;
import ru.moonlightmoth.keyzz_backend.model.request.*;
import ru.moonlightmoth.keyzz_backend.service.RecordService;

@Validated
@RestController
public class KeyzzBackendController {

    @Autowired
    private RecordService recordService;
    @Autowired
    private AuthService authService;

    @GetMapping("/get-new-records")
    public ResponseEntity<?> getNewRecords(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody GetRecordsUpdateRequest getRecordsUpdateRequest) //ResponseEntity<RecordUpdateResponse>
    {
        JwtToken jwtToken = JwtToken.builder().token(authHeader).stripBearer().build();
        return ResponseEntity.ok(recordService.getRecords(getRecordsUpdateRequest, jwtToken));
    }

    @PostMapping("/post-record")
    public ResponseEntity<?> postRecord(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody PostRecordRequest postRecordRequest)
    {
        JwtToken jwtToken = JwtToken.builder().token(authHeader).stripBearer().build();
        return ResponseEntity.ok(recordService.postRecord(postRecordRequest, jwtToken));
    }

    @PatchMapping("/patch-record")
    public ResponseEntity<?> patchRecord(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody PatchRecordRequest patchRecordRequest)
    {
        JwtToken jwtToken = JwtToken.builder().token(authHeader).stripBearer().build();
        return ResponseEntity.ok(recordService.patchRecord(patchRecordRequest, jwtToken));
    }

    @DeleteMapping("/delete-record")
    public ResponseEntity<?> deleteRecord(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody DeleteRecordRequest deleteRecordRequest)
    {
        JwtToken jwtToken = JwtToken.builder().token(authHeader).stripBearer().build();
        return ResponseEntity.ok(recordService.deleteRecord(deleteRecordRequest, jwtToken));
    }

}
