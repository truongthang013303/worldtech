package com.example.demo1.dto;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class UserDTO extends AbstractDTO<UserDTO>
{	
	@NotBlank
	@Length(min = 8, max = 32)
	private String userName;

	@NotBlank
	@Length(min = 8, max = 32)
	private String password;
	
	@NotBlank
	@Length(min = 8, max = 32)
	private String fullName;
	
	private Integer status;
	
	@NotEmpty
	private List<String> roleCode = new ArrayList<>();

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<String> getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(List<String> roleCode) {
		this.roleCode = roleCode;
	}
}
