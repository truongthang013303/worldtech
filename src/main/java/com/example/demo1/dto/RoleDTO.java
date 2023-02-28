package com.example.demo1.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoleDTO extends AbstractDTO<RoleDTO>
{
	@NotBlank
	private String name;
	
	@NotBlank
	private String code;

	@NotEmpty
	private Collection<String> privilegeCode = new ArrayList<>();

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

	public Collection<String> getPrivilegeCode() {
		return privilegeCode;
	}

	public void setPrivilegeCode(Collection<String> privilegeCode) {
		this.privilegeCode = privilegeCode;
	}
}
