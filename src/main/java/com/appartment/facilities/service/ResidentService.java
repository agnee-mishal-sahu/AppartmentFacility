package com.appartment.facilities.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.appartment.facilities.dto.CreateResidentResponseDto;
import com.appartment.facilities.dto.ResidentDto;
import com.appartment.facilities.exception.ResidentException;

@Service
public interface ResidentService {
	CreateResidentResponseDto createResident(ResidentDto residentDto) throws ResidentException;
	String deleteResident(int residentId) throws ResidentException;
	String UpdateResident(ResidentDto residentDto,int residentId) throws ResidentException;
	ResidentDto getResidentById(int residentId) throws ResidentException;
	List<ResidentDto> getAllResident();
}
