package com.example.demo1.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

public class CategoryDTO extends AbstractDTO<CategoryDTO> {
	
	@NotBlank
	@Size(min = 1, max = 200, message
			= "name must be between 1 and 200 characters")
	private String name;
	
	@NotBlank
	@Size(min = 1, max = 200, message
			= "code must be between 1 and 200 characters")
	private String code;

	private Integer postCount;
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

	public Integer getPostCount() {
		return postCount;
	}

	public void setPostCount(Integer postCount) {
		this.postCount = postCount;
	}
}
