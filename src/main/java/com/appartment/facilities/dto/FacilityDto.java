package com.appartment.facilities.dto;

public class FacilityDto {
	private Integer id;
	private String name;
	private String status;
	private String description;
	private String picture;

	public FacilityDto() {

	}

	public FacilityDto(Integer id, String name, String status, String description, String picture) {

		this.id = id;
		this.name = name;
		this.status = status;
		this.description = description;
		this.picture = picture;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Override
	public String toString() {
		return "Facility [id=" + id + ", name=" + name + ", status=" + status + ", description=" + description
				+ ", picture=" + picture + "]";
	}
}
