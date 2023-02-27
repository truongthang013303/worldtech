package com.example.demo1.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo1.converter.RoleConverter;
import com.example.demo1.dto.RoleDTO;
import com.example.demo1.entity.RoleEntity;
import com.example.demo1.repository.RoleRepository;
import com.example.demo1.service.IRoleService;
@Service
public class RoleService implements IRoleService 
{
	@Autowired
	private RoleConverter roleConverter;
	@Autowired
	private RoleRepository roleRepository;	
	@Override
	public List<RoleDTO> findAll(Pageable pageable) 
	{
		Page<RoleEntity> pageRoleEntity = roleRepository.findAll(pageable);
		List<RoleDTO> listRoleDTO = new ArrayList<RoleDTO>();
		for (RoleEntity roleEntity : pageRoleEntity) {
			listRoleDTO.add(roleConverter.toDto(roleEntity));
		}
		return listRoleDTO;
	}	
	@Override
	public List<RoleDTO> findAll() 
	{
		List<RoleEntity> listRoleEntity = roleRepository.findAll();
		List<RoleDTO> listRoleDTO = new ArrayList<RoleDTO>();
		for (RoleEntity roleEntity : listRoleEntity) {
			listRoleDTO.add(roleConverter.toDto(roleEntity));
		}
		return listRoleDTO;
	}
	
	@Override
	public RoleDTO findById(long id)
	{		
		return roleConverter.toDto(roleRepository.findOneById(id));
	}
	
	@Override
	public int getTotalItem() 
	{	
		return (int)roleRepository.count();
	}

	@Override
	@Transactional
	public RoleDTO saveOrUpdate(RoleDTO dto)
	{
		RoleEntity roleEntity = new RoleEntity();		
		if(dto.getId()!=null) 
		{
			RoleEntity oldUser = roleRepository.findOneById(dto.getId());
			roleEntity = roleConverter.toEntity(oldUser,dto);	
		}
		else if(roleRepository.existsByCode(dto.getCode())==false) //roleRepository.findExistByCode(dto.getCode())==0
		{
			roleEntity = roleConverter.toEntity(dto);
			System.out.println("Chua ton tai");
		}
		else
		{
			System.out.println("Da ton tai");
			return null;
		}
		RoleEntity roleEntitySaved = roleRepository.save(roleEntity);
		return roleConverter.toDto(roleEntitySaved);
	}
	
	@Override
	@Transactional
	public void delete(long[] ids) 
	{
		for (long id: ids) 
		{
			roleRepository.deleteById(id);
		}	
	}
}
