package com.appartment.facilities.service.impl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.appartment.facilities.constants.MessageConstants;
import com.appartment.facilities.dto.CreateManagerResponseDto;
import com.appartment.facilities.dto.ManagerDto;
import com.appartment.facilities.entity.Manager;
import com.appartment.facilities.exception.ManagerException;
import com.appartment.facilities.repository.ManagerRepository;
import com.appartment.facilities.service.impl.ManagerServiceImpl;

@SpringBootTest
public class ManagerServiceImplTest {

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private ManagerServiceImpl managerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateManager_success() throws ManagerException {
        ManagerDto managerDto = new ManagerDto();
        managerDto.setUserName("testuser");
        managerDto.setPassword("password");
        managerDto.setRole("Manager");
        managerDto.setStatus("Active");
        managerDto.setName("Test User");
        managerDto.setPhone("1234567890");
        managerDto.setEmail("test@example.com");

        Manager manager = new Manager();
        manager.setId(1);
        manager.setUserName("testuser");
        manager.setPassword("password");
        manager.setRole("Manager");
        manager.setStatus("Active");
        manager.setName("Test User");
        manager.setPhone("1234567890");
        manager.setEmail("test@example.com");

        when(managerRepository.findAll()).thenReturn(Collections.emptyList());
        when(managerRepository.save(any(Manager.class))).thenReturn(manager);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        CreateManagerResponseDto response = managerService.createManager(managerDto);

        assertNotNull(response);
        assertEquals(MessageConstants.MANAGER_STATUS_SUCCESS, response.getMessage());
        assertNotNull(response.getManagerDto());
        assertEquals(manager.getId(), response.getManagerDto().getId());
        assertNull(response.getManagerDto().getPassword());

        verify(managerRepository, times(1)).findAll();
        verify(managerRepository, times(1)).save(any(Manager.class));
        verify(passwordEncoder, times(1)).encode(anyString());
    }

    @Test
    void testUpdateManager_success() throws ManagerException {
        int managerId = 1;
        ManagerDto managerDto = new ManagerDto();
        managerDto.setUserName("updatedUser");
        managerDto.setPassword("newPassword");
        managerDto.setRole("Manager");
        managerDto.setStatus("Active");
        managerDto.setName("Updated User");
        managerDto.setPhone("1234567890");
        managerDto.setEmail("updated@example.com");

        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setUserName("testuser");
        manager.setPassword("password");
        manager.setRole("Manager");
        manager.setStatus("Active");
        manager.setName("Test User");
        manager.setPhone("1234567890");
        manager.setEmail("test@example.com");

        when(managerRepository.findById(managerId)).thenReturn(Optional.of(manager));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedNewPassword");
        when(managerRepository.save(any(Manager.class))).thenReturn(manager);

        String response = managerService.updateManager(managerDto, managerId);

        assertNotNull(response);
        assertEquals("manager with id:" + managerId + " is updated successfully.", response);

        verify(managerRepository, times(1)).findById(managerId);
        verify(passwordEncoder, times(1)).encode(managerDto.getPassword());
        verify(managerRepository, times(1)).save(any(Manager.class));
    }


    @Test
    void testGetManager() {
        Manager manager = new Manager();
        manager.setId(1);
        manager.setUserName("testuser");
        manager.setPassword("password");
        manager.setRole("Manager");
        manager.setStatus("Active");
        manager.setName("Test User");
        manager.setPhone("1234567890");
        manager.setEmail("test@example.com");

        when(managerRepository.findAll()).thenReturn(Collections.singletonList(manager));

        ManagerDto managerDto = managerService.getManager();

        assertNotNull(managerDto);
        assertEquals(manager.getId(), managerDto.getId());
        assertNull(managerDto.getPassword());

        verify(managerRepository, times(1)).findAll();
    }

}

