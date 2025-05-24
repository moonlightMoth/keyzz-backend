package ru.moonlightmoth.keyzz_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.moonlightmoth.keyzz_backend.exception.NoSuchAddressException;
import ru.moonlightmoth.keyzz_backend.exception.NoSuchRecordException;
import ru.moonlightmoth.keyzz_backend.exception.NoSuchUserRecordedException;
import ru.moonlightmoth.keyzz_backend.exception.UnauthorisedPatchException;
import ru.moonlightmoth.keyzz_backend.model.JwtToken;
import ru.moonlightmoth.keyzz_backend.model.entity.Address;
import ru.moonlightmoth.keyzz_backend.model.entity.Record;
import ru.moonlightmoth.keyzz_backend.model.entity.User;
import ru.moonlightmoth.keyzz_backend.model.request.DeleteRecordRequest;
import ru.moonlightmoth.keyzz_backend.model.request.GetRecordsUpdateRequest;
import ru.moonlightmoth.keyzz_backend.model.request.PatchRecordRequest;
import ru.moonlightmoth.keyzz_backend.model.request.PostRecordRequest;
import ru.moonlightmoth.keyzz_backend.model.response.*;
import ru.moonlightmoth.keyzz_backend.repository.AddressRepository;
import ru.moonlightmoth.keyzz_backend.repository.RecordRepository;
import ru.moonlightmoth.keyzz_backend.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecordServiceImpl implements RecordService{

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Override
    public GetRecordsUpdateResponse getRecords(GetRecordsUpdateRequest getRecordsUpdateRequest, JwtToken jwtToken)
    {
        authService.validateUserJwt(jwtToken);

        List<Record> records = recordRepository.findByTimestampGreaterThan(Timestamp.valueOf(getRecordsUpdateRequest.getLastUpdatedTimestamp()));

        return GetRecordsUpdateResponse.builder()
                .lastUpdatedTimestamp(getRecordsUpdateRequest.getLastUpdatedTimestamp())
                .records(records)
                .message("OK")
                .status(Status.OK)
                .build();
    }

    @Override
    public DeleteRecordResponse deleteRecord(DeleteRecordRequest deleteRecordRequest, JwtToken jwtToken)
    {
        authService.validateAdminJwt(jwtToken);

        recordRepository.deleteById(deleteRecordRequest.getId());

        return DeleteRecordResponse.builder()
                .message("Record deleted")
                .status(Status.OK)
                .build();
    }

    @Override
    public PatchRecordResponse patchRecord(PatchRecordRequest patchRecordRequest, JwtToken jwtToken)
    {
        User user = authService.validateUserJwtForUser(jwtToken);
        Optional<Record> recordOptional = recordRepository.findById(patchRecordRequest.getId());

        if (recordOptional.isEmpty())
            throw new NoSuchRecordException("Id of record provided to patch but no such record found");

        Record actualRecord = recordOptional.get();

        boolean isAdmin = false;

        if (!(actualRecord.getUser() != null && actualRecord.getUser().equals(user) || (isAdmin = authService.isTokenAdmins(jwtToken))))
            throw new UnauthorisedPatchException("Record is not posted by user trying to patch or record is primal");

        if (!isAdmin && actualRecord.getTimestamp().toLocalDateTime().plusDays(3).isBefore(LocalDateTime.now()))
            throw new UnauthorisedPatchException("Non admin user can edit records only in 3 days period after posting");

        Record updatedRecord = Record.builder()
                .id(actualRecord.getId())
                .user(actualRecord.getUser())
                .address(actualRecord.getAddress())
                .note(patchRecordRequest.getNote())
                .timestamp(actualRecord.getTimestamp())
                .build();

        recordRepository.save(updatedRecord);

        return PatchRecordResponse.builder()
                .status(Status.OK)
                .message("Record updated")
                .build();
    }

    @Override
    public PostRecordResponse postRecord(PostRecordRequest postRecordRequest, JwtToken jwtToken)
    {
        authService.validateUserJwt(jwtToken);

        Address address = checkAndGetAddress(postRecordRequest.getAddress());
        User user = checkAndGetUser(postRecordRequest.getUser());

        Record record = recordRepository.save(Record.builder()
                .address(address)
                .note(postRecordRequest.getNote())
                .user(user)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build());

        return PostRecordResponse.builder()
                .id(record.getId())
                .message("Posted successfully")
                .status(Status.OK)
                .build();
    }

    private Address checkAndGetAddress(Address incomingAddress)
    {
        Optional<Address> addressOptional = addressRepository.findById(incomingAddress.getId());

        if (addressOptional.isEmpty())
            throw new NoSuchAddressException("No address with such id");

        if (!addressOptional.get().getAddress().equals(incomingAddress.getAddress()))
            throw new NoSuchAddressException("No address with such name");

        return addressOptional.get();
    }

    private User checkAndGetUser(User incomingUser)
    {
        Optional<User> userOptional = userRepository.findByLogin(incomingUser.getLogin());

        if (userOptional.isEmpty())
            return userRepository.save(incomingUser);

        return userOptional.get();
    }
}
