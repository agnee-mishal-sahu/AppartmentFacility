package com.appartment.facilities.dto;

public class CreateManagerResponseDto {
	private ManagerDto managerDto;
	private String message;

	public ManagerDto getManagerDto() {
		return managerDto;
	}

	public void setManagerDto(ManagerDto managerDto) {
		this.managerDto = managerDto;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "CreateManagerResponseDto [managerDto=" + managerDto + ", message=" + message + "]";
	}

}
