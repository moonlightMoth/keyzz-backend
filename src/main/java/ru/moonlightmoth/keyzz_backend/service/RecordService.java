package ru.moonlightmoth.keyzz_backend.service;

import org.springframework.stereotype.Service;
import ru.moonlightmoth.keyzz_backend.model.JwtToken;
import ru.moonlightmoth.keyzz_backend.model.entity.Record;
import ru.moonlightmoth.keyzz_backend.model.request.DeleteRecordRequest;
import ru.moonlightmoth.keyzz_backend.model.request.PatchRecordRequest;
import ru.moonlightmoth.keyzz_backend.model.request.PostRecordRequest;
import ru.moonlightmoth.keyzz_backend.model.request.GetRecordsUpdateRequest;
import ru.moonlightmoth.keyzz_backend.model.response.DeleteRecordResponse;
import ru.moonlightmoth.keyzz_backend.model.response.GetRecordsUpdateResponse;
import ru.moonlightmoth.keyzz_backend.model.response.PatchRecordResponse;
import ru.moonlightmoth.keyzz_backend.model.response.PostRecordResponse;

import java.util.List;

@Service
public interface RecordService {

    GetRecordsUpdateResponse getNewRecords(GetRecordsUpdateRequest getRecordsUpdateRequest, JwtToken jwtToken);

    DeleteRecordResponse deleteRecordById(DeleteRecordRequest deleteRecordRequest, JwtToken jwtToken);

    PatchRecordResponse patchRecord(PatchRecordRequest patchRecordRequest, JwtToken jwtToken);

    PostRecordResponse postRecord(PostRecordRequest postRecordRequest, JwtToken jwtToken);


}
