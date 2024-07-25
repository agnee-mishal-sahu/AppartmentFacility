package com.appartment.facilities.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.appartment.facilities.dto.BookingDto;
import com.appartment.facilities.dto.CreateBookingResponseDto;
import com.appartment.facilities.exception.BookingException;

@Service
public interface BookingService {
	
	CreateBookingResponseDto createBooking(BookingDto bookingDto) throws BookingException;
	List<BookingDto> getAllBooking();
	String approveOrRejectBooking(int bookingId,boolean isApproved) throws BookingException;
	BookingDto getBookingById(int bookingId) throws BookingException;
	BookingDto cancelBooking(int bookingId) throws BookingException;
}
