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

import com.appartment.facilities.constants.MessageConstants;
import com.appartment.facilities.constants.ValidationConstants;
import com.appartment.facilities.dto.CreateFacilityResponseDto;
import com.appartment.facilities.dto.FacilityDto;
import com.appartment.facilities.entity.Facility;
import com.appartment.facilities.exception.FacilityException;
import com.appartment.facilities.repository.FacilityRepository;
import com.appartment.facilities.service.impl.FacilityServiceImpl;

@SpringBootTest
public class FacilityServiceImplTest {

    @Mock
    private FacilityRepository facilityRepository;

    @InjectMocks
    private FacilityServiceImpl facilityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFacility_success() throws FacilityException {
        FacilityDto facilityDto = new FacilityDto();
        facilityDto.setName("Gym");
        facilityDto.setDescription("Gym facility");
        facilityDto.setPicture("gym.jpg");

        Facility facility = new Facility();
        facility.setId(1);
        facility.setName("Gym");
        facility.setDescription("Gym facility");
        facility.setPicture("gym.jpg");
        facility.setStatus("Available");

        when(facilityRepository.save(any(Facility.class))).thenReturn(facility);

        CreateFacilityResponseDto response = facilityService.createFacility(facilityDto);

        assertNotNull(response);
        assertEquals(MessageConstants.FACILITY_STATUS_SUCCESS, response.getMessage());
        assertNotNull(response.getFacilityDto());
        assertEquals(facility.getId(), response.getFacilityDto().getId());
        assertEquals(facility.getStatus(), response.getFacilityDto().getStatus());

        verify(facilityRepository, times(1)).save(any(Facility.class));
    }

    @Test
    void testCreateFacility_invalidFacilityName() {
        FacilityDto facilityDto = new FacilityDto();
        facilityDto.setName("");
        facilityDto.setDescription("Gym facility");

        FacilityException exception = assertThrows(FacilityException.class, () -> {
            facilityService.createFacility(facilityDto);
        });

        assertEquals(ValidationConstants.INVALID_FACILITY_NAME, exception.getMessage());
    }

    @Test
    void testUpdateFacility_success() throws FacilityException {
        int facilityId = 1;
        FacilityDto facilityDto = new FacilityDto();
        facilityDto.setName("Updated Gym");
        facilityDto.setDescription("Updated description");
        facilityDto.setPicture("updated_gym.jpg");

        Facility facility = new Facility();
        facility.setId(facilityId);
        facility.setName("Gym");
        facility.setDescription("Gym facility");
        facility.setPicture("gym.jpg");

        when(facilityRepository.findById(facilityId)).thenReturn(Optional.of(facility));
        when(facilityRepository.save(any(Facility.class))).thenReturn(facility);

        String response = facilityService.updateFacility(facilityDto, facilityId);

        assertNotNull(response);
        assertEquals("Facility with id:" + facilityId + " is updated successfully.", response);

        verify(facilityRepository, times(1)).findById(facilityId);
        verify(facilityRepository, times(1)).save(any(Facility.class));
    }

    @Test
    void testUpdateFacility_notFound() {
        int facilityId = 1;
        FacilityDto facilityDto = new FacilityDto();

        when(facilityRepository.findById(facilityId)).thenReturn(Optional.empty());

        FacilityException exception = assertThrows(FacilityException.class, () -> {
            facilityService.updateFacility(facilityDto, facilityId);
        });

        assertEquals(MessageConstants.FACILITY_NOT_FOUND, exception.getMessage());
        verify(facilityRepository, times(1)).findById(facilityId);
    }

    @Test
    void testDeleteFacility_success() throws FacilityException {
        int facilityId = 1;
        Facility facility = new Facility();
        facility.setId(facilityId);

        when(facilityRepository.findById(facilityId)).thenReturn(Optional.of(facility));

        String response = facilityService.deleteFacility(facilityId);

        assertNotNull(response);
        assertEquals("Facility with id:" + facilityId + " is deleted.", response);

        verify(facilityRepository, times(1)).findById(facilityId);
        verify(facilityRepository, times(1)).deleteById(facilityId);
    }

