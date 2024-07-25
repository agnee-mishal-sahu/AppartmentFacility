package com.appartment.facilities.dto;

public class CreateResidentResponseDto {

	private ResidentDto residentDto;
	private String message;

	public ResidentDto getResidentDto() {
		return residentDto;
	}

	public void setResidentDto(ResidentDto residentDto) {
		this.residentDto = residentDto;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "CreateResidentResponseDto [residentDto=" + residentDto + ", message=" + message + "]";
	}

}
