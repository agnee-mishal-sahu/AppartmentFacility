package com.appartment.facilities.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appartment.facilities.dto.CreateManagerResponseDto;
import com.appartment.facilities.dto.ManagerDto;
import com.appartment.facilities.exception.ManagerException;
import com.appartment.facilities.exception.ResidentException;
import com.appartment.facilities.service.ManagerService;

@RestController
@RequestMapping("/v1/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @PostMapping("/register")
    public ResponseEntity<?> createManager(@RequestBody ManagerDto managerDto) throws ManagerException {
        CreateManagerResponseDto response = managerService.createManager(managerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateManager(@PathVariable int id, @RequestBody ManagerDto managerDto) throws ManagerException {
        String message = managerService.updateManager(managerDto, id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getManager() {
        ManagerDto managerDto = managerService.getManager();
        return new ResponseEntity<>(managerDto, HttpStatus.OK);
    }
    @PutMapping("/approve-signup/{username}")
    public ResponseEntity<?> approveSignUp(@PathVariable String username) throws ResidentException{
    	String message= managerService.approveSignUp(username);
    	return new ResponseEntity<>(message,HttpStatus.OK);
    }
}