    @Test
    void testDeleteFacility_notFound() {
        int facilityId = 1;

        when(facilityRepository.findById(facilityId)).thenReturn(Optional.empty());

        FacilityException exception = assertThrows(FacilityException.class, () -> {
            facilityService.deleteFacility(facilityId);
        });

        assertEquals(MessageConstants.FACILITY_NOT_FOUND, exception.getMessage());
        verify(facilityRepository, times(1)).findById(facilityId);
    }

    @Test
    void testGetAllFacility() {
        Facility facility = new Facility();
        facility.setId(1);
        facility.setName("Gym");
        facility.setDescription("Gym facility");
        facility.setPicture("gym.jpg");

        when(facilityRepository.findAll()).thenReturn(Collections.singletonList(facility));

        List<FacilityDto> facilities = facilityService.getAllFacility();

        assertNotNull(facilities);
        assertEquals(1, facilities.size());
        assertEquals(facility.getId(), facilities.get(0).getId());

        verify(facilityRepository, times(1)).findAll();
    }

    @Test
    void testGetFacilityById_success() throws FacilityException {
        int facilityId = 1;
        Facility facility = new Facility();
        facility.setId(facilityId);
        facility.setName("Gym");
        facility.setDescription("Gym facility");
        facility.setPicture("gym.jpg");

        when(facilityRepository.findById(facilityId)).thenReturn(Optional.of(facility));

        FacilityDto facilityDto = facilityService.getFacilityById(facilityId);

        assertNotNull(facilityDto);
        assertEquals(facility.getId(), facilityDto.getId());

        verify(facilityRepository, times(1)).findById(facilityId);
    }

    @Test
    void testGetFacilityById_notFound() {
        int facilityId = 1;

        when(facilityRepository.findById(facilityId)).thenReturn(Optional.empty());

        FacilityException exception = assertThrows(FacilityException.class, () -> {
            facilityService.getFacilityById(facilityId);
        });

        assertEquals(MessageConstants.FACILITY_NOT_FOUND, exception.getMessage());
        verify(facilityRepository, times(1)).findById(facilityId);
    }

    @Test
    void testChangeStatusToAvailable_success() throws FacilityException {
        int facilityId = 1;
        Facility facility = new Facility();
        facility.setId(facilityId);
        facility.setStatus("Occupied");

        when(facilityRepository.findById(facilityId)).thenReturn(Optional.of(facility));
        when(facilityRepository.save(any(Facility.class))).thenReturn(facility);

        String response = facilityService.changeStatusToAvailable(facilityId);

        assertNotNull(response);
        assertEquals("Facility with id:" + facilityId + " is available now.", response);

        verify(facilityRepository, times(1)).findById(facilityId);
        verify(facilityRepository, times(1)).save(any(Facility.class));
    }

    @Test
    void testChangeStatusToAvailable_alreadyAvailable() {
        int facilityId = 1;
        Facility facility = new Facility();
        facility.setId(facilityId);
        facility.setStatus("Available");

        when(facilityRepository.findById(facilityId)).thenReturn(Optional.of(facility));

        FacilityException exception = assertThrows(FacilityException.class, () -> {
            facilityService.changeStatusToAvailable(facilityId);
        });

        assertEquals(MessageConstants.FACILITY_ALREADY_AVAILABLE, exception.getMessage());
        verify(facilityRepository, times(1)).findById(facilityId);
    }

    @Test
    void testChangeStatusToAvailable_notFound() {
        int facilityId = 1;

        when(facilityRepository.findById(facilityId)).thenReturn(Optional.empty());

        FacilityException exception = assertThrows(FacilityException.class, () -> {
            facilityService.changeStatusToAvailable(facilityId);
        });

        assertEquals(MessageConstants.FACILITY_NOT_FOUND, exception.getMessage());
        verify(facilityRepository, times(1)).findById(facilityId);
    }
}

