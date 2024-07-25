package com.appartment.facilities.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appartment.facilities.constants.MessageConstants;
import com.appartment.facilities.constants.ValidationConstants;
import com.appartment.facilities.dto.BookingDto;
import com.appartment.facilities.dto.CreateBookingResponseDto;
import com.appartment.facilities.entity.Booking;
import com.appartment.facilities.entity.Facility;
import com.appartment.facilities.exception.BookingException;
import com.appartment.facilities.repository.BookingRepository;
import com.appartment.facilities.repository.FacilityRepository;
import com.appartment.facilities.repository.ResidentRepository;
import com.appartment.facilities.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	ResidentRepository residentRepository;
	
	@Autowired
	FacilityRepository facilityRepository;

	@Override
	public CreateBookingResponseDto createBooking(BookingDto bookingDto) throws BookingException {

		Booking booking = mapToBooking(bookingDto);
		CreateBookingResponseDto createBookingResponseDto = new CreateBookingResponseDto();

		if (booking != null) {
			
			if(validateBooking(booking)) {
				
				booking.setStatus("Pending");
				Booking bookingEntity = bookingRepository.save(booking);
				Facility facility= facilityRepository.findById(bookingEntity.getFacilityId()).get();
				facility.setStatus("Occupied");
				facilityRepository.save(facility);
				
				bookingDto.setStatus(booking.getStatus());
				createBookingResponseDto.setBookingDto(bookingDto);
				createBookingResponseDto.getBookingDto().setId(bookingEntity.getId());
				createBookingResponseDto.setMessage(MessageConstants.BOOKING_STATUS_SUCCESS);
			}
			
		} else {
			createBookingResponseDto.setMessage(MessageConstants.BOOKING_STATUS_FAILED);
		}

		return createBookingResponseDto;
	}

	@Override
	public List<BookingDto> getAllBooking() {
		return bookingRepository.findAll().stream().map(booking -> mapToBookingDto(booking))
				.collect(Collectors.toList());
	}

	@Override
	public String approveOrRejectBooking(int bookingId, boolean isApproved) throws BookingException {

		Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
		String bookingMessage = null;

		if (!bookingOptional.isPresent()) {
			throw new BookingException(MessageConstants.BOOKING_NOT_FOUND);
		}
		Booking booking = bookingOptional.get();
		if (booking.getStatus().equalsIgnoreCase(MessageConstants.BOOKING_CANCELLED)) {
			throw new BookingException("Booking is cancelled");
		}
		if (isApproved) {
			booking.setStatus(MessageConstants.BOOKING_ACCEPTED);
			bookingMessage = "accepted";
		} else {
			booking.setStatus(MessageConstants.BOOKING_DECLINED);
			bookingMessage = "declined";
		}
		bookingRepository.save(booking);

		return "booking with id:" + bookingId + " is " + bookingMessage;
	}

	@Override
	public BookingDto getBookingById(int bookingId) throws BookingException {
		Optional<Booking> booking = bookingRepository.findById(bookingId);
		if (!booking.isPresent()) {
			throw new BookingException(MessageConstants.BOOKING_NOT_FOUND);
		}
		return mapToBookingDto(booking.get());
	}

	@Override
	public BookingDto cancelBooking(int bookingId) throws BookingException {
		Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
		if (!bookingOptional.isPresent()) {
			throw new BookingException(MessageConstants.BOOKING_NOT_FOUND);
		}
		Booking booking = bookingOptional.get();
		booking.setStatus(MessageConstants.BOOKING_CANCELLED);
		bookingRepository.save(booking);
		return mapToBookingDto(booking);
	}

	private Booking mapToBooking(BookingDto bookingDto) {

		Booking booking = new Booking();

		booking.setBookingDate(bookingDto.getBookingDate());
		booking.setEventDate(bookingDto.getEventDate());
		booking.setFacilityId(bookingDto.getFacilityId());
		booking.setResidentId(bookingDto.getResidentId());
		booking.setStatus(bookingDto.getStatus());

		return booking;
	}

	private BookingDto mapToBookingDto(Booking booking) {
		BookingDto bookingDto = new BookingDto();
		if (booking != null) {
			bookingDto.setId(booking.getId());
			bookingDto.setBookingDate(booking.getBookingDate());
			bookingDto.setEventDate(booking.getEventDate());
			bookingDto.setFacilityId(booking.getFacilityId());
			bookingDto.setResidentId(booking.getResidentId());
			bookingDto.setStatus(booking.getStatus());
		}
		return bookingDto;
	}
	
	private boolean validateBooking(Booking booking) throws BookingException {
		
		int facilityId=booking.getFacilityId();
		int residentId=booking.getResidentId();
		
		if(!facilityRepository.findById(facilityId).isPresent()) {
			throw new BookingException(ValidationConstants.INVALID_FACILITY_FOR_BOOKING);
		}
		if(!residentRepository.findById(residentId).isPresent()) {
			throw new BookingException(ValidationConstants.INVALID_RESIDENT_FOR_BOOKING);
		}
		if(facilityRepository.findById(facilityId).get().getStatus().equalsIgnoreCase("Occupied")) {
			throw new BookingException(ValidationConstants.FACILITY_ALREADY_OCCUPIED);
		}
		
		return true;
	}

}
