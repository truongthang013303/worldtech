package com.example.demo1.converter;

import com.example.demo1.entity.PrivilegeEntity;
import com.example.demo1.service.IPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo1.dto.RoleDTO;
import com.example.demo1.entity.RoleEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

@Component
public class RoleConverter {
	@Autowired
	IPrivilegeService privilegeService;
	public RoleDTO toDto(RoleEntity entity) {
		RoleDTO result = new RoleDTO();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setCode(entity.getCode());
		Collection<String> privileges=new ArrayList<String>();
		entity.getPrivileges().forEach(s->privileges.add(s.getCode()));
		result.setPrivilegeCode(privileges);
		return result;
	}
	
	public RoleEntity toEntity(RoleDTO dto) {
		RoleEntity result = new RoleEntity();
		result.setName(dto.getName());
		result.setCode(dto.getCode());
		Collection<PrivilegeEntity> privileges=new LinkedHashSet<PrivilegeEntity>();
		if(dto.getPrivilegeCode().isEmpty()!=true)
		{
			for(String privilegeCodes:dto.getPrivilegeCode())
			{
				PrivilegeEntity privi = privilegeService.findOneByCode(privilegeCodes);
				privileges.add(privi);
			}
		}
		result.setPrivileges(privileges);
		return result;
	}

	public RoleEntity toEntity(RoleEntity oldRoleEntity, RoleDTO dto)
	{
		oldRoleEntity.setName(dto.getName());
		oldRoleEntity.setCode(dto.getCode());

		Collection<PrivilegeEntity> privileges=new LinkedHashSet<PrivilegeEntity>();
		if(dto.getPrivilegeCode().isEmpty()!=true)
		{
			for(String privilegeCodes:dto.getPrivilegeCode())
			{
				PrivilegeEntity privi = privilegeService.findOneByCode(privilegeCodes);
				privileges.add(privi);
			}
		}
		oldRoleEntity.setPrivileges(privileges);
		return oldRoleEntity;
	} 
}
