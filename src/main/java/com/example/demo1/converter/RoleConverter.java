package com.example.demo1.converter;

import org.springframework.stereotype.Component;

import com.example.demo1.dto.RoleDTO;
import com.example.demo1.entity.RoleEntity;

@Component
public class RoleConverter {
	
	public RoleDTO toDto(RoleEntity entity) {
		RoleDTO result = new RoleDTO();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setCode(entity.getCode());
		return result;
	}
	
	public RoleEntity toEntity(RoleDTO dto) {
		RoleEntity result = new RoleEntity();
		result.setName(dto.getName());
		result.setCode(dto.getCode());
		return result;
	}

	public RoleEntity toEntity(RoleEntity roleEntity, RoleDTO roleDTO) 
	{
		roleEntity.setName(roleDTO.getName());
		roleEntity.setCode(roleDTO.getCode());
		return roleEntity;
	} 
}
