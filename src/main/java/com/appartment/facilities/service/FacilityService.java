package com.appartment.facilities.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.appartment.facilities.dto.CreateFacilityResponseDto;
import com.appartment.facilities.dto.FacilityDto;
import com.appartment.facilities.exception.FacilityException;

@Service
public interface FacilityService {

	CreateFacilityResponseDto createFacility(FacilityDto facilityDto) throws FacilityException;
	String updateFacility(FacilityDto facilityDto,int id) throws FacilityException;
	String deleteFacility(int id) throws FacilityException;
	List<FacilityDto> getAllFacility();
	FacilityDto getFacilityById(int id) throws FacilityException;
	String changeStatusToAvailable(int id) throws FacilityException;
}
