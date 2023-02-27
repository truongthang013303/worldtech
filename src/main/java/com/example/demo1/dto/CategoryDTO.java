package com.example.demo1.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class CategoryDTO extends AbstractDTO<CategoryDTO> {
	
	@NotBlank
	@Length(max = 15)
	private String name;
	
	@NotBlank
	@Length(max = 15)
	private String code;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
