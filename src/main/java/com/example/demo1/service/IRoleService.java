package com.example.demo1.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.example.demo1.dto.RoleDTO;

public interface IRoleService 
{
	List<RoleDTO> findAll(Pageable pageable);
	List<RoleDTO> findAll();
	int getTotalItem();
	RoleDTO findById(long id);
	void delete(long[] ids);	
	RoleDTO saveOrUpdate(RoleDTO dto);
}
