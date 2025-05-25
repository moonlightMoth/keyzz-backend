package ru.moonlightmoth.keyzz_backend.unit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.moonlightmoth.keyzz_backend.exception.AuthFailException;
import ru.moonlightmoth.keyzz_backend.exception.UnauthorisedPatchException;
import ru.moonlightmoth.keyzz_backend.model.JwtToken;
import ru.moonlightmoth.keyzz_backend.model.entity.Record;
import ru.moonlightmoth.keyzz_backend.model.entity.User;
import ru.moonlightmoth.keyzz_backend.model.request.PatchRecordRequest;
import ru.moonlightmoth.keyzz_backend.model.request.PostRecordRequest;
import ru.moonlightmoth.keyzz_backend.model.response.Status;
import ru.moonlightmoth.keyzz_backend.repository.AddressRepository;
import ru.moonlightmoth.keyzz_backend.repository.RecordRepository;
import ru.moonlightmoth.keyzz_backend.repository.UserRepository;
import ru.moonlightmoth.keyzz_backend.service.AuthService;
import ru.moonlightmoth.keyzz_backend.service.RecordService;
import ru.moonlightmoth.keyzz_backend.service.RecordServiceImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PatchRecordsTest {

    @Mock
    private AuthService authService;

    @Mock
    private RecordRepository recordRepository;

    @Mock
    private AddressRepository addressRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RecordService recordService = new RecordServiceImpl();


    @Test
    public void whenPatchRecordsBadToken_thenExceptionThrown()
    {
        PatchRecordRequest request = PatchRecordRequest.builder()
                .build();
        JwtToken jwtToken = new JwtToken("token.token.token");

        doThrow(new AuthFailException("auth fialed")).when(authService).validateUserJwtForUser(jwtToken);

        assertThrows(AuthFailException.class, () -> recordService.patchRecord(request, jwtToken));
    }

    @Test
    public void whenPatchRecordsPrimalRecordAndUserToken_thenExceptionThrown()
    {
        PatchRecordRequest request = PatchRecordRequest.builder()
                .id(1L)
                .note("asd")
                .build();
        JwtToken jwtToken = new JwtToken("token.token.token");
        Record primalRecord = Record.builder().user(null).build();

        when(recordRepository.findById(1L)).thenReturn(Optional.of(primalRecord));
        when(authService.isTokenAdmins(jwtToken)).thenReturn(false);

        assertThrows(UnauthorisedPatchException.class, () -> recordService.patchRecord(request, jwtToken));
    }

    @Test
    public void whenPatchRecordsPrimalRecordAndAdminToken_thenSendOk()
    {
        PatchRecordRequest request = PatchRecordRequest.builder()
                .id(1L)
                .note("asd")
                .build();
        JwtToken jwtToken = new JwtToken("token.token.token");
        Record primalRecord = Record.builder().user(null).build();

        when(recordRepository.findById(1L)).thenReturn(Optional.of(primalRecord));
        when(authService.isTokenAdmins(jwtToken)).thenReturn(true);

        assertEquals(Status.OK, recordService.patchRecord(request, jwtToken).getStatus());
    }

    @Test
    public void whenPatchRecordsUserRecordAndUserTokenAndLessThanThreeDays_thenSendOk()
    {
        PatchRecordRequest request = PatchRecordRequest.builder()
                .id(1L)
                .note("asd")
                .build();
        JwtToken jwtToken = new JwtToken("token.token.token");
        User user = User.builder().login("a").name("b").surname("c").build();
        Record userRecord = Record.builder().user(user).timestamp(Timestamp.valueOf(LocalDateTime.now().minusDays(1))).build();

        when(recordRepository.findById(1L)).thenReturn(Optional.of(userRecord));
        when(authService.validateUserJwtForUser(jwtToken)).thenReturn(user);
        when(authService.isTokenAdmins(jwtToken)).thenReturn(false);

        assertEquals(Status.OK, recordService.patchRecord(request, jwtToken).getStatus());
    }

    @Test
    public void whenPatchRecordsUserRecordAndUserTokenAndMoreThanThreeDays_thenThrowsException()
    {
        PatchRecordRequest request = PatchRecordRequest.builder()
                .id(1L)
                .note("asd")
                .build();
        JwtToken jwtToken = new JwtToken("token.token.token");
        User user = User.builder().login("a").name("b").surname("c").build();
        Record userRecord = Record.builder().user(user).timestamp(Timestamp.valueOf(LocalDateTime.now().minusDays(10))).build();

        when(recordRepository.findById(1L)).thenReturn(Optional.of(userRecord));
        when(authService.isTokenAdmins(jwtToken)).thenReturn(false);

        assertThrows(UnauthorisedPatchException.class, () -> recordService.patchRecord(request, jwtToken));
    }

    @Test
    public void whenPatchRecordsUserRecordAndAdminTokenAndMoreThanThreeDays_thenSendOk()
    {
        PatchRecordRequest request = PatchRecordRequest.builder()
                .id(1L)
                .note("asd")
                .build();
        JwtToken jwtToken = new JwtToken("token.token.token");
        User user = User.builder().login("a").name("b").surname("c").build();
        Record userRecord = Record.builder().user(user).timestamp(Timestamp.valueOf(LocalDateTime.now().minusDays(10))).build();

        when(recordRepository.findById(1L)).thenReturn(Optional.of(userRecord));
        when(authService.validateUserJwtForUser(jwtToken)).thenReturn(user);
        when(authService.isTokenAdmins(jwtToken)).thenReturn(true);

        assertEquals(Status.OK, recordService.patchRecord(request, jwtToken).getStatus());
    }

    @Test
    public void whenPatchRecordsUserRecordAndAdminTokenAndLessThanThreeDays_thenSendOk()
    {
        PatchRecordRequest request = PatchRecordRequest.builder()
                .id(1L)
                .note("asd")
                .build();
        JwtToken jwtToken = new JwtToken("token.token.token");
        User user = User.builder().login("a").name("b").surname("c").build();
        Record userRecord = Record.builder().user(user).timestamp(Timestamp.valueOf(LocalDateTime.now().minusDays(1))).build();

        when(recordRepository.findById(1L)).thenReturn(Optional.of(userRecord));
        when(authService.validateUserJwtForUser(jwtToken)).thenReturn(user);
        when(authService.isTokenAdmins(jwtToken)).thenReturn(false);

        assertEquals(Status.OK, recordService.patchRecord(request, jwtToken).getStatus());
    }

}
