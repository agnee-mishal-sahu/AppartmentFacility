package com.appartment.facilities.service;

import org.springframework.stereotype.Service;

import com.appartment.facilities.dto.CreateManagerResponseDto;
import com.appartment.facilities.dto.ManagerDto;
import com.appartment.facilities.exception.ManagerException;

@Service
public interface ManagerService {
	CreateManagerResponseDto createManager(ManagerDto managerDto) throws ManagerException;
	String updateManager(ManagerDto managerDto,int managerId) throws ManagerException;
	ManagerDto getManager();
}
