package com.appartment.facilities.dto;

import java.util.Date;

public class BookingDto {
	private Integer id;
	private Date bookingDate;
	private Integer facilityId;
	private Integer residentId;
	private Date eventDate;
	private String status;

	public BookingDto() {

	}

	public BookingDto(Integer id, Date bookingDate, Integer facilityId, Integer residentId, Date eventDate,
			String status) {
		
		this.id = id;
		this.bookingDate = bookingDate;
		this.facilityId = facilityId;
		this.residentId = residentId;
		this.eventDate = eventDate;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Integer getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Integer facilityId) {
		this.facilityId = facilityId;
	}

	public Integer getResidentId() {
		return residentId;
	}

	public void setResidentId(Integer residentId) {
		this.residentId = residentId;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Booking [id=" + id + ", bookingDate=" + bookingDate + ", facilityId=" + facilityId + ", residentId="
				+ residentId + ", eventDate=" + eventDate + ", status=" + status + "]";
	}

}
