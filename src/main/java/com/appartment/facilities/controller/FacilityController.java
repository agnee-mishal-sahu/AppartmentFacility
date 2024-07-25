package com.appartment.facilities.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appartment.facilities.dto.CreateFacilityResponseDto;
import com.appartment.facilities.dto.FacilityDto;
import com.appartment.facilities.exception.FacilityException;
import com.appartment.facilities.service.FacilityService;

@RestController
@RequestMapping("/v1/facility")
public class FacilityController {

    @Autowired
    private FacilityService facilityService;

    @PostMapping
    public ResponseEntity<?> createFacility(@RequestBody FacilityDto facilityDto) throws FacilityException {
        CreateFacilityResponseDto response = facilityService.createFacility(facilityDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFacility(@PathVariable int id, @RequestBody FacilityDto facilityDto) throws FacilityException {
        String message = facilityService.updateFacility(facilityDto, id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFacility(@PathVariable int id) throws FacilityException {
        String message = facilityService.deleteFacility(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAllFacilities() {
        List<FacilityDto> facilities = facilityService.getAllFacility();
        return new ResponseEntity<>(facilities, HttpStatus.OK);
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<?> getFacilityById(@PathVariable int id) throws FacilityException {
        FacilityDto facilityDto = facilityService.getFacilityById(id);
        return new ResponseEntity<>(facilityDto, HttpStatus.OK);
    }
    
    @PutMapping("/status/{id}")
    public ResponseEntity<?> changeStatusToAvailable(@PathVariable int id) throws FacilityException{
    	String message=facilityService.changeStatusToAvailable(id);
    	return new ResponseEntity<>(message,HttpStatus.OK);
    }
}
