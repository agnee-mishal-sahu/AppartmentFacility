package com.appartment.facilities.service.impl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.appartment.facilities.constants.MessageConstants;
import com.appartment.facilities.constants.ValidationConstants;
import com.appartment.facilities.dto.CreateResidentResponseDto;
import com.appartment.facilities.dto.ResidentDto;
import com.appartment.facilities.entity.Resident;
import com.appartment.facilities.exception.ResidentException;
import com.appartment.facilities.repository.ResidentRepository;
import com.appartment.facilities.service.impl.ResisdentServiceImpl;

@SpringBootTest
public class ResisdentServiceImplTest {

    @Mock
    private ResidentRepository residentRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private ResisdentServiceImpl residentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateResident_success() throws ResidentException {
        ResidentDto residentDto = new ResidentDto();
        residentDto.setUserName("user1");
        residentDto.setPassword("password");
        residentDto.setEmail("user1@example.com");
        residentDto.setPhone("1234567890");

        Resident resident = new Resident();
        resident.setId(1);
        resident.setUserName("user1");
        resident.setPassword("encodedPassword");
        resident.setEmail("user1@example.com");
        resident.setPhone("1234567890");

        when(passwordEncoder.encode(residentDto.getPassword())).thenReturn("encodedPassword");
        when(residentRepository.save(any(Resident.class))).thenReturn(resident);

        CreateResidentResponseDto response = residentService.createResident(residentDto);

        assertNotNull(response);
        assertEquals(MessageConstants.RESIDENT_STATUS_SUCCESS, response.getMessage());
        assertNotNull(response.getResidentDto());
        assertEquals(resident.getId(), response.getResidentDto().getId());

        verify(residentRepository, times(1)).save(any(Resident.class));
    }

    @Test
    void testCreateResident_invalidEmail() {
        ResidentDto residentDto = new ResidentDto();
        residentDto.setUserName("user1");
        residentDto.setPassword("password");
        residentDto.setEmail("invalid-email");
        residentDto.setPhone("1234567890");

        ResidentException exception = assertThrows(ResidentException.class, () -> {
            residentService.createResident(residentDto);
        });

        assertEquals(ValidationConstants.INVALID_EMAIL, exception.getMessage());
    }

    @Test
    void testCreateResident_invalidPhone() {
        ResidentDto residentDto = new ResidentDto();
        residentDto.setUserName("user1");
        residentDto.setPassword("password");
        residentDto.setEmail("user1@example.com");
        residentDto.setPhone("123");

        ResidentException exception = assertThrows(ResidentException.class, () -> {
            residentService.createResident(residentDto);
        });

        assertEquals(ValidationConstants.INVALID_PHONE, exception.getMessage());
    }

    @Test
    void testUpdateResident_success() throws ResidentException {
        int residentId = 1;
        ResidentDto residentDto = new ResidentDto();
        residentDto.setUserName("updatedUser");
        residentDto.setPassword("updatedPassword");
        residentDto.setEmail("updated@example.com");
        residentDto.setPhone("0987654321");

        Resident resident = new Resident();
        resident.setId(residentId);
        resident.setUserName("user1");
        resident.setPassword("encodedPassword");
        resident.setEmail("user1@example.com");
        resident.setPhone("1234567890");

        when(residentRepository.findById(residentId)).thenReturn(Optional.of(resident));
        when(passwordEncoder.encode(residentDto.getPassword())).thenReturn("encodedUpdatedPassword");
        when(residentRepository.save(any(Resident.class))).thenReturn(resident);

        String response = residentService.UpdateResident(residentDto, residentId);

        assertNotNull(response);
        assertEquals("resident with id:" + residentId + " is updated successfully.", response);

        verify(residentRepository, times(1)).findById(residentId);
        verify(passwordEncoder, times(1)).encode(residentDto.getPassword());
        verify(residentRepository, times(1)).save(any(Resident.class));
    }

    @Test
    void testUpdateResident_notFound() {
        int residentId = 1;
        ResidentDto residentDto = new ResidentDto();

        when(residentRepository.findById(residentId)).thenReturn(Optional.empty());

        ResidentException exception = assertThrows(ResidentException.class, () -> {
            residentService.UpdateResident(residentDto, residentId);
        });

        assertEquals(MessageConstants.RESIDENT_NOT_FOUND, exception.getMessage());
        verify(residentRepository, times(1)).findById(residentId);
    }

    @Test
    void testDeleteResident_success() throws ResidentException {
        int residentId = 1;
        Resident resident = new Resident();
        resident.setId(residentId);

        when(residentRepository.findById(residentId)).thenReturn(Optional.of(resident));

        String response = residentService.deleteResident(residentId);

        assertNotNull(response);
        assertEquals("resident with id:" + residentId + " is deleted successfully.", response);

        verify(residentRepository, times(1)).findById(residentId);
        verify(residentRepository, times(1)).deleteById(residentId);
    }

    @Test
    void testDeleteResident_notFound() {
        int residentId = 1;

        when(residentRepository.findById(residentId)).thenReturn(Optional.empty());

        ResidentException exception = assertThrows(ResidentException.class, () -> {
            residentService.deleteResident(residentId);
        });

        assertEquals(MessageConstants.RESIDENT_NOT_FOUND, exception.getMessage());
        verify(residentRepository, times(1)).findById(residentId);
    }

    @Test
    void testGetAllResident() {
        Resident resident = new Resident();
        resident.setId(1);
        resident.setUserName("user1");
        resident.setPassword("encodedPassword");
        resident.setEmail("user1@example.com");
        resident.setPhone("1234567890");

        when(residentRepository.findAll()).thenReturn(Collections.singletonList(resident));

        List<ResidentDto> residents = residentService.getAllResident();

        assertNotNull(residents);
        assertEquals(1, residents.size());
        assertEquals(resident.getId(), residents.get(0).getId());

        verify(residentRepository, times(1)).findAll();
    }


    @Test
    void testGetResidentById_notFound() {
        int residentId = 1;

        when(residentRepository.findById(residentId)).thenReturn(Optional.empty());

        ResidentException exception = assertThrows(ResidentException.class, () -> {
            residentService.getResidentById(residentId);
        });

        assertEquals(MessageConstants.RESIDENT_NOT_FOUND, exception.getMessage());
        verify(residentRepository, times(1)).findById(residentId);
    }
}

