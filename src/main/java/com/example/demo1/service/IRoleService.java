package com.example.demo1.service;

import java.util.List;

import com.example.demo1.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo1.dto.RoleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface IRoleService 
{
	List<RoleDTO> findAll(Pageable pageable);
	List<RoleDTO> findAll();
	Long getTotalItem();
	void delete(long[] ids);
	RoleDTO saveOrUpdate(RoleDTO dto);

	@Transactional
	ResponseEntity<?> deleteRoles(long[] ids);

	List<RoleDTO> findByNameContaining(String roleName);

	Page<RoleDTO> findByPage(Pageable pageable);

	Page<RoleDTO> convertPageEntityToPageDTO(Page<RoleEntity> entities);

	RoleDTO findById(long roleid);
}
