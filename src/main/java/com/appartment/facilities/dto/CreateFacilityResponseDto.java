package com.appartment.facilities.dto;

public class CreateFacilityResponseDto {
	private FacilityDto facilityDto;
	private String message;

	public FacilityDto getFacilityDto() {
		return facilityDto;
	}

	public void setFacilityDto(FacilityDto facilityDto) {
		this.facilityDto = facilityDto;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "CreateFacilityResponseDto [facilityDto=" + facilityDto + ", message=" + message + "]";
	}

}
