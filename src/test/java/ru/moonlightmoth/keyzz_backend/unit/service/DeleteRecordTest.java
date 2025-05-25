package ru.moonlightmoth.keyzz_backend.unit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.moonlightmoth.keyzz_backend.exception.AuthFailException;
import ru.moonlightmoth.keyzz_backend.model.JwtToken;
import ru.moonlightmoth.keyzz_backend.model.request.DeleteRecordRequest;
import ru.moonlightmoth.keyzz_backend.model.request.PostRecordRequest;
import ru.moonlightmoth.keyzz_backend.model.response.Status;
import ru.moonlightmoth.keyzz_backend.repository.AddressRepository;
import ru.moonlightmoth.keyzz_backend.repository.RecordRepository;
import ru.moonlightmoth.keyzz_backend.repository.UserRepository;
import ru.moonlightmoth.keyzz_backend.service.AuthService;
import ru.moonlightmoth.keyzz_backend.service.RecordService;
import ru.moonlightmoth.keyzz_backend.service.RecordServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class DeleteRecordTest {

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
    public void whenDeleteRecordBadToken_thenExceptionThrown()
    {
        DeleteRecordRequest request = DeleteRecordRequest.builder()
                .id(1L)
                .build();
        JwtToken jwtToken = new JwtToken("token.token.token");

        doThrow(new AuthFailException("auth fialed")).when(authService).validateAdminJwt(jwtToken);

        assertThrows(AuthFailException.class, () -> recordService.deleteRecord(request, jwtToken));
    }

    @Test
    public void whenDeleteRecordUserToken_thenExceptionThrown()
    {
        DeleteRecordRequest request = DeleteRecordRequest.builder()
                .id(1L)
                .build();
        JwtToken jwtToken = new JwtToken("token.token.token");

        doThrow(new AuthFailException("auth fialed")).when(authService).validateAdminJwt(jwtToken);

        assertThrows(AuthFailException.class, () -> recordService.deleteRecord(request, jwtToken));
    }

    @Test
    public void whenDeleteRecordAdminToken_thenSendOk()
    {
        DeleteRecordRequest request = DeleteRecordRequest.builder()
                .id(1L)
                .build();
        JwtToken jwtToken = new JwtToken("token.token.token");

        assertEquals(Status.OK, recordService.deleteRecord(request, jwtToken).getStatus());
    }
}
