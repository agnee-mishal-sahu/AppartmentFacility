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

import com.appartment.facilities.dto.CreateResidentResponseDto;
import com.appartment.facilities.dto.ResidentDto;
import com.appartment.facilities.exception.ResidentException;
import com.appartment.facilities.service.ResidentService;

@RestController
@RequestMapping("/v1/resident")
public class ResidentController {

    @Autowired
    ResidentService residentService;

    @PostMapping("/register")
    public ResponseEntity<?> createResident(@RequestBody ResidentDto residentDto) throws ResidentException {
        CreateResidentResponseDto response = residentService.createResident(residentDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResident(@PathVariable int id) throws ResidentException {
        String response = residentService.deleteResident(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateResident(@RequestBody ResidentDto residentDto, @PathVariable int id) throws ResidentException {
        String response = residentService.UpdateResident(residentDto, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getResidentById(@PathVariable int id) throws ResidentException {
        ResidentDto residentDto = residentService.getResidentById(id);
        return new ResponseEntity<>(residentDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllResidents() {
        List<ResidentDto> residents = residentService.getAllResident();
        return new ResponseEntity<>(residents, HttpStatus.OK);
    }
}
