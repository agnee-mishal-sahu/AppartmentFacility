package com.appartment.facilities.dto;

public class CreateBookingResponseDto {
	
	private BookingDto bookingDto;
	private String message;
	
	
	public BookingDto getBookingDto() {
		return bookingDto;
	}
	public void setBookingDto(BookingDto bookingDto) {
		this.bookingDto = bookingDto;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "CreateBookingResponseDto [bookingDto=" + bookingDto + ", message=" + message + "]";
	}
	
	
	
}
