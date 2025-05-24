package ru.moonlightmoth.keyzz_backend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.moonlightmoth.keyzz_backend.exception.AuthFailException;
import ru.moonlightmoth.keyzz_backend.model.JwtToken;
import ru.moonlightmoth.keyzz_backend.model.entity.Record;
import ru.moonlightmoth.keyzz_backend.model.request.GetRecordsUpdateRequest;
import ru.moonlightmoth.keyzz_backend.model.response.Status;
import ru.moonlightmoth.keyzz_backend.repository.RecordRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GetRecordsTests {

    @Mock
    private AuthService authService;

    @Mock
    private RecordRepository recordRepository;

    @InjectMocks
    private RecordService recordService = new RecordServiceImpl();

    @Test
    public void whenGetRecordsBadToken_thenExceptionThrown()
    {
        GetRecordsUpdateRequest request = GetRecordsUpdateRequest.builder()
                .lastUpdatedTimestamp(LocalDateTime.of(2000,1,1,1,1))
                .build();
        JwtToken jwtToken = new JwtToken("token.token.token");

        doThrow(new AuthFailException("auth fialed")).when(authService).validateUserJwt(jwtToken);

        assertThrows(AuthFailException.class, () -> recordService.getRecords(request, jwtToken));
    }

    @Test
    public void whenGetRecordsGoodToken_thenReturnOK()
    {
        GetRecordsUpdateRequest request = GetRecordsUpdateRequest.builder()
                .lastUpdatedTimestamp(LocalDateTime.of(2000,1,1,1,1))
                .build();
        JwtToken jwtToken = new JwtToken("token.token.token");

//        when(recordRepository.findByTimestampGreaterThan(Timestamp.valueOf(request.getLastUpdatedTimestamp())))
//                .thenReturn(List.of(Record.builder().id(1L).build()));


        assertEquals(Status.OK, recordService.getRecords(request, jwtToken).getStatus());
    }


}
