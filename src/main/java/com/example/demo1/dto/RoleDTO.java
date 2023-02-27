package com.example.demo1.dto;

import javax.validation.constraints.NotBlank;

public class RoleDTO extends AbstractDTO<RoleDTO>
{
	@NotBlank
	private String name;
	
	@NotBlank
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
