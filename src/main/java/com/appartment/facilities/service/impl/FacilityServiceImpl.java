package com.appartment.facilities.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appartment.facilities.constants.MessageConstants;
import com.appartment.facilities.constants.ValidationConstants;
import com.appartment.facilities.dto.CreateFacilityResponseDto;
import com.appartment.facilities.dto.FacilityDto;
import com.appartment.facilities.entity.Facility;
import com.appartment.facilities.exception.FacilityException;
import com.appartment.facilities.repository.FacilityRepository;
import com.appartment.facilities.service.FacilityService;

@Service
public class FacilityServiceImpl implements FacilityService {

	@Autowired
	FacilityRepository facilityRepository;

	@Override
	public CreateFacilityResponseDto createFacility(FacilityDto facilityDto) throws FacilityException {
		Facility facility = mapToFacility(facilityDto);
		CreateFacilityResponseDto createFacilityResponseDto = new CreateFacilityResponseDto();

		if (facility != null) {
			if(validateFacility(facility)) {
				facility.setStatus("Available");
				Facility facilityEntity = facilityRepository.save(facility);
				
				facilityDto.setStatus(facility.getStatus());
				createFacilityResponseDto.setFacilityDto(facilityDto);
				createFacilityResponseDto.getFacilityDto().setId(facilityEntity.getId());
				createFacilityResponseDto.setMessage(MessageConstants.FACILITY_STATUS_SUCCESS);
			}
		} else {
			createFacilityResponseDto.setMessage(MessageConstants.FACILITY_STATUS_FAILED);
		}

		return createFacilityResponseDto;

	}

	@Override
	public String updateFacility(FacilityDto facilityDto, int id) throws FacilityException {
		Optional<Facility> facilityOptional = facilityRepository.findById(id);
		if (!facilityOptional.isPresent()) {
			throw new FacilityException(MessageConstants.FACILITY_NOT_FOUND);
		}
		Facility facility = facilityOptional.get();
		facility.setDescription(facilityDto.getDescription());
		facility.setName(facilityDto.getName());
		facility.setPicture(facilityDto.getPicture());
		facilityRepository.save(facility);
		return "Facility with id:" + id + " is updated successfully.";
	}

	@Override
	public String deleteFacility(int id) throws FacilityException {
		Optional<Facility> facilityOptional = facilityRepository.findById(id);
		if (!facilityOptional.isPresent()) {
			throw new FacilityException(MessageConstants.FACILITY_NOT_FOUND);
		}
		facilityRepository.deleteById(id);
		return "Facility with id:" + id + " is deleted.";
	}

	@Override
	public List<FacilityDto> getAllFacility() {

		return facilityRepository.findAll().stream().map(facility -> mapToFacilityDto(facility))
				.collect(Collectors.toList());
	}

	@Override
	public FacilityDto getFacilityById(int id) throws FacilityException {
		Optional<Facility> facilityOptional = facilityRepository.findById(id);
		if (!facilityOptional.isPresent()) {
			throw new FacilityException(MessageConstants.FACILITY_NOT_FOUND);
		}
		return mapToFacilityDto(facilityOptional.get());
	}
	
	@Override
	public String changeStatusToAvailable(int id) throws FacilityException {
		Optional<Facility> facilityOptional = facilityRepository.findById(id);
		if (!facilityOptional.isPresent()) {
			throw new FacilityException(MessageConstants.FACILITY_NOT_FOUND);
		}
		Facility facility= facilityOptional.get();
		if(facility.getStatus().equalsIgnoreCase("Available")) {
			throw new FacilityException(MessageConstants.FACILITY_ALREADY_AVAILABLE);
		}
		facility.setStatus("Available");
		facilityRepository.save(facility);
		return "Facility with id:"+id+" is available now.";
	}

	private Facility mapToFacility(FacilityDto facilityDto) {
		Facility facility = new Facility();
		facility.setId(facilityDto.getId());
		facility.setName(facilityDto.getName());
		facility.setStatus(facilityDto.getStatus());
		facility.setDescription(facilityDto.getDescription());
		facility.setPicture(facilityDto.getPicture());

		return facility;
	}

	private FacilityDto mapToFacilityDto(Facility facility) {
		FacilityDto facilityDto = new FacilityDto();
		if (facility != null) {
			facilityDto.setId(facility.getId());
			facilityDto.setName(facility.getName());
			facilityDto.setStatus(facility.getStatus());
			facilityDto.setDescription(facility.getDescription());
			facilityDto.setPicture(facility.getPicture());
		}
		return facilityDto;
	}
	
	private boolean validateFacility(Facility facility) throws FacilityException {
		if(facility.getName().isBlank()) {
			throw new FacilityException(ValidationConstants.INVALID_FACILITY_NAME);
		}
		if(facility.getDescription().isBlank()) {
			throw new FacilityException(ValidationConstants.INVALID_FACILITY_DESCRIPTION);
		}
		return true;
	}


}
