package com.appartment.facilities.dto;

public class ManagerDto extends UserDto {
	private String name;
	private String phone;
	private String email;
	
	 public ManagerDto() {}
	 
	  public ManagerDto(String userName, String password, String role, String status, String name, String phone, String email) {
	        super(userName, password, role, status);
	        this.name = name;
	        this.phone = phone;
	        this.email = email;
	    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Manager [name=" + name + ", phone=" + phone + ", email=" + email + "]";
	}
	
}
