package com.appartment.facilities.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.appartment.facilities.constants.MessageConstants;
import com.appartment.facilities.constants.ValidationConstants;
import com.appartment.facilities.dto.CreateResidentResponseDto;
import com.appartment.facilities.dto.ResidentDto;
import com.appartment.facilities.entity.Resident;
import com.appartment.facilities.exception.ResidentException;
import com.appartment.facilities.repository.ResidentRepository;
import com.appartment.facilities.service.ResidentService;

@Service
public class ResisdentServiceImpl implements ResidentService {

	@Autowired
	ResidentRepository residentRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public CreateResidentResponseDto createResident(ResidentDto residentDto) throws ResidentException {

		Resident resident = mapToResident(residentDto);
		CreateResidentResponseDto createResidentResponseDto = new CreateResidentResponseDto();
		
		if (resident != null) {
			if(validateResident(resident)) {
				resident.setStatus(ValidationConstants.USER_APPROVAL_PENDING);
				Resident residentEntity = residentRepository.save(resident);
				createResidentResponseDto.setResidentDto(residentDto);
				createResidentResponseDto.getResidentDto().setPassword(null);
				createResidentResponseDto.getResidentDto().setStatus(residentEntity.getStatus());
				createResidentResponseDto.getResidentDto().setId(residentEntity.getId());
				createResidentResponseDto.setMessage(MessageConstants.RESIDENT_STATUS_SUCCESS);
			}	
		} else {
			createResidentResponseDto.setMessage(MessageConstants.RESIDENT_STATUS_FAILED);
		}

		return createResidentResponseDto;

	}

	@Override
	public String deleteResident(int residentId) throws ResidentException {

		Optional<Resident> residentOptional = residentRepository.findById(residentId);
		if (!residentOptional.isPresent()) {
			throw new ResidentException(MessageConstants.RESIDENT_NOT_FOUND);
		}

		residentRepository.deleteById(residentId);

		return "resident with id:" + residentId + " is deleted successfully.";
	}

	@Override
	public String UpdateResident(ResidentDto residentDto, int residentId) throws ResidentException {

		Optional<Resident> residentOptional = residentRepository.findById(residentId);
		if (!residentOptional.isPresent()) {
			throw new ResidentException(MessageConstants.RESIDENT_NOT_FOUND);
		}
		Resident resident = residentOptional.get();
		resident.setUserName(residentDto.getUserName());
		resident.setPassword(passwordEncoder.encode(residentDto.getPassword()));
		resident.setRole(residentDto.getRole());
		resident.setStatus(residentDto.getStatus());
		resident.setName(residentDto.getName());
		resident.setFlatNo(residentDto.getFlatNo());
		resident.setFlatType(residentDto.getFlatType());
		resident.setPhone(residentDto.getPhone());
		resident.setEmail(residentDto.getEmail());
		resident.setPicture(residentDto.getPicture());

		residentRepository.save(resident);

		return "resident with id:" + residentId + " is updated successfully.";
	}

	@Override
	public ResidentDto getResidentById(int residentId) throws ResidentException {
		Optional<Resident> residentOptional = residentRepository.findById(residentId);
		if (!residentOptional.isPresent()) {
			throw new ResidentException(MessageConstants.RESIDENT_NOT_FOUND);
		}

		return mapToResidentDto(residentRepository.findById(residentId).get());
	}

	@Override
	public List<ResidentDto> getAllResident() {
		return residentRepository.findAll().stream().map(resident -> mapToResidentDto(resident))
				.collect(Collectors.toList());
	}

	private Resident mapToResident(ResidentDto residentDto) {
		if (residentDto == null || residentDto.getUserName().isBlank() || residentDto.getPassword().isBlank()) {
			return null;
		}
		Resident resident = new Resident();
		resident.setId(residentDto.getId());
		resident.setUserName(residentDto.getUserName());
		resident.setPassword(passwordEncoder.encode(residentDto.getPassword()));
		resident.setRole(residentDto.getRole());
		resident.setStatus(residentDto.getStatus());
		resident.setName(residentDto.getName());
		resident.setFlatNo(residentDto.getFlatNo());
		resident.setFlatType(residentDto.getFlatType());
		resident.setPhone(residentDto.getPhone());
		resident.setEmail(residentDto.getEmail());
		resident.setPicture(residentDto.getPicture());
		return resident;
	}

	private ResidentDto mapToResidentDto(Resident resident) {
		ResidentDto residentDto = new ResidentDto();
		if (resident != null) {
			residentDto.setId(resident.getId());
			residentDto.setUserName(resident.getUserName());
			residentDto.setPassword(null); // Setting password to null for security reasons
			residentDto.setRole(resident.getRole());
			residentDto.setStatus(resident.getStatus());
			residentDto.setName(resident.getName());
			residentDto.setFlatNo(resident.getFlatNo());
			residentDto.setFlatType(resident.getFlatType());
			residentDto.setPhone(resident.getPhone());
			residentDto.setEmail(resident.getEmail());
			residentDto.setPicture(resident.getPicture());
		}
		return residentDto;
	}

	private boolean validateResident(Resident resident) throws ResidentException {
		String emailRegex = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
		String phoneRegex = "^\\d{10}$";

		if (!resident.getEmail().matches(emailRegex)) {
			throw new ResidentException(ValidationConstants.INVALID_EMAIL);
		}
		if (!resident.getPhone().matches(phoneRegex)) {
			throw new ResidentException(ValidationConstants.INVALID_PHONE);
		}
		if(residentRepository.findByUserName(resident.getUserName()).isPresent()) {
			throw new ResidentException(ValidationConstants.USERNAME_EXISTS);
		}
		return true;
	}

}
