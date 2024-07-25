package com.appartment.facilities.dto;

public class ResidentDto extends UserDto{
	private String name;
    private String flatNo;
    private String flatType;
    private String phone;
    private String email;
    private String picture;
    
    
    public ResidentDto(String userName, String password, String role, String status, String name, String flatNo, String flatType, String phone, String email, String picture) {
        super(userName, password, role, status);
        this.name = name;
        this.flatNo = flatNo;
        this.flatType = flatType;
        this.phone = phone;
        this.email = email;
        this.picture = picture;
    }
	
	public ResidentDto() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public String getFlatType() {
		return flatType;
	}

	public void setFlatType(String flatType) {
		this.flatType = flatType;
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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Override
	public String toString() {
		return "Manager [name=" + name + ", flatNo=" + flatNo + ", flatType=" + flatType + ", phone=" + phone
				+ ", email=" + email + ", picture=" + picture + "]";
	}
    
    
}
