package com.example.demo1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo1.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long>
{	
	RoleEntity findOneByCode(String code);	
	RoleEntity findOneById(long id);
	boolean existsByCode(String code);
}
