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

import com.appartment.facilities.dto.BookingDto;
import com.appartment.facilities.exception.BookingException;
import com.appartment.facilities.service.BookingService;

@RestController
@RequestMapping("/v1/booking")
public class BookingController {

	@Autowired
	BookingService bookingService;

	@PostMapping("/create")
	public ResponseEntity<?> createBooking(@RequestBody BookingDto bookingDto) throws BookingException {
		return new ResponseEntity<>(bookingService.createBooking(bookingDto), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<?> getAllBookings() {
		return new ResponseEntity<>(bookingService.getAllBooking(), HttpStatus.OK);
	}

	@GetMapping("/byid/{id}")
	public ResponseEntity<?> getBookingById(@PathVariable int id) throws BookingException {
		return new ResponseEntity<>(bookingService.getBookingById(id), HttpStatus.OK);

	}

	@PutMapping("/{id}/approveOrReject/{isApproved}")
	public ResponseEntity<?> approveOrRejectBooking(@PathVariable int id, @PathVariable boolean isApproved)
			throws BookingException {

		return new ResponseEntity<>(bookingService.approveOrRejectBooking(id, isApproved), HttpStatus.OK);

	}

	@PutMapping("/cancel/{id}")
	public ResponseEntity<?> cancelBooking(@PathVariable int id) throws BookingException {
	
			return new ResponseEntity<>(bookingService.cancelBooking(id), HttpStatus.OK);
		
	}

}
