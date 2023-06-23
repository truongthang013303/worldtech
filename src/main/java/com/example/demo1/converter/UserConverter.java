package com.example.demo1.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo1.dto.UserDTO;
import com.example.demo1.entity.RoleEntity;
import com.example.demo1.entity.UserEntity;
import com.example.demo1.repository.RoleRepository;

@Component
public class UserConverter {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	public UserDTO toDto(UserEntity entity) {
		UserDTO result = new UserDTO();
		result.setId(entity.getId());
		result.setUserName(entity.getUserName());
		result.setPassword(entity.getPassword());
		result.setFullName(entity.getFullName());
		result.setStatus(entity.getStatus());
		
		Collection<RoleEntity> listRoles = entity.getRoles();
		List<String> listStringRoles = new ArrayList<String>();
		for (RoleEntity roleEntity : listRoles) 
		{
			listStringRoles.add(roleEntity.getCode());
		}
		result.setRoleCode(listStringRoles);
		result.setRatings(entity.getRatings());

		result.setCreatedBy(entity.getUserName());
		result.setCreatedDate(entity.getCreatedDate());
		result.setModifiedBy(entity.getModifiedBy());
		result.setModifiedDate(entity.getModifiedDate());
		return result;
	}

	public UserEntity toEntity(UserDTO dto) {
		UserEntity result = new UserEntity();
		result.setUserName(dto.getUserName());
		result.setPassword(passwordEncoder.encode(dto.getPassword()));
		result.setFullName(dto.getFullName());
		result.setStatus(dto.getStatus());
		
		List<RoleEntity> listRoleEntity = new ArrayList<RoleEntity>();
		List<String> listRolesString = dto.getRoleCode();
		for (String string : listRolesString) 
		{
			listRoleEntity.add(roleRepository.findOneByCode(string));
		}
		result.setRoles(listRoleEntity);	
		return result;
	}

	
	  public UserEntity toEntity(UserEntity userEntity, UserDTO userDTO) 
		{
			userEntity.setUserName(userDTO.getUserName());
			userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			userEntity.setFullName(userDTO.getFullName());
			userEntity.setStatus(userDTO.getStatus());

			List<RoleEntity> listRoleEntity = new ArrayList<RoleEntity>();
			List<String> listStringRoles = userDTO.getRoleCode();
			for (String string : listStringRoles) {
				listRoleEntity.add(roleRepository.findOneByCode(string));
			}
			userEntity.setRoles(listRoleEntity);
			return userEntity;
		}
	 
}
