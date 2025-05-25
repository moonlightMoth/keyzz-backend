package ru.moonlightmoth.keyzz_backend.unit.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.moonlightmoth.keyzz_backend.exception.AuthFailException;
import ru.moonlightmoth.keyzz_backend.exception.NoSuchAddressException;
import ru.moonlightmoth.keyzz_backend.model.JwtToken;
import ru.moonlightmoth.keyzz_backend.model.entity.Address;
import ru.moonlightmoth.keyzz_backend.model.entity.Record;
import ru.moonlightmoth.keyzz_backend.model.entity.User;
import ru.moonlightmoth.keyzz_backend.model.request.GetRecordsUpdateRequest;
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
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostRecordsTest {

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
    public void whenPostRecordsBadToken_thenExceptionThrown()
    {
        PostRecordRequest request = PostRecordRequest.builder()
                .build();
        JwtToken jwtToken = new JwtToken("token.token.token");

        doThrow(new AuthFailException("auth fialed")).when(authService).validateUserJwt(jwtToken);

        assertThrows(AuthFailException.class, () -> recordService.postRecord(request, jwtToken));
    }

    @Test
    public void whenPostRecordsBadAddress_thenExceptionThrown()
    {
        PostRecordRequest request = PostRecordRequest.builder()
                .address(Address.builder().id(1L).build())
                .build();
        JwtToken jwtToken = new JwtToken("token.token.token");

        when(addressRepository.findById(1L)).thenThrow(new NoSuchAddressException("NoSuchAddress"));

        assertThrows(NoSuchAddressException.class, () -> recordService.postRecord(request, jwtToken));
    }

    @Test
    public void whenPostRecordsUnknownUser_thenUserSavedAndStatusOk()
    {
        User user = User.builder().id(1L).name("a").surname("b").login("login").build();
        Address address = Address.builder().id(1L).address("address").build();
        PostRecordRequest request = PostRecordRequest.builder()
                .address(address)
                .user(user)
                .note("asd")
                .build();
        JwtToken jwtToken = new JwtToken("token.token.token");
        Record record = Record.builder().id(1L).user(user).note("asd").address(address)
                .timestamp(Timestamp.valueOf(LocalDateTime.of(2000,1,1,1,1))).build();

        when(addressRepository.findById(1L)).thenReturn(Optional.of(Address.builder().id(1L).address("address").build()));
        when(userRepository.findByLogin("login")).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);
        when(recordRepository.save(Mockito.any())).thenReturn(record);

        assertEquals(recordService.postRecord(request, jwtToken).getStatus(), Status.OK);

        verify(userRepository).save(user);

    }
}
