package com.appartment.facilities.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.appartment.facilities.constants.MessageConstants;
import com.appartment.facilities.constants.ValidationConstants;
import com.appartment.facilities.dto.CreateManagerResponseDto;
import com.appartment.facilities.dto.ManagerDto;
import com.appartment.facilities.entity.Manager;
import com.appartment.facilities.exception.ManagerException;
import com.appartment.facilities.repository.ManagerRepository;
import com.appartment.facilities.service.ManagerService;

@Service
public class ManagerServiceImpl implements ManagerService {

	@Autowired
	ManagerRepository managerRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public CreateManagerResponseDto createManager(ManagerDto managerDto) throws ManagerException {

		if (managerRepository.findAll().size() == 1) {
			throw new ManagerException(MessageConstants.MANAGER_EXISTS);
		}

		Manager manager = mapToManager(managerDto);
		CreateManagerResponseDto createManagerResponseDto = new CreateManagerResponseDto();

		if (manager != null) {
			if(validateManager(manager)) {
				Manager managerEntity = managerRepository.save(manager);
				createManagerResponseDto.setManagerDto(managerDto);
				createManagerResponseDto.getManagerDto().setPassword(null);
				createManagerResponseDto.getManagerDto().setId(managerEntity.getId());
				createManagerResponseDto.setMessage(MessageConstants.MANAGER_STATUS_SUCCESS);
			}
		} else {
			createManagerResponseDto.setMessage(MessageConstants.MANAGER_STATUS_FAILED);
		}

		return createManagerResponseDto;
	}

	@Override
	public String updateManager(ManagerDto managerDto, int managerId) throws ManagerException {
		Optional<Manager> managerOptional = managerRepository.findById(managerId);

		if (!managerOptional.isPresent()) {
			throw new ManagerException(MessageConstants.MANAGER_NOT_FOUND);
		}
		Manager manager = managerOptional.get();

		manager.setUserName(managerDto.getUserName());
		manager.setPassword(passwordEncoder.encode(managerDto.getPassword()));
		manager.setRole(managerDto.getRole());
		manager.setStatus(managerDto.getStatus());
		manager.setName(managerDto.getName());
		manager.setPhone(managerDto.getPhone());
		manager.setEmail(managerDto.getEmail());

		managerRepository.save(manager);

		return "manager with id:" + managerId + " is updated successfully.";
	}

	@Override
	public ManagerDto getManager() {

		return managerRepository.findAll().stream().map(e -> mapToManagerDto(e)).findFirst().get();
	}

	private Manager mapToManager(ManagerDto managerDto) {
		if (managerDto == null || managerDto.getUserName().isBlank() || managerDto.getPassword().isBlank()) {
			return null;
		}
		Manager manager = new Manager();
		manager.setId(managerDto.getId());
		manager.setUserName(managerDto.getUserName());
		manager.setPassword(passwordEncoder.encode(managerDto.getPassword()));
		manager.setRole(managerDto.getRole());
		manager.setStatus(managerDto.getStatus());
		manager.setName(managerDto.getName());
		manager.setPhone(managerDto.getPhone());
		manager.setEmail(managerDto.getEmail());
		return manager;
	}

	private ManagerDto mapToManagerDto(Manager manager) {
		ManagerDto managerDto = new ManagerDto();
		if (manager != null) {
			managerDto.setId(manager.getId());
			managerDto.setUserName(manager.getUserName());
			managerDto.setPassword(null);
			managerDto.setRole(manager.getRole());
			managerDto.setStatus(manager.getStatus());
			managerDto.setName(manager.getName());
			managerDto.setPhone(manager.getPhone());
			managerDto.setEmail(manager.getEmail());
		}
		return managerDto;
	}
	
	private boolean validateManager(Manager manager) throws ManagerException {
		String emailRegex = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
		String phoneRegex = "^\\d{10}$";

		if (!manager.getEmail().matches(emailRegex)) {
			throw new ManagerException(ValidationConstants.INVALID_EMAIL);
		}
		if (!manager.getPhone().matches(phoneRegex)) {
			throw new ManagerException(ValidationConstants.INVALID_PHONE);
		}
		return true;

	}

}
